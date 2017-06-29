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



public class Player extends Entity {

    public static float newX1;
    public static float newY1;

    //green


    public static final int	SPRITE_SCALE		= 16;
    public static final int	SPRITES_PER_HEADING	= 1;

    private enum Heading {
        NORTH(0 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST(6 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH(4 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST(2 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);

        private int	x, y, h, w;

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

    private Heading                    heading;
    private Map<Heading, Sprite>       spriteMap;
    public static float                scale;
    private float                      speed;
    private boolean                    firing;
    private long                       firingTimer;
    private long                       firingDelay;
    private int                        HP;

    public Player(float x, float y, float scale, float speed, int HP, Textures atlas) {
        super(EntityType.Player, x, y);

        heading = Heading.NORTH;
        spriteMap = new HashMap<Heading, Sprite>();
        this.scale = scale;
        this.speed = speed;
        this.HP = HP;

        firing = false;
        firingTimer = System.nanoTime();
        firingDelay = 500;  // сколько задержка между стрельбой в мс

        for (Heading h : Heading.values()) { //для каждого направления свой спрайт
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }

    }

    @Override
    public void update(Input input) {

        if (HP - Bullet.DMG_TO_FIRST == 0) Display.close();

        newX1 = x;
        newY1 = y;

        if (input.getKey(KeyEvent.VK_UP) &&
                !Collusion.checkCollusion(newX1, newY1 - speed, SPRITE_SCALE * scale, SPRITE_SCALE * scale, Player2.newX2, Player2.newY2, SPRITE_SCALE * scale, SPRITE_SCALE * scale)  &&
                !Level.bricks.stream().anyMatch(b -> Collusion.checkCollusion( newX1 , newY1 - speed ,SPRITE_SCALE * scale , SPRITE_SCALE * scale ,(float) b.getX() ,(float) b.getY() ,Level.TILE_IN_GAME ,Level.TILE_IN_GAME ))) {
            newY1 -= speed;
            heading = Heading.NORTH;
        } else if (input.getKey(KeyEvent.VK_RIGHT) &&
                !Collusion.checkCollusion(newX1 + speed, newY1, SPRITE_SCALE * scale, SPRITE_SCALE * scale, Player2.newX2, Player2.newY2, SPRITE_SCALE * scale, SPRITE_SCALE * scale) &&
                !Level.bricks.stream().anyMatch(b -> Collusion.checkCollusion( newX1 + speed , newY1 ,SPRITE_SCALE * scale , SPRITE_SCALE * scale ,(float) b.getX() ,(float) b.getY() ,Level.TILE_IN_GAME ,Level.TILE_IN_GAME ))) {
            newX1 += speed;
            heading = Heading.EAST;
        } else if (input.getKey(KeyEvent.VK_DOWN) &&
                !Collusion.checkCollusion(newX1, newY1 + speed, SPRITE_SCALE * scale, SPRITE_SCALE * scale, Player2.newX2, Player2.newY2, SPRITE_SCALE * scale, SPRITE_SCALE * scale) &&
                !Level.bricks.stream().anyMatch(b -> Collusion.checkCollusion( newX1  , newY1 + speed ,SPRITE_SCALE * scale , SPRITE_SCALE * scale ,(float) b.getX() ,(float) b.getY() ,Level.TILE_IN_GAME ,Level.TILE_IN_GAME ))) {
            newY1 += speed;
            heading = Heading.SOUTH;
        } else if (input.getKey(KeyEvent.VK_LEFT) &&
                !Collusion.checkCollusion(newX1 - speed, newY1, SPRITE_SCALE * scale, SPRITE_SCALE * scale, Player2.newX2, Player2.newY2, SPRITE_SCALE * scale, SPRITE_SCALE * scale) &&
                !Level.bricks.stream().anyMatch(b -> Collusion.checkCollusion( newX1 - speed , newY1 ,SPRITE_SCALE * scale , SPRITE_SCALE * scale ,(float) b.getX() ,(float) b.getY() ,Level.TILE_IN_GAME ,Level.TILE_IN_GAME ))) {
            newX1 -= speed;
            heading = Heading.WEST;
        }
        if (input.getKey(KeyEvent.VK_ENTER)) firing = true;



        if (firing) { //стрельба
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if (elapsed > firingDelay) {
                if (heading == Heading.NORTH) Game.bullets.add(new Bullet(270, newX1 + (SPRITE_SCALE * scale)/2 - 1, newY1 ));  //почему 23 а не 16?
                if (heading == Heading.EAST) Game.bullets.add(new Bullet(0, newX1 + (SPRITE_SCALE * scale) - 2, newY1 + (SPRITE_SCALE * scale)/2 - 1));
                if (heading == Heading.SOUTH) Game.bullets.add(new Bullet(90, newX1 + (SPRITE_SCALE * scale)/2 - 1, newY1 + (SPRITE_SCALE * scale) - 2));
                if (heading == Heading.WEST) Game.bullets.add(new Bullet(180, newX1, newY1 + (SPRITE_SCALE * scale)/2 - 1));
                firingTimer = System.nanoTime();
            }
            firing = false;
        }



        if (newX1 < 0) {     // не дает зайти за бортики
            newX1 = 0;
        } else if (newX1 >= Game.WIDTH - SPRITE_SCALE * scale) {
            newX1 = Game.WIDTH - SPRITE_SCALE * scale;
        }

        if (newY1 < 0) {      // не дает зайти за бортики
            newY1 = 0;
        } else if (newY1 >= Game.HEIGHT - SPRITE_SCALE * scale) {
            newY1 = Game.HEIGHT - SPRITE_SCALE * scale;
        }


        x = newX1;
        y = newY1;

    }


    @Override
    public void render(Graphics2D g) {
        spriteMap.get(heading).render(g, x, y);
    }

}
