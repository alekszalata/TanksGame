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

    private Heading					heading;
    private Map<Heading, Sprite> spriteMap;
    private float					scale;
    private float					speed;

    public Player(float x, float y, float scale, float speed, Textures atlas) {
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

        newX1 = x;
        newY1 = y;

        if (input.getKey(KeyEvent.VK_UP) && !Collusion.checkCollusion(newX1 , newY1 - speed ,SPRITE_SCALE , SPRITE_SCALE , Player2.newX2 , Player2.newY2 ,SPRITE_SCALE , SPRITE_SCALE)) {
            newY1 -= speed;
            heading = Heading.NORTH;
        } else if (input.getKey(KeyEvent.VK_RIGHT) && !Collusion.checkCollusion(newX1 + speed , newY1 ,SPRITE_SCALE , SPRITE_SCALE , Player2.newX2 , Player2.newY2 ,SPRITE_SCALE , SPRITE_SCALE)) {
            newX1 += speed;
            heading = Heading.EAST;
        } else if (input.getKey(KeyEvent.VK_DOWN) && !Collusion.checkCollusion(newX1 , newY1 + speed ,SPRITE_SCALE , SPRITE_SCALE , Player2.newX2 , Player2.newY2 ,SPRITE_SCALE , SPRITE_SCALE)) {
            newY1 += speed;
            heading = Heading.SOUTH;
        } else if (input.getKey(KeyEvent.VK_LEFT) && !Collusion.checkCollusion(newX1 - speed, newY1 ,SPRITE_SCALE , SPRITE_SCALE , Player2.newX2 , Player2.newY2 ,SPRITE_SCALE , SPRITE_SCALE)) {
            newX1 -= speed;
            heading = Heading.WEST;
        }

        if (newX1 < 0) {
            newX1 = 0;
        } else if (newX1 >= Game.WIDTH - SPRITE_SCALE * scale) {
            newX1 = Game.WIDTH - SPRITE_SCALE * scale;
        }

        if (newY1 < 0) {
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
