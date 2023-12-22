package Project;
import Project.Model.Core.*;


public class World {

    public static void main(String[] args){
        SimulationConfig config = new SimulationConfig(new Vector2d(1,1),3,1,5,2);
        Simulation sim = new Simulation(config);
        sim.simulate();



    }



}
