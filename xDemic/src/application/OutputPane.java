package application;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class OutputPane extends Pane {
	MainView mainView;
	
	private final CategoryAxis xAxis;
	private final NumberAxis yAxis;
	private LineChart<String, Number> lineChart;
	private XYChart.Series<String, Number> patched, infected;
	private final int MAX_DATA_POINTS = 20;
	
	public OutputPane(MainView mainView) {
		this.mainView = mainView;
		
		setMinWidth(400);
		setMinHeight(400);
		
		xAxis = new CategoryAxis();
		xAxis.setAnimated(false);
		yAxis = new NumberAxis(0.0, 100.0, 10.0);
		yAxis.setAnimated(false);
		yAxis.setLabel("Percent of population");
		
		patched = new XYChart.Series<>();
		patched.setName("Patched");
		infected = new XYChart.Series<>();
		infected.setName("Infected");
	}
	
	public void update() {
		setPrefWidth(mainView.getWidth()*2/5);
		setPrefHeight(mainView.getHeight()*2/3-mainView.getTools().getHeight());
		
		getChildren().clear();
        
        patched.getData().add(new XYChart.Data<>("" + mainView.getSimulator().getFrame(), mainView.getSimulation().calculatePercentPatched()));
        infected.getData().add(new XYChart.Data<>("" + mainView.getSimulator().getFrame(), mainView.getSimulation().calculatePercentInfected() + mainView.getSimulation().calculatePercentPatched()));
        
        if(patched.getData().size() > MAX_DATA_POINTS) {
        	patched.getData().remove(0);
        	infected.getData().remove(0);
        }
        
        lineChart = new LineChart<>(xAxis, yAxis) {
        	@Override
        	protected void layoutPlotChildren() {
        		super.layoutPlotChildren();
        		
        		Polygon infectedPolygon = new Polygon();
        		Polygon patchedPolygon = new Polygon();
        		for(int i=0; i<infected.getData().size()-1; i++) {
        			double x1 = getXAxis().getDisplayPosition(infected.getData().get(i).getXValue());
                    double y1 = getYAxis().getDisplayPosition(0);
                    double x2 = getXAxis().getDisplayPosition(infected.getData().get((i + 1)).getXValue());
                    double y2 = getYAxis().getDisplayPosition(0);
        			
        			infectedPolygon.getPoints().addAll(new Double[] {
        				x1, y1,
        				x1, getYAxis().getDisplayPosition(infected.getData().get(i).getYValue()),
        				x2, getYAxis().getDisplayPosition(infected.getData().get((i+1)).getYValue()),
        				x2, y2
        			});
        			
        			x1 = getXAxis().getDisplayPosition(patched.getData().get(i).getXValue());
                    y1 = getYAxis().getDisplayPosition(0);
                    x2 = getXAxis().getDisplayPosition(patched.getData().get((i + 1)).getXValue());
                    y2 = getYAxis().getDisplayPosition(0);
        			
        			patchedPolygon.getPoints().addAll(new Double[] {
        				x1, y1,
        				x1, getYAxis().getDisplayPosition(patched.getData().get(i).getYValue()),
        				x2, getYAxis().getDisplayPosition(patched.getData().get((i+1)).getYValue()),
        				x2, y2
        			});
        		}
        		infectedPolygon.setFill(Color.RED);
    	        patchedPolygon.setFill(Color.GREEN);
    	        getPlotChildren().addAll(infectedPolygon, patchedPolygon);
        	}
        };
        lineChart.setPrefWidth(mainView.getWidth()*2/5);
		lineChart.setPrefHeight(mainView.getHeight()*2/3-mainView.getTools().getHeight());
		
        lineChart.setAnimated(false);
		lineChart.setTitle("Population Status");
        
        lineChart.getData().add(infected);
        lineChart.getData().add(patched);
        
        getChildren().add(lineChart);
	}
	
	public void reset() {
		patched.getData().clear();
		infected.getData().clear();
	}
}