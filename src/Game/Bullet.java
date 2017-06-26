package Game;

import java.awt.*;

/**
 * Created by aleks on 26.06.2017.
 */
public class Bullet {

    //fields
   public float x;
   public float y;
   public static int r;

   private Color color1;

   private double speed;
   private double dx;
   private double dy;
   private double rad;



   //Constructor
    public Bullet(double angle , float x , float y) {
        this.x = x;
        this.y = y;
        r = 3;

        speed = 6.5f;
        rad = Math.toRadians(angle);
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;

        color1 = Color.white;
    }

    public boolean update() {
        x += dx;
        y += dy;

        if (x < -r || x > Game.WIDTH + r ||
            y < -r || y > Game.HEIGHT + r ||
                Collusion.checkShoot( x , y, (float) r, (float) r, Player2.newX2 , Player2.newY2 , Player2.SPRITE_SCALE , Player2.SPRITE_SCALE) ||
                Collusion.checkShoot( x , y, (float) r, (float) r, Player.newX1 , Player.newY1 , Player.SPRITE_SCALE , Player.SPRITE_SCALE)
            )  {
            return true;
        }

        return false;
    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int) x - r , (int) y - r, 2 * r , 2 * r);

    }

}
