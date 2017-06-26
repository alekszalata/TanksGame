package Game;

import java.util.ArrayList;

public class Collusion {

    public static int modelSize = 16;

    public static boolean checkCollusion(float x1 , float y1 , float width1 , float height1 ,
                                          float x2 , float y2 , float width2 , float height2) {
        return  x1 + width1 + modelSize * 2 - 1  > x2 && x2 + width2 + modelSize * 2 - 1 > x1 && y1 + height1 + modelSize * 2 - 1> y2 && y2 + height2 + modelSize * 2 - 1> y1;
    }

    public static boolean checkShoot(ArrayList<Bullet> bullets ,
                                     float x1 , float y1 , float width1 , float height1 ,
                                     float x2 , float y2 , float width2 , float height2 ) {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).x )
        }


    }


}
