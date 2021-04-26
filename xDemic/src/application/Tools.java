package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tools extends VBox {
	private MainView mainView;
	
	public Tools(MainView mainView) {
		this.mainView=mainView;
		
		update();
	}
	
	public void update() {
		getChildren().clear();
		// create menu bar
		MenuBar menu = new MenuBar();
		Menu file = new Menu("File");
		Menu add = new Menu("New");
		MenuItem malware = new MenuItem("Malware");
		malware.setOnAction((e) -> {
			if(mainView.editing()) mainView.getMalware().add();
	        else System.out.println("# Simulation in progress!");
		});
		MenuItem device = new MenuItem("Device");
		device.setOnAction((e) -> {
			if(mainView.editing()) mainView.getEnvironment().add();
			else System.out.println("# Simulation in progress!");
		});
		add.getItems().addAll(malware, device);
		
		Menu open = new Menu("Open");
		MenuItem random = new MenuItem("Random");
		random.setOnAction((e) -> {
			if(mainView.editing()) {
				mainView.getSimulation().generateRandom();
				mainView.getSimulator().reset();
				mainView.getOutput().reset();
				mainView.draw();
			} else {
				System.out.println("# Simulation in progress!");
			}
		});
		open.getItems().add(random);
		// TODO open saves
		/*for(int i=0; i<saves.size(); i++) {
			int id = i;
			MenuItem saved = new MenuItem("Save " + (id+1));
			saved.setOnAction((e) -> {
				if(mainView.editing()) {
					main.setMainView(saves.get(id));
					mainView.draw();
				} else {
					System.out.println("# Simulation in progress!");
				}
			});
			open.getItems().add(saved);
		}*/
		MenuItem save = new MenuItem("Save");
		save.setOnAction((e) -> {
			if(mainView.editing()) {
				// TODO save simulator and simulation
				//saves.add((MainView) PipedDeepCopy.copy(mainView));
				System.out.println("> Saved!");
				update();
			} else {
				System.out.println("# Simulation in progress!");
			}
		});
		MenuItem reset = new MenuItem("Reset");
		reset.setOnAction((e) -> {
			if(mainView.editing()) {
				mainView.getSimulation().reset();
				mainView.getSimulator().setFrameRate(60);
				mainView.getSimulator().reset();
				mainView.getOutput().reset();
				mainView.draw();
				System.out.println("> Simulation reset.");
			} else {
				System.out.println("# Simulation in progress!");
			}
		});
		MenuItem resetConnections = new MenuItem("Reset Connections");
		resetConnections.setOnAction((e) -> {
			if(mainView.editing()) {
				mainView.getSimulation().getConnectionList().clear();
				mainView.draw();
				System.out.println("> Connections reset.");
			} else {
				System.out.println("# Simulation in progress!");
			}
		});
		file.getItems().addAll(add, open, save, reset, resetConnections);
		
		Menu help = new Menu("Help");
		MenuItem helpDevices = new MenuItem("Devices");
		helpDevices.setOnAction((e) -> {
			deviceHelp();
		});
		help.getItems().addAll(helpDevices);
		menu.getMenus().addAll(file, help);
		
		// create tool bar
		ToolBar tools = new ToolBar();
        
        Button start = new Button();
        start.setOnAction((e) -> {
        	if(mainView.editing()) {
    			if(!(mainView.getSimulation().getDeviceList().isEmpty()) && !(mainView.getSimulation().getMalwareList().isEmpty()) && !(mainView.getSimulation().getConnectionList().isEmpty())) {
    				mainView.getSimulator().start();
    			} else {
    				System.out.println("# Simulation compoenent missing! Try creating devices, malware, or connections.");
    			}
    		} else {
    			System.out.println("# Simulation in progress!");
    		}
        });
        start.setGraphic(makeIcon(new Image("file:play.png")));
		
		Button stop = new Button();
		stop.setOnAction((e) -> {
			if(!mainView.editing()) mainView.getSimulator().stop();
			else System.out.println("# Simulation not in progress!");
		});
		stop.setGraphic(makeIcon(new Image("file:pause.png")));
		
		Button config = new Button();
		config.setOnAction((e) -> {
			config();
		});
		config.setGraphic(makeIcon(new Image("file:config.png")));
		
		tools.getItems().addAll(start, stop, config);
		
		getChildren().addAll(menu, tools);
	}
	
	private void deviceHelp() {
		VBox helpList = new VBox();
		helpList.getStyleClass().add("options");
		
		helpList.getChildren().add(new Text("Shift+drag to move devices."));
		helpList.getChildren().add(new Text("Ctrl+drag to connect devices."));
		helpList.getChildren().add(new Text("Right click to infect, patch, or delete devices."));
		
		Scene helpScene = new Scene(helpList, 400, 400);
		
		Stage helpStage = new Stage();
		helpStage.setTitle("xDemic Help");
		helpStage.setScene(helpScene);
		helpStage.show();
	}
	
	private void config() {
		if(mainView.editing()) {			
			VBox options = new VBox(10);
			options.getStyleClass().add("options");
			
			Text ddLabel = new Text("The multiplier for two connected devices interacting is ");
			Text ddValue = new Text(String.format("%.2f", mainView.getSimulation().getDdMobility()));
			Slider dd = new Slider(0, 1, mainView.getSimulation().getDdMobility());
			dd.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) {
					ddValue.setText(String.format("%.2f", newValue));
				}
			});
			
			Text dnLabel = new Text("The multiplier for a device and node interacting is ");
			Text dnValue = new Text(String.format("%.2f", mainView.getSimulation().getDnMobility()));
			Slider dn = new Slider(0, 1, mainView.getSimulation().getDnMobility());
			dn.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) {
					dnValue.setText(String.format("%.2f", newValue));
				}
			});
			
			Text nnLabel = new Text("The multiplier for two connected nodes interacting is ");
			Text nnValue = new Text(String.format("%.2f", mainView.getSimulation().getNnMobility()));
			Slider nn = new Slider(0, 1, mainView.getSimulation().getNnMobility());
			nn.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) {
					nnValue.setText(String.format("%.2f", newValue));
				}
			});
			
			Text fpsLabel = new Text("Simulation will run at ");
			Text fpsValue = new Text(String.format("%d", mainView.getSimulator().getFrameRate()));
			Text fpsLabel2 = new Text(" frames per second");
			Slider fps = new Slider(1, 60, mainView.getSimulator().getFrameRate());
			fps.setMajorTickUnit(1);
			fps.setMinorTickCount(0);
			fps.setSnapToTicks(true);
			fps.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue <? extends Number > observable, Number oldValue, Number newValue) {
					fpsValue.setText(String.format("%.0f", newValue));
				}
			});
			
			Button apply = new Button("Apply");
			apply.setOnAction((ee) -> {
				mainView.getSimulation().setDdMobility(dd.getValue());
				mainView.getSimulation().setDnMobility(dn.getValue());
				mainView.getSimulation().setNnMobility(nn.getValue());
				mainView.getSimulator().setFrameRate((int) fps.getValue());
				mainView.getSimulator().reset();
				mainView.getOutput().reset();
				mainView.setCenter(mainView.getOutput());
				mainView.draw();
			});
		
			Button cancel = new Button("Cancel");
			cancel.setOnAction((ee) -> {
				mainView.setCenter(mainView.getOutput());
				mainView.draw();
			});
			
			options.getChildren().addAll(new HBox(ddLabel, ddValue), dd, new HBox(dnLabel, dnValue), dn, new HBox(nnLabel, nnValue), nn, new HBox(fpsLabel, fpsValue, fpsLabel2), fps, new HBox(20, apply, cancel));
			mainView.setCenter(options);
		} else {
			System.out.println("# Simulation in progress!");
		}
	}
	
	private ImageView makeIcon(Image image) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(9);
		imageView.setFitHeight(10);
		return imageView;
	}
}