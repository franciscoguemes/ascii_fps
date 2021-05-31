package com.franciscoguemes.asciifps;

import java.util.Arrays;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AsciiFpsApp implements CommandLineRunner {

	/**
	 * Number of lines in the screen
	 */
	private static final int LINES =  24;
	
	/**
	 * Number of characters in the prompt (column)
	 */
	private static final int COLUMNS = 80;
	
	private static Logger LOG = LoggerFactory.getLogger(SpringBootApplication.class);
	
	private float fPlayerX = 8.0f;
	private float fPlayerY = 8.0f;
	private float fPlayerA = 0.0f;
	
	private static final float FOV = 3.14159f / 4.0f;
	
	private static final float fDepth = 16.0f;
	
	private static final int nMapHeight=16;
	private static final int nMapWidth=16;
	
	private static final String map =  
			"################"  +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"#..............#" +
			"################";

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		
//		PreconditionsChecker.verifyJavaVersion();
//		PreconditionsChecker.warnIfWindows();
//		PreconditionsChecker.verifyConsoleExists();
		
		AnsiConsole.systemInstall();
		
		SpringApplication.run(AsciiFpsApp.class, args);

		AnsiConsole.systemUninstall();
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) throws InterruptedException {
		LOG.info("EXECUTING : command line runner");

		/**
		 * Add here the selection of the screen Terminal Resolution...
		 */
		//TODO: 
		
		
		char [][] screen = new char[LINES][COLUMNS];
		this.initializeScreen(screen, '0');
				
//		writeConsoleOutputCharacter(screen);
//		Thread.sleep(2000l);
//		initializeScreen(screen, '*');
//		writeConsoleOutputCharacter(screen);
//		Thread.sleep(2000l);

		// Game Loop
		while(true) {
			
			for(int x = 0; x < COLUMNS; x++) {
				//For each column, calculate the projected ray angle into world space
				float fRayAngle = (fPlayerA - FOV / 2.0f) + ((float)x/(float)COLUMNS) + FOV;
				
				float distanceToWall = 0f;
				boolean hitWall = false;
				
				float eyeX = (float) Math.sin(fRayAngle); //Unit vector for ray in player space
				float eyeY = (float) Math.cos(fRayAngle);
				
				
				while(!hitWall && distanceToWall < fDepth) {
					distanceToWall += 0.1f;
					
					int nTestX = (int)(fPlayerX + eyeX * distanceToWall);
					int nTestY = (int)(fPlayerY + eyeY * distanceToWall);
					
					if (nTestX < 0 || nTestX >= nMapWidth || nTestY <0 || nTestY >= nMapHeight) {
						hitWall = true;
						distanceToWall = fDepth;
					}else {
						//Ray is inbounds so test to see if the ray cell is a wall block
						if(map.charAt(nTestY * nMapWidth + nTestX) == '#') {
							hitWall = true;
						}
						
					}
				}
				
				// Calculate distance to ceiling and floor
				int nCeiling = (int) ( (float)(LINES / 2.0) - LINES/((float)distanceToWall));
				int nFloor = COLUMNS - nCeiling;
				
				for(int y =0; y< LINES; y++) {
					if(y<nCeiling) {
						screen[y][x] = ' ';
					}else if(y>nCeiling && y<nFloor) { //Wall
						screen[y][x] = '#';
					}else { //floor
						screen[y][x] = ' ';
					}
				}
				
			}
			
			writeConsoleOutputCharacter(screen);
			
		}
		
		// Min 17

		
		/*
		 * Using Jansi 
		 */
//		Thread.sleep(10000l);
//		System.out.print( Ansi.ansi().eraseScreen().fg(Ansi.Color.RED).a("Hello").fg(Ansi.Color.GREEN).a(" World").reset() );
//		System.out.print(Ansi.ansi().newline());
//		Thread.sleep(500l);
		
		
//		System.out.print(Ansi.ansi().newline());
		
//		System.out.print(Ansi.ansi().eraseLine(Ansi.Erase.BACKWARD).cursorLeft(100) );
		
//		System.out.print(Ansi.ansi().newline());
//		System.out.print(Ansi.ansi().newline());
		
//		for(int i=0; i<LINES; i++) {
//			deletePreviousLine();
//		}
		

	}
	
	public void writeConsoleOutputCharacter(final char [][] screen) {
		this.writeConsoleOutputCharacter(screen, 0, 0);
	}
	
	public void writeConsoleOutputCharacter(final char [][] screen, int x, int y) {
		System.out.print(Ansi.ansi().cursor(x+1, y+1));
		int i=0;
		for( ; i<screen.length-1; i++) {
			System.out.print(Ansi.ansi().a(new String(screen[i])).newline());
		}
		System.out.print(new String(screen[i]));
	}
	
	public void initializeScreen(final char[][] screen, char initChar) {
		for(int i=0; i<screen.length; i++) {
			Arrays.fill(screen[i], initChar);
		}
	}
	
	public void deletePreviousLine() {
		System.out.print(Ansi.ansi().cursorUp(1) .cursorLeft(COLUMNS).eraseLine(Ansi.Erase.FORWARD));
	}
	
	public void deleteCurrentLine() {
		System.out.print(Ansi.ansi().cursorLeft(COLUMNS).eraseLine(Ansi.Erase.FORWARD));
	}

}