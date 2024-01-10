package Project;

import Project.Model.Core.SimulationConfig;
import Project.Model.WorldElements.Maps.Equator;
import Project.Model.WorldElements.Maps.Jungle;
import Project.Model.WorldElements.Maps.WorldMap;

public class Simulation {

    private final SimulationConfig config;
    //config: <mapDimensions, animalsNum, genomeLength, startingEnergy, dailyEnergy>
    private int days = 0;
    public Simulation(SimulationConfig config){
        this.config = config;
    }

    public SimulationConfig getConfig() {
        return config;
    }

    public void simulate(){
        WorldMap map = switch(config.getMapVariant()){
            case 0 -> new Equator(config.getMapDimensions().getX(), config.getMapDimensions().getY());
            case 1 -> new Jungle(config.getMapDimensions().getX(), config.getMapDimensions().getY());
            default -> new Equator(config.getMapDimensions().getX(), config.getMapDimensions().getY());
        };
        map.placeAnimals(config.getAnimalsNum(),this);
        System.out.println(map);


        while(!map.allDead()){
            System.out.println(map.getMapState());
            System.out.println(map);
            map.spreadSeeds(config.getNumberOfPlants());
            map.moveAnimals();
            map.animalsEat();
            map.clearDeadAnimals();
        }

    }
}
