package back;

import static org.junit.Assert.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.junit.Before;
import org.junit.Test;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;



public class GraphPrinter {
	
	void printChart(Vector<Map<Integer,graphInfo> > specified_graphs, Vector<String> names){
		
		DefaultCategoryDataset nr_of_reads_chart = new DefaultCategoryDataset();
		DefaultCategoryDataset avg_slots_chart = new DefaultCategoryDataset();
		DefaultCategoryDataset avg_empty_chart = new DefaultCategoryDataset();
		DefaultCategoryDataset avg_colli_chart = new DefaultCategoryDataset();
		DefaultCategoryDataset time_chart = new DefaultCategoryDataset();
		
		
		

	}
	
	DefaultCategoryDataset createGraph(Vector<Vector>){
	      line_chart_dataset.addValue( 15 , "schools" , "1970" );
	      line_chart_dataset.addValue( 30 , "schools" , "1980" );
	      line_chart_dataset.addValue( 60 , "schools" , "1990" );
	      line_chart_dataset.addValue( 120 , "schools" , "2000" );
	      line_chart_dataset.addValue( 240 , "schools" , "2010" ); 
	      line_chart_dataset.addValue( 300 , "schools" , "2014" );

	      JFreeChart lineChartObject = ChartFactory.createLineChart(
	         "Schools Vs Years","Year",
	         "Schools Count",
	         line_chart_dataset,PlotOrientation.VERTICAL,
	         true,true,false);

	      int width = 1024; /* Width of the image */
	      int height = 1080; /* Height of the image */ 
	      File lineChart = new File( "LineChart.jpeg" ); 
	      try {
			ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
