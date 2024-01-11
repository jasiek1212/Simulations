package Project.Model.WorldElements.Animals;

import Project.Model.Core.Genome;
import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import Project.Model.WorldElements.Maps.WorldMap;
import Project.Simulations.Simulation;



public class AnimalStandard extends Animal {

    public AnimalStandard(Vector2d position, Simulation simulation){
        super(position, simulation);
    }
    public AnimalStandard(Vector2d position,
                          Genome genome1, int energy1, Genome genome2,
                          int energy2, Simulation simulation){
        super(position, genome1, energy1, genome2, energy2, simulation);
    }

    @Override
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

    @Override
    public Animal makeChild(Animal other){
        Animal child = new AnimalStandard(this.position,this.genome,this.energy,other.genome, other.energy,this.simulation);
        this.tiredFromBreeding();
        other.tiredFromBreeding();
        this.stats.registerBirth(child);
        other.stats.registerBirth(child);
        return child;
    }
}

