package Project.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationMenu extends Application {

    @Override
    public void start(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        TextField mapWidthField = new TextField();
        mapWidthField.setPromptText("Szerokość mapy");
        TextField mapHeightField = new TextField();
        mapHeightField.setPromptText("Wysokość mapy");
        TextField mapAnimalsNum = new TextField();
        mapAnimalsNum.setPromptText("Liczba zwierząt");
        TextField mapGenomeLength = new TextField();
        mapGenomeLength.setPromptText("Genom");
        TextField mapStartingEnergy = new TextField();
        mapStartingEnergy.setPromptText("Startowa energia");
        TextField mapDailyEnergy = new TextField();
        mapDailyEnergy.setPromptText("Codzienna energia");
        TextField mapEnergyFromPlant = new TextField();
        mapEnergyFromPlant.setPromptText("Energia z rośliny");
        TextField mapNumberOfPlants = new TextField();
        mapNumberOfPlants.setPromptText("Liczba roślin");
        TextField mapStartingPlants = new TextField();
        mapStartingPlants.setPromptText("Startowa liczba roślin");
        TextField mapVariant = new TextField();
        mapVariant.setPromptText("Wariant Mapy");
        TextField mapBehaviourVariant = new TextField();
        mapBehaviourVariant.setPromptText("Rodzaj zachowania");
        TextField mapMaxMutationsNo = new TextField();
        mapMaxMutationsNo.setPromptText("Maks. liczba mutacji");
        TextField mapMinMutationsNo = new TextField();
        mapMinMutationsNo.setPromptText("Min. liczba mutacji");
        TextField mapBreedingEnergy = new TextField();
        mapBreedingEnergy.setPromptText("Energia rozmnażania");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Button startButtonWithNewConfig = new Button("Start simulation with this configuration");
        startButtonWithNewConfig.setOnAction(e -> startSimulation(List.of(mapWidthField.getText(), mapHeightField.getText(), mapAnimalsNum.getText(),
                mapGenomeLength.getText(), mapStartingEnergy.getText(), mapDailyEnergy.getText(), mapEnergyFromPlant.getText(),
                mapNumberOfPlants.getText(), mapStartingPlants.getText(), mapVariant.getText(), mapBehaviourVariant.getText(),
                mapMinMutationsNo.getText(), mapMaxMutationsNo.getText() ,mapBreedingEnergy.getText())));

        Button startButtonWithOldConfig = new Button("Start simulation with old configuration");
        startButtonWithOldConfig.setOnAction(e -> startSimWithOldConfig());

        // ... logika przycisku do uruchamiania symulacji z wybranymi ustawieniami

        layout.getChildren().addAll(new Label("Konfiguracja symulacji"), mapWidthField, mapHeightField, mapAnimalsNum, mapGenomeLength,
                mapStartingEnergy,mapDailyEnergy,mapEnergyFromPlant,mapNumberOfPlants,mapStartingPlants, mapVariant,
                mapBehaviourVariant, mapMinMutationsNo, mapMaxMutationsNo, mapBreedingEnergy, startButtonWithNewConfig,
                startButtonWithOldConfig);

        Scene scene = new Scene(layout, 600, 800);

    }
    private void startSimulation(List<String> args) {
        String errorMSG = "";

        for(String arg : args){
            try{
                Integer.parseInt(arg);
            }
            catch (NumberFormatException e) {
                errorMSG = errorMSG.concat(arg + "\n");
            }
        }
        try {
            changeJSON(args);
            showSimulationWindow();
        } catch (NumberFormatException | IOException | InterruptedException e) {
            System.out.println("Wprowadzono nieprawidłowe dane. Proszę spróbować ponownie. Błąd: " + errorMSG);
        }
    }
    private void startSimWithOldConfig(){
        try {
            showSimulationWindow();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void showSimulationWindow() throws IOException, InterruptedException {
        SimulationView sim = new SimulationView();
        sim.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void changeJSON(List<String> args){
        String filePath = new File("").getAbsolutePath();
        FileWriter file = null;
        List<String> definitions = new ArrayList<>(List.of("MapWidth","MapHeight","animalsNum","genomeLength",
                "startingEnergy","dailyEnergy","energyFromPlant","numberOfPlants","startingPlants","mapVariant",
                "behaviourVariant","minimumMutationsNo","maximumMutationsNo","breedingEnergy"));
        try {
            file = new FileWriter(filePath + "/src/main/java/Project/Model/Core/config.json");
            JSONObject json = new JSONObject();
            for(int i=0;i<definitions.size();i++){
                json.put(definitions.get(i),args.get(i));
            }

            file.write(json.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}