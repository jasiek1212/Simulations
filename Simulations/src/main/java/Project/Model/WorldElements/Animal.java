package Project.Model.WorldElements;

import Project.Model.Core.Vector2d;


public class Animal {
    private final Genome genome;
    private Vector2d position;

    public Animal(Vector2d position){
        this(position, new Genome());
    }
    public Animal(Vector2d position, Genome genome){
        this.position = position;
        this.genome = genome;
    }
}
