package Game;

import Display.Display;
import KeyBoard.Input;
import Sprites.Sprite;
import Sprites.SpriteSheet;
import Utils.Time;

import java.awt.*;
import Graphics.Textures;

public class Game implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String NAME = "Tanks";
    public static final int COLOR_TO_CLEAR = 0xff000000;
    public static final int NUM_OF_BUFFERS = 3;

    public static final float UPDATE_RATE = 60.0f;              //сколько раз в сек обновляться
    public static final float UPD_INTERVAL = Time.second / UPDATE_RATE;       //каждые сколько времени обновляться
    public static final long IDLE_TIME = 1;          //время дать thrad'у отойти (1мс)

    private static final String TEXTURE_PATH = "texture_atlas.png";

    private boolean running;
    private Thread threadGame;
    private Input input;
    private Graphics2D graphics;
    private Textures textureImage;
    private SpriteSheet sheet;
    private Sprite sprite;
    private Player player;
    private Player2 player2;



    public Game(){
        running = false;
        Display.create(WIDTH , HEIGHT , NAME , COLOR_TO_CLEAR , NUM_OF_BUFFERS);
        graphics = Display.getImgGraph();
        input = new Input();
        Display.InputListener(input);
        textureImage = new Textures(TEXTURE_PATH);
        sheet = new SpriteSheet(textureImage.cut(0 , 0 , 32 , 32) , 2 , 16);
        sprite = new Sprite(sheet , 1);
        player = new Player(400 , 300 ,3 , 3, textureImage);
        player2 = new Player2(100 , 100 , 3 , 3 ,textureImage);

    }

    public synchronized void start() {           // synchronized = 2 разные thread не могут призывать одновр
      if (running) return;
      running = true;
      threadGame = new Thread(this);
      threadGame.start();
    }

    public void run(){ //запускается от threadGame.start
        int fps = 0;
        int upd = 0;
        int updloops = 0;  //сколько апдейтов делается с запозданием
        long count = 0;  //время в котором будет считаться сколько игра запущена
        float delta = 0;
        long lastTime = Time.getTime();
        while (running) {
            long nowTime = Time.getTime();
            long timeBetween = nowTime - lastTime;       //кол-во времени которое прошло с тех пор как while бежал в прошлый раз
            lastTime = nowTime;

            count += timeBetween;

            boolean render = false;
            delta += (timeBetween / UPD_INTERVAL);   //когда = 1 значит пора делать еще один апдейт
            while (delta > 1) {
                update();
                upd++;
                delta--;
                if (render) {
                    updloops++;
                } else {
                    render = true;
                }
            }

            if (render) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            if (count >= Time.second) {
                Display.titleSetting(NAME + " || Fps" + fps + " | Upd" + upd + " | UpdLoops" + updloops);
                upd =0;
                fps = 0;
                updloops = 0;
                count = 0;
            }

        }
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            threadGame.join();       //прекратить все и ждать пока добежит посл процесс
        } catch (InterruptedException e) {
            e.printStackTrace();    //напечатать где произошел эксепшн
        }
        cleanUp();
    }

    private void update() {
        player.update(input);
        player2.update(input);

    }

    private void render() {
        Display.clearImage();
        player2.render(graphics);
        player.render(graphics);
        Display.swap();

    }

    private void cleanUp() {//закрывание всяких ресурсов
      Display.close();
    }
}
