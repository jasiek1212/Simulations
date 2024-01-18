package Project.Model.Util;

import Project.Model.WorldElements.Animals.Animal;
import Project.Simulations.Simulation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonalizedStatistics {

    private final int dayOfBirth;
    private int dayOfDeath = -1;
    private final List<Animal> children = new ArrayList<>();
    private final Simulation simulation;
    private int plantsEaten = 0;

    public PersonalizedStatistics(Simulation simulation){

        this.dayOfBirth = simulation.getDays();
        this.simulation = simulation;
    }

    //Change Stats
    public void atePlant(){plantsEaten += 1;}
    public void registerBirth(Animal animal){
        children.add(animal);
    }
    public void die(){
        this.dayOfDeath = simulation.getDays();
    }


    //Get Stats
    public int childrenCount(){return children.size();}
    public List<Animal> getChildren(){return children;}
    public int howManyPlantsAte(){return plantsEaten;}
    public int whenBorn(){return dayOfBirth;}
    public int whenDied(){return dayOfDeath;}
    public int offSpringCount(Animal animal){
        return recursiveOffspringCounter(animal, new HashSet<>()).size();
    }

    //Helper
    private Set<Animal> recursiveOffspringCounter(Animal animal, Set<Animal> children){
        if(animal.getStats().children.isEmpty()){return children;}
        for(Animal child : animal.getStats().children) {
            if (children.contains(child)) {
                continue;
            }
            children.add(child);
            children = recursiveOffspringCounter(child,children);
        }
        return children;
    }
}
