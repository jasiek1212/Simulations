package Project.Model.WorldElements.Maps;

import Project.Model.Core.Vector2d;
import Project.Model.WorldElements.Grass;

import java.util.*;

public class Equator extends WorldMap{

    private List<Vector2d> preferredPositions = new ArrayList<>();
    private List<Vector2d> unpreferredPositions = new ArrayList<>();


    public Equator(int width, int height){
        super(width, height);
        initializePositions();
    }

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
    public void spreadSeeds(int numberOfPlants) {
        Random random = new Random();

        for (int i = 0; i < numberOfPlants; i++) {
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
                    preferredPositions.remove(position);
                    unpreferredPositions.remove(position);
                }
            }
        }
    }

}
