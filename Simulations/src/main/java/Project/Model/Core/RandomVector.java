package Project.Model.Core;

import java.util.Random;

public class RandomVector extends Vector2d{
    public RandomVector(int width, int height) {
        super(new Random().nextInt(width),new Random().nextInt(height));
    }
}
