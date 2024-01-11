package Project.Simulations;

import Project.Model.Core.SimulationConfig;
import Project.Model.WorldElements.Maps.Equator;
import Project.Model.WorldElements.Maps.Jungle;
import Project.Model.WorldElements.Maps.WorldMap;

public class Simulation implements Runnable {

    private final SimulationConfig config;
    int days = 0;
    public Simulation(SimulationConfig config){
        this.config = config;
    }

    //Run simulation
    public void run(){
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
            map.clearDeadAnimals();
            map.moveAnimals();
            map.animalsEat();
            map.breedAnimals(config.getBreedingEnergy());
            map.spreadSeeds(config.getNumberOfPlants());
            days++;
        }

    }

    //Getters
    public SimulationConfig getConfig() {
        return config;
    }
    public int getDays() { return days;}
}
