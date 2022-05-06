package com.streep.EEMODLOADER.utils;

public class SMath {

	public static int clamp(int value, int min, int max) {
		if(value < min)
			return min;
		if(value > max)
			return max;
		return value;
	}
	
	public static double clamp(double value, double min, double max) {
		if(value < min)
			return min;
		if(value > max)
			return max;
		return value;
	}
	
}
