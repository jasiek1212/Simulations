package Project.Model.Core;

import java.util.Objects;
import java.util.Random;

public class Vector2d{
    private final int x;
    private final int y;
    public Vector2d(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public String toString(){
        return "(%d,%d)".formatted(this.getX(),this.getY());
    }
    public boolean precedes(Vector2d other){
        return this.x<=other.x && this.y<=other.y;
    }
    public boolean follows(Vector2d other){
        return this.x>=other.x && this.y>=other.y;
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x,this.y+other.y);
    }
    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x-other.x,this.y-other.y);
    }
    public Vector2d upperRight(Vector2d other){
        int firstNum;
        int lastNum;
        firstNum = Math.max(this.x,other.x);
        lastNum = Math.max(this.y, other.y);
        return new Vector2d(firstNum,lastNum);
    }
    public Vector2d lowerLeft(Vector2d other){
        int firstNum;
        int lastNum;
        firstNum = Math.min(this.x,other.x);
        lastNum = Math.min(this.y,other.y);
        return new Vector2d(firstNum,lastNum);
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x,-this.y);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Vector2d randomVector(int width, int height){
        return new Vector2d(new Random().nextInt(width),new Random().nextInt(height));
    }

    int difference(){
        return this.y - this.x;
    }
}
