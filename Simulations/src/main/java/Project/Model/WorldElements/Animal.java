package Project.Model.WorldElements;

import Project.Model.Core.Genome;
import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import Project.Model.WorldElements.Maps.Equator;
import Project.Model.WorldElements.Maps.WorldMap;
import Project.Simulation;



public class Animal implements MapObject {
    private Vector2d position;
    private MapDirection direction;
    private final Genome genome;
    private int energy;

    private static int currId;

    private final int id;

    private final Simulation simulation;

    static {
        int currId = 0;
    }

    public Animal(Vector2d position, Simulation simulation){
        this(position, new Genome(simulation.getConfig().getGenomeLength()),simulation);
    }
    public Animal(Vector2d position, Genome genome, Simulation simulation){
        this.direction = MapDirection.randomDirection();
        this.position = position;
        this.genome = genome;
        this.energy = simulation.getConfig().getStartingEnergy();
        this.simulation = simulation;
        this.id = currId++;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public byte[] getGenome(){
        return this.genome.getGenes();
    }

    public boolean move(WorldMap globe){
        if(this.energy < simulation.getConfig().getDailyEnergy()){
            globe.animalDied(this);
            return false;
        }
        else {
            this.direction = MapDirection.setDirection((this.direction.getValue() + genome.getGene()) % 8);
            Vector2d newPosition = this.position.add(this.direction.toUnitVector());
            if (globe.crossedThePole(newPosition)) {
                this.direction = MapDirection.setDirection((this.direction.getValue() + 4) % 8);
            } else {
                this.position = globe.aroundTheGlobe(newPosition);
            }
            this.genome.nextGene();
            this.energy = this.energy-simulation.getConfig().getDailyEnergy();
            return true;
        }
    }

    public int getEnergy(){
        return this.energy;
    }

    public MapDirection getDirection(){
        return this.direction;
    }
    public void eatPlant() {
        this.energy += simulation.getConfig().getEnergyFromPlant();
    }
    public String toString(){
        return String.valueOf(this.id);
    }
}
