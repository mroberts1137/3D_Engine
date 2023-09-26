package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP = 30;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float vspeed = 0;
	
	private boolean isInAir = false;
	
	private static final float TERRAIN_HEIGHT = 0;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(){
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx,  0,  dz);
		vspeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, vspeed * DisplayManager.getFrameTimeSeconds(), 0);
		if (super.getPosition().y < TERRAIN_HEIGHT){
			vspeed = 0;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
	}
	
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_I)){
			this.currentSpeed = RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_K)){
			this.currentSpeed = -RUN_SPEED;
		}else{
			this.currentSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_L)){
			this.currentTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_J)){
			this.currentTurnSpeed = TURN_SPEED;
		}else{
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SEMICOLON)){
			jump();
		}
	}
	
	private void jump(){
		if (super.getPosition().y == TERRAIN_HEIGHT){
			this.vspeed = JUMP;
		}
	}
}
