package Project.Model.Core;

public class SimulationConfig {

    private final Vector2d mapDimensions;
    private final int animalsNum;
    private final int genomeLength;
    private final int startingEnergy;
    private final int dailyEnergy;
    private final int energyFromPlant;

    private final int numberOfPlants;

    private final int mapVariant;

    public SimulationConfig(Vector2d mapDimensions, int animalsNum, int genomeLength, int startingEnergy, int dailyEnergy, int energyFromPlant, int numberOfPlants, int mapVariant){
        this.mapDimensions = mapDimensions;
        this.animalsNum = animalsNum;
        this.genomeLength = genomeLength;
        this.startingEnergy = startingEnergy;
        this.dailyEnergy = dailyEnergy;
        this.energyFromPlant = energyFromPlant;
        this.numberOfPlants = numberOfPlants;
        this.mapVariant = mapVariant;

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
}
