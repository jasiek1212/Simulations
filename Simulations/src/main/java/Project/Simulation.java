package Project;

import Project.Model.Core.SimulationConfig;
import Project.Model.WorldElements.WorldMap;

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
        WorldMap map = new WorldMap(config.getMapDimensions().getX(),config.getMapDimensions().getY());
        map.placeAnimals(config.getAnimalsNum(),this);
        for(int i=0;i<10;i++){
            map.moveAnimals();
            System.out.println(map);
        }




    }
}
