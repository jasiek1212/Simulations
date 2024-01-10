package Project.Model.WorldElements.Animals;

import Project.Model.Core.Genome;
import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import Project.Model.WorldElements.MapObject;
import Project.Model.WorldElements.Maps.WorldMap;
import Project.Simulation;

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
    protected Animal(Vector2d position, Simulation simulation) {
        this.direction = MapDirection.randomDirection();
        this.position = position;
        this.simulation = simulation;
        this.energy = simulation.getConfig().getStartingEnergy();
        this.id = currId++;
        this.genome = new Genome(simulation.getConfig().getGenomeLength());
    }

    public Animal(Vector2d position, Genome genome1, int energy1, Genome genome2, int energy2, Simulation simulation){
        this.direction = MapDirection.randomDirection();
        this.position = position;
        this.genome = Genome.breed(genome1, energy1, genome2, energy2);
        this.energy = simulation.getConfig().getStartingEnergy();
        this.simulation = simulation;
        this.id = currId++;
    }

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

    public void eatPlant() {
        this.energy += simulation.getConfig().getEnergyFromPlant();
    }


}
