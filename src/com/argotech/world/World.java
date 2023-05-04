package com.argotech.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.argotech.entidades.Entity;
import com.argotech.entidades.Key;
import com.argotech.entidades.Key1;
import com.argotech.entidades.Key2;
import com.argotech.entidades.Key3;
import com.argotech.entidades.Lover;
import com.argotech.entidades.NPC;
import com.argotech.entidades.Player;
import com.argotech.entidades.Saw;
import com.argotech.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static int TILE_SIZE = 32;

	public World(String path) {
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[mapa.getWidth() * mapa.getHeight()];
			WIDTH = mapa.getWidth();
			HEIGHT = mapa.getHeight();
			tiles = new Tile[mapa.getWidth() * mapa.getHeight()];
			mapa.getRGB(0, 0, mapa.getWidth(), mapa.getHeight(), pixels, 0, mapa.getWidth());
			for (int xx = 0; xx < mapa.getWidth(); xx++) {
				for (int yy = 0; yy < mapa.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * mapa.getWidth())];
					tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_FLOOR);
					if (pixelAtual == 0xff000000) {
						tiles[xx + (yy * WIDTH)] = new Floor_Tile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_FLOOR);
						if (yy + 1 >= mapa.getHeight() && pixels[xx + ((yy - 1) * mapa.getWidth())] == 0xff000000) {
							tiles[xx + (yy * WIDTH)] = new Void(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_FLOOR);
						}
					} else if (pixelAtual == 0xffffffff) {
						// paredes
						tiles[xx + (yy * WIDTH)] = new Wall_Tile(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_WALL);
						if (yy - 1 >= 0 && pixels[xx + ((yy - 1) * mapa.getWidth())] == 0xffffffff) {
							tiles[xx + (yy * WIDTH)] = new Wall_Tile(xx * TILE_SIZE, yy * TILE_SIZE,
									Game.spritesheet.getSprite(16, 16,16,16));
						}
					} else if (pixelAtual == 0xff0000ff) {
						Game.player.setX(xx * TILE_SIZE);
						Game.player.setY(yy * TILE_SIZE);
					} else if (pixelAtual == 0xfffc0fc0) {
						Lover lover = new Lover(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
						Game.entidades.add(lover);
					} else if (pixelAtual == 0xffa52a2a) {
						// porta1
						tiles[xx + (yy * WIDTH)] = new Door1(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_DOOR);
					}else if (pixelAtual == 0xffa8812a) {
						// porta2
						tiles[xx + (yy * WIDTH)] = new Door2(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_DOOR);
					}else if (pixelAtual == 0xffa50d2a) {
						// porta3
						tiles[xx + (yy * WIDTH)] = new Door3(xx * TILE_SIZE, yy * TILE_SIZE, Tile.TILE_DOOR);
					} else if (pixelAtual == 0xffffd700) {
						// chave1
						Key1 key1 = new Key1(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Key.key);
						Game.entidades.add(key1);
					}else if (pixelAtual == 0xffff8d00) {
						// chave2
						Key2 key2 = new Key2(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Key.key);
						Game.entidades.add(key2);
					}else if (pixelAtual == 0xfffff600) {
						// chave3
						Key3 key3 = new Key3(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, Key.key);
						Game.entidades.add(key3);
					}else if (pixelAtual == 0xffd3d3d3) {
						// serra
						Saw saw = new Saw(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
						Game.entidades.add(saw);
					}else if (pixelAtual == 0xff8f45fc) {
						// secret
						tiles[xx + (yy * WIDTH)] = new Secret(xx * TILE_SIZE, yy * TILE_SIZE, null);
					}else if (pixelAtual == 0xff00ffff) {
						// npc
						NPC npc = new NPC(xx * TILE_SIZE, yy * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
						Game.entidades.add(npc);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;

		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;

		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof Wall_Tile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof Wall_Tile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof Wall_Tile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof Wall_Tile));
	}public static boolean isFreeSecret(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;

		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;

		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof Secret)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof Secret)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof Secret)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof Secret));
	}

	public static boolean isFreeDoor(int xNext, int yNext) {
		int xTile = xNext + Door.maskx;
		int yTile = yNext + Door.masky;

		int x1 = xTile / TILE_SIZE;
		int y1 = yTile / TILE_SIZE;

		int x2 = (xTile + Door.maskw - 1) / TILE_SIZE;
		int y2 = yTile / TILE_SIZE;

		int x3 = xTile / TILE_SIZE;
		int y3 = (yTile + Door.maskh - 1) / TILE_SIZE;

		int x4 = (xTile + Door.maskw - 1) / TILE_SIZE;
		int y4 = (yTile + Door.maskh - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof Door) || (tiles[x2 + (y2 * World.WIDTH)] instanceof Door)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof Door)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof Door));
	}

	public static boolean isFreeVoid(int xNext, int yNext) {
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;

		int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;

		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof Void) || (tiles[x2 + (y2 * World.WIDTH)] instanceof Void)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof Void)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof Void));
	}

	public static void restart(String nextLevel) {
		Game.entidades.clear();
		Game.entidades = new ArrayList<Entity>();
		Game.player = new Player(Game.player.getX(), Game.player.getY(), TILE_SIZE, TILE_SIZE, null);
		Game.entidades.add(Game.player);
		Player.kiss = false;
		Game.world = new World("/levels/" + nextLevel + ".png");
		return;
	}

	public void render(Graphics g) {

		int xStart = Camera.x / TILE_SIZE;
		int yStart = Camera.y / TILE_SIZE;

		int xFinal = xStart + (Game.WIDTH / TILE_SIZE);
		int yFinal = yStart + (Game.HEIGHT / TILE_SIZE);

		for (int xx = xStart; xx <= xFinal; xx++) {
			for (int yy = yStart; yy <= yFinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}

	}
}
