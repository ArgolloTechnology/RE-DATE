package com.argotech.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Door extends Tile{

	public static int maskx = 8;
	public static int masky = 0;
	public static int maskw = World.TILE_SIZE/2;
	public static int maskh = World.TILE_SIZE;
	
	
	public Door(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(sprite == null) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, maskw, maskh);
		}else {
			super.render(g);
		}
	}

}
