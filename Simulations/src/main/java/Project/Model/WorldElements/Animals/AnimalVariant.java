package Project.Model.WorldElements.Animals;

import Project.Model.Core.Genome;
import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import Project.Model.WorldElements.WorldMap;
import Project.Simulation;

public class AnimalVariant extends Animal{

    private boolean genomeDirection = true;

    public AnimalVariant(Vector2d position, Simulation simulation){
        super(position, simulation);
    }
    public AnimalVariant(Vector2d position, Genome genome1, int energy1, Genome genome2, int energy2, Simulation simulation){
        super(position, genome1, energy1, genome2, energy2, simulation);
    }

    @Override
    public boolean move(WorldMap globe) {
        if(this.energy < simulation.getConfig().getDailyEnergy()){
            globe.animalDied(this);
            return false;
        }
        else {
            if(genomeDirection && (this.genome.getCurrentGeneIndex() == this.genome.size()-1)) {
                genomeDirection = false;
                return this.move(globe);
            } else if (!genomeDirection && (this.genome.getCurrentGeneIndex() == 0)) {
                genomeDirection = true;
                return this.move(globe);
            }
            this.direction = MapDirection.setDirection((this.direction.getValue() + genome.getGene()) % 8);
            Vector2d newPosition = this.position.add(this.direction.toUnitVector());
            if (globe.crossedThePole(newPosition)) {
                this.direction = MapDirection.setDirection((this.direction.getValue() + 4) % 8);
            } else {
                this.position = globe.aroundTheGlobe(newPosition);
            }
            if(genomeDirection){
                this.genome.nextGene();
            } else {
                this.genome.prevGene();
            }
            this.energy = this.energy-simulation.getConfig().getDailyEnergy();
            return true;
        }
    }
}
