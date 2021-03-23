import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class CloudPane extends Pane {
	private int size=10;
	private int[] closestPair, furthestPair;
	private double[] cm;
	private double[][] points;
	private double xi, yi, xf, yf;
	private Line xAxis, yAxis, closest, furthest;
	private Text x, y, center;
	private FlowPane buttons;
	private Button btCloud, btSave, btOpen;
	private ToggleButton btClose, btFar, btCenter;
	private Cloud cloud;
	private File save, open;
	
	CloudPane() {		
		xAxis = new Line();
		xAxis.setStartX(50);
		xAxis.setStartY(250);
		xAxis.setEndX(250);
		xAxis.setEndY(250);
		
		yAxis = new Line();
		yAxis.setStartX(50);
		yAxis.setStartY(250);
		yAxis.setEndX(50);
		yAxis.setEndY(50);
		
		x = new Text(255, 260, "X");
		y = new Text(40, 45, "Y");
		
		getChildren().addAll(xAxis, yAxis, x, y);
		
		cloud = new Cloud();
		cloud.setCloudRandomly(size);
		points=cloud.getPoints();
		
		closestPair=cloud.findClosestPoints();
		furthestPair=cloud.findFurthestPoints();
		cm=cloud.findCenterOfMass();
		
		xi=250-2*(points[closestPair[0]][0]);
		yi=250-2*(points[closestPair[0]][1]);
		xf=250-2*(points[closestPair[1]][0]);
		yf=250-2*(points[closestPair[1]][1]);
		
		closest = new Line(xi, yi, xf, yf);
		closest.setStroke(Color.RED);
		
		xi=250-2*(points[furthestPair[0]][0]);
		yi=250-2*(points[furthestPair[0]][1]);
		xf=250-2*(points[furthestPair[1]][0]);
		yf=250-2*(points[furthestPair[1]][1]);
		
		furthest = new Line(xi, yi, xf, yf);
		furthest.setStroke(Color.BLUE);
		
		center = new Text(250-2*(cm[0]), 250-2*(cm[1]), "+");
		
		getChildren().addAll(closest, furthest, center);
		
		for(int i=0; i<size; i++) {
			Circle circle = new Circle(250-2*(points[i][0]), 250-2*(points[i][1]), 2);
			getChildren().add(circle);
		}
		
		buttons = new FlowPane();
		buttons.setPrefWrapLength(650);
		btCloud = new Button("New Point Cloud");
		btClose = new ToggleButton("Toggle Closest");
		btFar = new ToggleButton("Toggle Furthest");
		btCenter = new ToggleButton("Toggle Center of Mass");
		btSave = new Button("Save Cloud");
		btOpen = new Button("Open Saved Cloud");
		
		buttons.getChildren().addAll(btCloud, btClose, btFar, btCenter, btSave, btOpen);
		getChildren().add(buttons);
		
		btCloud.setOnAction(e -> {
			getChildren().clear();
			
			cloud.setCloudRandomly(size);
			points=cloud.getPoints();
			
			closestPair=cloud.findClosestPoints();
			furthestPair=cloud.findFurthestPoints();
			cm=cloud.findCenterOfMass();
			
			closest.setStartX(250-2*(points[closestPair[0]][0]));
			closest.setStartY(250-2*(points[closestPair[0]][1]));
			closest.setEndX(250-2*(points[closestPair[1]][0]));
			closest.setEndY(250-2*(points[closestPair[1]][1]));
			
			furthest.setStartX(250-2*(points[furthestPair[0]][0]));
			furthest.setStartY(250-2*(points[furthestPair[0]][1]));
			furthest.setEndX(250-2*(points[furthestPair[1]][0]));
			furthest.setEndY(250-2*(points[furthestPair[1]][1]));
			
			center.setX(250-2*(cm[0]));
			center.setY(250-2*(cm[1]));
			
			getChildren().addAll(xAxis, yAxis, x, y);
			getChildren().addAll(closest, furthest, center);
			
			for(int i=0; i<size; i++) {
				Circle circle = new Circle(250-2*(points[i][0]), 250-2*(points[i][1]), 2);
				getChildren().add(circle);
			}
			
			getChildren().add(buttons);
		});
		
		btClose.setSelected(true);
		btClose.setOnAction(e -> {
			closest.setVisible(btClose.isSelected());
		});
		
		btFar.setSelected(true);
		btFar.setOnAction(e -> {
			furthest.setVisible(btFar.isSelected());
		});
		
		btCenter.setSelected(true);
		btCenter.setOnAction(e -> {
			center.setVisible(btCenter.isSelected());
		});
		
		save = new File("cloud.dat");
		open = new File("cloud.dat");
		
		btSave.setOnAction(e -> {
			try {
				FileWriter fileWriter = new FileWriter(save);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				
				for(int i=0; i<size; i++) {
					bufferedWriter.write(points[i][0] + " " + points[i][1]);
					bufferedWriter.newLine();
				}
				
				bufferedWriter.close();
			} catch (Exception x) {
				x.printStackTrace();
			}
		});
		
		btOpen.setOnAction(e -> {
			try {
				FileReader fileReader = new FileReader(open);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				
				int i=0;
				String line;
				while((line=bufferedReader.readLine())!=null) {
					//System.out.println(line);
					String[] splitLine = line.split(" ");
					points[i][0]=(double) Double.parseDouble(splitLine[0]);
					points[i][1]=(double) Double.parseDouble(splitLine[1]);
					//System.out.println(points[i][0] + " " + points[i][1]);
					i++;					
				}
				
				getChildren().clear();
				
				closestPair=cloud.findClosestPoints();
				furthestPair=cloud.findFurthestPoints();
				cm=cloud.findCenterOfMass();
				
				closest.setStartX(250-2*(points[closestPair[0]][0]));
				closest.setStartY(250-2*(points[closestPair[0]][1]));
				closest.setEndX(250-2*(points[closestPair[1]][0]));
				closest.setEndY(250-2*(points[closestPair[1]][1]));
				
				furthest.setStartX(250-2*(points[furthestPair[0]][0]));
				furthest.setStartY(250-2*(points[furthestPair[0]][1]));
				furthest.setEndX(250-2*(points[furthestPair[1]][0]));
				furthest.setEndY(250-2*(points[furthestPair[1]][1]));
				
				center.setX(250-2*(cm[0]));
				center.setY(250-2*(cm[1]));
				
				getChildren().addAll(xAxis, yAxis, x, y);
				getChildren().addAll(closest, furthest, center);
				
				for(i=0; i<size; i++) {
					Circle circle = new Circle(250-2*(points[i][0]), 250-2*(points[i][1]), 2);
					getChildren().add(circle);
				}
			
				getChildren().add(buttons);
			} catch (Exception x) {
				x.printStackTrace();
			}
		});
	}
}