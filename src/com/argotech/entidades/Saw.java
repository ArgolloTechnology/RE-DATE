package com.argotech.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.argotech.main.Game;
import com.argotech.world.Camera;
import com.argotech.world.World;

public class Saw extends Entity {

	private boolean right = true, left = false;
	private double speed = 2;
	private int rotacion = 90;
	private BufferedImage saw = Game.spritesheet.getSprite(0, 16, 16, 16);

	public Saw(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public void tick() {
		AI();
	}

	public void AI() {
		if (right && World.isFree((int) (getX() + speed), getY())) {
			x += speed;
			rotacion+=10;
			if (World.isFree(getX() + World.TILE_SIZE, getY() + 1) || !World.isFree((int) (getX() + speed), getY())) {
				right = false;
				left = true;
			}
		}else if (right && !World.isFree((int) (x + speed), (int) y)) {
			int signVsp = 1;
			while (World.isFree((int) (x + signVsp), (int) y)) {
				x = x + signVsp;
			}
		}
		if (left && World.isFree((int) (getX() - speed), getY())) {
			x -= speed;
			rotacion-=10;
			if (World.isFree(getX() - World.TILE_SIZE, getY() + 1) || !World.isFree((int) (getX() - speed), getY())) {
				left = false;
				right = true;
			}
		}else if (left && !World.isFree((int) (x - speed), (int) y)) {
			int signVsp = -1;
			while (World.isFree((int) (x + signVsp), (int) y)) {
				x = x + signVsp;
			}
		}
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.rotate(Math.toRadians(rotacion), this.getX()+(World.TILE_SIZE / 2) - Camera.x, this.getY()+(World.TILE_SIZE / 2)- Camera.y);
		g.drawImage(saw, getX()-Camera.x, getY()-Camera.y,World.TILE_SIZE,World.TILE_SIZE, null);
		g2.rotate(Math.toRadians(-rotacion), this.getX()+(World.TILE_SIZE / 2)- Camera.x, this.getY()+(World.TILE_SIZE / 2)- Camera.y);
	}

}
