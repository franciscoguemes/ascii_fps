package com.franciscoguemes.asciifps.utils;

import java.io.Console;

import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;

public class PreconditionsChecker {

	public static void verifyJavaVersion() {
		if(SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5)) return;
		
		StringBuilder errMsg = new StringBuilder();
		errMsg.append("The application requires at least Java 1.5.0");
		errMsg.append(System.lineSeparator());
		errMsg.append("Please install a Java version >= 1.5.0");
		errMsg.append(System.lineSeparator());
		errMsg.append("More information about Java versions at: https://en.wikipedia.org/wiki/Java_version_history");
		errMsg.append(System.lineSeparator());
		
		throw new IllegalStateException(errMsg.toString());
	}

	public static void warnIfWindows() {
		if(SystemUtils.IS_OS_WINDOWS) {
			StringBuilder warnMsg= new StringBuilder();
			warnMsg.append("You are running a Windows OS. It is possible that this application do not run properly in your OS");
			warnMsg.append(System.lineSeparator());
			warnMsg.append("Please take into account that in order to make this programm to work you will need to install \"Microsoft Visual C++ 2008 SP1 Redistributable\" or higher");
			warnMsg.append(System.lineSeparator());
			warnMsg.append("You can download it from here: https://support.microsoft.com/en-gb/help/2977003/the-latest-supported-visual-c-downloads");
			warnMsg.append(System.lineSeparator());
			
			System.err.print(warnMsg.toString());
		}
		
	}

	public static void verifyConsoleExists() {
		Console console = System.console();
		if (console == null) {
			StringBuilder errMsg = new StringBuilder();
			errMsg.append("The application requires to run in a Java environment with a console.");
			errMsg.append(System.lineSeparator());
			errMsg.append("This error is usually triggered due to you are trying to run this application from your IDE");
			errMsg.append(System.lineSeparator());
			errMsg.append("Please try to run the application from the terminal/console of your OS.");
			errMsg.append(System.lineSeparator());
			
			throw new IllegalStateException(errMsg.toString());
		}
		
	}
	
	
}
