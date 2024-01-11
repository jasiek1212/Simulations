package Project.Model.Util;

import Project.Model.WorldElements.Animals.Animal;
import Project.Simulations.Simulation;

import java.util.ArrayList;
import java.util.List;

public class PersonalizedStatistics {

    private final int dayOfBirth;
    private int dayOfDeath = -1;
    private List<Animal> children = new ArrayList<>();
    private Simulation simulation;
    private int plantsEaten = 0;

    public PersonalizedStatistics(Simulation simulation){

        this.dayOfBirth = simulation.getDays();
        this.simulation = simulation;
    }

    public void atePlant(){plantsEaten += 1;}
    public int howManyPlantsAte(){return plantsEaten;}
    public int whenBorn(){return dayOfBirth;}
    public int whenDied(){return dayOfDeath;}
    public void registerBirth(Animal animal){
        children.add(animal);
    }
    public List<Animal> getChildren(){return children;}
    public int childrenCount(){return children.size();}
    public void die(){
        this.dayOfDeath = simulation.getDays();
    }
}
