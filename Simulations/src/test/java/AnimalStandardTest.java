import static org.junit.jupiter.api.Assertions.*;

import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
import Project.Model.WorldElements.Animals.AnimalStandard;
import Project.Model.WorldElements.Maps.Equator;
import Project.Simulations.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnimalStandardTest {
    private Simulation simulation;
    private AnimalStandard animal;
    private Equator worldMap;

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
        animal = new AnimalStandard(new Vector2d(2, 2), simulation);
        worldMap = new Equator(simulation);

    }

    @Test
    void testInitialization() {
        assertEquals(new Vector2d(2, 2), animal.getPosition());
        assertEquals(simulation.getConfig().getStartingEnergy(), animal.getEnergy());
        assertNotNull(animal.getDirection());
        assertNotNull(animal.getGenome());
    }
    @Test
    void testMove() {
        Vector2d initialPosition = animal.getPosition();
        assertTrue(animal.move(worldMap));
        assertNotEquals(initialPosition, animal.getPosition());
        while (animal.getEnergy() >= simulation.getConfig().getDailyEnergy()) {
            animal.eatPlant();
        }

        assertFalse(animal.move(worldMap));
    }
    @Test
    void testEatPlant() {
        int initialEnergy = animal.getEnergy();
        animal.eatPlant();
        assertTrue(animal.getEnergy() > initialEnergy);
    }

}

