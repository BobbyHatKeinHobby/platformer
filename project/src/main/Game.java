package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;

public class Game implements Runnable{
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Player player;
	private LevelManager levelManager;
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 2f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public Game() {
		initClasses(); // Muss als erstes im Konstruktor stehen
		
		gamePanel = new GamePanel(this); // MUSS vor der nächsten Zeile passieren, ansonsten ist gamePanel nicht initialisiert
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameLoop(); // steht zuletzt im Konstruktor
	}
	
	private void initClasses() {
		levelManager = new LevelManager(this);
		player = new Player(200, 200, (int) (32 * SCALE), (int) (32 * SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		player.update();
		levelManager.update();
	}
	
	public void render(Graphics g) {
		levelManager.draw(g); // Level vor Spieler
		player.render(g);
	}

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET; // Zeit die jeder Frame brauchen sollte in Nano-Sekunden
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		long previosTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {
			
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previosTime) / timePerUpdate;
			deltaF += (currentTime - previosTime) / timePerFrame;
			previosTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			
			if(System.currentTimeMillis() - lastCheck >= 1000) { // Wartet bis 1000ms vergangen sind und speichert neue Zeit
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS:" + updates);
				frames = 0;
				updates = 0;
			}
		}
		
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public GameWindow getGameWindow() {
		if(gameWindow != null) {
			return gameWindow;
		}
		else {
			System.err.println("ERROR: gameWindow is null; function 'getGameWindow()' in Game.java was called too early or the program has crashed");
			return null;
		}
	}
	
	public Player getPlayer() {
		return player;
	}
}
