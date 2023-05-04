package com.argotech.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import com.argotech.entidades.Key;
import com.argotech.main.Game;
import com.argotech.world.World;

public class UI {

	public static int frames = 0;
	public static int seconds = 0;
	public static int minutes = 0;
	public static boolean beginTime = true;
	public int fps = 0;

	public void tick() {
		if(beginTime) {
			beginTime=false;
			frames = 0;
			seconds = 0;
			minutes = 0;
		}
		frames++;
		if (frames == 60) {
			frames = 0;
			seconds++;
			if (seconds == 60) {
				seconds = 0;
				minutes++;
			}
		}
	}

	public void render(Graphics g) {
		if (Game.mode == "NORMAL") {
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("fps: "+fps, 20, 20);	
			g.setFont(Game.cute);
			g.setColor(Color.black);
			g.drawString("Level " + Game.CUR_LEVEL, 100, 128);
			g.setColor(new Color(0xff023ec2));
			g.drawString("Level " + Game.CUR_LEVEL, 102, 126);
			String format = "";
			if(minutes < 10) {
				format+="0"+minutes+":";
			}else {
				format+=minutes+":";
			}
			if(seconds < 10) {
				format+="0"+seconds;
			}else {
				format+=seconds;
			}
			g.setFont(Game.cute);
			g.setColor(Color.black);
			g.drawString(format, Toolkit.getDefaultToolkit().getScreenSize().width - 216, 128);
			g.setColor(new Color(0xff023ec2));
			g.drawString(format, Toolkit.getDefaultToolkit().getScreenSize().width - 214, 126);
			if (Game.CUR_LEVEL == 1) {
				g.setFont(Game.cute1);
				g.setColor(Color.black);
				g.drawString("Press R to Reset Level or T to Reset the Game", 300, 128);
				g.setColor(Color.RED);
				g.drawString("Press R to Reset Level or T to Reset the Game", 302, 126);
			}
			for (int i = 0; i < (Game.player.chave1 + Game.player.chave2 + Game.player.chave3); i++) {
					g.drawImage(Key.key, Toolkit.getDefaultToolkit().getScreenSize().width - 64 - 16, 32 + (i * 32), 64,
							64, null);
			}
		}
	}

}
