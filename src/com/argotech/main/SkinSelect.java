package com.argotech.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import com.argotech.entidades.Player;
import com.argotech.graficos.UI;
import com.argotech.world.World;

public class SkinSelect{
	public String[] options = { "Marvat", "MarvatCovided" ,"KK", "Pinto", "Luck", "Iris", "IrisCovided", "Lucy", "Pinta","Lucky"};
	public int maxOption = options.length - 1;
	public int currentOption = 0;
	public boolean right,left,up,down,enter;
	public static int skin = 0;
	public static boolean unlocked[] = {
			true,false,false,false,false,false,false,false,false,false
	};
	public void tick() {
		if (left) {
			Sound.blip.Play();
			left = false;
			currentOption--;
			if (currentOption < 0) {
				currentOption = maxOption;
			}
		}
		if (right) {
			Sound.blip.Play();
			right = false;
			currentOption++;
			if (currentOption > maxOption) {
				currentOption = 0;
			}
		}
		if (up) {
			Sound.blip.Play();
			up = false;
			currentOption-=5;
			if (currentOption < 0) {
				currentOption+=10;
			}
		}
		if (down) {
			Sound.blip.Play();
			down = false;
			currentOption+=5;
			if (currentOption > maxOption) {
				currentOption-=10;
			}
		}
		if (enter) {
			Sound.blip.Play();
			enter = false;
			if (unlocked[currentOption]) {
				switch (options[currentOption]) {
				case "Marvat":
					skin = 0;
					break;
				case "MarvatCovided":
					skin = 1;
					break;
				case "KK":
					skin = 2;
					break;
				case "Pinto":
					skin = 3;
					break;
				case "Luck":
					skin = 4;
					break;
				case "Iris":
					skin = 5;
					break;
				case "IrisCovided":
					skin = 6;
					break;
				case "Lucy":
					skin = 7;
					break;
				case "Pinta":
					skin = 8;
					break;
				case "Lucky":
					skin = 9;
					break;
				}
			}
			World.restart("Level"+Game.CUR_LEVEL);
			Game.mode="MENU";
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// fundo
		g2.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		// skins
		for (int i = 0; i <= 9; i++) {
			BufferedImage skin = null;
			BufferedImage locked = Game.spritesheet.getSprite(48, 0, 16, 16);
			if(i<=4) {
				skin=Player.playerSkins.getSprite(0, i*16, 16, 16);
				g.drawImage(skin,
						Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 325 + (i*130),
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 130, 128, 128, null);
				if(!unlocked[i]) {
					g.drawImage(locked,
							Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 325 + (i*130),
							Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 130, 128, 128, null);
				}
			}else {
				skin=Player.playerSkins.getSprite(64, -80+(i*16), 16, 16);
				g.drawImage(skin,
						Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 325 + (i*130)-(5*130),
						Toolkit.getDefaultToolkit().getScreenSize().height / 2, 128, 128, null);
				if(!unlocked[i]) {
					g.drawImage(locked,
							Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 325 + (i*130)-(5*130),
							Toolkit.getDefaultToolkit().getScreenSize().height / 2, 128, 128, null);
				}
			}
		}
		// cursor
		g.setColor(Color.WHITE);
		if (options[currentOption] == "Marvat") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 325+i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 130+i, 128-(2*i), 128-(2*i));
			}
		} else if (options[currentOption] == "MarvatCovided") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) -195+ i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 130+i, 128-(2*i), 128-(2*i));
			}
		}else if (options[currentOption] == "KK") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) -65 + i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 130+i, 128-(2*i), 128-(2*i));
			}
		}else if (options[currentOption] == "Pinto") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) +65+ i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 130+i, 128-(2*i), 128-(2*i));
			}
		}else if (options[currentOption] == "Luck") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) +195+ i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 130+i, 128-(2*i), 128-(2*i));
			}
		}else if (options[currentOption] == "Iris") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) - 325+i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 +i, 128-(2*i), 128-(2*i));
			}
		} else if (options[currentOption] == "IrisCovided") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) -195+ i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2+i, 128-(2*i), 128-(2*i));
			}
		}else if (options[currentOption] == "Lucy") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) -65 + i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 +i, 128-(2*i), 128-(2*i));
			}
		}else if (options[currentOption] == "Pinta") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) +65+ i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 +i, 128-(2*i), 128-(2*i));
			}
		}else if (options[currentOption] == "Lucky") {
			for (int i = 0; i < 4; i++) {
				g.drawRect((Toolkit.getDefaultToolkit().getScreenSize().width / 2) +195+ i,
						Toolkit.getDefaultToolkit().getScreenSize().height / 2 +i, 128-(2*i), 128-(2*i));
			}
		}
	}
}
