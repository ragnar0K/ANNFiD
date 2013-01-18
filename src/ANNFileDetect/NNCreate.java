/*
 * Copyright 2013 ragnar0k@fabytes.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ANNFileDetect;

/**
 *
 * @author ragnar0k@fabytes.com
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class NNCreate {

    BasicNetwork nn;
    String nname;
    String trainLocation;
    String toSave;
    SQLiteLib sql;
    String desc;
    ArrayList epochs = null;
    FileOperations fo;

    NNCreate(String networkName, String GoodFilesLocation, String nndir, SQLiteLib sqlite, double threshold) {
        sql = sqlite;
        nname = networkName;
        toSave = nndir;
        trainLocation = GoodFilesLocation;
        fo = new FileOperations();
        epochs = new ArrayList();
        String netFile = sql.GetNetworkFile(networkName);
        String FPnetFile = sql.GetNetworkFPFile(networkName);
        nn = fo.openNetwork(nndir + "/" + netFile);
        BasicNetwork nnFP = fo.openNetwork(nndir + "/" + FPnetFile);
        String[] toTrain = fo.getFiles(trainLocation);
        if (!fo.createDirOrRemoveContents("tempTrainingFiles")) {
            JOptionPane.showMessageDialog(null, "Error creating temp directory.\nAre you sure this directory is writable?\n(the one you are launching manniac from)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < toTrain.length; i++) {
            try {
            TrainNetwork(trainLocation + "/" + toTrain[i], threshold);
            }
            catch (Exception exc ) {
                System.out.println("Found exception:" + exc.toString());
            }
            int percent = i * 100 / toTrain.length;
            //wb.UpdateBar(percent);
        }
        fo.saveNetwork(toSave + "/" + netFile, nn);
        EncogTestClass ectest = new EncogTestClass();
        for (int i = 0; i < toTrain.length; i++) {
            try {
            ectest.runNet(trainLocation + "/" + toTrain[i], nn, false, 0);
            }
            catch (Exception exc) {
                System.out.println(exc.toString());
            }
        }
        String[] fingerPrints = fo.getFiles("tempTrainingFiles");
        for (String FPFile : fingerPrints) {
            System.out.println("Training FP NN from " + FPFile);
            trainFPNet(nnFP, readFPfile("tempTrainingFiles/" + FPFile), threshold);
        }
        fo.saveNetwork(toSave + "/" + FPnetFile, nnFP);
        
        
        //double[] data = readFPfile
        //trainFPNet(nnFP, double[] values, double desiredOut);
        
        
    }

    NNCreate(int inputNeurons, String networkName, String GoodFilesLocation, String saveDir, String description, SQLiteLib ss) {
        sql = ss;
        nn = new BasicNetwork();
        nn.addLayer(new BasicLayer(new ActivationSigmoid(), true, inputNeurons));
        nn.addLayer(new BasicLayer(new ActivationSigmoid(), true, (inputNeurons * 2)));
        nn.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        nn.getStructure().finalizeStructure();
        nn.reset();
        desc = description;
        nname = networkName;
        toSave = saveDir;
        trainLocation = GoodFilesLocation;
        fo = new FileOperations();
        epochs = new ArrayList();


    }

    public void StartTraining() throws IOException {
        if (!fo.createDirOrRemoveContents("tempTrainingFiles")) {
            JOptionPane.showMessageDialog(null, "Error creating temp directory.\nAre you sure this directory is writable?\n(the one you are launching manniac from)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] toTrain = fo.getFiles(trainLocation);
        /*
         * Runnable runna = new WaitBar(); Thread thread = new Thread(runna);
         * thread.start();
         *
         */
        /*
         * WaitBar wb = WaitInit(); wb.setVisible(true);
         */
        for (int i = 0; i < toTrain.length; i++) {
            TrainNetwork(trainLocation + "/" + toTrain[i]);
            int percent = i * 100 / toTrain.length;
            //wb.UpdateBar(percent);
        }
        //wb.dispose();
        fo.saveNetwork(toSave + "/" + nname + ".eg", nn);
        System.out.println("\n\nTraining finished - network saved on " + toSave + "/" + nname + ".eg");
        System.out.println("Creating translator - Building fingerprint files");

        EncogTestClass ectest = new EncogTestClass();
        for (int i = 0; i < toTrain.length; i++) {
            ectest.runNet(trainLocation + "/" + toTrain[i], nn, false, 0);
        }

        System.out.println("\n\nFingerprints created. Creating translation network");
        BasicNetwork fpnet = createFingerPrintNN("FPNN" + nname);
        String[] fingerPrints = fo.getFiles("tempTrainingFiles");
        for (String FPFile : fingerPrints) {
            System.out.println("Training FP NN from " + FPFile);
            trainFPNet(fpnet, readFPfile("tempTrainingFiles/" + FPFile), 0.2);
        }
        fo.saveNetwork(toSave + "/" + "FPNN" + nname + ".eg", fpnet);
        System.out.println("FP network saved on " + toSave + "/" + "FPNN" + nname + ".eg");
        sql.insertNN(nname, nname + ".eg", "FPNN" + nname + ".eg", desc);
        /*
         * GraphingClass gc = new GraphingClass(); Object[] arral = new
         * Object[epochs.size()]; arral = epochs.toArray(arral); Integer[] farr
         * = Arrays.copyOf(arral, arral.length, Integer[].class);
         * gc.drawchartFromInt(farr);
         *
         */
    }

    /*
     * private WaitBar WaitInit() { return new WaitBar(); }
     */
    private void TrainNetwork(String fileWithPath) throws IOException {
        TrainNetwork(new double[]{0.2}, 0.001, fileWithPath);
    }
    
    private void TrainNetwork(String fileWithPath, double threshold) throws IOException {
        TrainNetwork(new double[]{threshold}, 0.001, fileWithPath);
    }

    public void TrainNetwork(double[] Output, double Threshold, String fileWithPath) throws IOException {
        int inputNeurons = nn.getInputCount();
        byte[] file = fo.fileToByte(fileWithPath);
        double[][] fileMatrix = fo.DoubleMatrix(file, inputNeurons, 0);
        double[][] output = new double[fileMatrix.length][];
        FileWriter fstream = new FileWriter("CurrentIteration");
        BufferedWriter out = new BufferedWriter(fstream);
        for (int i = 0; i < output.length; i++) {
            output[i] = Output;
        }
        MLDataSet trainingSet = new BasicMLDataSet(fileMatrix, output);
        MLTrain train = new ResilientPropagation(nn, trainingSet);
        int epoch = 1;
        double lasterr = 0;
        double firstOut = 0;
        do {
            train.iteration();
            //System.out.println("Training in process. Error: " + train.getError());
            lasterr = train.getError();
            if (epoch == 1) {
                firstOut = lasterr;
            }
            epoch++;
            System.out.println("Current Iteration: " + (epoch - 1) + " error: " + lasterr + "\n");
            //out.flush();
        } while (train.getError() > Threshold);
        out.close();
        System.out.println("File: " + fileWithPath + " Epochs needed: " + (epoch - 1) + " last Error: " + lasterr + " First error: " + firstOut);
        epochs.add(epoch - 1);

        //Encog.getInstance().shutdown();		
    }

    public BasicNetwork createFingerPrintNN(String filename) {
        BasicNetwork nn = new BasicNetwork();
        nn.addLayer(new BasicLayer(new ActivationSigmoid(), true, 20));
        nn.addLayer(new BasicLayer(new ActivationSigmoid(), true, 25));
        nn.addLayer(new BasicLayer(new ActivationSigmoid(), true, 1));
        nn.getStructure().finalizeStructure();
        nn.reset();
        return nn;
    }

    public static void trainFPNet(BasicNetwork nn, double[] values, double desiredOut) {
        double errorTreshold = 0.0001;
        double[][] tmp = new double[1][];
        double[][] tmp2 = new double[1][];
        tmp2[0] = new double[]{desiredOut};
        tmp[0] = values;
        MLDataSet trainingSet = new BasicMLDataSet(tmp, tmp2);
        MLTrain train = new ResilientPropagation(nn, trainingSet);
        int epoch = 1;
        double lasterr = 0;
        do {
            train.iteration();
            //System.out.println("Training in process. Error: " + train.getError());
            lasterr = train.getError();
            epoch++;
            System.out.println("Current Iteration: " + (epoch - 1) + " error: " + lasterr + "\n");
            //out.flush();
        } while (train.getError() > errorTreshold);
    }

    public static double[] readFPfile(String file) {
         double[] tmpdbl = new double[20];
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            int cnt = 0;
            while ((line = br.readLine()) != null) {
                String[] tmp = line.split(" ");
                tmpdbl[cnt] = (double) Double.parseDouble(tmp[1]);
                cnt++;
                tmpdbl[cnt] = (double) Double.parseDouble(tmp[3]);
                cnt++;
            }
            while (cnt < tmpdbl.length) {
                tmpdbl[cnt] = 0.0;
                cnt++;
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Found exc: " + e.getMessage());
        }
        return tmpdbl;
    }
}
