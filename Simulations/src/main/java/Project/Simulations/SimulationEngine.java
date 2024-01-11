package Project.Simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine implements Runnable{

    private final List<Simulation> simulationList;

    private final List<Thread> threads = new ArrayList<>();

    ExecutorService executorService = Executors.newFixedThreadPool(4);
    public SimulationEngine(List<Simulation> simulationList){
        this.simulationList = simulationList;
    }

    @Override
    public void run() {
        System.out.println("Thread started.");
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        if(!threads.isEmpty()){
            for (Thread thread: threads) {
                thread.join();
            }
        }
        if(!executorService.isShutdown()){
            executorService.shutdown();
            if(!executorService.awaitTermination(10, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        }
    }

    public void runAsyncInThreadPool(){
        for(Simulation simulation: simulationList) {
            executorService.submit(simulation);
        }
    }
}
