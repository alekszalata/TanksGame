package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


import Display.Display;
import Graphics.Textures;
import Level.Level;
import Sprites.SpriteSheet;
import KeyBoard.Input;
import Sprites.Sprite;

public class Player2 extends Entity {

    //purple


    public static float newX2;
    public static float newY2;


    public static final int SPRITE_SCALE = 16;
    public static final int SPRITES_PER_HEADING = 1;
    public static boolean a;
    public static boolean b;
    public static boolean c;
    public static boolean d;

    public enum Heading {
        NORTH(8 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST(14 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH(12 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST(10 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);

        private int x, y, h, w;

        Heading(int x, int y, int h, int w) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        protected BufferedImage texture(Textures atlas) {
            return atlas.cut(x, y, w, h);
        }
    }

    private Heading heading;
    private Map<Heading, Sprite> spriteMap;
    public static float scale;
    public static float speed;
    private boolean firing;
    private long firingTimer;
    private long firingDelay;
    private int HP;

    public Player2(float x, float y, float scale, float speed, int HP, Textures atlas) {
        super(EntityType.Player, x, y);

        heading = Heading.SOUTH;
        spriteMap = new HashMap<Heading, Sprite>();
        this.scale = scale;
        this.speed = speed;
        this.HP = HP;

        firing = false;
        firingTimer = System.nanoTime();
        firingDelay = 500;

        for (Heading h : Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }

    }

    @Override
    public void update(Input input) {


        newX2 = x;
        newY2 = y;




        if (input.getKey(KeyEvent.VK_W) && !Collusion.checkCollusion(newX2, newY2 - speed, SPRITE_SCALE * scale, SPRITE_SCALE * scale, Player.newX1, Player.newY1, SPRITE_SCALE * scale, SPRITE_SCALE * scale) && !Level.bricks.stream().anyMatch(b -> Collusion.checkCollusion( newX2 , newY2 - speed ,SPRITE_SCALE * scale , SPRITE_SCALE * scale ,(float) b.getX() ,(float) b.getY() ,Level.TILE_IN_GAME ,Level.TILE_IN_GAME ))) {
            newY2 -= speed ;
            heading = Heading.NORTH;
        } else if (input.getKey(KeyEvent.VK_D) && !Collusion.checkCollusion(newX2 + speed, newY2, SPRITE_SCALE * scale, SPRITE_SCALE * scale, Player.newX1, Player.newY1, SPRITE_SCALE * scale, SPRITE_SCALE * scale) && !Level.bricks.stream().anyMatch(b -> Collusion.checkCollusion( newX2 + speed , newY2 ,SPRITE_SCALE * scale , SPRITE_SCALE * scale ,(float) b.getX() ,(float) b.getY() ,Level.TILE_IN_GAME ,Level.TILE_IN_GAME))) {
            newX2 += speed ;
            heading = Heading.EAST;
        } else if (input.getKey(KeyEvent.VK_S) && !Collusion.checkCollusion(newX2, newY2 + speed, SPRITE_SCALE * scale, SPRITE_SCALE * scale, Player.newX1, Player.newY1, SPRITE_SCALE * scale, SPRITE_SCALE * scale) && !Level.bricks.stream().anyMatch(b -> Collusion.checkCollusion( newX2 , newY2 + speed ,SPRITE_SCALE * scale , SPRITE_SCALE * scale ,(float) b.getX() ,(float) b.getY() ,Level.TILE_IN_GAME ,Level.TILE_IN_GAME))) {
            newY2 += speed ;
            heading = Heading.SOUTH;
        } else if (input.getKey(KeyEvent.VK_A) && !Collusion.checkCollusion(newX2 - speed, newY2, SPRITE_SCALE * scale, SPRITE_SCALE * scale, Player.newX1, Player.newY1, SPRITE_SCALE * scale, SPRITE_SCALE * scale) && !Level.bricks.stream().anyMatch(b -> Collusion.checkCollusion( newX2 - speed , newY2 ,SPRITE_SCALE * scale , SPRITE_SCALE * scale ,(float) b.getX() ,(float) b.getY() ,Level.TILE_IN_GAME ,Level.TILE_IN_GAME))) {
            newX2 -= speed;
            heading = Heading.WEST;
        }
        if (input.getKey(KeyEvent.VK_SPACE)) firing = true;



        if (HP - Bullet.DMG_TO_SECOND == 0) Display.close();



        if (firing) {
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if (elapsed > firingDelay) {
                if (heading == Heading.NORTH) Game.bullets.add(new Bullet(270, newX2 + (SPRITE_SCALE * scale)/2 - 1, newY2 ));  //почему 23 а не 24?
                if (heading == Heading.EAST) Game.bullets.add(new Bullet(0, newX2 + (SPRITE_SCALE * scale) - 2, newY2 + (SPRITE_SCALE * scale)/2 - 1));
                if (heading == Heading.SOUTH) Game.bullets.add(new Bullet(90, newX2 + (SPRITE_SCALE * scale)/2 - 1, newY2 + (SPRITE_SCALE * scale) - 2));
                if (heading == Heading.WEST) Game.bullets.add(new Bullet(180, newX2, newY2 + (SPRITE_SCALE * scale)/2 - 1));
                firingTimer = System.nanoTime();
            }
            firing = false;
        }


        if (newX2 < 0) {
            newX2 = 0;
        } else if (newX2 >= Game.WIDTH - SPRITE_SCALE * scale) {
            newX2 = Game.WIDTH - SPRITE_SCALE * scale;
        }

        if (newY2 < 0) {
            newY2 = 0;
        } else if (newY2 >= Game.HEIGHT - SPRITE_SCALE * scale) {
            newY2 = Game.HEIGHT - SPRITE_SCALE * scale;
        }


        x = newX2;
        y = newY2;

    }



    @Override
    public void render(Graphics2D g) {
        spriteMap.get(heading).render(g, x, y);
    }

}
