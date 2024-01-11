import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
import Project.Model.WorldElements.Animals.Animal;
import Project.Model.WorldElements.Animals.AnimalStandard;
import Project.Model.WorldElements.Grass;
import Project.Model.WorldElements.Maps.Jungle;
import Project.Simulations.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JungleTest {
    private Jungle jungle;
    private SimulationConfig config;
    private Simulation simulation;
    @BeforeEach
    void setUp() {
        config = new SimulationConfig(
                new Vector2d(5,5),
                5,
                5,
                3,
                1,
                1,
                2,
                1,
                1,
                0,
                new Vector2d(0,0),
                3);
        simulation = new Simulation(config);
        jungle = new Jungle(10, 10);

    }

    @Test
    void testObjectAt() {
        Vector2d positionWithAnimal = new Vector2d(1, 1);
        Vector2d positionWithPlant = new Vector2d(2, 2);
        Vector2d emptyPosition = new Vector2d(3, 3);

        jungle.place(new AnimalStandard(positionWithAnimal, simulation));
        jungle.placePlant(new Grass(positionWithPlant));

        assertTrue(jungle.objectAt(positionWithAnimal) instanceof Animal);
        assertTrue(jungle.objectAt(positionWithPlant) instanceof Grass);
        assertNull(jungle.objectAt(emptyPosition));
    }

    @Test
    void testIsOccupied() {
        Vector2d occupiedPosition = new Vector2d(1, 1);
        Vector2d unoccupiedPosition = new Vector2d(3, 3);

        jungle.place(new AnimalStandard(occupiedPosition, simulation));

        assertTrue(jungle.isOccupied(occupiedPosition));
        assertFalse(jungle.isOccupied(unoccupiedPosition));
    }

    @Test
    void testPlaceAnimals() {
        int numberOfAnimals = 5;
        jungle.placeAnimals(numberOfAnimals, simulation);

        assertEquals(numberOfAnimals, jungle.getAnimals().size());
    }
    @Test
    void testIsInside() {
        Vector2d insidePosition = new Vector2d(5, 5);
        Vector2d outsidePosition = new Vector2d(11, 11);

        assertTrue(jungle.isInside(insidePosition));
        assertFalse(jungle.isInside(outsidePosition));
    }
    @Test
    void testIsPlantAt() {
        Vector2d positionWithPlant = new Vector2d(2, 2);
        Vector2d positionWithoutPlant = new Vector2d(3, 3);

        jungle.placePlant(new Grass(positionWithPlant));

        assertTrue(jungle.isPlantAt(positionWithPlant));
        assertFalse(jungle.isPlantAt(positionWithoutPlant));
    }
    @Test
    void testRemovePlant() {
        Vector2d plantPosition = new Vector2d(2, 2);
        jungle.placePlant(new Grass(plantPosition));
        jungle.removePlant(plantPosition);

        assertFalse(jungle.isPlantAt(plantPosition));
    }
}
