package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.PhysicsEntity;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrain.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		MasterRenderer renderer = new MasterRenderer();
		
		Light light = new Light(new Vector3f(50, 500, 50), new Vector3f(1,1,1));
		Camera camera = new Camera(new Vector3f(40,10,40), 0, 0, 0);
		
		Random random = new Random();
		
		
		//-----textures
		
		ModelTexture reptileSkin = new ModelTexture(loader.loadTexture("reptileSkin"));
		reptileSkin.setShineDamper(30);
		reptileSkin.setReflectivity(1);
		
		ModelTexture brickTexture = new ModelTexture(loader.loadTexture("brickTexture"));
		brickTexture.setShineDamper(3);
		brickTexture.setReflectivity(.2f);
		
		ModelTexture tallGrassTexture = new ModelTexture(loader.loadTexture("grassTexture"));
		tallGrassTexture.setHasTransparency(true);
		tallGrassTexture.setUseFakeLighting(true);
		ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));
		fernTexture.setHasTransparency(true);
		fernTexture.setUseFakeLighting(true);
		
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("tree"));
		
		ModelTexture blueTexture = new ModelTexture(loader.loadTexture("blueTexture"));
		blueTexture.setShineDamper(30);
		blueTexture.setReflectivity(1);
		
		TerrainTexture grassTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture grassyTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture grassFlowersTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture mudTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture pathTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexture backgroundTexture = grassyTexture;
		TerrainTexture rTexture = mudTexture;
		TerrainTexture gTexture = grassFlowersTexture;
		TerrainTexture bTexture = pathTexture;
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		//-------models
		
		RawModel dragonRawModel = OBJLoader.loadObjModel("dragon",  loader);
		TexturedModel dragonModel = new TexturedModel(dragonRawModel, reptileSkin);
		
		RawModel torusRawModel = OBJLoader.loadObjModel("torus",  loader);	
		TexturedModel torusModel = new TexturedModel(torusRawModel, new ModelTexture(loader.loadTexture("redTexture")));
		
		RawModel cubeRawModel = OBJLoader.loadObjModel("cube",  loader);	
		TexturedModel cubeModel = new TexturedModel(cubeRawModel, brickTexture);
		
		RawModel sphereRawModel = OBJLoader.loadObjModel("sphere",  loader);	
		TexturedModel sphereModel = new TexturedModel(sphereRawModel, brickTexture);
		
		RawModel tallGrassRawModel = OBJLoader.loadObjModel("grassModel",  loader);	
		TexturedModel tallGrassModel = new TexturedModel(tallGrassRawModel, tallGrassTexture);
		
		RawModel treeRawModel = OBJLoader.loadObjModel("tree",  loader);	
		TexturedModel treeModel = new TexturedModel(treeRawModel, treeTexture);
		
		RawModel fernRawModel = OBJLoader.loadObjModel("fern",  loader);	
		TexturedModel fernModel = new TexturedModel(fernRawModel, fernTexture);
		
		RawModel bunnyRawModel = OBJLoader.loadObjModel("bunny",  loader);	
		TexturedModel bunnyModel = new TexturedModel(bunnyRawModel, blueTexture);
		
		//-------entities
		
		//List<Entity> allEntities = new ArrayList<Entity>();
		
		Entity dragon = new Entity(dragonModel, new Vector3f(0,20,-20), 0,0,0, 1);
		Player player = new Player(bunnyModel, new Vector3f(40,0,-40), 0,0,0, 1);
		
		PhysicsEntity ball = new PhysicsEntity(sphereModel, new Vector3f(20,20,-20), 0, 0, 0, 1, 50, 0, 1);
		
		List<Entity> tallGrassList = new ArrayList<Entity>();
		for(int i=0 ; i<100 ; i++){
			float x = random.nextFloat()*400-200;
			float z = random.nextFloat()* -400;
			tallGrassList.add(new Entity(tallGrassModel, new Vector3f(x,0,z), 0, 0, 0, 1));
		}
		List<Entity> fernList = new ArrayList<Entity>();
		for(int i=0 ; i<100 ; i++){
			float x = random.nextFloat()*400-200;
			float z = random.nextFloat()* -400;
			fernList.add(new Entity(fernModel, new Vector3f(x,0,z), 0, 0, 0, 1));
		}
		List<Entity> treeList = new ArrayList<Entity>();
		for(int i=0 ; i<50 ; i++){
			float x = random.nextFloat()*400-200;
			float z = random.nextFloat()* -400;
			tallGrassList.add(new Entity(treeModel, new Vector3f(x,0,z), 0, 0, 0, 5));
		}
		
		//-----terrains
		
		Terrain terrain = new Terrain(-1,-1, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(0,-1, loader, texturePack, blendMap);
		
		//--------main game loop
		
		while(!Display.isCloseRequested()){
			
			//Game Logic
			
			dragon.increaseRotation(0, .5f, 0);
			
			camera.move();
			player.move();
			ball.move();
			
			renderer.processEntity(dragon);
			renderer.processEntity(player);
			renderer.processEntity(ball);
			for (Entity e:tallGrassList){
				renderer.processEntity(e);
			}
			for (Entity e:fernList){
				renderer.processEntity(e);
			}
			for (Entity e:treeList){
				renderer.processEntity(e);
			}
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
			renderer.render(light,  camera);
			
			DisplayManager.updateDisplay();
			
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
