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

public class WorldMapTest {
    private Equator worldMap;
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
        worldMap = new Equator(10, 10);
    }
    @Test
    void testAnimalDied() {
        Animal animal = new AnimalStandard(new Vector2d(1, 1), simulation);
        worldMap.animalDied(animal);
        assertTrue(worldMap.getDeadAnimals().contains(animal));
    }

    @Test
    void testAllDead() {
        assertTrue(worldMap.allDead());
        worldMap.place(new AnimalStandard(new Vector2d(1, 1), simulation));
        assertFalse(worldMap.allDead());
    }

    @Test
    void testAnimalsEat() {
        Vector2d plantPosition = new Vector2d(2, 2);
        worldMap.placePlant(new Grass(plantPosition));
        Animal animal = new AnimalStandard(plantPosition, simulation);
        worldMap.place(animal);

        int initialEnergy = animal.getEnergy();
        worldMap.animalsEat();

        assertTrue(animal.getEnergy() > initialEnergy);
        assertFalse(worldMap.isPlantAt(plantPosition));
    }

    @Test
    void testClearDeadAnimals() {
        Animal animal = new AnimalStandard(new Vector2d(1, 1), simulation);
        worldMap.animalDied(animal);
        worldMap.clearDeadAnimals();
        assertFalse(worldMap.getAnimals().contains(animal));
    }
    @Test
    void testCrossedThePole() {
        assertTrue(worldMap.crossedThePole(new Vector2d(1, worldMap.getHeight() + 1)));
        assertFalse(worldMap.crossedThePole(new Vector2d(1, 1)));
    }
    @Test
    void testAroundTheGlobe() {
        assertEquals(new Vector2d(0, 1), worldMap.aroundTheGlobe(new Vector2d(worldMap.getWidth() + 1, 1)));
        assertEquals(new Vector2d(worldMap.getWidth(), 1), worldMap.aroundTheGlobe(new Vector2d(-1, 1)));
    }


}
