package com.argotech.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.argotech.graficos.Animation;
import com.argotech.graficos.Spritesheet;
import com.argotech.graficos.UI;
import com.argotech.main.Game;
import com.argotech.main.Menu;
import com.argotech.main.SkinSelect;
import com.argotech.world.Camera;
import com.argotech.world.Door1;
import com.argotech.world.Door2;
import com.argotech.world.Door3;
import com.argotech.world.Floor_Tile;
import com.argotech.world.Secret;
import com.argotech.world.Tile;
import com.argotech.world.World;

public class Player extends Entity {

	public boolean right, left;
	public int DIR = 1;
	public boolean jump = false;
	public static double vida = 100;
	public static int maxvida = 100;

	public int index = 0;
	public int frames = 0;
	public static boolean kiss = false;
	public int framesKiss = 0;

	public int chave1 = 0;
	public int chave2 = 0;
	public int chave3 = 0;

	private double vspd = 0;
	private double gravity = 0.8;

	public boolean dash = false;
	private int dashTime = 0;
	private boolean cooldown;
	private int delay = 0;
	private BufferedImage controls;
	public static boolean door;
	public static Animation defalt = new Animation(10, 0, 1);
	public static Animation fall = new Animation(10, 3, 5);
	public boolean holding = false;
	public static double speed = 3;
	public static Spritesheet playerSkins = new Spritesheet("/SpritesheetP.png");
	public static List<BufferedImage> skins = new ArrayList<>();

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		try {
			controls = ImageIO.read(getClass().getResource("/controls.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		skins();
	}

	public void tick() {
		depth = 5;
		death();
		// pulo
		jump(12.2);
		// movimentação
		moviment();
		// dash
		dash(3);
		// player chegou ate a amada
		reecontro();
		// animação final
		courseFinish(60, 26);
		// camera
		cameraFollow(this);
		// abrir porta
		openDoor(32);
		// coletar chave
		colectKey();
		// secret check
		if (!World.isFreeSecret(getX() + 1, getY()) || !World.isFreeSecret(getX() - 1, getY())
				|| !World.isFreeSecret(getX(), getY() + 1) || !World.isFreeSecret(getX(), getY() - 1)) {
			Secret.tick();
		}
	}

	public void skins() {
		skins.clear();
		if (SkinSelect.skin <= 4) {
			for (int i = 0; i < 4; i++) {
				skins.add(playerSkins.getSprite(0 + (i * 16), SkinSelect.skin * 16, 16, 16));
			}
		} else {
			for (int i = 0; i < 4; i++) {
				skins.add(playerSkins.getSprite(64 + (i * 16), (SkinSelect.skin * 16) - 80, 16, 16));
			}
		}

	}

	/**
	 * 
	 */
	private void colectKey() {
		for (int i = 0; i < Game.entidades.size(); i++) {
			Entity e = Game.entidades.get(i);
			if (e instanceof Key1) {
				if (isColliding(e, this)) {
					chave1++;
					Game.entidades.remove(e);
				}
			} else if (e instanceof Key2) {
				if (isColliding(e, this)) {
					chave2++;
					Game.entidades.remove(e);
				}
			} else if (e instanceof Key3) {
				if (isColliding(e, this)) {
					chave3++;
					Game.entidades.remove(e);
				}
			}
		}
	}

	/**
	 * @param range
	 * 
	 */
	private void openDoor(int range) {
		if (chave1 > 0) {
			for (int xx = 0; xx < World.WIDTH; xx++) {
				for (int yy = 0; yy < World.HEIGHT; yy++) {
					if (World.tiles[xx + (yy * World.WIDTH)] instanceof Door1
							&& Math.sqrt(((getX() - xx * World.TILE_SIZE) * (getX() - xx * World.TILE_SIZE))
									+ ((getY() - yy * World.TILE_SIZE) * (getY() - yy * World.TILE_SIZE))) < range) {
						World.tiles[xx + (yy * World.WIDTH)] = new Floor_Tile(xx * World.TILE_SIZE,
								yy * World.TILE_SIZE, Tile.TILE_FLOOR);
						chave1--;

					}
				}
			}
		} else if (chave2 > 0) {
			for (int xx = 0; xx < World.WIDTH; xx++) {
				for (int yy = 0; yy < World.HEIGHT; yy++) {
					if (World.tiles[xx + (yy * World.WIDTH)] instanceof Door2
							&& Math.sqrt(((getX() - xx * World.TILE_SIZE) * (getX() - xx * World.TILE_SIZE))
									+ ((getY() - yy * World.TILE_SIZE) * (getY() - yy * World.TILE_SIZE))) < range) {
						World.tiles[xx + (yy * World.WIDTH)] = new Floor_Tile(xx * World.TILE_SIZE,
								yy * World.TILE_SIZE, Tile.TILE_FLOOR);
						chave2--;

					}
				}
			}
		} else if (chave3 > 0) {
			for (int xx = 0; xx < World.WIDTH; xx++) {
				for (int yy = 0; yy < World.HEIGHT; yy++) {
					if (World.tiles[xx + (yy * World.WIDTH)] instanceof Door3
							&& Math.sqrt(((getX() - xx * World.TILE_SIZE) * (getX() - xx * World.TILE_SIZE))
									+ ((getY() - yy * World.TILE_SIZE) * (getY() - yy * World.TILE_SIZE))) < range) {
						World.tiles[xx + (yy * World.WIDTH)] = new Floor_Tile(xx * World.TILE_SIZE,
								yy * World.TILE_SIZE, Tile.TILE_FLOOR);
						chave3--;

					}
				}
			}
		}
	}

	private void death() {
		if (!World.isFreeVoid(getX(), (int) (getY() + gravity)) || !World.isFreeVoid((int) (getX() + speed), getY())
				|| !World.isFreeVoid((int) (getX() - speed), getY()) || sawCollision()) {
			World.restart("Level" + Game.CUR_LEVEL);
		}
	}

	private boolean sawCollision() {
		for (int i = 0; i < Game.entidades.size(); i++) {
			Entity e = Game.entidades.get(i);
			if (e instanceof Saw && isColliding(e, this)) {
				return true;
			}
		}
		return false;
	}

	private void moviment() {
		if (!kiss) {
			if (right && World.isFree((int) (x + speed), (int) y) && World.isFreeDoor((int) (x + speed), (int) y)) {
				x += speed;
				DIR = 1;
			} else if (right && !World.isFree((int) (x + speed), (int) y)) {
				int signVsp = 1;
				while (World.isFree((int) (x + signVsp), (int) y)) {
					x = x + signVsp;
				}
			} else if (right && !World.isFreeDoor((int) (x + speed), (int) y)) {
				int signVsp = 1;
				while (World.isFreeDoor((int) (x + signVsp), (int) y)) {
					x = x + signVsp;
				}
			}
			if (left && World.isFree((int) (x - speed), (int) y) && World.isFreeDoor((int) (x - speed), (int) y)) {
				x -= speed;
				DIR = -1;
			} else if (left && !World.isFree((int) (x - speed), (int) y)) {
				int signVsp = -1;
				while (World.isFree((int) (x + signVsp), (int) y)) {
					x = x + signVsp;
				}
			} else if (left && !World.isFreeDoor((int) (x - speed), (int) y)) {
				int signVsp = -1;
				while (World.isFreeDoor((int) (x + signVsp), (int) y)) {
					x = x + signVsp;
				}
			}
			if ((right && left) && (World.isFree((int) (x - 1), (int) y) || World.isFree((int) (x + 1), (int) y))) {
				DIR = 1;
			}

		}
	}

	private void jump(double altura) {
		if (!dash) {
			vspd += gravity;
			if (!World.isFree((int) x, (int) (y + gravity)) && jump) {
				vspd = -altura;
				jump = false;
			}
			if (vspd >= World.TILE_SIZE / 2) {
				vspd = World.TILE_SIZE / 2;
			}

			if (!World.isFree((int) x, (int) (y + vspd))) {
				int signVsp = 0;
				if (vspd >= 0) {
					signVsp = 1;
				} else {
					signVsp = -1;
				}
				while (World.isFree((int) x, (int) (y + signVsp))) {
					y = y + signVsp;
				}
				vspd = 0;
			}

			y = y + vspd;
		}
	}

	static int boolToInt(boolean b) {
		if (b)
			return 1;
		return 0;
	}

	public static boolean IntToBool(int i) {
		if (i == 1)
			return true;
		return false;
	}
	private void checkUnlocked() {
		switch(SkinSelect.skin) {
		case 0:
			SkinSelect.unlocked[1]=true;
			SkinSelect.unlocked[5]=true;
			break;
		case 1:
			SkinSelect.unlocked[2]=true;
			SkinSelect.unlocked[6]=true;
			break;
		case 2:
			SkinSelect.unlocked[3]=true;
			SkinSelect.unlocked[7]=true;
			break;
		case 3:
			SkinSelect.unlocked[8]=true;
			break;
		case 4:
			SkinSelect.unlocked[9]=true;
			break;
		}
	}
	public void courseFinish(int maxFrames, int maxLevel) {
		if (kiss) {
			framesKiss++;
			if (framesKiss == maxFrames) {
				Game.CUR_LEVEL++;
				if (Game.CUR_LEVEL >= (maxLevel + 1)) {
					checkUnlocked();
					Game.CUR_LEVEL = 1;
					String[] opt1 = { "Min", "Seg", "Marvat", "MarvatCovided", "KK", "Pinto", "Luck", "Iris",
							"IrisCovided", "Lucy", "Pinta", "Lucky" };
					int[] opt2 = { Menu.bestTimeM, Menu.bestTimeS, boolToInt(SkinSelect.unlocked[0]),
							boolToInt(SkinSelect.unlocked[1]), boolToInt(SkinSelect.unlocked[2]),
							boolToInt(SkinSelect.unlocked[3]), boolToInt(SkinSelect.unlocked[4]),
							boolToInt(SkinSelect.unlocked[5]), boolToInt(SkinSelect.unlocked[6]),
							boolToInt(SkinSelect.unlocked[7]), boolToInt(SkinSelect.unlocked[8]),
							boolToInt(SkinSelect.unlocked[9]) };
					Menu.save(opt1, opt2, 1);
					Menu.load = true;
					System.out.println("salvo");
					if (UI.minutes < Menu.bestTimeM) {
						String[] opt11 = { "Min", "Seg", "Marvat", "MarvatCovided", "KK", "Pinto", "Luck", "Iris",
								"IrisCovided", "Lucy", "Pinta", "Lucky" };
						int[] opt21 = { UI.minutes, UI.seconds, boolToInt(SkinSelect.unlocked[0]),
								boolToInt(SkinSelect.unlocked[1]), boolToInt(SkinSelect.unlocked[2]),
								boolToInt(SkinSelect.unlocked[3]), boolToInt(SkinSelect.unlocked[4]),
								boolToInt(SkinSelect.unlocked[5]), boolToInt(SkinSelect.unlocked[6]),
								boolToInt(SkinSelect.unlocked[7]), boolToInt(SkinSelect.unlocked[8]),
								boolToInt(SkinSelect.unlocked[9]) };
						Menu.save(opt11, opt21, 1);
						Menu.load = true;
						System.out.println("salvo");
					} else if (UI.minutes == Menu.bestTimeM && UI.seconds < Menu.bestTimeS) {
						String[] opt11 = { "Min", "Seg", "Marvat", "MarvatCovided", "KK", "Pinto", "Luck", "Iris",
								"IrisCovided", "Lucy", "Pinta", "Lucky" };
						int[] opt21 = { UI.minutes, UI.seconds, boolToInt(SkinSelect.unlocked[0]),
								boolToInt(SkinSelect.unlocked[1]), boolToInt(SkinSelect.unlocked[2]),
								boolToInt(SkinSelect.unlocked[3]), boolToInt(SkinSelect.unlocked[4]),
								boolToInt(SkinSelect.unlocked[5]), boolToInt(SkinSelect.unlocked[6]),
								boolToInt(SkinSelect.unlocked[7]), boolToInt(SkinSelect.unlocked[8]),
								boolToInt(SkinSelect.unlocked[9]) };
						Menu.save(opt11, opt21, 1);
						Menu.load = true;
						System.out.println("salvo");
					}
					Game.mode="MENU";
				}
				World.restart("Level" + Game.CUR_LEVEL);
				framesKiss = 0;
			}
		}

	}

	public void reecontro() {
		for (int i = 0; i < Game.entidades.size(); i++) {
			Entity e = Game.entidades.get(i);
			if (e instanceof Lover) {
				if (Entity.isColliding(this, e)) {
					kiss = true;
				} else {
					kiss = false;
				}
				break;
			}
		}
	}

	private void dash(int distance) {
		dashTime++;
		if (cooldown) {
			delay++;
			if (delay >= 15) {
				delay = 0;
				cooldown = false;
			}
		}
		if (holding) {
			dash = true;
		}
		if (dash) {
			if (dashTime <= distance && !kiss && !cooldown) {
				if (DIR == 1 && World.isFree(getX() + World.TILE_SIZE, getY())
						&& World.isFreeDoor(getX() + World.TILE_SIZE, getY()))
					x += World.TILE_SIZE;
				else if (DIR == 1 && !World.isFree(getX() + World.TILE_SIZE, getY())) {
					while (World.isFree(getX() + 1, getY())) {
						x++;
					}
				} else if (DIR == 1 && !World.isFreeDoor(getX() + World.TILE_SIZE, getY())) {
					while (World.isFreeDoor(getX() + 1, getY())) {
						x++;
					}
				}
				if (DIR == -1 && World.isFree(getX() - World.TILE_SIZE, getY())
						&& World.isFreeDoor(getX() - World.TILE_SIZE, getY()))
					x -= World.TILE_SIZE;
				else if (DIR == -1 && !World.isFree(getX() - World.TILE_SIZE, getY())) {
					while (World.isFree(getX() - 1, getY())) {
						x--;
					}
				} else if (DIR == -1 && !World.isFreeDoor(getX() - World.TILE_SIZE, getY())) {
					while (World.isFreeDoor(getX() - 1, getY())) {
						x--;
					}
				}
			} else {
				dash = false;
				cooldown = true;
			}
		} else {
			dashTime = 0;
		}
	}

	public void render(Graphics g) {

		if (Game.mode == "NORMAL") {
			if (Game.CUR_LEVEL == 1 && !kiss) {
				g.drawImage(controls, getX() - 20 - Camera.x, getY() - 75 - Camera.y, controls.getWidth() * 2,
						controls.getHeight() * 2, null);
			}
		}
		if (vspd <= 0) {
			if (!dash) {
				if (DIR == 1) {
					sprite = skins.get(defalt.index);
					widthsprite = World.TILE_SIZE;
					espelhoW = 0;
				} else if (DIR == -1) {
					sprite = skins.get(defalt.index);
					widthsprite = -World.TILE_SIZE;
					espelhoW = World.TILE_SIZE;
				}
			} else {
				if (DIR == 1) {
					sprite = skins.get(2);
					widthsprite = World.TILE_SIZE;
					espelhoW = 0;
				} else if (DIR == -1) {
					sprite = skins.get(2);
					widthsprite = -World.TILE_SIZE;
					espelhoW = World.TILE_SIZE;
				}
			}
		} else {
			if (!dash) {
				if (DIR == 1) {
					sprite = skins.get(3);
					widthsprite = World.TILE_SIZE;
					espelhoW = 0;
				} else if (DIR == -1) {
					sprite = skins.get(3);
					widthsprite = -World.TILE_SIZE;
					espelhoW = World.TILE_SIZE;
				}
			} else {
				if (DIR == 1) {
					sprite = skins.get(2);
					widthsprite = World.TILE_SIZE;
					espelhoW = 0;
				} else if (DIR == -1) {
					sprite = skins.get(2);
					widthsprite = -World.TILE_SIZE;
					espelhoW = World.TILE_SIZE;
				}
			}
		}
		if (Player.kiss) {
			g.drawImage(Lover.heart[Lover.heartanim.index], getX() + 8 - Camera.x, getY() - 16 - Camera.y, 16, 16,
					null);
		}
		super.render(g);
	}
}