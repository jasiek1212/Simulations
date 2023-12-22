package Project.Model.WorldElements;

import Project.Model.Core.MapState;
import Project.Model.Core.RandomVector;
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
    //mapState przechowuje każdą pozycję na mapie która jest zajmowaną przez trawę lub zwierzę.
    //Może być wiele obiektów na jednej pozycji dlatego używamy listy, a linkedList dlatego, że
    //raczej będziemy dodawać i usuwać elementy, niż się do nich odwoływać (jeszcze nie wiem jak
    //z rozmnazaniem ale zawsze to mozna latwo zmienic na ArrayList albo coś innego)
    private final Set<Vector2d> grassPositions = new HashSet<>();
    private final Set<Vector2d> preferredPositions = new HashSet<>();
    private final Set<Vector2d> unPreferredPositions = new HashSet<>();

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
    }

    public LinkedList<Animal> objectAt(Vector2d position){
        return mapState.get(position);
    }

    public boolean isOccupied(Vector2d position){
        if(grassPositions.contains(position)){
            return true;
        }
        return mapState.get(position) != null;
    }

    public void placeAnimals(int amount, Simulation simulation){
        for(int i=0;i<amount;i++){
            this.place(new Animal(new RandomVector(width,height),simulation));
        }
    }
    private void place(Animal animal){
        mapState.put(animal);
        animals.add(animal);
    }

    public void moveAnimals(){
        for (Animal currAnimal : animals) {
            mapState.remove(currAnimal);
            currAnimal.move(this);
            mapState.put(currAnimal);
        }

    }

    public boolean crossedThePole(Vector2d position){
        return position.getY() >= this.height || position.getY() < 0;
    }

    public Vector2d aroundTheGlobe(Vector2d position){
        if(position.getX() < 0){return new Vector2d(width-1,position.getY());}
        if(position.getX() >= width){return new Vector2d(0, position.getY());}
        return position;
    }

    public void placeGrasses(int grassDailyIncrease){
        for(int i=0;i<grassDailyIncrease;i++){
            double placedOnPreferred = Math.random();
            if(placedOnPreferred >= 0.8){
                this.placeOnUnPreferred();
            }
            else{
                this.placeOnPreferred();
            }

        }
    }
    //placeGrasses kładzie codziennie grassDailyIncrease trawek
    private void placeOnPreferred(){
        Vector2d placedVector = new RandomVector(width,height);
        while(!preferredPositions.contains(placedVector)){
            placedVector = new RandomVector(width,height);
        }
        grassPositions.add(placedVector);
    }

    private void placeOnUnPreferred(){
        if(unPreferredPositions.isEmpty()){return;}
        Vector2d placedVector = new RandomVector(width,height);
        while(!unPreferredPositions.contains(placedVector)){
            placedVector = new RandomVector(width,height);
        }
        grassPositions.add(placedVector);
    }

    public String toString() {
        MapVisualizer map= new MapVisualizer(this);
        return map.draw(MAP_BEGINNING,new Vector2d(this.width,this.height));
    }

    private Map<Vector2d,List<MapObject>> generatePositions(){
        Map<Vector2d,List<MapObject>> mapPositions = new HashMap<>();
        for(int i=0;i<this.width;i++){
            for(int j=0;j<this.height;j++){
                mapPositions.put(new Vector2d(i,j),new ArrayList<MapObject>());
            }
        }
        return mapPositions;
    }
}
