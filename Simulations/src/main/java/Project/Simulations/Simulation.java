package Project.Simulations;

import Project.Model.Core.SimulationConfig;
import Project.Model.Util.GeneralStatistics;
import Project.Model.WorldElements.Maps.Equator;
import Project.Model.WorldElements.Maps.Jungle;
import Project.Model.WorldElements.Maps.MapChangeListener;
import Project.Model.WorldElements.Maps.WorldMap;

import java.util.LinkedList;
import java.util.List;

public class Simulation implements Runnable {

    private final SimulationConfig config;
    private final WorldMap map;
    private final GeneralStatistics stats;
    private volatile boolean isRunning = true;
    private final List<MapChangeListener> observers = new LinkedList<>();
    int days = 0;
    public Simulation(SimulationConfig config){
        this.config = config;
        this.map = switch(config.getMapVariant()){
            case 1 -> new Jungle(this);
            default -> new Equator(this);
        };
        this.stats = new GeneralStatistics(map);
        map.placeAnimals(config.getAnimalsNum(),this);
    }

    public void registerObserver(MapChangeListener observer){
        observers.add(observer);
    }

    public void dayPassed(){
        days++;
        for(MapChangeListener observer : observers){
            observer.notifyNextDay(this);
        }
    }

    //Run simulation
    public void run(){

        while(!map.allDead()){
            try {
                synchronized (this) {
                    while (!isRunning) {
                        wait();  // Wait until the isRunning flag is true
                    }
                }
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            map.clearDeadAnimals();
            map.moveAnimals();
            map.animalsEat();
            map.breedAnimals();
            map.spreadSeeds();
            dayPassed();
        }
    }

    public synchronized void toggle(){
        isRunning = !isRunning;
        notify();
    }

    //Getters
    public SimulationConfig getConfig() {
        return config;
    }
    public int getDays() { return days;}
    public WorldMap getMap(){
        return map;
    }

    public GeneralStatistics getStats(){return stats;}
}
