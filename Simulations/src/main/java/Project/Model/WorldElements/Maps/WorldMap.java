package Project.Model.WorldElements.Maps;

import Project.Model.Core.MapState;
import Project.Model.Core.Vector2d;
import Project.Model.Util.GeneralStatistics;
import Project.Model.Util.MapVisualizer;
import Project.Model.WorldElements.Animals.Animal;
import Project.Model.WorldElements.Animals.AnimalStandard;
import Project.Model.WorldElements.Animals.AnimalVariant;
import Project.Model.WorldElements.Grass;
import Project.Model.WorldElements.MapObject;
import Project.Simulations.Simulation;

import java.util.*;

public abstract class WorldMap {
    public static final Vector2d MAP_BEGINNING = new Vector2d(0,0);
    protected final int width;
    protected final int height;
    protected final List<Animal> animals = new LinkedList<>();
    protected final MapState mapState = new MapState();
    protected final GeneralStatistics statistics = new GeneralStatistics(this);
    protected final Set<Vector2d> grassPositions = new HashSet<>();
    protected final LinkedList<Animal> deadAnimals = new LinkedList<>();
    protected final Simulation simulation;


    protected WorldMap(Simulation simulation){
        this.width = simulation.getConfig().getMapDimensions().getX();
        this.height = simulation.getConfig().getMapDimensions().getY();
        this.simulation = simulation;
    }

    //Simulation Helpers
    public MapObject objectAt(Vector2d position){
        LinkedList<Animal> animalsAtPosition = mapState.get(position);

        if (animalsAtPosition != null && !animalsAtPosition.isEmpty()) {
            return animalsAtPosition.getFirst();
        } else if (isPlantAt(position)) {
            return new Grass(position);
        }

        return null;
    }
    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }
    public boolean isInside(Vector2d position) {
        return position.getX() >= 0 && position.getX() <= width &&
                position.getY() >= 0 && position.getY() <= height;
    }
    public void place(Animal animal){
        mapState.put(animal);
        animals.add(animal);
    }
    public boolean isPlantAt(Vector2d position) {
        return grassPositions.contains(position);
    }
    public void placePlant(Grass grass){
        mapState.putPlant(grass);
        grassPositions.add(grass.getPosition());
    }
    public void removePlant(Vector2d position) {
        grassPositions.remove(position);
        mapState.removePlant(position);
    }
    public void animalDied(Animal animal){
        deadAnimals.add(animal);
        statistics.registerDeath(animal);
    }
    public boolean allDead(){
        return animals.isEmpty();
    }

    //Simulation Methods
    public void placeAnimals(int amount, Simulation simulation){
        for(int i=0;i<amount;i++){
            this.place(switch(simulation.getConfig().getBehaviourVariant()){
                case 1 -> new AnimalVariant(Vector2d.randomVector(this.width,this.height),simulation);
                default -> new AnimalStandard(Vector2d.randomVector(this.width,this.height),simulation);
            });
        }
    }
    public void moveAnimals() {

        for (Animal currAnimal : animals) {
            mapState.remove(currAnimal);

            if (currAnimal.move(this)) {
                mapState.put(currAnimal);
            }
        }
    }
    public void breedAnimals() {
        for(Map.Entry<Vector2d, LinkedList<Animal>> entry : mapState.entrySet()){
            LinkedList<Animal> animalsAtPosition = entry.getValue();
            if(animalsAtPosition.size() >= 2){
                Animal[] twoMostEnergetic = findTopTwoAnimals(animalsAtPosition);
                if(twoMostEnergetic[1].getEnergy() > simulation.getConfig().getBreedingEnergy()) {
                    Animal child = twoMostEnergetic[0].makeChild(twoMostEnergetic[1]);
                    this.place(child);
                }
            }
        }
    }
    public void animalsEat() {
        for (Map.Entry<Vector2d, LinkedList<Animal>> entry : mapState.entrySet()) {
            Vector2d position = entry.getKey();
            LinkedList<Animal> animalsAtPosition = entry.getValue();

            if (grassPositions.contains(position) && !animalsAtPosition.isEmpty()) {
                Animal animalToEat = findAnimalWithMaxEnergy(animalsAtPosition);
                animalToEat.eatPlant();
                grassPositions.remove(position);
                mapState.removePlant(position);

            }
        }
    }
    public void clearDeadAnimals(){
        for(Animal animal : deadAnimals){
            this.animals.remove(animal);
        }
        deadAnimals.clear();
    }

    //Map Boundaries Checker
    public boolean crossedThePole(Vector2d position){
        return position.getY() > this.height || position.getY() < 0;
    }
    public Vector2d aroundTheGlobe(Vector2d position){
        if(position.getX() < 0){return new Vector2d(width,position.getY());}
        if(position.getX() > width){return new Vector2d(0, position.getY());}
        return position;
    }

    //Getters
    public LinkedList<Animal> getDeadAnimals() {
        return deadAnimals;
    }
    public MapState getMapState(){
        return this.mapState;
    }
    public List<Animal> getAnimals() {
        return animals;
    }
    public int getWidth() {
        return width;
    }
    public int getGrassCount(){return grassPositions.size();}
    public int getHeight() {
        return height;
    }
    public String toString() {
        MapVisualizer map= new MapVisualizer(this);
        return map.draw(MAP_BEGINNING,new Vector2d(this.width,this.height));
    }

    //Helper
    private Animal findAnimalWithMaxEnergy(LinkedList<Animal> animals) {
        if (animals.isEmpty()) {
            return null;
        }

        Animal maxEnergyAnimal = animals.getFirst();
        for (Animal animal : animals) {
            maxEnergyAnimal = chooseBetterAnimal(maxEnergyAnimal, animal);
        }
        return maxEnergyAnimal;
    }
    private Animal chooseBetterAnimal(Animal maxEnergyAnimal, Animal animal) {
        if (animal.getEnergy() > maxEnergyAnimal.getEnergy()) {
            maxEnergyAnimal = animal;
        } else if (animal.getEnergy() == maxEnergyAnimal.getEnergy()) {
            if(simulation.getDays()-animal.getStats().whenBorn() > simulation.getDays()-maxEnergyAnimal.getStats().whenBorn()){
                maxEnergyAnimal = animal;
            }
            else if(simulation.getDays()-animal.getStats().whenBorn() == simulation.getDays()-maxEnergyAnimal.getStats().whenBorn()){
                if(animal.getStats().childrenCount() > maxEnergyAnimal.getStats().childrenCount()){
                    maxEnergyAnimal = animal;
                }
                else if(animal.getStats().childrenCount() == maxEnergyAnimal.getStats().childrenCount()){
                    double random = Math.random();
                    maxEnergyAnimal = random > 0.5 ? maxEnergyAnimal : animal;
                }
            }
        }
        return maxEnergyAnimal;
    }
    private Animal[] findTopTwoAnimals(LinkedList<Animal> animalList) {
        Animal[] topTwoAnimals = new Animal[2];
        topTwoAnimals[0] = findAnimalWithMaxEnergy(animalList);
        Animal secondBestAnimal = animalList.get(1);
        for (Animal animal : animalList) {
            if(animal == topTwoAnimals[0]){continue;}
            secondBestAnimal = chooseBetterAnimal(secondBestAnimal, animal);
        }
        topTwoAnimals[1] = secondBestAnimal;
        return topTwoAnimals;
    }


    //Abstract
    public abstract void spreadSeeds();

}

