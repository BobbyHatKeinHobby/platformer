package entities;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.HelpMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity{

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 30;
	private int playerAction = IDLE;
	private boolean left, up, right, down, jump;
	private boolean moving = false;
	private float playerSpeed = 2f;
	private int[][] lvlData;
	private float xDrawOffset = 8 * Game.SCALE;
	private float yDrawOffset = 2 * Game.SCALE;
	
	// Sprung/Gravitation
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
	

	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height); // x und y an Überklasse geben
		loadAnimations();
		initHitbox(x, y, 14*Game.SCALE, 28*Game.SCALE);
		
	}
	
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[aniIndex][playerAction], (int) (hitbox.x- xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
		drawHitbox(g);
	}
	
	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
			}
		}
	}
	
	private void setAnimation() {
		if(moving) {
			//playerAction = RUNNING;
		}
	}
	
	private void updatePos() {
		moving = false;
		
		if(jump)
			jump();
		if(!left && !right && !inAir)
			return;
		
		float xSpeed = 0;
		
		if(left)
			xSpeed -= playerSpeed;
		if (right)
			xSpeed += playerSpeed;
		
		if(!inAir) {
			if(!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		}
		
		if(inAir) {
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}
			else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if(airSpeed > 0) {
					resetInAir();
				}
				else
					airSpeed = fallSpeedAfterCollision;
				
				updateXPos(xSpeed);
			}
			
		} else
			updateXPos(xSpeed);
		
		moving = true;
	}
	
	private void jump() {
		if(inAir)
			return;
		
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPostionNextToWall(hitbox, xSpeed);
		}
			
	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
		animations = new BufferedImage[2][1]; // Anzahl an Animierungssprites
			
		for(int i = 0; i < animations.length; i++) {
			animations[i][0] = img.getSubimage(i*32, 0, 32, 32);
		}
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}
	
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
}
