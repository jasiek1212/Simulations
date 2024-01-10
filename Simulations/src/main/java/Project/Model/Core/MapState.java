package Project.Model.Core;

import Project.Model.WorldElements.Grass;
import Project.Model.WorldElements.Animals.Animal;


import java.util.*;

public class MapState{

    private final Map<Vector2d, LinkedList<Animal>> state;

    public Map<Vector2d, Grass> getPlants() {
        return plants;
    }

    private final Map<Vector2d, Grass> plants;

    public MapState(){
        this.state = new HashMap<>();
        this.plants = new HashMap<>();
    }

    public LinkedList<Animal> get(Vector2d position){
        return this.state.get(position);
    }

    public void remove(Animal animal){
        if(state.get(animal.getPosition()).size()==1){state.remove(animal.getPosition());}
        else{state.get(animal.getPosition()).remove(animal);}
    }
    public void put(Animal animal){
        if(state.get(animal.getPosition()) != null){state.get(animal.getPosition()).add(animal);}
        else{ state.put(animal.getPosition(),new LinkedList<>(List.of(animal)));}
    }
    public void removePlant(Grass grass) {
        plants.remove(grass.getPosition());
    }

    public void putPlant(Grass grass) {
        plants.put(grass.getPosition(), grass);
    }
    public String toString(){
        StringBuilder result = new StringBuilder();

        for (Map.Entry<Vector2d, LinkedList<Animal>> entry : state.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        for (Map.Entry<Vector2d, Grass> entry : plants.entrySet()) {
            result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        return result.toString();
    }
    public Set<Map.Entry<Vector2d, LinkedList<Animal>>> entrySet(){
        return this.state.entrySet();
    }
}

