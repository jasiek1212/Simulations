package Project.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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

    private final Text errorLabel = new Text();
    @Override
    public void start(Stage stage) {
        errorLabel.setFill(Color.RED);
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
                startButtonWithOldConfig,errorLabel);

        Scene scene = new Scene(layout, 600, 800);
        stage.setTitle("Menu Symulacji");
        stage.setScene(scene);
        stage.show();
    }
    private void startSimulation(List<String> args) {
        errorLabel.setText("");
        try {
            parseJSON(args);
            changeJSON(args);
            showSimulationWindow();
        } catch ( IOException | InterruptedException e) {
            System.out.println("Wprowadzono nieprawidłowe dane. Proszę spróbować ponownie.");
        } catch (Exception e){
            String[] msgToShowAllLines = e.getMessage().split("\n");
            String msgToShow = "";
            for(int i=0;i<Math.min(7, msgToShowAllLines.length);i++){
                msgToShow = msgToShow.concat(msgToShowAllLines[i] + "\n");
            }
            errorLabel.setText(msgToShow);
        }

    }
    private void startSimWithOldConfig(){
        errorLabel.setText("");
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
        FileWriter file;
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
    private void parseJSON(List<String> args) throws Exception{
        String msg = "";
        int errorNo = 0;
        int formatErrorNo = 0;
        try{
            int mapWidth = Integer.parseInt(args.get(0));
            if(mapWidth<2){
                throw new Exception("Szerokość nie może być mniejsza od 2.\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format szerokości mapy.\n");
            formatErrorNo += 1;

        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int mapHeight = Integer.parseInt(args.get(1));
            if(mapHeight<2){
                throw new Exception("Wysokość nie może być mniejsza od 2.\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format wysokości mapy.\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int animalsNum = Integer.parseInt(args.get(2));
            if(animalsNum<1){
                throw new Exception("Liczba zwierząt powinna być dodatnia\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format liczby zwierząt\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int genomeLength = Integer.parseInt(args.get(3));
            if(genomeLength<1){
                throw new Exception("Genotyp musi być dodatniej długości\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format długości genotypu\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int startingEnergy = Integer.parseInt(args.get(4));
            if(startingEnergy<1){
                throw new Exception("Startowa energia musi być dodatnia\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format energii startowej\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int dailyEnergy = Integer.parseInt(args.get(5));
            if(dailyEnergy<1){
                throw new Exception("Dzienna energia musi być dodatnia\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format ilości energii dziennej\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int plantEnergy = Integer.parseInt(args.get(6));
            if(plantEnergy<1){
                throw new Exception("Energia z rośliny musi być dodatnia\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format energii z rośliny\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }

        try{
            int dailyPlants = Integer.parseInt(args.get(7));
            if(dailyPlants<1){
                throw new Exception("Dzienny przyrost roślin musi być dodatnia\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format przyrostu roślin\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int startingPlants = Integer.parseInt(args.get(8));
            if(startingPlants<1){
                throw new Exception("Startowa liczba roślin musi być ujemna\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format energii z rośliny\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int mapVariant = Integer.parseInt(args.get(9));
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format wariantu mapy\n");
            formatErrorNo += 1;
        }
        try{
            int behaviourVariant = Integer.parseInt(args.get(10));
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format wariantu zachowania\n");
            formatErrorNo += 1;
        }
        try{
            int minMutations = Integer.parseInt(args.get(11));
            if(minMutations<0){
                throw new Exception("Minimalna liczba putacji musi być nieujemna\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format minimalnej liczby mutacji\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int maxMutations = Integer.parseInt(args.get(12));
            if(maxMutations<0){
                throw new Exception("Minimalna liczba putacji musi być nieujemna\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format minimalnej liczby mutacji\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int breedingEnergy = Integer.parseInt(args.get(13));
            if(breedingEnergy<0){
                throw new Exception("Energia rozmnażania musi być nieujemna\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Zły format energii rozmnażania\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }

        String res = "Ilość niepoprawnych danych: " + errorNo + "\n" + "Ilość niepoprawnych liczb: " + formatErrorNo + "\n";
        if(errorNo > 0 || formatErrorNo > 0){
            throw new Exception(res.concat(msg));
        }
    }
}