package Graphics;

import Utils.ResourcesLoader;

import java.awt.image.BufferedImage;

public class Textures {
    BufferedImage image;

    public Textures(String imageName) {
        image = ResourcesLoader.loadImage(imageName);
    }

    public BufferedImage cut (int x , int y , int w ,int h) {
        return image.getSubimage(x,y,w,h);
    }
}
