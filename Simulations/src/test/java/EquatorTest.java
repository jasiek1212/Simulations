import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
import Project.Model.WorldElements.Animals.Animal;
import Project.Model.WorldElements.Animals.AnimalStandard;
import Project.Model.WorldElements.Grass;
import Project.Model.WorldElements.Maps.Equator;
import Project.Simulations.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EquatorTest {
    private Equator equator;
    private Simulation simulation;

    @BeforeEach
    void setUp() {
        SimulationConfig config = new SimulationConfig(
                new Vector2d(5, 5),
                5,
                5,
                3,
                1,
                1,
                2,
                1,
                1,
                0,
                new Vector2d(0, 0),
                3);
        simulation = new Simulation(config);
        equator = new Equator(simulation);
    }

    @Test
    void testPreferredPositionsInitialization() {
        for (Vector2d position : equator.getPreferredPositions()) {
            assertTrue(position.getY() >= 4 && position.getY() <= 6);
        }
    }

    @Test
    void testUnpreferredPositionsInitialization() {
        for (Vector2d position : equator.getUnpreferredPositions()) {
            assertFalse(position.getY() >= 4 && position.getY() <= 6);
        }
    }
    @Test
    void testObjectAt() {
        Vector2d positionWithAnimal = new Vector2d(1, 1);
        Vector2d positionWithPlant = new Vector2d(2, 2);
        Vector2d emptyPosition = new Vector2d(3, 3);

        equator.place(new AnimalStandard(positionWithAnimal, simulation));
        equator.placePlant(new Grass(positionWithPlant));

        assertTrue(equator.objectAt(positionWithAnimal) instanceof Animal);
        assertTrue(equator.objectAt(positionWithPlant) instanceof Grass);
        assertNull(equator.objectAt(emptyPosition));
    }
    @Test
    void testIsOccupied() {
        Vector2d occupiedPosition = new Vector2d(1, 1);
        Vector2d unoccupiedPosition = new Vector2d(3, 3);

        equator.place(new AnimalStandard(occupiedPosition, simulation));

        assertTrue(equator.isOccupied(occupiedPosition));
        assertFalse(equator.isOccupied(unoccupiedPosition));
    }
    @Test
    void testPlaceAnimals() {
        int numberOfAnimals = 5;
        equator.placeAnimals(numberOfAnimals, simulation);

        assertEquals(numberOfAnimals, equator.getAnimals().size());
    }
    @Test
    void testIsInside() {
        Vector2d insidePosition = new Vector2d(5, 5);
        Vector2d outsidePosition = new Vector2d(11, 11);

        assertTrue(equator.isInside(insidePosition));
        assertFalse(equator.isInside(outsidePosition));
    }
    @Test
    void testIsPlantAt() {
        Vector2d positionWithPlant = new Vector2d(2, 2);
        Vector2d positionWithoutPlant = new Vector2d(3, 3);

        equator.placePlant(new Grass(positionWithPlant));

        assertTrue(equator.isPlantAt(positionWithPlant));
        assertFalse(equator.isPlantAt(positionWithoutPlant));
    }
    @Test
    void testRemovePlant() {
        Vector2d plantPosition = new Vector2d(2, 2);
        equator.placePlant(new Grass(plantPosition));
        equator.removePlant(plantPosition);

        assertFalse(equator.isPlantAt(plantPosition));
    }


}
