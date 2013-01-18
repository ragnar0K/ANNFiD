/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/


import java.awt.Point;
import java.awt.geom.Rectangle2D;
import ANNFileDetect.ThreshEdit;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleEdge;

public class MouseEventListener implements ChartMouseListener{
        
        
        private ChartPanel chartPanel;

        public MouseEventListener(ThreshEdit th, ChartPanel chartpanel){
                //this.simuladorGUI = simuladorGui;
                this.chartPanel = chartpanel;
        }
        
        
        @Override
        public void chartMouseClicked(ChartMouseEvent arg0) {

                Point point = arg0.getTrigger().getPoint(); 
                //add a new point to the model editor series 
                int x = point.x; 
                int y = point.y; 
                
                Rectangle2D dataArea = chartPanel.getScreenDataArea();
                float x0 = (float) chartPanel.getChart().getXYPlot().getRangeAxis().java2DToValue(y, dataArea, RectangleEdge.LEFT); 
                float t0 = (float) chartPanel.getChart().getXYPlot().getDomainAxis().java2DToValue(x, dataArea, RectangleEdge.BOTTOM);
                
                //simuladorGUI.nuevoPuntoInicial(t0, x0);
        }

        @Override
        public void chartMouseMoved(ChartMouseEvent arg0) {
                Double chartX;
                Double chartT;  
                
                XYPlot plot = (XYPlot) chartPanel.getChart().getPlot();
                chartX = plot.getRangeAxis().java2DToValue(chartPanel.translateScreenToJava2D(arg0.getTrigger().getPoint()).getY(), chartPanel.getScreenDataArea(), plot.getRangeAxisEdge());
                chartT = plot.getDomainAxis().java2DToValue(chartPanel.translateScreenToJava2D(arg0.getTrigger().getPoint()).getX(), chartPanel.getScreenDataArea(), plot.getDomainAxisEdge());

                //simuladorGUI.actualizarPosicionCursor(chartX, chartT);
                
        }

}
