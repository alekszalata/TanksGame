package Game;

import java.awt.*;
import Level.Level;
import Level.Tile;
import Level.TileType;

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

        if (Collusion.checkCollusion(x, y, (float) r * 2, (float) r * 2, Player.newX1, Player.newY1, Player.SPRITE_SCALE* Player.scale, Player.SPRITE_SCALE* Player2.scale))
            DMG_TO_FIRST += 1;
        if (Collusion.checkCollusion(x, y, (float) r * 2, (float) r *2, Player2.newX2 , Player2.newY2, Player2.SPRITE_SCALE * Player2.scale, Player2.SPRITE_SCALE * Player2.scale))
            DMG_TO_SECOND += 1;

        for (Point p: Level.bricks){
            if (Collusion.checkCollusion(x, y, (float) r * 2, (float) r *2, p.x , p.y, Level.TILE_IN_GAME, Level.TILE_IN_GAME)) return true;
        }


        if (x < -r || x > Game.WIDTH + r ||
                y < -r || y > Game.HEIGHT + r ||
                Collusion.checkCollusion(x, y, (float) r * 2, (float) r *2, Player2.newX2 , Player2.newY2, Player2.SPRITE_SCALE * Player2.scale, Player2.SPRITE_SCALE * Player2.scale) ||
                Collusion.checkCollusion(x, y, (float) r * 2, (float) r * 2, Player.newX1, Player.newY1, Player.SPRITE_SCALE* Player.scale, Player.SPRITE_SCALE* Player2.scale)
                ) {
            return true;
        }

        return false;
    }

    public void draw(Graphics2D g) {
        g.setColor(color1);
        g.fillOval((int) x - r, (int) y - r, 2 * r, 2 * r);

    }

    public void render(Graphics2D g) {

    }
}
