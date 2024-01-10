package Project;
import Project.Model.Core.*;

import java.io.File;


public class World {

    public static void main(String[] args){
        String filePath = new File("").getAbsolutePath();
        System.out.println (filePath);

        SimulationConfig config = new SimulationConfig(new Vector2d(1,1),6,1,1,1,0);
        Simulation sim = new Simulation(config);
        sim.simulate();



    }



}
