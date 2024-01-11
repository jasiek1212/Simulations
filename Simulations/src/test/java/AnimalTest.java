
import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import Project.Model.WorldElements.Animals.Animal;
import Project.Model.WorldElements.Animals.AnimalStandard;
import Project.Model.WorldElements.Maps.Equator;
import Project.Model.WorldElements.Maps.WorldMap;
import Project.Simulations.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {
    private Animal animal;
    private SimulationConfig config;
    private WorldMap map;
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
                1, 0, new Vector2d(0,0), 3);
        simulation = new Simulation(config);
        map = new Equator(10,10);
        animal = new AnimalStandard(new Vector2d(2, 2), simulation);
    }

    @Test
    void testMove() {
        Vector2d initialPosition = animal.getPosition();
        animal.move(map);
        Vector2d newPosition = animal.getPosition();
        assertNotEquals(initialPosition, newPosition);
    }

    @Test
    void testEatPlant() {
        int initialEnergy = animal.getEnergy();
        animal.eatPlant();
        assertTrue(animal.getEnergy() > initialEnergy);
    }

    @Test
    void testGetDirection() {
        assertNotNull(animal.getDirection());
        assertTrue(animal.getDirection() instanceof MapDirection);
    }
    @Test
    void testAnimalInitialization() {
        assertEquals(new Vector2d(2, 2), animal.getPosition());
        assertEquals(3, animal.getEnergy());
        assertNotNull(animal.getDirection());
        assertNotNull(animal.getGenome());
    }

    @Test
    void testMoveWithLessEnergy() {
        while (animal.getEnergy() >= config.getDailyEnergy()) {
            animal.eatPlant();
        }
        assertFalse(animal.move(map));
    }
    @Test
    void testGetGenome() {
        byte[] genome = animal.getGenome();
        assertNotNull(genome);
        assertEquals(config.getGenomeLength(), genome.length);
    }

    @Test
    void testToString() {
        String animalString = animal.toString();
        assertNotNull(animalString);
        assertFalse(animalString.isEmpty());
    }
}
