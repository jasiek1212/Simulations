package Project;
import Project.Model.Core.*;


public class World {

    public static void main(String[] args){
        SimulationConfig config = new SimulationConfig(new Vector2d(4,4),4,4,10,1);
        Simulation sim = new Simulation(config);
        sim.simulate();



    }



}
