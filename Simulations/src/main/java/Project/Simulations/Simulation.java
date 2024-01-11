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
            case 1 -> new Jungle(this);
            default -> new Equator(this);
        };
        map.placeAnimals(config.getAnimalsNum(),this);
        System.out.println(map);


        while(!map.allDead()){
            System.out.println("Day: " + days);
            map.clearDeadAnimals();
            map.moveAnimals();
            map.animalsEat();
            map.breedAnimals();
            map.spreadSeeds();
            days++;
            System.out.println(map.getMapState());
            System.out.println(map);
        }

    }

    //Getters
    public SimulationConfig getConfig() {
        return config;
    }
    public int getDays() { return days;}
}
