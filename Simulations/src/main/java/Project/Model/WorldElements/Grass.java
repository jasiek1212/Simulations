package Project.Model.WorldElements;

import Project.Model.Core.Vector2d;

public class Grass implements MapObject{

    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }
    public Vector2d getPosition() {
        return this.position;
    }
    @Override
    public String toString() {
        return "*";
    }
}
