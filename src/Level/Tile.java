package Level;

import Utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by aleks on 27.06.2017.
 */
public class Tile {
    private BufferedImage image;
    private TileType type;

    protected Tile(BufferedImage image , int scale , TileType type) {
        this.type = type;
        this.image = Utils.resize(image,image.getWidth() * scale, image.getHeight() * scale);
    }

    protected void render (Graphics2D g , int x , int y) {
        g.drawImage(image , x , y , null);
    }

    public TileType type() {
        return type;
    }
}
