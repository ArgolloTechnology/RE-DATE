package com.argotech.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.argotech.graficos.Animation;
import com.argotech.main.Game;
import com.argotech.main.SkinSelect;
import com.argotech.world.Camera;
import com.argotech.world.World;

public class Lover extends Entity {

	public static BufferedImage lover[] = { Game.spritesheet.getSprite(48, 16, 16, 16),
			Game.spritesheet.getSprite(64, 16, 16, 16) };
	public static BufferedImage heart[] = { Game.spritesheet.getSprite(32, 16, 8, 8),
			Game.spritesheet.getSprite(40, 16, 8, 8) };
	public static List<BufferedImage> skins = new ArrayList<>();
	public static Animation defalt = new Animation(10, 0, 1);
	public static Animation heartanim = new Animation(10, 0, 1);
	public int DIR = 1;

	public Lover(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		skins();
	}

	public void tick() {
		if (getX() < Game.player.getX()) {
			DIR = -1;
		} else if (getX() > Game.player.getX()) {
			DIR = 1;
		}
	}
	public void skins() {
		skins.clear();
		if (SkinSelect.skin<=4) {
			for (int i = 0; i < 4; i++) {
				skins.add(Player.playerSkins.getSprite(64 + (i * 16), SkinSelect.skin * 16, 16, 16));
			} 
		}else {
			for (int i = 0; i < 4; i++) {
				skins.add(Player.playerSkins.getSprite((i * 16), (SkinSelect.skin * 16)-80, 16, 16));
			}
		}
		
	}
	public void render(Graphics g) {
			if (DIR == -1) {
				sprite = skins.get(defalt.index);
				widthsprite = World.TILE_SIZE;
				espelhoW = 0;
			} else if (DIR == 1) {
				sprite = skins.get(defalt.index);
				widthsprite = -World.TILE_SIZE;
				espelhoW = World.TILE_SIZE;
			}
			
		if (Player.kiss) {
			g.drawImage(heart[heartanim.index], getX() + 8 - Camera.x, getY() - 16 - Camera.y, 16, 16, null);
		}
		super.render(g);
	}
}
