package Project;
import Project.Model.Core.*;


public class World {

    public static void main(String[] args){
        SimulationConfig config = new SimulationConfig(new Vector2d(1,1),6,1,1,1);
        Simulation sim = new Simulation(config);
        sim.simulate();



    }



}
