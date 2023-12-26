package Project.Model.WorldElements;

import Project.Model.Core.MapState;
import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import Project.Model.Util.MapVisualizer;
import Project.Simulation;

import java.util.*;

public class WorldMap {

    public static final Vector2d MAP_BEGINNING = new Vector2d(0,0);

    public int getWidth() {
        return width;
    }

    private final int width;

    public int getHeight() {
        return height;
    }

    private final int height;

    private final List<Animal> animals = new LinkedList<>();

    private final MapState mapState = new MapState();

    public Set<Vector2d> getGrassPositions() {
        return grassPositions;
    }

    private final Set<Vector2d> grassPositions = new HashSet<>();

    private final LinkedList<Animal> deadAnimals = new LinkedList<>();

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
    }

    public MapObject objectAt(Vector2d position){
        List<Animal> animalsAtPosition = mapState.get(position);

        if (animalsAtPosition != null && !animalsAtPosition.isEmpty()) {
            return ((LinkedList<Animal>) animalsAtPosition).getFirst();
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
            this.place(new Animal(Vector2d.randomVector(this.width,this.height),simulation));
        }
    }
    public void spreadSeeds(WorldMap map, Simulation simulation, int numberOfPlants) {
        Random random = new Random();

        int equatorStart = (int) (height * 0.4);
        int equatorEnd = (int) (height * 0.6);

        List<Vector2d> preferredPositions = new ArrayList<>();
        List<Vector2d> unpreferredPositions = new ArrayList<>();

        for (int j = equatorStart; j <= equatorEnd; j++) {
            for (int i = 0; i < width; i++) {
                preferredPositions.add(new Vector2d(i, j));
            }
        }

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (j < equatorStart || j > equatorEnd) {
                    unpreferredPositions.add(new Vector2d(i, j));
                }
            }
        }

        Collections.shuffle(unpreferredPositions);
        Collections.shuffle(preferredPositions);

        int j = 0;
        int k = 0;
        for (int i = 0; i < numberOfPlants; i++) {
            Vector2d position;
            if (random.nextDouble() < 0.8) {
                position = preferredPositions.get(j);
                j++;
                map.placePlant(new Grass(position));
            }
            else {
                position = unpreferredPositions.get(k);
                k++;
                map.placePlant(new Grass(position));
            }
        }
    }
    public void creepingJungle(WorldMap map) {
        List<Vector2d> directions = Arrays.asList(
                new Vector2d(1, 0), new Vector2d(1, 1), new Vector2d(0, 1),
                new Vector2d(-1, 1), new Vector2d(-1, 0), new Vector2d(-1, -1),
                new Vector2d(0, -1), new Vector2d(1, -1)
        );

        for (Vector2d plantPosition : grassPositions) {
                for (Vector2d direction : directions) {
                    Vector2d newPosition = plantPosition.add(direction);
                    if (!map.isPlantAt(newPosition) && map.isInside(newPosition)) {
                        map.placePlant(new Grass(newPosition));
                    }
                }
        }
    }
    public boolean isInside(Vector2d position) {
        return position.getX() >= 0 && position.getX() < width &&
                position.getY() >= 0 && position.getY() < height;
    }
//    public void eatPlants(Vector2d position) {
//        LinkedList<Animal> animalsAtPosition = mapState.get(position);
//
//        if (animalsAtPosition != null && !animalsAtPosition.isEmpty()) {
//            for (Animal animal : animalsAtPosition) {
//                animal.eatPlant(new Grass(animal.getPosition()));
//            }
//
//            removePlant(position);
//        }
//    }
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
    private void placePlant(Grass grass){
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
    }

    public boolean allDead(){
        return animals.isEmpty();
    }
    public void moveAnimals() {
        Set<Vector2d> eatenGrassPositions = new HashSet<>();

        for (Animal currAnimal : animals) {
            mapState.remove(currAnimal);

            if (currAnimal.move(this)) {
                Vector2d newPosition = currAnimal.getPosition();

                if (grassPositions.contains(newPosition)) {
                    currAnimal.eatPlant(new Grass(newPosition));
                    eatenGrassPositions.add(newPosition);
                    mapState.removePlant(new Grass(newPosition));
                }
                mapState.put(currAnimal);
            }
        }

        grassPositions.removeAll(eatenGrassPositions);
    }
    public void animalsEat() {
        for (Map.Entry<Vector2d, LinkedList<Animal>> entry : mapState.entrySet()) {
            Vector2d position = entry.getKey();
            LinkedList<Animal> animalsAtPosition = entry.getValue();

            if (grassPositions.contains(position) && !animalsAtPosition.isEmpty()) {
                Animal animalToEat = findAnimalWithMaxEnergy(animalsAtPosition);
                animalToEat.eatPlant(new Grass(position));
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

}
