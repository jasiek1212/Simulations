package Project.Model.WorldElements.Maps;

import Project.Model.Core.Vector2d;
import Project.Model.WorldElements.Animals.Animal;
import Project.Model.WorldElements.Grass;
import Project.Simulations.Simulation;

import java.util.*;

public class Equator extends WorldMap{
    private final List<Vector2d> preferredPositions = new ArrayList<>();
    private final List<Vector2d> unpreferredPositions = new ArrayList<>();

    public Equator(Simulation simulation){
        super(simulation);
        initializePositions();
        spreadSeeds();
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

    public void spreadSeeds() {
        if(grassPositions.size() < ((height + 1) * (width + 1))){
            Random random = new Random();

            for (int i = 0; i < simulation.getConfig().getNumberOfPlants(); i++) {
                boolean placed = false;
                while (!placed) {
                    Vector2d position;
                    if (random.nextDouble() < 0.8 && !preferredPositions.isEmpty()) {
                        position = preferredPositions.get(random.nextInt(preferredPositions.size()));
                    } else if (!unpreferredPositions.isEmpty()) {
                        position = unpreferredPositions.get(random.nextInt(unpreferredPositions.size()));
                    } else {
                        break;
                    }

                    if (!grassPositions.contains(position)) {
                        this.placePlant(new Grass(position));
                        placed = true;
                    }
                }
            }
        }
    }

    //Getters
    public List<Vector2d> getUnpreferredPositions() {
        return unpreferredPositions;
    }
    public List<Vector2d> getPreferredPositions() {
        return preferredPositions;
    }

    //Helper
    private void initializePositions() {
        int equatorStart = (int) (height * 0.4);
        int equatorEnd = (int) (height * 0.6);

        for (int j = 0; j <= height; j++) {
            for (int i = 0; i <= width; i++) {
                Vector2d position = new Vector2d(i, j);
                if (j >= equatorStart && j <= equatorEnd) {
                    preferredPositions.add(position);
                } else {
                    unpreferredPositions.add(position);
                }
            }
        }
    }



}
