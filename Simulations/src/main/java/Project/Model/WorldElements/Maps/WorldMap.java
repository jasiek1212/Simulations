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


    protected WorldMap(int width, int height){
        this.width = width;
        this.height = height;
    }

    public List<Animal> getAnimals(){
        return animals;
    }
    public int getGrassCount(){return grassPositions.size();}

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

    public void placeAnimals(int amount, Simulation simulation){
        for(int i=0;i<amount;i++){
            this.place(switch(simulation.getConfig().getBehaviourVariant()){
                case 1 -> new AnimalVariant(Vector2d.randomVector(this.width,this.height),simulation);
                default -> new AnimalStandard(Vector2d.randomVector(this.width,this.height),simulation);
            });
        }
    }

    public boolean isInside(Vector2d position) {
        return position.getX() >= 0 && position.getX() < width &&
                position.getY() >= 0 && position.getY() < height;
    }
    public MapState getMapState(){
        return this.mapState;
    }
    private void place(Animal animal){
        mapState.put(animal);
        animals.add(animal);
    }
    public boolean isPlantAt(Vector2d position) {
        return grassPositions.contains(position);
    }
    protected void placePlant(Grass grass){
        mapState.putPlant(grass);
        grassPositions.add(grass.getPosition());
    }
    public void removePlant(Vector2d position) {
        Grass grass = mapState.getPlants().get(position);
        if (grass != null) {
            mapState.getPlants().remove(position);
            grassPositions.remove(position);
        }
    }
    public void animalDied(Animal animal){
        deadAnimals.add(animal);
        statistics.registerDeath(animal);
    }

    public boolean allDead(){
        return animals.isEmpty();
    }
    public void moveAnimals() {

        for (Animal currAnimal : animals) {
            mapState.remove(currAnimal);

            if (currAnimal.move(this)) {
                mapState.put(currAnimal);
            }
        }
    }

    public void breedAnimals(int breedingEnergy) {
        for(Map.Entry<Vector2d, LinkedList<Animal>> entry : mapState.entrySet()){
            LinkedList<Animal> animalsAtPosition = entry.getValue();
            if(animalsAtPosition.size() >= 2){
                Animal[] twoMostEnergetic = findTopTwoAnimals(animalsAtPosition);
                if(twoMostEnergetic[1].getEnergy() > breedingEnergy) {
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

            }
        }
    }

    private Animal findAnimalWithMaxEnergy(LinkedList<Animal> animals) {
        if (animals.isEmpty()) {
            return null;
        }

        Animal maxEnergyAnimal = animals.getFirst();
        for (Animal animal : animals) {
            if (animal.getEnergy() > maxEnergyAnimal.getEnergy()) {
                maxEnergyAnimal = animal;
            }
        }

        return maxEnergyAnimal;
    }

    private static Animal[] findTopTwoAnimals(LinkedList<Animal> animalList) {
        Animal[] topTwoAnimals = new Animal[2];
        int maxEnergy = Integer.MIN_VALUE;

        for (Animal animal : animalList) {
            int energy = animal.getEnergy();

            if (energy > maxEnergy) {
                topTwoAnimals[1] = topTwoAnimals[0];
                topTwoAnimals[0] = animal;
                maxEnergy = energy;
            } else if (topTwoAnimals[1] == null || energy > topTwoAnimals[1].getEnergy()) {
                topTwoAnimals[1] = animal;
            }
        }

        return topTwoAnimals;
    }
    public void clearDeadAnimals(){
        for(Animal animal : deadAnimals){
            this.animals.remove(animal);
        }
        deadAnimals.clear();
    }

    public boolean crossedThePole(Vector2d position){
        return position.getY() > this.height || position.getY() < 0;
    }

    public Vector2d aroundTheGlobe(Vector2d position){
        if(position.getX() < 0){return new Vector2d(width,position.getY());}
        if(position.getX() > width){return new Vector2d(0, position.getY());}
        return position;
    }

    public String toString() {
        MapVisualizer map= new MapVisualizer(this);
        return map.draw(MAP_BEGINNING,new Vector2d(this.width,this.height));
    }
    public abstract void spreadSeeds(int numberOfPlants);
}

