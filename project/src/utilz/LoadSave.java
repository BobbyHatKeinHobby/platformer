package utilz;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import main.Game;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "PlayerSpriteSheet.png";
	public static final String LEVEL_ATLAS = "spritesheet.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/res/" + fileName); // Stream öffnen
		
		try {
			if(is == null){
				System.err.println("Could not find file: " + fileName);
            	return null;
			}
			img = ImageIO.read(is);
			
		} catch (IOException e) {
			e.printStackTrace(); // Bild wurde nicht gefunden
		} finally { // finally wird immer ausgeführt
			try {
				is.close(); // Stream schließen
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		return img;
	}
	
	public static int[][] GetLevelData() {
	    int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
	    BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

	    // Loop based on the Image dimensions, but cap it at the Game dimensions
	    for (int j = 0; j < img.getHeight(); j++) {
	        for (int i = 0; i < img.getWidth(); i++) {
	            if (i < Game.TILES_IN_WIDTH && j < Game.TILES_IN_HEIGHT) {
	                Color color = new Color(img.getRGB(i, j));
	                int value = color.getRed();

	                if (value == 1) value = 0;
	                else if (value == 2) value = 1;
	                else if (value == 3) value = 2;
	                else value = 0;

	                lvlData[j][i] = value;
	            }
	        }
	    }
	    return lvlData;
	}
}
