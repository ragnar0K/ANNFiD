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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

public class FileOperations {

    public boolean createDirectory(String directory) {
        return (new File(directory)).mkdir();
    }
    
    public void removeFile(String file) {
        new File(file).delete();
    }
    
    public boolean createDirOrRemoveContents(String directory) {
        if (new File(directory).exists())
        {
            String[] files = getFiles(directory);
            for (String file : files)
            {
                new File(directory + '/' + file).delete();
            }
            return true;
        }
        else return createDirectory(directory);
    }

    // Return a file list from a given Dir
    public void saveNetwork(String filename, BasicNetwork nn) {
        EncogDirectoryPersistence.saveObject(new File(filename), nn);
    }

    public BasicNetwork openNetwork(String filename) {
        return (BasicNetwork) EncogDirectoryPersistence.loadObject(new File(filename));
    }

    public String[] getFiles(String directory) {

        if (directory.length() == 0) {
            return new String[]{};
        } else {
            try {
                File dir = new File(directory);
                return dir.list();
            } catch (Exception exc) {
                return new String[]{};
            }
        }

    }

    // Return a byte array, translated from a given file
    public byte[] fileToByte(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream is = null;
        byte[] out = null;
        try {
            is = new FileInputStream(file);
            long fl = file.length();

            if (fl > Integer.MAX_VALUE) {
                return new byte[]{};
            }
            out = new byte[(int) fl];
        } catch (Exception e) {
        }
        int offset = 0;
        int numRead = 0;
        while (offset < out.length
                && (numRead = is.read(out, offset, out.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < out.length) {
            throw new IOException("Issue reading the file " + file.getName());
        }
        is.close();
        return out;
    }

    // For a given set of bytes, return a double array
    public double[] makeDoubleArray(byte[] bytes) {
        double[] outdbl = new double[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            outdbl[i] = (double) bytes[i];
        }
        return outdbl;
    }

    // Create a double matrix for a given column
    public double[][] DoubleMatrix(byte[] bytes, int columns, int startingOffset) {
        int height = (bytes.length - (columns * startingOffset)) / columns;
        double[][] fbyte = new double[height][];
        for (int i = startingOffset; i < height; i++) {
            byte[] tmpbyte = new byte[columns];
            int cnt = 0;
            while (cnt != columns) {
                int tstcount = ((i * columns) + cnt);
                tmpbyte[cnt] = bytes[tstcount];
                cnt++;
            }
            fbyte[i] = makeDoubleArray(tmpbyte);

        }
        return fbyte;
    }
}