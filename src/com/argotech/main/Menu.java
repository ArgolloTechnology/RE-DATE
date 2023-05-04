package com.argotech.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.argotech.entidades.Lover;
import com.argotech.entidades.Player;
import com.argotech.graficos.UI;
import com.argotech.world.World;

public class Menu {

	public String[] options = { "New Game", "Skins", "Credits", "Quit" };
	public int maxOption = options.length - 1;
	public int currentOption = 0;

	public boolean up, down, enter;
	public static boolean pause;
	public static BufferedImage coração = Game.spritesheet.getSprite(32, 0, 16, 16);
	private BufferedImage logoArgotech;
	public static BufferedImage logoREDATE;
	public static BufferedImage rateGame;
	public static boolean load = true;

	public static int bestTimeM = 9999, bestTimeS;
	public static boolean saveExists = false;
	public static boolean saveGame = false;

	public Menu() {
		try {
			logoArgotech = ImageIO.read(getClass().getResource("/Logo.png"));
			logoREDATE = ImageIO.read(getClass().getResource("/RE-DATE.png"));
			rateGame = ImageIO.read(getClass().getResource("/Stars.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tick() {
		File file = new File("save.txt");
		if (load) {
			load = false;
			if (file.exists()) {
				String save = load(1);
				applySave(save);
			}
		}
		if (up) {
			Sound.blip.Play();
			up = false;
			currentOption--;
			if (currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if (down) {
			Sound.blip.Play();
			down = false;
			currentOption++;
			if (currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if (enter) {
			Sound.blip.Play();
			enter = false;
			if (options[currentOption] == "New Game") {
				Game.mode = "NORMAL";
				if (!pause) {
					pause = false;
					UI.beginTime = true;
				}
			} else if (options[currentOption] == "Skins") {
				Game.mode = "SKINS";
			}else if (options[currentOption] == "Credits") {
				Game.mode = "CREDITS";
			} else if (options[currentOption] == "Quit") {
				System.exit(1);
			}
		}
	}

	public static void applySave(String str) {
		String[] spl = str.split("/");
		for (int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch (spl2[0]) {
			case "Min":
				bestTimeM = Integer.parseInt(spl2[1]);
				break;
			case "Seg":
				bestTimeS = Integer.parseInt(spl2[1]);
				break;
			case "Marvat":
				SkinSelect.unlocked[0] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "MarvatCovided":
				SkinSelect.unlocked[1] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "KK":
				SkinSelect.unlocked[2] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "Pinto":
				SkinSelect.unlocked[3] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "Luck":
				SkinSelect.unlocked[4] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "Iris":
				SkinSelect.unlocked[5] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "IrisCovided":
				SkinSelect.unlocked[6] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "Lucy":
				SkinSelect.unlocked[7] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "Pinta":
				SkinSelect.unlocked[8] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			case "Lucky":
				SkinSelect.unlocked[9] = Player.IntToBool(Integer.parseInt(spl2[1]));
				break;
			}
		}
	}

	public static String load(int encode) {
		String line = "";
		File file = new File("save.txt");
		if (file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader(file));
				try {
					while ((singleLine = reader.readLine()) != null) {
						try {
							String[] trans = singleLine.split(":");
							char[] val = trans[1].toCharArray();
							trans[1] = "";
							for (int i = 0; i < val.length; i++) {
								val[i] -= encode;
								trans[1] += val[i];
							}
							line += trans[0];
							line += ":";
							line += trans[1];
							line += "/";
						} catch (Exception e) {
						}
					}
				} catch (IOException e) {
				}
			} catch (FileNotFoundException e) {
			}
		}

		return line;
	}

	public static void save(String[] val1, int[] val2, int encode) {
		File file = new File("save.txt");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for (int n = 0; n < value.length; n++) {
				value[n] += encode;
				current += value[n];
			}
			try {
				writer.write(current);
				if (i < val1.length - 1)
					writer.newLine();
			} catch (IOException e) {
			}
		}
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
		}
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// fundo
		g2.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		// logo ARGOTECH
		g.drawImage(logoArgotech, Toolkit.getDefaultToolkit().getScreenSize().width - 113,
				Toolkit.getDefaultToolkit().getScreenSize().height - 128, null);
		// logo jogo
		g.setFont(Game.cute);
		g.drawImage(logoREDATE,
				(Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - (logoREDATE.getWidth() / 3) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 150, logoREDATE.getWidth() / 3,
				logoREDATE.getHeight() / 3, null);
		//skin
		g.drawImage(Player.skins.get(Player.defalt.index),
				(Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - (logoREDATE.getWidth() / 3) / 2 - 130,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 140, 128, 128, null);
		g.drawImage(Lover.skins.get(Player.defalt.index),
				(Toolkit.getDefaultToolkit().getScreenSize().width) / 2 + (logoREDATE.getWidth() / 3) - 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - 140, -128, 128, null);
		// opções do menu
		if (!pause) {
			g.setColor(Color.BLACK);
			g.drawString("New Game", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 82,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 252);
			g.setColor(new Color(0x023ec2));
			g.drawString("New Game", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 80,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 250);
		} else {
			g.setColor(Color.BLACK);
			g.drawString("Resume", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 72,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 252);
			g.setColor(new Color(0x023ec2));
			g.drawString("Resume", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 70,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 250);
		}
		g.setColor(Color.BLACK);
		g.drawString("Skins", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 47,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 302);
		g.setColor(new Color(0x023ec2));
		g.drawString("Skins", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 45,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 300);
		g.setColor(Color.BLACK);
		g.setColor(Color.BLACK);
		g.drawString("Credits", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 57,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 352);
		g.setColor(new Color(0x023ec2));
		g.drawString("Credits", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 55,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 350);
		g.setColor(Color.BLACK);
		g.drawString("Quit", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 33,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 402);
		g.setColor(new Color(0x023ec2));
		g.drawString("Quit", (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 31,
				(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 400);
		// seletor
		if (options[currentOption] == "New Game") {
			g.drawImage(coração, (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 110,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 220, 32, 32, null);
		} else if (options[currentOption] == "Skins") {
			g.drawImage(coração, (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 85,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 270, 32, 32, null);
		}else if (options[currentOption] == "Credits") {
			g.drawImage(coração, (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 85,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 320, 32, 32, null);
		} else if (options[currentOption] == "Quit") {
			g.drawImage(coração, (Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 62,
					(Toolkit.getDefaultToolkit().getScreenSize().height) / 3 + 370, 32, 32, null);
		}
		// HIGH SCORE
		bestTime(g);
		// versão
		version(g, 4.1);
		// mendigada
		g.drawImage(rateGame, 10, Toolkit.getDefaultToolkit().getScreenSize().height - 150, null);
	}

	/**
	 * @param g
	 */
	private void version(Graphics g, double version) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString("version: " + version, Toolkit.getDefaultToolkit().getScreenSize().width - 64, 20);
	}

	/**
	 * @param g
	 */
	private void bestTime(Graphics g) {
		String format = "";
		if (bestTimeM < 10) {
			format += "0" + bestTimeM + ":";
		} else {
			format += bestTimeM + ":";
		}
		if (bestTimeS < 10) {
			format += "0" + bestTimeS;
		} else {
			format += bestTimeS;
		}
		g.setFont(Game.cute);
		File file = new File("save.txt");
		if (file.exists()) {
			g.setColor(Color.black);
			g.drawString("BEST TIME: " + format, Toolkit.getDefaultToolkit().getScreenSize().width - 450, 64);
			g.setColor(new Color(0xff023ec2));
			g.drawString("BEST TIME: " + format, Toolkit.getDefaultToolkit().getScreenSize().width - 448, 62);
		}
	}
}
