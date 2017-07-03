package Level;

import Game.Game;
import Graphics.Textures;
import Utils.Utils;
import Game.Collusion;

import Game.Player2;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by aleks on 27.06.2017.
 */
public class Level {

    public static final int TILE_SIZE = 16;
    public static final int TILE_SCALE = 2;
    public static final int TILE_IN_GAME = TILE_SCALE * TILE_SIZE;
    public static final int TILES_IN_WIDTH = Game.WIDTH / TILE_IN_GAME;
    public static final int TILES_IN_HEIGHT = Game.HEIGHT / TILE_IN_GAME;

    public static Integer[][] tileMap;
    public static Map<TileType , Tile > tiles;
    public static List<Point> bushes;
    public static List<Point> bricks;

    public Level (Textures atlas) {
        tileMap = new Integer[TILES_IN_WIDTH][TILES_IN_HEIGHT];
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.BRICK , new Tile(atlas.cut(16 * TILE_SIZE , 0 * TILE_SIZE , TILE_SIZE , TILE_SIZE),
                TILE_SCALE ,TileType.BRICK));
        tiles.put(TileType.BUSH , new Tile(atlas.cut(17 * TILE_SIZE , 2 * TILE_SIZE , TILE_SIZE , TILE_SIZE),
                TILE_SCALE ,TileType.BUSH));
        tiles.put(TileType.EMPTY , new Tile(atlas.cut(18 * TILE_SIZE , 3 * TILE_SIZE , TILE_SIZE , TILE_SIZE),
                TILE_SCALE ,TileType.EMPTY));

        tileMap = Utils.levelReader("resources/Level2.lvl");


        bushes = new ArrayList<Point>();               //кусты
        for (int i = 0 ; i < tileMap.length ; i ++) {
            for (int j = 0 ; j < tileMap[i].length; j++)  {
                Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                if (tile.type() == TileType.BUSH)
                    bushes.add(new Point(j * TILE_IN_GAME ,i * TILE_IN_GAME));
            }
        }

        bricks = new ArrayList<Point>();              //кирпичи
        for (int i = 0 ; i < tileMap.length ; i ++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                if (tile.type() == TileType.BRICK)
                    bricks.add(new Point(j * TILE_IN_GAME, i * TILE_IN_GAME));
            }
        }


    }

    public void update() { //если буду делать разбивание кирпичей
    }




    public void renderBricks(Graphics2D g){
        for (Point p : bricks) {
            tiles.get(TileType.BRICK).render(g , p.x , p.y);
        }
    }


    public void renderBush(Graphics2D g) {
        for (Point p : bushes) {
            tiles.get(TileType.BUSH).render(g , p.x , p.y);
        }
    }


}
