package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	public static final float SPEED = 0.4f;
	public static final float FLY_SPEED = 0.3f;
	public static final float TURN_SPEED = 0.8f;
	
	private Vector3f position;
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera(Vector3f position, float pitch, float yaw, float roll) {
		super();
		this.position = position;
		this.pitch = pitch;
		this.yaw = yaw;
		this.roll = roll;
	}

	public void move(){
		if (Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.x += (float)SPEED*Math.sin(yaw*Math.PI/180);
			position.z -= (float)SPEED*Math.cos(yaw*Math.PI/180);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.x -= (float)SPEED*Math.sin(yaw*Math.PI/180);
			position.z += (float)SPEED*Math.cos(yaw*Math.PI/180);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x += (float)SPEED*Math.cos(yaw*Math.PI/180);
			position.z += (float)SPEED*Math.sin(yaw*Math.PI/180);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x -= (float)SPEED*Math.cos(yaw*Math.PI/180);
			position.z -= (float)SPEED*Math.sin(yaw*Math.PI/180);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y += FLY_SPEED;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			position.y -= FLY_SPEED;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)){
			pitch -= TURN_SPEED;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			pitch += TURN_SPEED;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw -= TURN_SPEED;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw += TURN_SPEED;
		}
		
		/*
		yaw = -(Display.getWidth()-Mouse.getX()/2);
		pitch = (Display.getHeight()/2)-Mouse.getY();
		if (pitch>90){pitch=90;}
		if (pitch<-90){pitch=-90;}
		*/
	}
	
	public Vector3f getPosition() {
		return position;
	}
	public float getPitch() {
		return pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public float getRoll() {
		return roll;
	}
	


}
