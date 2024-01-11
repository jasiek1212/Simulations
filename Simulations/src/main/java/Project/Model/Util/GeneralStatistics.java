package Project.Model.Util;

import Project.Model.WorldElements.Animals.Animal;
import Project.Model.WorldElements.Maps.WorldMap;
import Project.Simulations.Simulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GeneralStatistics {
    private WorldMap map;

    private Set<Animal> deadAnimals = new HashSet<>();
    public GeneralStatistics(WorldMap map){
        this.map = map;
    }

    public int aliveAnimalsCount(){return map.getAnimals().size();}
    public int grassesNumber(){return map.getGrassCount();}
    public double averageEnergy(){
        if(map.getAnimals().isEmpty()){
            return 0;
        }
        double sum = 0;
        for(Animal animal : map.getAnimals()){
            sum += animal.getEnergy();
        }
        return sum/map.getAnimals().size();
    }

    public void registerDeath(Animal animal){
        animal.die();
        deadAnimals.add(animal);
    }

    public double averageAgeOfDeath(){
        if(deadAnimals.isEmpty()){
            return 0;
        }
        double sum = 0;
        for(Animal animal : deadAnimals){
            sum += animal.getStats().whenDied()-animal.getStats().whenBorn();
        }
        return sum/deadAnimals.size();
    }

    public int offSpringCount(Animal animal){
        return recursiveOffspringCounter(animal, new HashSet<>()).size();
    }
    private Set<Animal> recursiveOffspringCounter(Animal animal, Set<Animal> children){
        if(animal.getStats().getChildren().isEmpty()){return children;}
        for(Animal child : animal.getStats().getChildren()) {
            if (children.contains(child)) {
                continue;
            }
            children.add(child);
            children = recursiveOffspringCounter(animal, children);
        }
        return children;
    }

    public double averageChildrenCount(){
        if(map.getAnimals().isEmpty()){return 0;}
        double sum = 0;
        for(Animal animal : map.getAnimals()){
            sum += animal.getStats().childrenCount();
        }
        return sum/map.getAnimals().size();
    }
}
