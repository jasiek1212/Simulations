package Project.Model.WorldElements;

import Project.Model.Core.MapState;
import Project.Model.Core.Vector2d;
import Project.Model.Util.MapVisualizer;
import Project.Simulation;

import java.util.*;

public class WorldMap {

    public static final Vector2d MAP_BEGINNING = new Vector2d(0,0);
    private final int width;
    private final int height;

    private final List<Animal> animals = new LinkedList<>();

    private final MapState mapState = new MapState();

    private final Set<Vector2d> grassPositions = new HashSet<>();
    private final Set<Vector2d> preferredPositions = new HashSet<>();
    private final Set<Vector2d> unPreferredPositions = new HashSet<>();

    private final LinkedList<Animal> deadAnimals = new LinkedList<>();

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
    }

    public LinkedList<Animal> objectAt(Vector2d position){
        return mapState.get(position);
    }


    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    public void placeAnimals(int amount, Simulation simulation){
        for(int i=0;i<amount;i++){
            this.place(new Animal(Vector2d.randomVector(this.width,this.height),simulation));
        }
    }

    public MapState getMapState(){
        return this.mapState;
    }
    private void place(Animal animal){
        mapState.put(animal);
        animals.add(animal);
    }

    public void animalDied(Animal animal){
        deadAnimals.add(animal);
    }

    public boolean allDead(){
        return animals.isEmpty();
    }
    public void moveAnimals(){
        for (Animal currAnimal : animals) {
            System.out.println(currAnimal.toString()+' '+ currAnimal.getDirection()+' '+ Arrays.toString(currAnimal.getGenome())+' '+currAnimal.getPosition());
            mapState.remove(currAnimal);
            if(currAnimal.move(this)){
                mapState.put(currAnimal);
            }
        }
    }

    public void clearDeadAnimals(){
        for(Animal animal : deadAnimals){
            this.animals.remove(animal);
        }
    }

    public boolean crossedThePole(Vector2d position){
        return position.getY() > this.height || position.getY() < 0;
    }

    public Vector2d aroundTheGlobe(Vector2d position){
        if(position.getX() < 0){return new Vector2d(width,position.getY());}
        if(position.getX() > width){return new Vector2d(0, position.getY());}
        return position;
    }

//    public void placeGrasses(int grassDailyIncrease){
//        for(int i=0;i<grassDailyIncrease;i++){
//            double placedOnPreferred = Math.random();
//            if(placedOnPreferred >= 0.8){
//                this.placeOnUnPreferred();
//            }
//            else{
//                this.placeOnPreferred();
//            }
//
//        }
//    }
    //placeGrasses kładzie codziennie grassDailyIncrease trawek
//    private void placeOnPreferred(){
//        Vector2d placedVector = new RandomVector(width,height);
//        while(!preferredPositions.contains(placedVector)){
//            placedVector = new RandomVector(width,height);
//        }
//        grassPositions.add(placedVector);
//    }
//
//    private void placeOnUnPreferred(){
//        if(unPreferredPositions.isEmpty()){return;}
//        Vector2d placedVector = new RandomVector(width,height);
//        while(!unPreferredPositions.contains(placedVector)){
//            placedVector = new RandomVector(width,height);
//        }
//        grassPositions.add(placedVector);
//    }

    public String toString() {
        MapVisualizer map= new MapVisualizer(this);
        return map.draw(MAP_BEGINNING,new Vector2d(this.width,this.height));
    }
}
