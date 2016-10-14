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
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.junit.Before;
import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
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
	
	public GraphPrinter(){}
	
	public static void printChart(Map<Estimator, Map<Integer,graphInfo> > specified_graphs){
		
		DefaultCategoryDataset nr_of_reads_chart = new DefaultCategoryDataset();
		DefaultCategoryDataset avg_slots_chart = new DefaultCategoryDataset();
		DefaultCategoryDataset avg_empty_chart = new DefaultCategoryDataset();
		DefaultCategoryDataset avg_colli_chart = new DefaultCategoryDataset();
		DefaultCategoryDataset time_chart = new DefaultCategoryDataset();
		
		for (Map.Entry<Estimator, Map<Integer,graphInfo>> entry : specified_graphs.entrySet())
		{
			Vector steps = extractSteps(entry.getValue());
			Vector reads =  extractNr_Reads(entry.getValue());
			Vector avgSlots =  extractAvg_Slots(entry.getValue());
			Vector avgColli =  extractAvg_Colli(entry.getValue());
			Vector avgEmpty =  extractAvg_Empty(entry.getValue());
			Vector avgTime =  extractAvg_Time(entry.getValue());

			addLinesToGraph(nr_of_reads_chart,entry.getKey().getName(),reads,steps);
			addLinesToGraph(avg_slots_chart,entry.getKey().getName(),avgSlots,steps);
			addLinesToGraph(avg_empty_chart,entry.getKey().getName(),avgEmpty,steps);
			addLinesToGraph(avg_colli_chart,entry.getKey().getName(),avgColli,steps);
			addLinesToGraph(time_chart,entry.getKey().getName(),avgTime,steps);
		}
		
		createGraphFromDataset(nr_of_reads_chart, "Signals Sent by Reader", "Tags", "Signals");
		createGraphFromDataset(avg_slots_chart, "Average Slots on a Frame", "Tags", "Average Slots");
		createGraphFromDataset(avg_empty_chart, "Average Empty Slots on a Frame", "Tags", "Average Empty Slots");
		createGraphFromDataset(avg_colli_chart, "Average Collision Slots on a Frame", "Tags", "Average Collision Slots");
		createGraphFromDataset(time_chart, "Average Time Required", "Tags", "Time");
	}
	
	static Vector extractSteps(Map<Integer,graphInfo> a)
	{
		Vector extracted = new Vector();
		for (Map.Entry<Integer, graphInfo> entry : a.entrySet())
		{
		    extracted.add(entry.getKey());
		}
		return extracted;
	}
	
	static Vector extractNr_Reads(Map<Integer,graphInfo> a)
	{
		Vector extracted = new Vector();
		for (Map.Entry<Integer, graphInfo> entry : a.entrySet())
		{
			extracted.add(entry.getValue().nr_of_Reads);
		}
		return extracted;
	}
	static Vector extractAvg_Slots(Map<Integer,graphInfo> a)
	{
		Vector extracted = new Vector();
		for (Map.Entry<Integer, graphInfo> entry : a.entrySet())
		{
			extracted.add(entry.getValue().avg_slots);
		}
		return extracted;
	}
	static Vector extractAvg_Empty(Map<Integer,graphInfo> a)
	{
		Vector extracted = new Vector();
		for (Map.Entry<Integer, graphInfo> entry : a.entrySet())
		{
			extracted.add(entry.getValue().avg_empty);
		}
		return extracted;
	}
	static Vector extractAvg_Colli(Map<Integer,graphInfo> a)
	{
		Vector extracted = new Vector();
		for (Map.Entry<Integer, graphInfo> entry : a.entrySet())
		{
			extracted.add(entry.getValue().avg_colli);
		}
		return extracted;
	}
	static Vector extractAvg_Time(Map<Integer,graphInfo> a)
	{
		Vector extracted = new Vector();
		for (Map.Entry<Integer, graphInfo> entry : a.entrySet())
		{
			extracted.add(entry.getValue().time);
		}
		return extracted;
	}
	
	
	
	static DefaultCategoryDataset addLinesToGraph(DefaultCategoryDataset graph, String name, Vector values, Vector steps){
	    DefaultCategoryDataset tempGraph = graph;
		
		for(int i = 0; i < steps.size(); i++)
	    {
	    	tempGraph.addValue((Number) values.elementAt(i), name, Integer.toString((int)steps.elementAt(i)));
	    }
	    
	    return tempGraph;
	}
	
	static void createGraphFromDataset(DefaultCategoryDataset graph, String name, String x_axis, String y_axis){
		JFreeChart lineChartObject = ChartFactory.createLineChart(
	         name,x_axis,
	         y_axis,
	         graph,PlotOrientation.VERTICAL,
	         true,true,false);
		
		lineChartObject.getPlot().setBackgroundPaint(Color.DARK_GRAY);

	      int width = 1024; /* Width of the image */
	      int height = 1080; /* Height of the image */ 
	      File lineChart = new File( name+".jpeg" ); 
	      try {
	    	  
			ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
