package Game;

import java.util.ArrayList;

public class Collusion {

    public static int modelSize = 16;

    public static boolean checkCollusion(float x1, float y1, float width1, float height1,
                                         float x2, float y2, float width2, float height2) {
        return x1 + width1 * Player.scale > x2 && x2 + width2 * Player.scale > x1 && y1 + height1 * Player.scale > y2 && y2 + height2 * Player.scale > y1;
    }

    public static boolean checkShoot(float x1, float y1, float width1, float height1,
                                     float x2, float y2, float width2, float height2) {
        return x1 + width1 * Bullet.r == x2 && x2 + width2 * Player.scale == x1 && y1 + height1 * Bullet.r == y2 && y2 + height2 * Player.scale == y1;
    }
}
