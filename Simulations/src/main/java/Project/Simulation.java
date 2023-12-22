package Project;

import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
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

        while(!map.allDead()){
            System.out.println(map.getMapState());
            System.out.println(map.getMapState().get(new Vector2d(1,1)));
            System.out.println(map);
            map.moveAnimals();
            map.clearDeadAnimals();
        }




    }
}
