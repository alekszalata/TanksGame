package Utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, width, height, null);

        return newImage;
    }

    public static Integer[][] levelReader(String filepath) {   //считывает лвл с карты
        Integer[][] result = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)))) {

            String line = null;
            List<Integer[]> lvlLines = new ArrayList<Integer[]>();
            while ((line = reader.readLine()) != null) {
                lvlLines.add(string_to_array_array(line.split(" ")));
            }
            result = new Integer[lvlLines.size()][lvlLines.get(0).length];
            for (int i = 0 ; i < lvlLines.size() ; i++) {
                result[i] = lvlLines.get(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static final Integer[] string_to_array_array(String[] Array) {
        Integer[] result = new Integer[Array.length];
        for (int i = 0 ; i < Array.length; i++) {
            result[i] = Integer.parseInt(Array[i]);
        }
        return result;

    }
}
