package com.franciscoguemes.asciifps.io.textui;

import org.apache.commons.lang3.SystemUtils;

public class CommandLine {

	private static String OS = null;
	
	public static String getOsName() {
		if(OS == null) { OS = System.getProperty("os.name"); }
	      return OS;
	}
	
	public static boolean isWindows() {
		return SystemUtils.IS_OS_WINDOWS;
	}
	
	public static boolean isLinux() {
		return SystemUtils.IS_OS_LINUX;
	}
	
}
