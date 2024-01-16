package Project.GUI;

import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
import Project.Model.WorldElements.Animals.Animal;
import Project.Model.WorldElements.Maps.MapChangeListener;
import Project.Simulations.Simulation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static Project.Model.WorldElements.Maps.WorldMap.MAP_BEGINNING;
import static java.util.Objects.isNull;

public class SimulationPresenter implements MapChangeListener, Initializable {

    // FXML
    @FXML
    private Label statsAnimalsNumVal;
    @FXML
    private Label statsPlantsNumVal;
    @FXML
    private Label statsAverageEnergyVal;
    @FXML
    private Label statsAverageAgeVal;
    @FXML
    private Label statsAverageChildrenCountVal;
    @FXML
    private Label statsEmptySpacesVal;
    @FXML
    private Label personalGenotypeVal;
    @FXML
    private Label personalActiveGeneVal;
    @FXML
    private Label personalEnergyVal;
    @FXML
    private Label personalPlantsEatenVal;
    @FXML
    private Label personalChildNumVal;
    @FXML
    private Label personalOffspringCountVal;
    @FXML
    private Label personalAgeVal;
    @FXML
    private Label personalDeathVal;
    @FXML
    private Label chosenPositionVal;
    @FXML
    private Label chosenAnimalVal;
    @FXML
    private Label messageLabel;
    @FXML
    private GridPane mapGrid = new GridPane();

    private Simulation simulation;
    private Animal animalChosen = null;

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

        int CELL_WIDTH = Math.round((float) 300 / (simulation.getMap().getWidth() + 1));
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        int CELL_HEIGHT = Math.round((float) 300 / (simulation.getMap().getHeight() + 1));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));

        // Creating axis x and axis y
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

        // Filling map with actual objects
        if(simulation.getConfig().getMapVariant() == 1) {
            for (int i = bottomLeft.getY(); i <= topRight.getY(); i++) {
                for (int j = bottomLeft.getX(); j <= topRight.getX(); j++) {
                    Vector2d currPoint = new Vector2d(j, i);
                    int columnInd = j - bottomLeft.getX() + 1;
                    int rowInd = gridHeight - (i - bottomLeft.getY()) - 1;

                    if (simulation.getMap().isOccupied(currPoint)) {
                        if (simulation.getMap().isAnimalAt(currPoint)) {
                            if (simulation.getMap().animalAt(currPoint).getEnergy() > 0.5 * simulation.getConfig().getStartingEnergy()) {
                                mapGrid.add(createLabel1("\uD83E\uDD8D"), columnInd, rowInd);
                            } else if (simulation.getMap().animalAt(currPoint).getEnergy() > 0.3 * simulation.getConfig().getStartingEnergy() && simulation.getMap().animalAt(currPoint).getEnergy() < 0.5 * simulation.getConfig().getStartingEnergy()) {
                                mapGrid.add(createLabel2("\uD83E\uDDA7"), columnInd, rowInd);
                            } else {
                                mapGrid.add(createLabel3("\uD83D\uDC12"), columnInd, rowInd);
                            }
                        } else {
                            mapGrid.add(createGrassCell("\uD83C\uDF4C"), columnInd, rowInd);
                        }
                    }
                }
            }
            String imagePath = getClass().getResource("/jungle.jpg").toExternalForm();
            mapGrid.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");
        }
        else {
            for (int i = bottomLeft.getY(); i <= topRight.getY(); i++) {
                for (int j = bottomLeft.getX(); j <= topRight.getX(); j++) {
                    Vector2d currPoint = new Vector2d(j, i);
                    int columnInd = j - bottomLeft.getX() + 1;
                    int rowInd = gridHeight - (i - bottomLeft.getY()) - 1;

                    // Create a label for the cell
                    Label cellLabel = createLabel(simulation.getMap().objectAt(currPoint).toString());

                    // Set the event handler for the mouse click
                    int finalJ = j;
                    int finalI = i;
                    cellLabel.setOnMouseClicked(event -> handleCellClick(finalJ, finalI));

                    // Add the label to the grid
                    mapGrid.add(cellLabel, columnInd, rowInd);

                    if (simulation.getMap().isOccupied(currPoint)) {
                        if (simulation.getMap().isAnimalAt(currPoint)) {
                            if (simulation.getMap().animalAt(currPoint).getEnergy() > 0.5 * simulation.getConfig().getStartingEnergy()) {
                                mapGrid.add(createLabel1("\uD83E\uDD81"), columnInd, rowInd);
                            } else if (simulation.getMap().animalAt(currPoint).getEnergy() > 0.3 * simulation.getConfig().getStartingEnergy() && simulation.getMap().animalAt(currPoint).getEnergy() < 0.5 * simulation.getConfig().getStartingEnergy()) {
                                mapGrid.add(createLabel2("\uD83D\uDC2F"), columnInd, rowInd);
                            } else {
                                mapGrid.add(createLabel3("\uD83D\uDC31"), columnInd, rowInd);
                            }
                        } else {
                            mapGrid.add(createGrassCell("\uD83E\uDD69"), columnInd, rowInd);
                        }
                    }
                }
            }
            String imagePath = getClass().getResource("/equator3.jpg").toExternalForm();
            mapGrid.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");
        }
    }

    private void handleCellClick(int x, int y) {
        this.animalChosen =  simulation.getMap().findAnimalWithMaxEnergy(simulation.getMap().getMapState().get(new Vector2d(x,y)));

        // Perform the desired action based on the x and y coordinates
        // For example, you can access the simulation and do something with the cell
        System.out.println("Clicked on cell at coordinates: (" + x + ", " + y + ")");
    }

    private Label createGrassCell(String text) {
        Label grassCell = new Label(text);
        GridPane.setHalignment(grassCell, HPos.CENTER);
        grassCell.setStyle("-fx-min-width: 20px; -fx-min-height: 20px; -fx-pref-width: 20px; -fx-pref-height: 20px; -fx-max-width: 20px; -fx-max-height: 20px; -fx-alignment: center;");
        return grassCell;
    }
    private Label createGrassCell2(String text) {
        Label grassCell = new Label(text);
        GridPane.setHalignment(grassCell, HPos.CENTER);
        grassCell.setStyle("-fx-min-width: 20px; -fx-min-height: 20px; -fx-pref-width: 20px; -fx-pref-height: 20px; -fx-max-width: 20px; -fx-max-height: 20px; -fx-alignment: center;");
        return grassCell;
    }
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-border-color: black;-fx-border-width: 1; -fx-min-width: 20px; -fx-min-height: 20px; -fx-pref-width: 20px; -fx-pref-height: 20px; -fx-max-width: 20px; -fx-max-height: 20px; -fx-alignment: center; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #f0f0f0;");
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }
    private Label createLabel1(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-min-width: 20px; -fx-min-height: 20px; -fx-pref-width: 20px; -fx-pref-height: 20px; -fx-max-width: 20px; -fx-max-height: 20px; -fx-alignment: center;");
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }
    private Label createLabel2(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-min-width: 20px; -fx-min-height: 20px; -fx-pref-width: 20px; -fx-pref-height: 20px; -fx-max-width: 20px; -fx-max-height: 20px; -fx-alignment: center;");
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }
    private Label createLabel3(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-min-width: 20px; -fx-min-height: 20px; -fx-pref-width: 20px; -fx-pref-height: 20px; -fx-max-width: 20px; -fx-max-height: 20px; -fx-alignment: center;");
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }

    public void notifyNextDay(Simulation sim) {
        Platform.runLater(() -> {
            drawMap(sim);
            setGeneralStats();
            if(this.animalChosen != null){
                setPersonalStats();
            }
        });
    }
    private void setPersonalStats(){
        chosenAnimalVal.setText(String.valueOf(animalChosen.getID()));
        chosenPositionVal.setText(String.valueOf(animalChosen.getPosition()));
        personalGenotypeVal.setText(Arrays.toString(animalChosen.getGenome()));
        personalActiveGeneVal.setText(String.valueOf(animalChosen.getActiveGene()));
        if(animalChosen.getStats().whenDied() > 0){
            personalAgeVal.setText(String.valueOf(animalChosen.getStats().whenDied()-animalChosen.getStats().whenBorn()));
        }
        else{
            personalAgeVal.setText(String.valueOf(simulation.getDays()-animalChosen.getStats().whenBorn()));
        }
        personalChildNumVal.setText(String.valueOf(animalChosen.getStats().childrenCount()));
        personalOffspringCountVal.setText(String.valueOf(animalChosen.getStats().offSpringCount(animalChosen)));
        if(animalChosen.getStats().whenDied() > -1){
            personalDeathVal.setText(String.valueOf(animalChosen.getStats().whenDied()));
        }
        personalEnergyVal.setText(String.valueOf(animalChosen.getEnergy()));
        personalPlantsEatenVal.setText(String.valueOf(animalChosen.getStats().howManyPlantsAte()));
    }
    private void setGeneralStats(){
        messageLabel.setText("Day: " + simulation.getDays());
        statsAnimalsNumVal.setText(String.valueOf(simulation.getStats().aliveAnimalsCount()));
        statsPlantsNumVal.setText(String.valueOf(simulation.getStats().grassesNumber()));
        statsAverageEnergyVal.setText(String.valueOf(round(simulation.getStats().averageEnergy(),2)));
        statsAverageAgeVal.setText(String.valueOf(round(simulation.getStats().averageAgeOfDeath(),2)));
        statsAverageChildrenCountVal.setText(String.valueOf(round(simulation.getStats().averageChildrenCount(),2)));
        statsEmptySpacesVal.setText(String.valueOf(simulation.getStats().emptySpaces()));
    }
    @FXML
    public void toggleSimulation(){
        this.simulation.toggle();
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SimulationConfig config = SimulationConfig.get();

        Simulation simulation = new Simulation(config);

        this.setSimulation(simulation);
        simulation.registerObserver(this);

        Thread thread = new Thread(simulation);
        thread.start();
    }
}
