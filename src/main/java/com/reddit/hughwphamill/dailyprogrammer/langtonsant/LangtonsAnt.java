package com.reddit.hughwphamill.dailyprogrammer.langtonsant;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import jline.console.ConsoleReader;

public class LangtonsAnt {

	private static final int WIDTH = 1024;
	private static final int HEIGHT = 1024;
	
	private static final String DEBUG_PATTERN = "LRLRLRLR";
	private static final String DEBUG_ITERATIONS = "100";
	private static boolean DEBUG = false;

	private String pattern;
	private long iterations;
	private Color[][] map = new Color[WIDTH][HEIGHT];
	private Color[] colours;
	private Map<Color, Integer> colourIndex = new HashMap<>();
	private Map<Color, Character> colourRotation = new HashMap<>();
	private int antX;
	private int antY;
	private Direction antDirection = Direction.NORTH;

	public LangtonsAnt(String pattern, long iterations) {
		this.pattern = pattern;
		this.iterations = iterations;
	}

	public static void main(String... args) {

		ConsoleReader console;
		try {
			console = new ConsoleReader();
			String pattern;
			String iterationsString;
			if (!DEBUG ) {
				pattern = console.readLine("Ant Pattern: ");
				iterationsString = console.readLine("Iterations: ");
			} else {
				pattern = DEBUG_PATTERN;
				iterationsString = DEBUG_ITERATIONS;
			}
			verifyPattern(pattern);
			
			long iterations = verifyIterations(iterationsString);
			LangtonsAnt app = new LangtonsAnt(pattern, iterations);
			app.initColours();
			app.initAnt();
			app.simulate();
			app.render();
		} catch (IOException e) {
			System.out.println("Fatal IO Exception: " + e.getMessage());
			System.exit(1);
		}

	}

	private void render() throws IOException {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < WIDTH; x ++) {
			for (int y = 0; y < HEIGHT; y++) {
				image.setRGB(x, y, map[x][y].getRGB());
			}
		}
		File png = new File(pattern + "_" + iterations + ".png");
		png.createNewFile();
		ImageIO.write(image, "PNG", png);	
	}

	private static long verifyIterations(String iterations) {
		if (iterations.isEmpty() || !iterations.matches("^\\d+$")) {
			System.out.println("Iterations must be a postive number.");
			System.exit(1);
		}
		return Long.parseLong(iterations);

	}

	private void simulate() {
		for (int i=0; i<iterations; i++) {
			Direction newDirection = inferDirection(antX, antY);
			changeColour(antX, antY);
			moveAnt(newDirection);
		}	
	}

	private void moveAnt(Direction newDirection) {
		int newX = antX + newDirection.moveX();
		int newY = antY + newDirection.moveY();
		
		if (newX < 0 || newX == WIDTH) {
			newX = antX;
		}
		if (newY < 0 || newY == HEIGHT) {
			newY = antY;
		}
		
		antDirection = newDirection;
		antX = newX;
		antY = newY;
	}

	private Direction inferDirection(int x, int y) {
		char rotation = colourRotation.get(map[x][y]);
		switch (rotation) {
		case 'L' :
			return antDirection.ccw();
		case 'R' :
			return antDirection.cw();
		default :
			return null;
		}
	}

	private void changeColour(int x, int y) {
		Color colour = map[x][y];
		Color nextColour = colours[(colourIndex.get(colour) + 1) % colours.length];
		map[x][y] = nextColour;
	}

	private void initAnt() {
		Random random = new Random();
		antX = random.nextInt(WIDTH);
		antY = random.nextInt(HEIGHT);
	}

	private void initColours() {
		colours = new Color[pattern.length()];
		Random random = new Random();
		for (int i=0; i<pattern.length(); i++) {
			Color colour = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			colours[i] = colour;
			colourIndex.put(colour, i);
			colourRotation.put(colour, pattern.charAt(i));
		}

		for (int x=0; x<WIDTH; x++) {
			for (int y=0; y<HEIGHT; y++) {
				map[x][y] = colours[0];
			}
		}

	}

	private static void verifyPattern(String pattern) {
		if (pattern.isEmpty() || !pattern.matches("[LR]*")) {
			System.out.println("Ant Pattern must be a combination of L and R");
			System.exit(1);
		}

	}

}
