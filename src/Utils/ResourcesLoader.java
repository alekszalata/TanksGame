package Utils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourcesLoader {
    public static final String path = "resources/";

    public static BufferedImage loadImage(String fileName) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
