package Project.Model.WorldElements;

import Project.Model.Core.Genome;
import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import Project.Simulation;



public class Animal implements MapObject {
    private Vector2d position;
    private MapDirection direction;
    private final Genome genome;
    private int energy;
    private final Simulation simulation;

    public Animal(Vector2d position, Simulation simulation){
        this(position, new Genome(simulation.getConfig().getGenomeLength()),simulation);
    }
    public Animal(Vector2d position, Genome genome, Simulation simulation){
        this.direction = MapDirection.randomDirection();
        this.position = position;
        this.genome = genome;
        this.energy = simulation.getConfig().getStartingEnergy();
        this.simulation = simulation;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public byte[] getGenome(){
        return this.genome.getGenes();
    }

    void move(WorldMap globe){
        this.direction = MapDirection.setDirection((this.direction.getValue()+genome.getGene())%8);
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        if(globe.crossedThePole(newPosition)){
            this.direction = MapDirection.setDirection((this.direction.getValue()+4)%8);
        }
        else{
            this.position = globe.aroundTheGlobe(newPosition);
        }
        this.genome.nextGene();
    }

    public String toString(){
        return direction.toString();
    }
}
