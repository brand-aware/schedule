/**
 * 
 * @author mike802
 *
 * brand_aware
 * ??? - 2019
 * 
 */
package ui;
import java.awt.Color;
import java.awt.GradientPaint;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import core.ColorHolder;
import core.Properties;
import core.SummaryData;
import core.Utilities;

public class ChartBar {

	private Utilities utilities;
	private Properties properties;
	private ConcurrentHashMap<String, ColorHolder> colorMap = new ConcurrentHashMap<String, ColorHolder>();
	
	public ChartBar(Utilities utils){
		utilities = utils;
		properties = utils.getProperties();
	}
	
	public final ChartPanel getChartPanel(ArrayList<SummaryData> activities, String activityName, String progressName){
		ArrayList<GradientPaint> paint = new ArrayList<GradientPaint>();
		JFreeChart chart = null;
		if(activityName == null){
			activityName = activities.get(0).getName();
		}
		if(progressName == null){
			progressName = properties.getMeasurement()[0];
		}
		System.out.println("CHART: " + activityName + ", " + progressName);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int counter = 0;
		for(int x = 0; x < activities.size(); x++){
			SummaryData data = activities.get(x);
			//String month = utilities.getMonth(data.getMonth());
			String date = data.getYear() + " - " + utilities.getMonth(data.getMonth());
			if(progressName.compareTo(properties.getMeasurement()[0]) == 0){
				if(activities.get(x).getName().compareTo(activityName) == 0){
					dataset.addValue(data.getCompleted(), "completed", date);
					paint.add(new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, new Color(0,0,64)));
					
					dataset.addValue(data.getMissed(), "missed", date);
					paint.add(new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0,64,0)));
				}
			}else if(progressName.compareTo(properties.getMeasurement()[1]) == 0){
				System.out.println("got here");
				int individual = data.getTotalTime() / (data.getCompleted() + data.getMissed());
				int total = individual * data.getCompleted();		
				dataset.addValue(total, data.getName(), date);
				System.out.print(data.getName());
				if(colorMap.containsKey(data.getName())){
					ColorHolder color = colorMap.get(data.getName());
					paint.add(new GradientPaint(
							color.getX1(), 
							color.getY1(),
							color.getColor1(),
							color.getX2(),
							color.getY2(),
							color.getColor2()));
					System.out.println(" found");
				}else if(counter == 0){
					colorMap.put(data.getName(), new ColorHolder(0.0f, 0.0f, Color.blue, 0.0f, 0.0f, new Color(0,0,64)));
					ColorHolder color = colorMap.get(data.getName());
					paint.add(new GradientPaint(
							color.getX1(), 
							color.getY1(),
							color.getColor1(),
							color.getX2(),
							color.getY2(),
							color.getColor2()));
					counter++;
					System.out.println(" new color blue");
				}else if(counter == 1){
					colorMap.put(data.getName(), new ColorHolder(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0,64,0)));
					ColorHolder color = colorMap.get(data.getName());
					paint.add(new GradientPaint(
							color.getX1(), 
							color.getY1(),
							color.getColor1(),
							color.getX2(),
							color.getY2(),
							color.getColor2()));
					counter++;
					System.out.println(" new color green");
				}else if(counter == 2){
					colorMap.put(data.getName(), new ColorHolder(0.0f, 0.0f, Color.red, 0.0f, 0.0f, new Color(64,0,0)));
					ColorHolder color = colorMap.get(data.getName());
					paint.add(new GradientPaint(
							color.getX1(), 
							color.getY1(),
							color.getColor1(),
							color.getX2(),
							color.getY2(),
							color.getColor2()));
					counter++;
				}else{
					randomizeColor();
				}
			}
		}
		
		String label = "";
		if(progressName.compareTo(properties.getMeasurement()[0]) == 0){
			label = "Count";
		}else if(progressName.compareTo(properties.getMeasurement()[1]) == 0){
			label = "Total Time Spent(min)";
		}
		
		chart = ChartFactory.createBarChart(activityName, "month", label, dataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = (CategoryPlot) chart.getCategoryPlot();
		plot.setDomainGridlinesVisible(true);
		plot.setRangeCrosshairVisible(true);
		plot.setRangeCrosshairPaint(Color.lightGray);
		
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		for(int y = 0; y < paint.size(); y++){
			renderer.setSeriesPaint(y, paint.get(y));
		}
		renderer.setLegendItemToolTipGenerator(new StandardCategorySeriesLabelGenerator("Tooltip: {0}"));
		//CategoryAxis domainAxis = plot.getDomainAxis();
		//domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(INVERT_CATEGORY_LABELS));
		
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	private final void randomizeColor(){
		//TODO: randomize color selection and make sure random color is unique
		//TODO: determine reasonable limit on possible chart colors
	}
}
