package Game;


public class Collusion {

    public static boolean checkCollusion(float x1, float y1, float width1, float height1,
                                         float x2, float y2, float width2, float height2) {
        return x1 + width1 > x2 && x2 + width2 > x1 && y1 + height1 > y2 && y2 + height2 > y1;
    }

}
