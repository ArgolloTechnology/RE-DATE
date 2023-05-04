package com.argotech.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.argotech.entidades.Entity;
import com.argotech.entidades.Player;
import com.argotech.graficos.Animation;
import com.argotech.graficos.Spritesheet;
import com.argotech.graficos.UI;
import com.argotech.world.World;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	/* Variaveis */
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	/* int */
	public final static int WIDTH = World.TILE_SIZE * 16;
	public final static int HEIGHT = World.TILE_SIZE * 8;
	public final static int SCALE = 3;
	public static int CUR_LEVEL = 1;
	/* BufferedImage */
	private BufferedImage image;

	public static World world;

	public static Player player;

	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("CuteFont.ttf");
	public InputStream stream1 = ClassLoader.getSystemClassLoader().getResourceAsStream("CuteFont.ttf");
	public static Font cute;
	public static Font cute1;

	public Menu menu;
	public SkinSelect skins = new SkinSelect();
	public Credits credits;
	public UI ui;

	public static List<Entity> entidades;
	public static List<Animation> animation = new ArrayList<>();
	public static Spritesheet spritesheet;

	public static Random rand;
	public static String mode = "MENU";
	public static boolean FULL_SCREEN = true;

	// *******//

	public Game() {
		Sound.GameSong.Loop();
		rand = new Random();
		this.addKeyListener(this);
		setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		intframe();
		// iniciando objetos
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entidades = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/Spritesheet.png");
		player = new Player(0, 0, World.TILE_SIZE, World.TILE_SIZE, null);
		entidades.add(player);
		world = new World("/levels/Level" + CUR_LEVEL + ".png");
		try {
			cute = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(64f);
			cute1 = Font.createFont(Font.TRUETYPE_FONT, stream1).deriveFont(50f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menu = new Menu();
		credits = new Credits();
	}

	public void intframe() {
		// Abre a janela
		frame = new JFrame("RE-DATE");
		frame.add(this);
		frame.setResizable(true);
		frame.setUndecorated(true);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Game game = new Game();
		game.start();
	}

	public void tick() {
		for (int i = 0; i < animation.size(); i++) {
			Animation e = animation.get(i);
			e.tick();
		}
		if (mode == "NORMAL") {
			for (int i = 0; i < entidades.size(); i++) {
				Entity e = entidades.get(i);
				e.tick();
			}
			ui.tick();
		} else if (mode == "MENU") {
			// menu incial
			menu.tick();
		}else if (mode == "SKINS") {
			// menu de skins
			skins.tick();
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0xff5e5e5e));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if (mode=="NORMAL") {
			world.render(g);
			Collections.sort(entidades, Entity.nodeSorter);
			for (int i = 0; i < entidades.size(); i++) {
				Entity e = entidades.get(i);
				e.render(g);
			} 
		}
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height, null);
		ui.render(g);
		if (mode == "MENU") {
			menu.render(g);
		}else if(mode == "CREDITS") {
			credits.render(g);
		}else if(mode == "SKINS") {
			skins.render(g);
		}
		bs.show();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println(frames);
				ui.fps = frames;
				frames = 0;
				timer += 1000;
			}
		}
		stop();

	}

	@Override

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override

	public void keyPressed(KeyEvent e) {
		if (mode == "NORMAL") {
			if (!Player.kiss) {
				if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					player.right = true;
				} else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
					player.left = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP
						|| e.getKeyCode() == KeyEvent.VK_SPACE) {
					player.jump = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					mode = "MENU";
					Menu.pause = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
					player.holding = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_R) {
					// Reset de fase
					World.restart("Level" + CUR_LEVEL);
				}else if(e.getKeyCode() == KeyEvent.VK_T) {
					// Reset de jogo
					CUR_LEVEL = 1;
					World.restart("Level" + CUR_LEVEL);
					UI.beginTime = true;
				}
			}
		} else if(mode == "MENU"){
			if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				menu.up = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.down = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
				menu.enter = true;
			}
		}else if(mode == "CREDITS"){
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				mode = "MENU";
			}
		}else if(mode == "SKINS"){
			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				skins.left = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				skins.right = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				skins.up=true;
			}
			if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
				skins.down=true;
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
				skins.enter = true;
			}
		}
	}

	@Override

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			player.jump = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			player.holding = false;
		}
	}
}
