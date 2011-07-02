package com.arpitonline.utils;

public class MathUtils {
	public static int randRange(int min, int max){
		int i = min + (int)(Math.random() * ((max - min) + 1));
		return i;
	}
}
