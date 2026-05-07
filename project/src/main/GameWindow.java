package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow{
	private JFrame jframe;
	
	public GameWindow(GamePanel gamePanel) {
		
		jframe = new JFrame();
		
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Beendet das Programm, wenn das Fenster geschlossen wird
		jframe.add(gamePanel); // Panel und Fenster verbinden
		jframe.setResizable(false);
		jframe.pack(); // Fenster an GamePanel anpassen
		jframe.setLocationRelativeTo(null); // Fenster erscheint in der Mitte des Bildschirms
		jframe.setVisible(true); // Macht das Fenster sichtbar (MUSS GENAU HIER STEHEN, sonst entsteht in seltenen Fällen ein leeres Fenster)
		jframe.addWindowFocusListener(new WindowFocusListener() { // wenn man runter vom fenster geht
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
			}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
