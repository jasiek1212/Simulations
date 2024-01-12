package Project.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        mapMaxMutationsNo.setPromptText("Maks. iczba mutacji");
        TextField mapMinMutationsNo = new TextField();
        mapMinMutationsNo.setPromptText("Min. liczba mutacji");
        TextField mapBreedingEnergy = new TextField();
        mapBreedingEnergy.setPromptText("Energia rozmnażania");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(e -> startSimulation(mapWidthField.getText(), mapHeightField.getText(), mapAnimalsNum.getText(),
                mapGenomeLength.getText(), mapStartingEnergy.getText(), mapDailyEnergy.getText(), mapEnergyFromPlant.getText(),
                mapNumberOfPlants.getText(), mapStartingPlants.getText(), mapVariant.getText(), mapBehaviourVariant.getText(),
                mapMinMutationsNo.getText(), mapMaxMutationsNo.getText() ,mapBreedingEnergy.getText()));
        // ... logika przycisku do uruchamiania symulacji z wybranymi ustawieniami

        layout.getChildren().addAll(new Label("Konfiguracja symulacji"), mapWidthField, mapHeightField, mapAnimalsNum, mapGenomeLength,
                mapStartingEnergy,mapDailyEnergy,mapEnergyFromPlant,mapNumberOfPlants,mapStartingPlants, mapVariant,
                mapBehaviourVariant, mapMinMutationsNo, mapMaxMutationsNo, mapBreedingEnergy, startButton);

        Scene scene = new Scene(layout, 800, 800);
        stage.setTitle("Menu Symulacji");
        stage.setScene(scene);
        stage.show();
    }
    private void startSimulation(String width, String height, String animalsNum, String genomeLength, String startingEnergy, String dailyEnergy,
                                 String energyFromPlant, String numberOfPlants, String startingPlants, String mapVariant, String behaviourVariant,
                                 String minMutationsNo, String maxMutationsNo, String breedingEnergy) {
        SimulationConfig config = null;
        try {
            int mapWidth = Integer.parseInt(width);
            int mapHeight = Integer.parseInt(height);
            int animalsNumInt = Integer.parseInt(animalsNum);
            int genomeLengthInt = Integer.parseInt(genomeLength);
            int startingEnergyInt = Integer.parseInt(startingEnergy);
            int dailyEnergyInt = Integer.parseInt(dailyEnergy);
            int energyFromPlantInt = Integer.parseInt(energyFromPlant);
            int numberOfPlantsInt = Integer.parseInt(numberOfPlants);
            int startingPlantsInt = Integer.parseInt(startingPlants);
            int mapVariantInt = Integer.parseInt(mapVariant);
            int behaviourVariantInt = Integer.parseInt(behaviourVariant);
            int minMutationsNoInt = Integer.parseInt(minMutationsNo);
            int maxMutationsNoInt = Integer.parseInt(maxMutationsNo);
            int breedingEnergyInt = Integer.parseInt(breedingEnergy);

            changeJSON(width, height, animalsNum, genomeLength, startingEnergy, dailyEnergy,
                    energyFromPlant, numberOfPlants, startingPlants, mapVariant, behaviourVariant, minMutationsNo, maxMutationsNo, breedingEnergy);

            showSimulationWindow();
        } catch (NumberFormatException | IOException | InterruptedException e) {
            System.out.println("Wprowadzono nieprawidłowe dane. Proszę spróbować ponownie.");
        }
    }

    private void showSimulationWindow() throws IOException, InterruptedException {
        SimulationView sim = new SimulationView();
        sim.start(new Stage());
    }
    private Vector2d parseVector2d(String input) {
        String[] parts = input.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Nieprawidłowy format danych dla Vector2d. Oczekiwano dwóch liczb oddzielonych przecinkiem.");
        }
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());
        return new Vector2d(x, y);}

    public static void main(String[] args) {
        launch(args);
    }

    public void changeJSON(String width, String height,
                             String animalsNum, String genomeLength,
                             String startingEnergy, String dailyEnergy,
                             String energyFromPlant, String numberOfPlants,
                             String startingPlants, String mapVariant,
                             String behaviourVariant, String minMutationsNo, String maxMutationsNo,
                             String breedingEnergy){
        String filePath = new File("").getAbsolutePath();
        FileWriter file = null;
        try {
            file = new FileWriter(filePath + "/src/main/java/Project/Model/Core/config.json");
            JSONObject json = new JSONObject();
            json.put("MapWidth", width);
            json.put("MapHeight", height);
            json.put("animalsNum", animalsNum);
            json.put("genomeLength", genomeLength);
            json.put("startingEnergy", startingEnergy);
            json.put("energyFromPlant",energyFromPlant);
            json.put("dailyEnergy", dailyEnergy);
            json.put("numberOfPlants", numberOfPlants);
            json.put("startingPlants",startingPlants);
            json.put("mapVariant",mapVariant);
            json.put("behaviourVariant",behaviourVariant);
            json.put("minimumMutationsNo", minMutationsNo);
            json.put("maximumMutationsNo", maxMutationsNo);
            json.put("breedingEnergy",breedingEnergy);

            file.write(json.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}