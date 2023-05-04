package com.argotech.entidades;

import java.awt.image.BufferedImage;

import com.argotech.main.Game;

public class Key extends Entity{

	public static BufferedImage key = Game.spritesheet.getSprite(64, 32, 16, 16);
	
	public Key(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}

}
