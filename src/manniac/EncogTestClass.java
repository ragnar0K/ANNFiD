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
package manniac;

/**
 *
 * @author ragnar0k@fabytes.com
 */

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class EncogTestClass {

    int filebytes = 0;
    private static int files = 0;

    public void removeStdDirs() {
        FileOperations fo = new FileOperations();
        fo.createDirOrRemoveContents("tempTrainingFiles");
    }

    private void createReport(TreeMap<Double, Integer> ht, String file) throws IOException {
        TreeMap<Integer, ArrayList<Double>> tm = new TreeMap<Integer, ArrayList<Double>>();
        for (Map.Entry<Double, Integer> entry : ht.entrySet()) {
            if (tm.containsKey(entry.getValue())) {
                ArrayList<Double> al = (ArrayList<Double>) tm.get(entry.getValue());
                al.add(entry.getKey());
                tm.put(entry.getValue(), al);
            } else {
                ArrayList<Double> al = new ArrayList<Double>();
                al.add(entry.getKey());
                tm.put(entry.getValue(), al);
            }
        }
        
        String[] tmpfl = file.split("/");
        if (tmpfl.length < 2)
            tmpfl = file.split("\\\\");
        
        String crp = tmpfl[tmpfl.length - 1];
        String[] actfl = crp.split("\\.");
        FileWriter fstream = new FileWriter("tempTrainingFiles/" + actfl[1].toUpperCase() + actfl[0] + ".txt");
        BufferedWriter fileto = new BufferedWriter(fstream);
        int size = tm.size();
        int cnt = 0;
        for (Map.Entry<Integer, ArrayList<Double>> entry : tm.entrySet()) {
            if (cnt > (size - 10) && entry.getKey() > 2 && entry.getValue().size() < 20) {
                double tmpval = ((double) entry.getKey()) / filebytes;
                fileto.write("Times: " + tmpval + " Values: ");
                for (Double dbl : entry.getValue()) {
                    fileto.write(dbl + " ");
                }
                fileto.write("\n");
            }

            cnt++;
        }
        fileto.close();
    }

    private Double hashDbl(Double dbl) {
        DecimalFormat df = new DecimalFormat("#0.00000");
        String tmp = df.format(dbl);
        return Double.valueOf(tmp);

    }

    private void drawchart(TreeMap<Double, Integer> ht, String file, int minima) throws IOException {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        //XYDataset xy = new XYDataset();
		/*
         * Enumeration<Double> e = ht.keys(); while (e.hasMoreElements()) {
         * Double tmp = e.nextElement(); //ds.addValue(ht.get(tmp), "Times",
         * tmp); if (ht.get(tmp) > 100) ds.addValue(ht.get(tmp), "Times", tmp);
         * }
         */
        for (Map.Entry<Double, Integer> entry : ht.entrySet()) {
            //ds.addValue(ht.get(tmp), "Times", tmp); 
            if (entry.getValue() > minima) {
                ds.addValue(entry.getValue(), "Times", entry.getKey());
            }
        }
        /*
         * while (e.hasMoreElements()) { Double tmp = e.nextElement();
         * //ds.addValue(ht.get(tmp), "Times", tmp); if (ht.get(tmp) > 100)
         * ds.addValue(ht.get(tmp), "Times", tmp); }
         */
        JFreeChart chart = ChartFactory.createBarChart(file, "quantity", "value", ds, PlotOrientation.VERTICAL, true, true, false);
        //JFreeChart chart = ChartFactory.createScatterPlot(file, "quantity", "value",ds, PlotOrientation.VERTICAL, true, true, false);
        String dlt = "/";
        String[] tmpfl = file.split(dlt);
        String crp = tmpfl[tmpfl.length - 1];
        String[] actfl = crp.split("\\.");
        ChartUtilities.saveChartAsJPEG(new File("/tmp/charts/" + actfl[1].toUpperCase() + actfl[0]), chart, 6000, 1200);
        files++;
    }

    public MLData runNet(String fileToExamine, String nnFile, boolean wantGraph, int graphMinima) {
        BasicNetwork nn = (new FileOperations()).openNetwork(nnFile);
        MLData ml = null;
        try {
            ml = runNet(fileToExamine, nn, wantGraph, graphMinima);
        } catch (IOException ex) {
            Logger.getLogger(EncogTestClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ml;
    }

    public MLData runNet(String fileToExamine, BasicNetwork nn, boolean wantGraph, int graphMinima) throws IOException {
        double[] output = new double[]{0.0};
        System.out.println("File: " + fileToExamine);
        TreeMap<Double, Integer> ht = new TreeMap<Double, Integer>();
        FileOperations fo = new FileOperations();
        int inputNeurons = nn.getInputCount();
        int outputNeurons = nn.getOutputCount();
        byte[] file = fo.fileToByte(fileToExamine);
        filebytes = file.length;
        double[][] fileMatrix = fo.DoubleMatrix(file, inputNeurons, 0);
        double[][] Output = new double[fileMatrix.length][];
        for (int i = 0; i < fileMatrix.length; i++) {
            Output[i] = output;
        }
        MLDataSet trainingSet = new BasicMLDataSet(fileMatrix, Output);
        MLData out = null;
        double[] average = new double[outputNeurons];
        for (MLDataPair pair : trainingSet) {
            out = nn.compute(pair.getInput());
            //System.out.println("Output Computed for file: " + fileToExamine + ": ");
            for (int a = 0; a < outputNeurons; a++) {
                average[a] = (average[a] + out.getData(a)) / 2;
                Double tst = hashDbl(out.getData(a));
                if (ht.containsKey(tst)) {
                    ht.put(tst, (ht.get(tst) + 1));
                } else {
                    ht.put(tst, 1);
                }
            }
        }
        if (wantGraph) {
            drawchart(ht, fileToExamine, graphMinima);
        } else {
            createReport(ht, fileToExamine);
        }


        for (int i = 0; i < average.length; i++) {
            System.out.println("Neuron" + i + ": " + average[i]);
        }
        return out;
    }

    public double testFPfile(String FPnetworkFile, String fileToTest) {
        double[] file = readFPfile(fileToTest);
        double out = testFPnet(FPnetworkFile, file);
        System.out.println("network " + FPnetworkFile + " tested:" + fileToTest + " and resulted: " + out);
        return out;
    }

    public double testSingleFPfile(String FPnetworkFile) {
        FileOperations fo = new FileOperations();
        String files[] = fo.getFiles("tempTrainingFiles");
        String found = files[0];
        double[] file = readFPfile("tempTrainingFiles" + "/" + found);
        double out = testFPnet(FPnetworkFile, file);
        System.out.println("network " + FPnetworkFile + " tested:" + found + " and resulted: " + out);
        return out;
    }

    private double[] readFPfile(String file) {
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

    private double testFPnet(String nname, double[] values) {
        BasicNetwork nn = new FileOperations().openNetwork(nname);
        double[][] tmp = new double[1][];
        double[][] tmp2 = new double[1][];
        tmp[0] = values;
        tmp2[0] = new double[]{0.0};
        MLDataSet trainingSet = new BasicMLDataSet(tmp, tmp2);
        MLData out = null;
        for (MLDataPair pair : trainingSet) {
            out = nn.compute(pair.getInput());
            //System.out.println(out.getData(0));
        }
        return hashDbl(out.getData(0));
    }
}
