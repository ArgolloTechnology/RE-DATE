package com.argotech.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.argotech.main.Game;
import com.argotech.main.SkinSelect;
import com.argotech.world.Camera;
import com.argotech.world.World;

public class NPC extends Entity{

	private boolean talk = false;
	private int talkindex = 0;
	private String[] fala = {
			"You shold not be here"
	};
	public NPC(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	public void tick() {
		if(isColliding(this, Game.player)) {
			talk  = true;
		}else {
			talk = false;
		}
		if(talk) {
			SkinSelect.unlocked[4] = true;
			if(talkindex<fala[0].length())talkindex++;
		}else {
			talkindex=0;
		}
	}
	public void render(Graphics g) {
		if (getX() < Game.player.getX()) {
			sprite = Player.playerSkins.getSprite(0+(Player.defalt.index*16), 64, 16, 16);
			widthsprite = World.TILE_SIZE;
			espelhoW = 0;
		} else if (getX() > Game.player.getX()) {
			sprite = Player.playerSkins.getSprite(0+(Player.defalt.index*16), 64, 16, 16);
			widthsprite = -World.TILE_SIZE;
			espelhoW = World.TILE_SIZE;
		}
		super.render(g);
		if(talk) {
			g.setColor(Color.BLACK);
			g.fillRect(getX()-Camera.x-44, getY()-Camera.y-22, 128,15);
			g.setColor(Color.WHITE);
			g.drawString(fala [0].substring(0,talkindex), getX()-Camera.x-40, getY()-Camera.y-10);
		}
	}

}
