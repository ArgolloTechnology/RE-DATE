package com.argotech.world;

public class Camera {

	public static int x = 0;
	public static int y = 0;
	
	public static int Clamp(int xAtual,int xMin,int xMax) {
		if(xAtual <= xMin) {
			xAtual = xMin;
		}
		if(xAtual >= xMax) {
			xAtual = xMax;
		}
		
		return xAtual;
		
	}
	
}
