package Project.Model.Core;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimulationConfig {

    private final Vector2d mapDimensions;
    private final int animalsNum;
    private final int genomeLength;
    private final int startingEnergy;
    private final int dailyEnergy;
    private final int energyFromPlant;

    private final int numberOfPlants;

    private final int behaviourVariant;

    private final int mapVariant;

    private final int startingNumberOfPlants;

    private final Vector2d mutationsNo;
    private final int breedingEnergy;

    public SimulationConfig(
            Vector2d mapDimensions,
            int animalsNum, int genomeLength,
            int startingEnergy, int dailyEnergy, int energyFromPlant,
            int numberOfPlants, int startingNumberOfPlants,
            int mapVariant, int behaviourVariant,
            Vector2d mutationsNo, int breedingEnergy){
        this.mapDimensions = mapDimensions;
        this.animalsNum = animalsNum;
        this.genomeLength = genomeLength;
        this.startingEnergy = startingEnergy;
        this.dailyEnergy = dailyEnergy;
        this.energyFromPlant = energyFromPlant;
        this.numberOfPlants = numberOfPlants;
        this.startingNumberOfPlants = startingNumberOfPlants;
        this.mapVariant = mapVariant;
        this.behaviourVariant = behaviourVariant;
        this.mutationsNo = mutationsNo;
        this.breedingEnergy = breedingEnergy;
    }

    public int getBreedingEnergy(){
        return breedingEnergy;
    }
    public int getBehaviourVariant(){
        return behaviourVariant;
    }

    public int getNumberOfPlants() {
        return numberOfPlants;
    }
    public int getMapVariant() {
        return mapVariant;
    }
    public Vector2d getMapDimensions() {
        return mapDimensions;
    }
    public int getAnimalsNum() {
        return animalsNum;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getStartingEnergy() {
        return startingEnergy;
    }

    public int getDailyEnergy() {
        return dailyEnergy;
    }

    public int getEnergyFromPlant() {return energyFromPlant;}

    public Vector2d getMutationsNo(){
        return this.mutationsNo;
    }

    public int getStartingNumberOfPlants(){return startingNumberOfPlants;}
    public static SimulationConfig get(){
        String filePath = new File("").getAbsolutePath();

        String jsonContent = null;
        try {
            jsonContent = new String(Files.readAllBytes(Paths.get(filePath + "/Simulations/src/main/java/Project/Model/Core/config.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse the JSON string
        JSONObject jsonObject = new JSONObject(jsonContent);

        return new SimulationConfig(
                new Vector2d(jsonObject.getInt("MapWidth"),jsonObject.getInt("MapHeight")),
                jsonObject.getInt("animalsNum"), jsonObject.getInt("genomeLength"),
                jsonObject.getInt("startingEnergy"), jsonObject.getInt("dailyEnergy"), jsonObject.getInt("energyFromPlant"),
                jsonObject.getInt("StartingNumberOfPlants"), jsonObject.getInt("dailyPlantsIncrease"),
                jsonObject.getInt("mapVariant"), jsonObject.getInt("behaviourVariant"),
                new Vector2d(jsonObject.getInt("minimumMutations"),jsonObject.getInt("maximumMutations")),
                jsonObject.getInt("breedingEnergy"));
    }
}
