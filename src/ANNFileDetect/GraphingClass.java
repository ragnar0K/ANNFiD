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

import java.awt.Rectangle;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class GraphingClass {
    public JFreeChart chartOutcome(HashMap hm) {
        JFreeChart jf = null;
        DefaultCategoryDataset dc = new DefaultCategoryDataset();
        String xax = "File Type";
        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            dc.addValue((Double)pair.getValue(), xax, pair.getKey().toString());
        }
        jf = ChartFactory.createBarChart3D("File detection results", "File type", "Score", (CategoryDataset) dc, PlotOrientation.VERTICAL, true, true, true);
        return jf;
    }
    

    public void drawchartFromInt(Integer[] values) throws IOException {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        for (int i = 0; i < values.length; i++) {
            //double a = (double) i;
            ds.addValue(i, String.valueOf(i), String.valueOf(values[i]));
            //ds.addValue((double)i, "Times", values[i]);
        }
        JFreeChart chart = ChartFactory.createBarChart("chart", "quantity", "value", ds, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setSize(1200, 700);
        JPanel jf = new JPanel();
        jf.setSize(1300, 800);
        chartPanel.setVisible(true);
        chartPanel.setZoomAroundAnchor(true);
        chartPanel.setDomainZoomable(true);
        jf.add(chartPanel);
        JLabel jl = new JLabel("hello!");
        jf.add(jl);
        jf.setVisible(true);
        jf.repaint();
        //jf.setAlwaysOnTop(true);
    }

    public JFreeChart RenderFiles(TreeMap finalMap) {
        XYSeriesCollection ds = new XYSeriesCollection();
        Iterator entries = finalMap.entrySet().iterator();
        while (entries.hasNext()) {
            Entry thisentry = (Entry) entries.next();

            String key = (String) thisentry.getKey();
            TreeMap<Double, Integer> tm = (TreeMap) thisentry.getValue();
            XYSeries series = new XYSeries(key);
            //HashMap<Double, Integer> tmp = finalMap.get(key);
            for (Map.Entry<Double, Integer> entry : tm.entrySet()) {
                //ds.addValue(ht.get(tmp), "Times", tmp); 
                series.add(entry.getKey(), entry.getValue());
            }
            ds.addSeries(series);
        }
        JFreeChart chart = ChartFactory.createXYLineChart("Test graph", "Value", "Times", ds, PlotOrientation.VERTICAL, true, true, false);

        XYLineAndShapeRenderer rend = (XYLineAndShapeRenderer) chart.getXYPlot().getRenderer();
        rend.setBaseShape(new Rectangle(-2, -2, 4, 4));
        rend.setBaseShapesVisible(true);
        rend.setBaseSeriesVisible(true);
        //rend.setSeriesVisible(i, true);
        chart.getXYPlot().setRenderer(rend);
        return chart;
    }
}
