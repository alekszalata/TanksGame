package Game;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


import Graphics.Textures;
import Sprites.SpriteSheet;
import KeyBoard.Input;
import Sprites.Sprite;

public class Player2 extends Entity {

    //purple


    public static float newX2;
    public static float newY2;


    public static final int	SPRITE_SCALE		= 16;
    public static final int	SPRITES_PER_HEADING	= 1;

    public enum Heading {
        NORTH(8 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST(14 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH(12 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST(10 * SPRITE_SCALE, 9 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);

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

    private Heading					heading;
    private Map<Heading, Sprite> spriteMap;
    private float					scale;
    private float					speed;
    private boolean firing;
    private long firingTimer;
    private long firingDelay;

    public Player2 (float x, float y, float scale, float speed, Textures atlas) {
        super(EntityType.Player , x, y);

        heading = Heading.NORTH;
        spriteMap = new HashMap<Heading, Sprite>();
        this.scale = scale;
        this.speed = speed;

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


        if (input.getKey(KeyEvent.VK_W) && !Collusion.checkCollusion(newX2 , newY2 - speed ,SPRITE_SCALE , SPRITE_SCALE , Player.newX1 , Player.newY1,SPRITE_SCALE , SPRITE_SCALE)) {
            newY2 -= speed;
            heading = Heading.NORTH;
        } else if (input.getKey(KeyEvent.VK_D) && !Collusion.checkCollusion(newX2 + speed , newY2 ,SPRITE_SCALE , SPRITE_SCALE , Player.newX1 , Player.newY1 ,SPRITE_SCALE , SPRITE_SCALE)) {
            newX2 += speed;
            heading = Heading.EAST;
        } else if (input.getKey(KeyEvent.VK_S) && !Collusion.checkCollusion(newX2 , newY2 + speed ,SPRITE_SCALE , SPRITE_SCALE , Player.newX1 , Player.newY1 ,SPRITE_SCALE , SPRITE_SCALE)) {
            newY2 += speed;
            heading = Heading.SOUTH;
        } else if (input.getKey(KeyEvent.VK_A) && !Collusion.checkCollusion(newX2 - speed , newY2 ,SPRITE_SCALE , SPRITE_SCALE , Player.newX1 , Player.newY1 ,SPRITE_SCALE , SPRITE_SCALE)) {
            newX2 -= speed;
            heading = Heading.WEST;
        }
        if (input.getKey(KeyEvent.VK_SPACE)) firing = true;

        if (firing) {
            long elapsed = (System.nanoTime() - firingTimer)/1000000;
            if (elapsed > firingDelay) {
                if (heading == Heading.NORTH) Game.bullets.add(new Bullet(270 , newX2 + 23 , newY2));  //почему 23 а не 16?
                if (heading == Heading.EAST) Game.bullets.add(new Bullet(0 , newX2 + 46 , newY2 + 23));
                if (heading == Heading.SOUTH) Game.bullets.add(new Bullet(90 , newX2 + 23 , newY2 + 46));
                if (heading == Heading.WEST) Game.bullets.add(new Bullet(180 , newX2 , newY2 + 23));
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
