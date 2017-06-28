package Game;

import java.awt.*;
import Level.Level;

import static Level.Level.tileMap;

/**
 * Created by aleks on 26.06.2017.
 */
public class Bullet {

    //fields
    public float x;
    public float y;
    public static int r;
    public static int DMG_TO_FIRST;
    public static int DMG_TO_SECOND;
    public static Integer[][] Bricks;


    private Color color1;

    private double speed;
    private double dx;
    private double dy;
    private double rad;



    //Constructor
    public Bullet(double angle, float x, float y) {
        Bricks = new Integer[Level.TILES_IN_WIDTH][Level.TILES_IN_HEIGHT];
        this.x = x;
        this.y = y;
        r = 3;

        speed = 6.5f;
        rad = Math.toRadians(angle);
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;
        Bricks = Level.tileMap;

        color1 = Color.white;
    }

    public boolean update() {
        x += dx;
        y += dy;

        if (Collusion.checkShoot(x, y, (float) r, (float) r, Player.newX1, Player.newY1, Player.SPRITE_SCALE, Player.SPRITE_SCALE))
            DMG_TO_FIRST += 1;
        if (Collusion.checkShoot(x, y, (float) r, (float) r, Player2.newX2, Player2.newY2, Player2.SPRITE_SCALE, Player2.SPRITE_SCALE))
            DMG_TO_SECOND += 1;

        for (int i = 0; i < Bricks.length; i++) {
            for (int j = 0; j < Bricks[i].length; j++) {
                if (Bricks[i][j] == 1) {
                    if (Collusion.checkShoot(x, y, (float) r, (float) r, j, i, Level.TILE_SCALE * Level.TILE_SIZE, Level.TILE_SCALE * Level.TILE_SIZE))
                        return true;
                }
            }
        }

        if (x < -r || x > Game.WIDTH + r ||
                y < -r || y > Game.HEIGHT + r ||
                Collusion.checkShoot(x, y, (float) r, (float) r, Player2.newX2, Player2.newY2, Player2.SPRITE_SCALE, Player2.SPRITE_SCALE) ||
                Collusion.checkShoot(x, y, (float) r, (float) r, Player.newX1, Player.newY1, Player.SPRITE_SCALE, Player.SPRITE_SCALE)
                ) {
            return true;
        }

        return false;
    }

    public void draw(Graphics2D g) {
        g.setColor(color1);
        g.fillOval((int) x - r, (int) y - r, 2 * r, 2 * r);

    }
}
