package com.argotech.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;

import com.argotech.main.Game;
import com.argotech.world.Camera;
import com.argotech.world.World;

public class Entity {

	public int depth;

	public int vida;
	public double x;
	public double y;
	protected int width;
	protected int widthsprite;
	protected int height;
	protected int heightsprite;
	protected int espelhoW;
	public int maskx;
	public int masky;
	public int maskw;
	public int maskh;

	public BufferedImage sprite;

	protected int offsety;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.widthsprite = width;
		this.height = height;
		this.heightsprite = height;
		this.sprite = sprite;
		this.maskx = 0;
		this.masky = 0;
		this.maskw = width;
		this.maskh = height;

	}

	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity n0, Entity n1) {
			if (n1.depth < n0.depth)
				return +1;
			if (n1.depth > n0.depth)
				return -1;
			return 0;
		}

	};

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public void setMask(int maskx, int masky, int maskw, int maskh) {
		this.maskx = maskx;
		this.masky = masky;
		this.maskw = maskw;
		this.maskh = maskh;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void tick() {
	}

	public double distacia(int x1, int y1, int x2, int y2) {
		return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
	}

	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskw, e1.maskh);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskw, e2.maskh);

		return e1Mask.intersects(e2Mask);
	}

	public void cameraFollow(Entity e) {
		int xmid = e.width/2;
		int ymid = e.height/2;
		Camera.x = Camera.Clamp(this.getX() - (Game.WIDTH / 2 - xmid), 0, World.WIDTH * World.TILE_SIZE - Game.WIDTH);
		Camera.y = Camera.Clamp(this.getY() - (Game.HEIGHT / 2 - ymid), 0, (World.HEIGHT-1) * World.TILE_SIZE - Game.HEIGHT);
	}
	
	public void gravidade(int peso) {
		boolean noAr = false;
		if (World.isFree(getX(), getY() + peso)) {
			noAr = true;
		} else {
			noAr = false;
		}
		if (noAr) {
			y += peso;
		}
	}

	public void morte(Entity e) {
		Game.entidades.remove(e);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() + espelhoW - Camera.x, this.getY() - offsety - Camera.y, widthsprite,
				heightsprite, null);
	}
}
