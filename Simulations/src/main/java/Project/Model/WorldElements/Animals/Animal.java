package Project.Model.WorldElements.Animals;

import Project.Model.Core.Genome;
import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import Project.Model.Util.PersonalizedStatistics;
import Project.Model.WorldElements.MapObject;
import Project.Model.WorldElements.Maps.WorldMap;
import Project.Simulations.Simulation;

public abstract class Animal implements MapObject {

    protected Vector2d position;
    protected MapDirection direction;

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    protected int energy;
    protected static int currId;
    static {
        int currId = 0;
    }
    protected final int id;
    protected final Simulation simulation;
    protected final Genome genome;
    protected final PersonalizedStatistics stats;
    protected Animal(Vector2d position, Simulation simulation) {
        this.direction = MapDirection.randomDirection();
        this.position = position;
        this.simulation = simulation;
        this.energy = simulation.getConfig().getStartingEnergy();
        this.id = currId++;
        this.genome = new Genome(simulation.getConfig().getGenomeLength());
        this.stats = new PersonalizedStatistics(simulation);
    }

    public Animal(Vector2d position, Genome genome1, int energy1, Genome genome2, int energy2, Simulation simulation){
        this.direction = MapDirection.randomDirection();
        this.position = position;
        this.genome = Genome.breed(genome1, energy1, genome2, energy2, simulation.getConfig());
        this.energy = simulation.getConfig().getBreedingEnergy()*2;
        this.simulation = simulation;
        this.id = currId++;
        this.stats = new PersonalizedStatistics(simulation);
    }

    public abstract Animal makeChild(Animal other);

    protected void tiredFromBreeding(){
        this.energy -= this.simulation.getConfig().getBreedingEnergy();
    }
    public PersonalizedStatistics getStats(){return stats;}

    public abstract boolean move(WorldMap globe);

    public Vector2d getPosition(){
        return this.position;
    }

    public MapDirection getDirection(){
        return this.direction;
    }

    public int getEnergy(){
        return this.energy;
    }

    public byte[] getGenome(){
        return this.genome.getGenes();
    }

    public String toString(){
        return String.valueOf(this.id);
    }

    public void die(){
        this.stats.die();
    }

    public void eatPlant() {
        this.energy += simulation.getConfig().getEnergyFromPlant();
        this.stats.atePlant();
    }


}
