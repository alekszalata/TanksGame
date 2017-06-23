package Display;
import KeyBoard.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {

    private static boolean created;
    private static JFrame windowFrame;
    private static Canvas windowContent;
    private static BufferedImage image;
    private static int[] imageStorage;     //хранилище картинки по пикселям rgb
    private static Graphics imageGraphics;   //графика картинки рисует что-то и тд
    private static int colortoClear;    //цвет для отчистки Canavas'a
    private static BufferStrategy bufferStrategy;


    public static void create (int width,int height, String name,int _colortoClear , int numOfBuffers){
        if (created) return;

        windowFrame = new JFrame(name);
        windowContent = new Canvas();
        Dimension size = new Dimension(width , height);  //т.к только так можно передать размер Canvas'у
        windowContent.setPreferredSize(size);          //передаем размер
        windowFrame.setResizable(false);             //юзер не может менять размер окна
        windowFrame.getContentPane().add(windowContent);  //крестики и тд не загораживали контент
        windowFrame.pack();   //подгоняет размер рамки под контент
        windowFrame.setVisible(true);   //так оно будет видимым
        windowFrame.setLocationRelativeTo(null);     //создается на середине экрана
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //закрытие и остановка програмы на керстик

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);    //255 не прозрачн, 0 прозрачн
        imageStorage = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();  //информация об image rgb через int
        imageGraphics = image.getGraphics(); //элемент графики который умеет рисовать круги и тд (все то же самое что и image)
        ((Graphics2D)imageGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   //сглаживание объекта
        colortoClear = _colortoClear;

        windowContent.createBufferStrategy(numOfBuffers);
        bufferStrategy = windowContent.getBufferStrategy();

        created = true;
    }

    public static void clearImage() {                     //заполняет image выбранным цветом
        Arrays.fill(imageStorage , colortoClear);
    }

    public static void swap() {                          // меняет экран
        Graphics g = bufferStrategy.getDrawGraphics();   //вернет граф объект тот который след по очереди
        g.drawImage(image , 0 , 0 , null);
        bufferStrategy.show();   //т.к все происх за кулисами , то нам надо его показать
    }

    public static Graphics2D getImgGraph() {
        return (Graphics2D)imageGraphics;
    }

    public static void close() {
        if (!created) return;
        windowFrame.dispose();
    }
    public static void titleSetting(String title) {
        windowFrame.setTitle(title);
    }

    public static void InputListener(Input inputListener) {
        windowFrame.add(inputListener);

    }
}
