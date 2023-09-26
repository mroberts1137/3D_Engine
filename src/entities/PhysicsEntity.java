package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;

public class PhysicsEntity extends Entity {
	
	private float gravity;
	private float friction;
	private float mass;
	
	private Vector3f velocity = new Vector3f(0,0,0);
	private Vector3f acceleration = new Vector3f(0,0,0);
	private Vector3f force = new Vector3f(0,0,0);

	public PhysicsEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
			float gravity, float friction, float mass) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.gravity = gravity;
		this.friction = friction;
		this.mass = mass;
		
		setForce(new Vector3f(0,-gravity,0));
		updateAcceleration();
	}
	
	public void move(){
		boolean inAir = placeFree(new Vector3f(0,0,0));
		//boolean inAirRefract = 
		updateVelocity();
		updatePosition();
		if (!inAir){
			this.velocity.y = 0;//-this.velocity.y*0.5f;
			//super.increasePosition(0, 1, 0);
		}
	}
	
	public void updateAcceleration(){
		this.acceleration.x = this.force.x/this.mass;
		this.acceleration.y = this.force.y/this.mass;
		this.acceleration.z = this.force.z/this.mass;
	}
	
	public void updateVelocity(){
		velocity.x += acceleration.x * DisplayManager.getFrameTimeSeconds();
		velocity.y += acceleration.y * DisplayManager.getFrameTimeSeconds();
		velocity.z += acceleration.z * DisplayManager.getFrameTimeSeconds();
	}
	
	public void updatePosition(){
		float dx = this.velocity.x * DisplayManager.getFrameTimeSeconds();
		float dy = this.velocity.y * DisplayManager.getFrameTimeSeconds();
		float dz = this.velocity.z * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx,dy,dz);
	}
	
	private boolean placeFree(Vector3f point){
		float checkX = super.getPosition().x + point.x;
		float checkY = super.getPosition().y + point.y;
		float checkZ = super.getPosition().z + point.z;
		if (checkY < 0){
			return false;
		}else{
			return true;
		}
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}

	public Vector3f getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector3f acceleration) {
		this.acceleration = acceleration;
	}

	public Vector3f getForce() {
		return force;
	}

	public void setForce(Vector3f force){
		this.force = force;
	}
}
