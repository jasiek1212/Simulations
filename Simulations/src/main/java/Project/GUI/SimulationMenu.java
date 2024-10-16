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
        mapWidthField.setPromptText("Map width");
        TextField mapHeightField = new TextField();
        mapHeightField.setPromptText("Map height");
        TextField mapAnimalsNum = new TextField();
        mapAnimalsNum.setPromptText("Number of animals");
        TextField mapGenomeLength = new TextField();
        mapGenomeLength.setPromptText("Genome length");
        TextField mapStartingEnergy = new TextField();
        mapStartingEnergy.setPromptText("Starting energy");
        TextField mapDailyEnergy = new TextField();
        mapDailyEnergy.setPromptText("Movement energy cost");
        TextField mapEnergyFromPlant = new TextField();
        mapEnergyFromPlant.setPromptText("Energy gained from eating");
        TextField mapNumberOfPlants = new TextField();
        mapNumberOfPlants.setPromptText("Daily food increase");
        TextField mapStartingPlants = new TextField();
        mapStartingPlants.setPromptText("Starting amount of food");
        TextField mapVariant = new TextField();
        mapVariant.setPromptText("Map variant");
        TextField mapBehaviourVariant = new TextField();
        mapBehaviourVariant.setPromptText("Behaviour variant");
        TextField mapMaxMutationsNo = new TextField();
        mapMaxMutationsNo.setPromptText("Max mutations number");
        TextField mapMinMutationsNo = new TextField();
        mapMinMutationsNo.setPromptText("Min mutations number");
        TextField mapBreedingEnergy = new TextField();
        mapBreedingEnergy.setPromptText("Reproduction energy cost");

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Button startButtonWithNewConfig = new Button("Start simulation with this configuration");
        startButtonWithNewConfig.setOnAction(e -> startSimulation(List.of(mapWidthField.getText(), mapHeightField.getText(), mapAnimalsNum.getText(),
                mapGenomeLength.getText(), mapStartingEnergy.getText(), mapDailyEnergy.getText(), mapEnergyFromPlant.getText(),
                mapNumberOfPlants.getText(), mapStartingPlants.getText(), mapVariant.getText(), mapBehaviourVariant.getText(),
                mapMinMutationsNo.getText(), mapMaxMutationsNo.getText() ,mapBreedingEnergy.getText())));

        Button startButtonWithOldConfig = new Button("Start simulation with old configuration");
        startButtonWithOldConfig.setOnAction(e -> startSimWithOldConfig());

        // ... logika przycisku do uruchamiania symulacji z wybranymi ustawieniami

        layout.getChildren().addAll(new Label("Simulation configuration"), mapWidthField, mapHeightField, mapAnimalsNum, mapGenomeLength,
                mapStartingEnergy,mapDailyEnergy,mapEnergyFromPlant,mapNumberOfPlants,mapStartingPlants, mapVariant,
                mapBehaviourVariant, mapMinMutationsNo, mapMaxMutationsNo, mapBreedingEnergy, startButtonWithNewConfig,
                startButtonWithOldConfig,errorLabel);

        Scene scene = new Scene(layout, 600, 800);
        stage.setTitle("Simulation Menu");
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
            System.out.println("Incorrect input. Please try again.");
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
            file = new FileWriter(filePath + "/Simulations/src/main/java/Project/Model/Core/config.json");
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
                throw new Exception("Width must not be smaller than 2.\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong map width.\n");
            formatErrorNo += 1;

        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int mapHeight = Integer.parseInt(args.get(1));
            if(mapHeight<2){
                throw new Exception("Height must not be smaller than 2.\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong map height.\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int animalsNum = Integer.parseInt(args.get(2));
            if(animalsNum<1){
                throw new Exception("Number of animals must be a positive integer\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong animals number\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int genomeLength = Integer.parseInt(args.get(3));
            if(genomeLength<1){
                throw new Exception("Genotype must be a positive integer\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong genotype length\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int startingEnergy = Integer.parseInt(args.get(4));
            if(startingEnergy<1){
                throw new Exception("Starting energy must be positive\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong positive energy format\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int dailyEnergy = Integer.parseInt(args.get(5));
            if(dailyEnergy<1){
                throw new Exception("Movement energy cost must be positive\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong movement energy cost\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int plantEnergy = Integer.parseInt(args.get(6));
            if(plantEnergy<1){
                throw new Exception("Energy gained from food has to be positive\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong food energy gain\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }

        try{
            int dailyPlants = Integer.parseInt(args.get(7));
            if(dailyPlants<1){
                throw new Exception("Daily food increase has to be positive\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong daily food increase\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int startingPlants = Integer.parseInt(args.get(8));
            if(startingPlants<1){
                throw new Exception("Starting food amount has to be positive\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong starting food amount\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int mapVariant = Integer.parseInt(args.get(9));
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong map variant\n");
            formatErrorNo += 1;
        }
        try{
            int behaviourVariant = Integer.parseInt(args.get(10));
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong behaviour variant\n");
            formatErrorNo += 1;
        }
        try{
            int minMutations = Integer.parseInt(args.get(11));
            if(minMutations<0){
                throw new Exception("Minium number of mutations must be non-negative\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong min. of mutations\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int maxMutations = Integer.parseInt(args.get(12));
            if(maxMutations<0){
                throw new Exception("Maximum number of mutations must be non-negative\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong max. of mutations\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }
        try{
            int breedingEnergy = Integer.parseInt(args.get(13));
            if(breedingEnergy<0){
                throw new Exception("Reproduction energy cost must be positive\n");
            }
        } catch (NumberFormatException e){
            msg = msg.concat("Wrong reproduction energy cost\n");
            formatErrorNo += 1;
        } catch (Exception e){
            msg = msg.concat(e.getMessage());
            errorNo += 1;
        }

        String res = "Number of incorrectly typed data: " + errorNo + "\n" + "Number of incorreclty formatted numbers: " + formatErrorNo + "\n";
        if(errorNo > 0 || formatErrorNo > 0){
            throw new Exception(res.concat(msg));
        }
    }
}