package Project.Model.Core;

import Project.Model.WorldElements.Animal;

import java.util.*;

public class MapState {

    private final Map<Vector2d, LinkedList<Animal>> state = new HashMap<>();

    public LinkedList<Animal> get(Vector2d position){
        return state.get(position);
    }

    public void remove(Animal animal){
        if(state.get(animal.getPosition()).size()==1){state.remove(animal.getPosition());}
        else{state.get(animal.getPosition()).remove(animal);}
    }
    public void put(Animal animal){
        if(state.get(animal.getPosition()) != null){state.get(animal.getPosition()).add(animal);}
        else{ state.put(animal.getPosition(),new LinkedList<>(List.of(animal)));}
    }
}

