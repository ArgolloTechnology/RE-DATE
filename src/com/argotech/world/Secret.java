package com.argotech.world;

import java.awt.image.BufferedImage;

import com.argotech.main.Game;

public class Secret extends Tile{

	public Secret(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		// TODO Auto-generated constructor stub
	}

	public static void tick() {
		switch(Game.CUR_LEVEL) {
		case 10:
			World.restart("Level1S");
		}
	}

}
