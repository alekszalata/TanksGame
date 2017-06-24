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
import Game.Player;

public class Player2 extends Entity {

    //purple


    public static float newX2;
    public static float newY2;
    public static boolean collusion;

    public static final int	SPRITE_SCALE		= 16;
    public static final int	SPRITES_PER_HEADING	= 1;

    private enum Heading {
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

    public Player2 (float x, float y, float scale, float speed, Textures atlas) {
        super(EntityType.Player , x, y);

        heading = Heading.NORTH;
        spriteMap = new HashMap<Heading, Sprite>();
        this.scale = scale;
        this.speed = speed;

        for (Heading h : Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }

    }

    @Override
    public void update(Input input) {

        float newX2 = x;
        float newY2 = y;



        if (input.getKey(KeyEvent.VK_W) && !Collusion.checkCollusion(newX2 , newY2 - speed ,SPRITE_SCALE , SPRITE_SCALE , Player.getnewX1() , Player.getnewY1() ,SPRITE_SCALE , SPRITE_SCALE)) {
            newY2 -= speed;
            heading = Heading.NORTH;
        } else if (input.getKey(KeyEvent.VK_D) && !Collusion.checkCollusion(newX2 + speed , newY2 ,SPRITE_SCALE , SPRITE_SCALE , Player.getnewX1() , Player.getnewY1() ,SPRITE_SCALE , SPRITE_SCALE)) {
            newX2 += speed;
            heading = Heading.EAST;
        } else if (input.getKey(KeyEvent.VK_S) && !Collusion.checkCollusion(newX2 , newY2 + speed ,SPRITE_SCALE , SPRITE_SCALE , Player.getnewX1() , Player.getnewY1() ,SPRITE_SCALE , SPRITE_SCALE)) {
            newY2 += speed;
            heading = Heading.SOUTH;
        } else if (input.getKey(KeyEvent.VK_A) && !Collusion.checkCollusion(newX2 - speed , newY2 ,SPRITE_SCALE , SPRITE_SCALE , Player.getnewX1() , Player.getnewY1() ,SPRITE_SCALE , SPRITE_SCALE)) {
            newX2 -= speed;
            heading = Heading.WEST;
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

    public static float getnewX2(){
        return newX2;
    }

    public static float getnewY2(){
        return newY2;
    }

    @Override
    public void render(Graphics2D g) {
        spriteMap.get(heading).render(g, x, y);
    }

}
