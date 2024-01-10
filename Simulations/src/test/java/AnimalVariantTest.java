import Project.Model.Core.SimulationConfig;
import Project.Model.Core.Vector2d;
import Project.Model.WorldElements.Animals.AnimalVariant;
import Project.Model.WorldElements.Maps.Equator;
import Project.Model.WorldElements.Maps.WorldMap;
import Project.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnimalVariantTest {
    private AnimalVariant animal;
    private Simulation simulation;
    private SimulationConfig config;
    private Equator worldMap;


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
                1);
        simulation = new Simulation(config);
        animal = new AnimalVariant(new Vector2d(1, 1), simulation);
        worldMap = new Equator(10, 10);

    }

    @Test
    void testInitialization() {
        assertNotNull(animal.getPosition());
        assertNotNull(animal.getDirection());
        assertTrue(animal.getEnergy() > 0);
        assertNotNull(animal.getGenome());
    }
    @Test
    void testMoveWithoutEnoughEnergy() {
        animal.setEnergy(simulation.getConfig().getDailyEnergy() - 1);
        assertFalse(animal.move(worldMap));
    }
    @Test
    void testMoveWithGenomeDirectionChange() {
        Vector2d initialPosition = animal.getPosition();
        assertTrue(animal.move(worldMap));
        assertNotEquals(initialPosition, animal.getPosition());

    }
    @Test
    void testEnergyConsumptionOnMove() {
        int initialEnergy = animal.getEnergy();
        animal.move(worldMap);
        assertEquals(initialEnergy - simulation.getConfig().getDailyEnergy(), animal.getEnergy());
    }


}