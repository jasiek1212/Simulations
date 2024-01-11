import Project.Model.Core.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testConstructorAndGetters() {
        Vector2d vector = new Vector2d(5, 10);
        assertEquals(5, vector.getX());
        assertEquals(10, vector.getY());
    }

    @Test
    void testToString() {
        Vector2d vector = new Vector2d(3, 4);
        assertEquals("(3,4)", vector.toString());
    }

    @Test
    void testPrecedes() {
        Vector2d vector1 = new Vector2d(2, 3);
        Vector2d vector2 = new Vector2d(3, 4);
        assertTrue(vector1.precedes(vector2));
        assertFalse(vector2.precedes(vector1));
    }

    @Test
    void testFollows() {
        Vector2d vector1 = new Vector2d(5, 6);
        Vector2d vector2 = new Vector2d(4, 5);
        assertTrue(vector1.follows(vector2));
        assertFalse(vector2.follows(vector1));
    }

    @Test
    void testAdd() {
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(3, 4);
        Vector2d sum = vector1.add(vector2);
        assertEquals(new Vector2d(4, 6), sum);
    }
    @Test
    void testSubtract() {
        Vector2d vector1 = new Vector2d(5, 5);
        Vector2d vector2 = new Vector2d(2, 3);
        Vector2d result = vector1.subtract(vector2);
        assertEquals(new Vector2d(3, 2), result);
    }

    @Test
    void testUpperRightAndLowerLeft() {
        Vector2d vector1 = new Vector2d(1, 4);
        Vector2d vector2 = new Vector2d(3, 2);
        assertEquals(new Vector2d(3, 4), vector1.upperRight(vector2));
        assertEquals(new Vector2d(1, 2), vector1.lowerLeft(vector2));
    }

    @Test
    void testOpposite() {
        Vector2d vector = new Vector2d(3, -4);
        assertEquals(new Vector2d(-3, 4), vector.opposite());
    }

    @Test
    void testEqualsAndHashCode() {
        Vector2d vector1 = new Vector2d(3, 4);
        Vector2d vector2 = new Vector2d(3, 4);
        Vector2d vector3 = new Vector2d(4, 3);

        assertEquals(vector1, vector2);
        assertNotEquals(vector1, vector3);
        assertEquals(vector1.hashCode(), vector2.hashCode());
    }

    @Test
    void testRandomVector() {
        int width = 10, height = 10;
        Vector2d randomVector = Vector2d.randomVector(width, height);
        assertTrue(randomVector.getX() >= 0 && randomVector.getX() < width);
        assertTrue(randomVector.getY() >= 0 && randomVector.getY() < height);
    }
}
