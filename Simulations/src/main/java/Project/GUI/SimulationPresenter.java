package Project.GUI;

import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
import Project.Model.WorldElements.Maps.MapChangeListener;
import Project.Simulations.Simulation;
import Project.Simulations.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

import static Project.Model.WorldElements.Maps.WorldMap.MAP_BEGINNING;
import static java.util.Objects.isNull;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private Label statsAnimalsNum;
    @FXML
    private Label statsAnimalsNumVal;
    @FXML
    private Label statsPlantsNum;
    @FXML
    private Label statsPlantsNumVal;
    @FXML
    private Label statsAverageEnergy;
    @FXML
    private Label statsAverageEnergyVal;
    @FXML
    private Label statsAverageAge;
    @FXML
    private Label statsAverageAgeVal;
    @FXML
    private Label statsAverageChildrenCount;
    @FXML
    private Label statsAverageChildrenCountVal;

    private Simulation simulation;
    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid = new GridPane();
    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }
    private void clearGrid() {
        if (!isNull(mapGrid.getChildren())) {
            mapGrid.getChildren().clear();  // Clear all children
        }
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }
    public void drawMap(Simulation simulation) {
        clearGrid();
        Vector2d topRight = simulation.getConfig().getMapDimensions();
        Vector2d bottomLeft = MAP_BEGINNING;

        int gridWidth = topRight.getX() - bottomLeft.getX() + 2;
        int gridHeight = topRight.getY() - bottomLeft.getY() + 2;
        mapGrid.add(createLabel("y \\ x"), 0, 0);


        int CELL_WIDTH = 40;
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        int CELL_HEIGHT = 40;
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));



        // creating axis x and axis y

        int leftStart = bottomLeft.getX();

        for (int i = 1; i < gridWidth; i++) {
            mapGrid.add(createLabel(String.valueOf(leftStart)), i, 0);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            leftStart++;
        }

        int upperStart = topRight.getY();
        for (int i = 1; i < gridHeight; i++) {
            mapGrid.add(createLabel(String.valueOf(upperStart)), 0, i);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
            upperStart--;
        }

        // filling map with actual objects
        for (int i = bottomLeft.getY(); i <= topRight.getY(); i++) {
            for (int j = bottomLeft.getX(); j <= topRight.getX(); j++) {
                Vector2d currPoint = new Vector2d(j, i);
                if (simulation.getMap().isOccupied(currPoint)) {
                    int columnInd = j - bottomLeft.getX() + 1;
                    int rowInd = gridHeight - (i - bottomLeft.getY()) - 1;
                    mapGrid.add(createLabel(simulation.getMap().objectAt(currPoint).toString()),
                            columnInd,
                            rowInd);
                }
            }
        }
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }
    public void notifyNextDay(Simulation sim) {
        Platform.runLater(() -> {
            drawMap(sim);
            messageLabel.setText("Dzień: " + sim.getDays());
            statsAnimalsNumVal.setText(String.valueOf(sim.getStats().aliveAnimalsCount()));
            statsPlantsNumVal.setText(String.valueOf(sim.getStats().grassesNumber()));
            statsAverageEnergyVal.setText(String.valueOf(sim.getStats().averageEnergy()));
            statsAverageAgeVal.setText(String.valueOf(sim.getStats().averageAgeOfDeath()));
            statsAverageChildrenCountVal.setText(String.valueOf(sim.getStats().averageChildrenCount()));
        });
    }
    @FXML
    public void onSimulationStartClicked() {

        SimulationConfig config = SimulationConfig.get();

        Simulation simulation = new Simulation(config);
        Platform.runLater(() -> {
            statsAnimalsNum.setText("Animals Number");
            statsPlantsNum.setText("Plants number");
            statsAverageAge.setText("Average lifespan");
            statsAverageChildrenCount.setText("Average childcount");
            statsAverageEnergy.setText("Average energy");
        });
        this.setSimulation(simulation);
        simulation.registerObserver(this);
        SimulationEngine engine = new SimulationEngine(List.of(simulation));
        engine.runAsyncInThreadPool();
    }

    @FXML
    public void toggleSimulation(){
        this.simulation.toggle();
    }
}
