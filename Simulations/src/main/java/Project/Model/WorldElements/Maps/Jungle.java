    package Project.Model.WorldElements.Maps;

    import Project.Model.Core.Vector2d;
    import Project.Model.Enums.MapDirection;
    import Project.Model.WorldElements.Animals.Animal;
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
            if(grassPositions.size() < ((height + 1) * (width + 1)) && !grassPositions.isEmpty()) {
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
                            preferredPositions.remove(position);
                            unpreferredPositions.remove(position);
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
            List<Vector2d> positions = new ArrayList<>();
            for (int i = 0; i <= height; i++) {
                for (int j = 0; j <= width; j++) {
                    positions.add(new Vector2d(i, j));
                }
            }
            Collections.shuffle(positions);
            int k = 0;
            for(int i = 0; i < simulation.getConfig().getStartingNumberOfPlants();i++){
                if(!grassPositions.contains(positions.get(i)) && k < positions.size()){
                    grassPositions.add(positions.get(k));
                    k++;
                }
            }
            for(int i = k; i < positions.size(); i++){
                if(!grassPositions.contains(positions.get(i))){
                    unpreferredPositions.add(positions.get(i));
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

        public void animalsEat() {
            for (Map.Entry<Vector2d, LinkedList<Animal>> entry : mapState.entrySet()) {
                Vector2d position = entry.getKey();
                LinkedList<Animal> animalsAtPosition = entry.getValue();

                if (grassPositions.contains(position) && !animalsAtPosition.isEmpty()) {
                    Animal animalToEat = findAnimalWithMaxEnergy(animalsAtPosition);
                    animalToEat.eatPlant();
                    grassPositions.remove(position);
                    mapState.removePlant(position);

                    List<Vector2d> neightbourlist = getNeighbor(position);
                    for(Vector2d neightbour : neightbourlist){
                        if(grassPositions.contains(neightbour) && !preferredPositions.contains(position)){
                            preferredPositions.add(position);
                        }
                        else if(!unpreferredPositions.contains(position)){
                            unpreferredPositions.add(position);
                        }
                    }

                }
            }
        }
    }
