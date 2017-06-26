package Game;

import java.awt.*;

/**
 * Created by aleks on 26.06.2017.
 */
public class Bullet {

    //fields
   private float x;
   private float y;
   private int r;

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
            y < -r || y > Game.HEIGHT + r) {
            return true;
        }

        return false;
    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int) x - r , (int) y - r, 2 * r , 2 * r);

    }

}
