    package Project.Model.WorldElements.Maps;

    import Project.Model.Core.Vector2d;
    import Project.Model.Enums.MapDirection;
    import Project.Model.WorldElements.Grass;
    import Project.Simulations.Simulation;

    import java.util.*;

    public class Jungle extends WorldMap{
        private final List<Vector2d> preferredPositions = new ArrayList<>();
        private final List<Vector2d> unpreferredPositions = new ArrayList<>();

        public Jungle(Simulation simulation){
            super(simulation);
            initializePositions();
            spreadSeeds();
        }

        public void spreadSeeds() {
            Random random = new Random();

            for(int i = 0; i < simulation.getConfig().getNumberOfPlants(); i++){
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

        //Getters
        public List<Vector2d> getUnpreferredPositions() {
            return unpreferredPositions;
        }
        public List<Vector2d> getPreferredPositions() {
            return preferredPositions;
        }

        //Helpers
        private List<Vector2d> getNeighbor(Vector2d position){
            List<Vector2d> neighborlist = new ArrayList<>();
            for(int i = 0; i <= 7; i++){
                Vector2d newPosition = position.add(MapDirection.setDirection(i).toUnitVector());
                if(newPosition.precedes(new Vector2d(width, height)) && newPosition.follows(MAP_BEGINNING)){
                    neighborlist.add(newPosition);
                }
            }
            return neighborlist;
        }
        private void initializePositions() {

            for (int i = 0; i <= height; i++) {
                for (int j = 0; j <= width; j++) {
                    Vector2d position = new Vector2d(i, j);
                    if (!grassPositions.contains(position)) {
                        unpreferredPositions.add(position);
                    }
                }
            }

            for (Vector2d plant : grassPositions) {
                for (Vector2d neighbour : getNeighbor(plant)) {
                    if (!grassPositions.contains(neighbour) && !preferredPositions.contains(neighbour)) {
                        preferredPositions.add(neighbour);
                        unpreferredPositions.remove(neighbour);
                    }
                }
            }
        }
    }
