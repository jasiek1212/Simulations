package Project.Model.Enums;

import Project.Model.Core.Vector2d;

import java.util.Random;

public enum MapDirection {

    N(0),NE(1),E(2),SE(3),S(4),SW(5),W(6),NW(7);

    private final int value;

    public Vector2d toUnitVector(){
        return switch (this){
            case N -> new Vector2d(0,1);
            case NE -> new Vector2d(1,1);
            case E -> new Vector2d(1,0);
            case SE -> new Vector2d(1,-1);
            case S -> new Vector2d(0,-1);
            case SW -> new Vector2d(-1,-1);
            case W -> new Vector2d(-1,0);
            case NW -> new Vector2d(-1,1);
        };
    }

    MapDirection(int value){
        this.value = value;
    }

    public static MapDirection setDirection(int value){
        try{
        return switch(value){
            case 0 ->  MapDirection.N;
            case 1 ->  MapDirection.NE;
            case 2 ->  MapDirection.E;
            case 3 ->  MapDirection.SE;
            case 4 ->  MapDirection.S;
            case 5 ->  MapDirection.SW;
            case 6 ->  MapDirection.W;
            case 7 -> MapDirection.NW;
            default ->
                throw new Exception("Wrong direction!!");
        };}
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    public int getValue(){
        return this.value;
    }

    public static MapDirection randomDirection() {
        byte random = (byte) new Random().nextInt(8);
        return setDirection(random);
    }
}
