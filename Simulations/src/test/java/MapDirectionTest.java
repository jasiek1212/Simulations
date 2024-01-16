import Project.Model.Core.Vector2d;
import Project.Model.Enums.MapDirection;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void testToUnitVector() {
        assertEquals(new Vector2d(0, 1), MapDirection.N.toUnitVector());
        assertEquals(new Vector2d(1, 1), MapDirection.NE.toUnitVector());
    }
    @Test
    void testSetDirection() {
        assertEquals(MapDirection.N, MapDirection.setDirection(0));
        assertEquals(MapDirection.SE, MapDirection.setDirection(3));

    }
    @Test
    void testGetValue() {
        assertEquals(0, MapDirection.N.getValue());
        assertEquals(3, MapDirection.SE.getValue());
    }
    @Test
    void testRandomDirection() {
        assertTrue(EnumSet.allOf(MapDirection.class).contains(MapDirection.randomDirection()));
    }

}
