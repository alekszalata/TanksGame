package Sprites;


import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage sheet;
    private int SpriteCount; //кол-во танчиков если буду делать анимацию
    private int scale; // размер одного спрайта в нашем случае 16
    private int spriteWidth;  //кол-во спрайтов в ширину

    public SpriteSheet(BufferedImage sheet, int SpriteCount,int scale) {
        this.sheet = sheet;
        this.scale = scale;
        this.SpriteCount = SpriteCount;
        this.spriteWidth = sheet.getWidth() / scale;
    }

    public BufferedImage getSprite(int index) {
        int x = index % spriteWidth * scale;   //когда вырезаем какой либо танк его кооорд х
        int y = index / spriteWidth * scale;

        return sheet.getSubimage(x , y, scale ,scale);
    }

}
