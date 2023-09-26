package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Ball extends PhysicsEntity{
	
	private float radius;

	public Ball(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, float gravity,
			float friction, float mass, float radius) {
		super(model, position, rotX, rotY, rotZ, scale, gravity, friction, mass);
		this.radius = radius;
		super.setForce(new Vector3f(0,-gravity,0));
	}
	
	
	private boolean collisionCheck(Vector3f point){
		float checkX = super.getPosition().x + point.x;
		float checkY = super.getPosition().y + point.y;
		float checkZ = super.getPosition().z + point.z;
		if (checkY < 0){
			return true;
		}else{
			return false;
		}
	}

}
