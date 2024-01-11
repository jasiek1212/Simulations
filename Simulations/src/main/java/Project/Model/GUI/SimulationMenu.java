package Project.Model.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;

public class SimulationMenu extends Application {

    @Override
    public void start(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        // Tworzenie kontrolek do wprowadzania danych konfiguracyjnych
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
        TextField mapMutationsNumber = new TextField();
        mapMutationsNumber.setPromptText("Liczba mutacji");
        TextField mapBreedingEnergy = new TextField();
        mapBreedingEnergy.setPromptText("Energia rozmnażania");

        // ... inne kontrolki dla różnych ustawień konfiguracyjnych

        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(e -> startSimulation(mapWidthField.getText(), mapHeightField.getText(), mapAnimalsNum.getText(),
                mapGenomeLength.getText(), mapStartingEnergy.getText(), mapDailyEnergy.getText(), mapEnergyFromPlant.getText(),
                mapNumberOfPlants.getText(), mapStartingPlants.getText(), mapVariant.getText(), mapBehaviourVariant.getText(),
                mapMutationsNumber.getText(), mapBreedingEnergy.getText()
        ));
        // ... logika przycisku do uruchamiania symulacji z wybranymi ustawieniami

        layout.getChildren().addAll(new Label("Konfiguracja symulacji"), mapWidthField, mapHeightField, startButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setTitle("Menu Symulacji");
        stage.setScene(scene);
        stage.show();
    }
    private void startSimulation(String width, String height, String animalsNum, String genomeLength, String startingEnergy, String dailyEnergy,
                                 String energyFromPlant, String numberOfPlants, String startingPlants, String mapVariant, String behaviourVariant, String mutationsNumber, String breedingEnergy) {
        try {
            // Parsowanie danych wejściowych do odpowiednich typów
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
            Vector2d mutationsNo = parseVector2d(mutationsNumber);
            int breedingEnergyInt = Integer.parseInt(breedingEnergy);

            // Utworzenie obiektu konfiguracyjnego
            SimulationConfig config = new SimulationConfig(
                    new Vector2d(mapWidth, mapHeight),
                    animalsNumInt,
                    genomeLengthInt,
                    startingEnergyInt,
                    dailyEnergyInt,
                    energyFromPlantInt,
                    numberOfPlantsInt,
                    startingPlantsInt,
                    mapVariantInt,
                    behaviourVariantInt,
                    mutationsNo,
                    breedingEnergyInt
            );
        } catch (NumberFormatException e) {
            // Obsługa wyjątku dla nieprawidłowych danych wejściowych
            System.out.println("Wprowadzono nieprawidłowe dane. Proszę spróbować ponownie.");
        }
        // ... logika uruchamiania symulacji z podaną konfiguracją
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
}
