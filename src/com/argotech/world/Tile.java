package com.argotech.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.argotech.main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0,0,16,16);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16,0,16,16);
	public static BufferedImage TILE_SPIKES = Game.spritesheet.getSprite(0,16,16,16);
	public static BufferedImage TILE_DOOR = Game.spritesheet.getSprite(48,32,16,16);
	
	protected BufferedImage sprite;
	protected int x,y;
	
	public Tile(int x,int y,BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y,World.TILE_SIZE,World.TILE_SIZE, null);
	}
	
	
}
