package com.argotech.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Credits {
	private String[] musics = {
		"Snabisch"
	};
	private BufferedImage fanart;
	public Credits() {
		try {
			fanart = ImageIO.read(getClass().getResource("/fanartKK.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void render(Graphics g) {
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		g.setFont(Game.cute);
		// programção
		g.setColor(Color.BLACK);
		g.drawString("Programming:", 100, 128);
		g.setColor(Color.RED);
		g.drawString("Programming:", 102, 126);
		g.setColor(Color.BLACK);
		g.drawString("Marvin Argollo", 100, 178);
		g.setColor(new Color(0xff023ec2));
		g.drawString("Marvin Argollo", 102, 176);
		// arte
		g.setColor(Color.BLACK);
		g.drawString("Art:", 100, 228);
		g.setColor(Color.RED);
		g.drawString("Art:", 102, 226);
		g.setColor(Color.BLACK);
		g.drawString("Marvin Argollo", 100, 278);
		g.setColor(new Color(0xff023ec2));
		g.drawString("Marvin Argollo", 102, 276);
		// animação
		g.setColor(Color.BLACK);
		g.drawString("Animation:", 100, 328);
		g.setColor(Color.RED);
		g.drawString("Animation:", 102, 326);
		g.setColor(Color.BLACK);
		g.drawString("Marvin Argollo", 100, 378);
		g.setColor(new Color(0xff023ec2));
		g.drawString("Marvin Argollo", 102, 376);
		// efeitos sonoros
		g.setColor(Color.BLACK);
		g.drawString("Sound Efects:", 100, 428);
		g.setColor(Color.RED);
		g.drawString("Sound Efects:", 102, 426);
		g.setColor(Color.BLACK);
		g.drawString("Marvin Argollo", 100, 478);
		g.setColor(new Color(0xff023ec2));
		g.drawString("Marvin Argollo", 102, 476);
		// musicas
		g.setColor(Color.BLACK);
		g.drawString("Musics:", 100, 528);
		g.setColor(Color.RED);
		g.drawString("Musics:", 102, 526);
		g.setColor(Color.BLACK);
		g.drawString(musics[0], 100, 578);
		g.setColor(new Color(0xff023ec2));
		g.drawString(musics[0], 102, 576);
		// fanart
		g.drawImage(fanart, 500, 100,fanart.getWidth()/3,fanart.getHeight()/3, null);
		g.setColor(Color.BLACK);
		g.drawString("fanart made by: *kk*#9141", 530, (fanart.getHeight()/3)+134);
		g.setColor(Color.RED);
		g.drawString("fanart made by: *kk*#9141", 532, (fanart.getHeight()/3)+132);
	}
}
