package Project.Model.WorldElements;

import Project.Model.Core.Vector2d;
import Project.Model.Util.MapVisualizer;

import java.util.Vector;

public class WorldMap {
    private final int width;
    private final int height;
    public static Vector2d MAP_BEGINNING = new Vector2d(0,0);

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
    }

    public Object objectAt(Vector2d position){
        return null;
    }

    public boolean isOccupied(Vector2d position){
        return false;
    }





    public String toString() {
        MapVisualizer map= new MapVisualizer(this);
        return map.draw(MAP_BEGINNING,new Vector2d(this.width,this.height));
    }
}
