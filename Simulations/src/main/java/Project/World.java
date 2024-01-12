package Project;
import Project.GUI.SimulationMenu;
import Project.GUI.SimulationView;
import Project.Model.Core.*;

import Project.Simulations.Simulation;
import Project.Simulations.SimulationEngine;
import javafx.application.Application;

import java.util.List;

public class World {

    public static void main(String[] args) throws InterruptedException {

//        SimulationConfig config = SimulationConfig.get();
//
//        SimulationEngine engine = new SimulationEngine(List.of(new Simulation(config)));
//
//        engine.runAsyncInThreadPool();
//        engine.awaitSimulationsEnd();

        Application.launch(SimulationMenu.class, args);

    }
}
