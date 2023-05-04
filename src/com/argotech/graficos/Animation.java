package com.argotech.graficos;

import com.argotech.main.Game;

public class Animation {
	private int frames = 0;
	private int maxFrames;
	public int index = 0;
	public int maxIndex;
	public int inicialIndex;
	public Animation(int maxFrames,int inicialIndex, int maxIndex) {
		this.maxFrames = maxFrames;
		this.maxIndex = maxIndex;
		this.inicialIndex = inicialIndex;
		Game.animation.add(this);
	}
	public void tick() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = inicialIndex;
			}
		}
	}
}
