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


import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

public class SQLiteLib {

    SQLiteConnection conn;
    String filename;

    SQLiteLib(String SQLiteFile) {
        try {
            conn = new SQLiteConnection(new File(SQLiteFile));
            filename = SQLiteFile;
        } finally {
        }
    }

    public String[] GetAllFinalResults() {
        ArrayList al = new ArrayList();
        try {
            if (!conn.isOpen()) {
                conn.open();
            }
            
            SQLiteStatement st = conn.prepare("SELECT File_type, Results from Results");
            while (st.step()) {
                al.add(st.columnValue(0).toString() + " =   " + st.columnValue(1).toString());
            }
        } catch (Exception exc) {
            System.out.println("Found exception: " + exc.toString());
        }
        String[] out = new String[al.size()];
        al.toArray(out);
        return out;
    }

    public String GetFinalResultsMetrics(HashMap inventory) {
        String outcome = "";
        HashMap hm = new HashMap();
        HashMap results = new HashMap();
        try {
            if (!conn.isOpen()) {
                conn.open();
            }
            SQLiteStatement st = conn.prepare("SELECT File_type, Results from Results");
            while (st.step()) {
                hm.put(st.columnValue(0).toString(), st.columnValue(1).toString().split(","));
            }
        } catch (Exception exc) {
            System.out.println("Found exception: " + exc.toString());
        }
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entries = (Map.Entry) it.next();
            String fileType = entries.getKey().toString();
            String[] values = (String[]) entries.getValue();
            boolean found = true;
            for (String value : values) {
                String[] splvals = value.split(" ");
                if (inventory.containsKey(splvals[0])) {
                    double desval = Double.parseDouble(splvals[2]);
                    double actualval = Double.parseDouble(inventory.get(splvals[0]).toString());
                    if (splvals[1].matches(">")) {
                        if (actualval <= desval) {
                            found = false;
                        }
                    }
                    if (splvals[1].matches("<")) {
                        if (actualval >= desval) {
                            found = false;
                        }
                    }
                } else {
                    found = false;
                }
            }
            if (found) {
                results.put(fileType, values);
            }
        }
        Iterator ite = results.entrySet().iterator();
        while (ite.hasNext()) {
            Map.Entry entries = (Map.Entry) ite.next();
            outcome = outcome + entries.getKey().toString() + " ";
        }
        return outcome;
    }

    public HashMap GetValuescore(String network, double value) {

        String[] files = GetImagesForNetwork(network);
        HashMap hm = new HashMap();
        double lastval = 0;
        double lastvalscore = 0;
        double lastvalweight = 0;
        for (String file : files) {
            //String[] threshs = GetThresholdswithCalc(network, file);
            String[] threshs = GetThresholds(network, file);
            //Determine if it is within the range of values we have
            String firstvals[] = threshs[0].split(",");
            lastval = Double.parseDouble(firstvals[0]);
            lastvalscore = 0;
            lastvalweight = 0;
            String lastvals[] = threshs[threshs.length - 1].split(",");

            if (value < Double.parseDouble(firstvals[0]) || value > Double.parseDouble(lastvals[1])) {
                hm.put(file, "0,0");
            }
            for (String thresh : threshs) {
                String[] vals = thresh.split(",");
                double vala = Double.parseDouble(vals[0]);
                double valb = Double.parseDouble(vals[1]);
                double weight = Double.parseDouble(vals[2]);
                double score = Double.parseDouble(vals[3]);
                //check if it sits between the gaps
                if (value > lastval && value < vala) {
                    //calculate gap average score/weight
                    double gapscore = (double) (lastvalscore + score) / 2;
                    double weightscore = (double) (lastvalweight + weight) / 2;
                    hm.put(file, gapscore + "," + weightscore);
                }
                //check the well known values
                if (value >= vala && value <= valb) {
                    hm.put(file, score + "," + weight);
                }
                lastval = valb;
                lastvalscore = score;
                lastvalweight = weight;
            }

        }
        return hm;
    }

    public String[] GetNetworkNames() {
        String[] strout = null;
        try {
            conn.open(true);
            Map<String, Map> dic = new HashMap<String, Map>();
            SQLiteStatement st = conn.prepare("SELECT Network_Name from NetworkIndex");
            ArrayList<String> al = new ArrayList();
            while (st.step()) {
                al.add(st.columnValue(0).toString());
            }
            strout = new String[al.size()];
            al.toArray(strout);
            return strout;
        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strout;
    }

    public void deleteFiletypeFromNet(String fileType, String networkName) {
        String netID = GetNetworkID(networkName);
        try {
            String insert = "delete from Thresholds where Network_id = '" + netID + "' and File_Type = '" + fileType + "'";
            doCmd(insert);
        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteSingleThreshold(String fileType, String networkName, String start, String end) {
        String netID = GetNetworkID(networkName);
        try {
            String insert = "delete from Thresholds where Network_id = '" + netID + "' and File_Type = '" + fileType + "' and start_range = '" + start + "' and end_range = '" + end + "'";
            doCmd(insert);
        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addSingleThreshold(String network, String fileType, String start, String End, String Weight, String score) {
        String netID = GetNetworkID(network);
        try {
            String insert = "insert into thresholds (Network_ID, File_Type, Start_Range, End_Range, Weight, Score) values ('" + netID + "', '" + fileType + "', '" + start + "', '" + End + "', '" + Weight + "', '" + score + "')";
            doCmd(insert);
        } catch (Exception exc) {
            System.out.println(exc.toString());
        }
    }

    public String[] GetImagesForNetwork(String network) {
        String netID = GetNetworkID(network);
        String[] strout = null;
        try {
            if (!conn.isOpen()) {
                conn.open();
            }
            SQLiteStatement st = conn.prepare("SELECT distinct file_type from Thresholds where Network_ID = '" + netID + "'");
            ArrayList<String> al = new ArrayList();
            while (st.step()) {
                al.add(st.columnValue(0).toString());
            }
            strout = new String[al.size()];
            al.toArray(strout);
            return strout;
        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strout;
    }

    public String[] GetThresholds(String networkName, String imageType) {
        return GetThresholds(networkName, imageType, false);
    }

    public String[] GetThresholdswithCalc(String networkName, String imageType) {
        return GetThresholds(networkName, imageType, true);
    }

    private String[] GetThresholds(String networkName, String imageType, boolean withCalc) {
        String netID = GetNetworkID(networkName);
        String[] strout = null;
        try {
            if (!conn.isOpen()) {
                conn.open();
            }
            SQLiteStatement st = conn.prepare("SELECT * from Thresholds where Network_ID = '" + netID + "' and File_Type = '" + imageType + "' order by Start_Range asc");
            ArrayList<String[]> al = new ArrayList();
            int total = 0;
            while (st.step()) {
                String a = st.columnValue(2).toString();
                String b = st.columnValue(3).toString();
                String c = st.columnValue(4).toString();
                String d = st.columnValue(5).toString();
                String[] tmp = new String[]{a, b, c, d};
                total = total + Integer.parseInt(c);
                al.add(tmp);
            }
            ArrayList<String> fin = new ArrayList();


            double pc = (double) 100 / total;

            for (int i = 0; i < al.size(); i++) {
                String tmp[] = al.get(i);
                if (withCalc) {
                    fin.add(tmp[0] + "," + tmp[1] + "," + (Double.parseDouble(tmp[2]) * pc) + "," + tmp[3]);
                } else {
                    fin.add(tmp[0] + "," + tmp[1] + "," + tmp[2] + "," + tmp[3]);
                }
            }

            strout = new String[fin.size()];
            fin.toArray(strout);
            return strout;
        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strout;
    }

    public String GetNetworkFile(String networkName) {
        String toget = "";
        try {
            if (!conn.isOpen()) {
                conn.open(true);
            }
            SQLiteStatement st = conn.prepare("SELECT Network_File from NetworkIndex where Network_Name = '" + networkName + "'");
            while (st.step()) {
                toget = st.columnValue(0).toString();
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return toget;
    }

    public String GetNetworkFPFile(String networkName) {
        String toget = "";
        try {
            conn.open(true);
            SQLiteStatement st = conn.prepare("SELECT Network_Translator from NetworkIndex where Network_Name = '" + networkName + "'");
            while (st.step()) {
                toget = st.columnValue(0).toString();
            }

        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toget;
    }

    private String GetNetworkID(String networkName) {
        String toget = "";
        try {
            conn.open(true);
            SQLiteStatement st = conn.prepare("SELECT rowid from NetworkIndex where Network_Name = '" + networkName + "'");
            while (st.step()) {
                toget = st.columnValue(0).toString();
            }

        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toget;
    }

    public Map<String, Map> GetNetworkData() throws SQLiteException {
        conn.open(true);
        Map<String, Map> dic = new HashMap<String, Map>();
        SQLiteStatement st = conn.prepare("SELECT * from NetworkIndex");
        //st.bind(1, SQLiteFile)
        int columns = st.columnCount();
        while (st.step()) {
            int col = st.getBindParameterIndex("Network_Name");
            Map<String, String> tmpdic = new HashMap<String, String>();

            for (int i = 0; i < columns; i++) {
                tmpdic.put(st.getColumnName(i), st.columnValue(i).toString());
            }
            dic.put(st.columnValue(col).toString(), tmpdic);
        }
        conn.open(false);
        return dic;
    }

    private void doCmd(String cmd) throws SQLiteException {
        conn.exec(cmd);
    }

    public void insertNN(String Name, String NFile, String NTranslator, String Description) {
        String insert = "insert into NetworkIndex (Network_name, Network_File, Network_Translator, Description, Value_Parse) values (" + "'" + Name + "','" + NFile + "', '" + NTranslator + "', '" + Description + "', 0)";
        try {
            doCmd(insert);
        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteNN(String NetworkName) {
        String networkID = GetNetworkID(NetworkName);
        try {
            String insert = "delete from Thresholds where Network_id = '" + networkID + "'";
            doCmd(insert);
            insert = "delete from NetworkIndex where rowid = '" + networkID + "'";
            doCmd(insert);
        } catch (SQLiteException ex) {
            Logger.getLogger(SQLiteLib.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDBFile() {
        return filename;
    }
}
