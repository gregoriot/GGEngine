package utils;

import openGL.GGRenderGL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import scenes.GGScene;

/**
* GamePanel, class of program that control the game, your game loop, 
* update scenes and render scenes.
* 
* @author Gilvanei Gregorio
* @author Gustavo Gregorio
* 
* @version 1.0
*/
public class GGGamePanel {

	/** Singleton , instance of game panel */
	public static GGGamePanel instance;
	
	/** Frame per second */
	public static int FPS;
	
	/** Conditional of get out main thread */
	public boolean running = false;
	
	public int width;
	public int height;
    
	/** Current time of system */
	private long currentTime = 0;
	/** Time in last cycle of system */
	private int lastTime = 0;
	/** Difference of current time of system and time in last cycle of system */
	private int difTime = 0;
	/** Some fps */
	private int sfps = 0;
	/** Maximum fps */
    private int fpsSync = 0;
	
    
    /* Scene group */
    public GGScene currentScene;
    
    /**
     * GamePanel, constructor of class.
     */
    public GGGamePanel(){
    	instance  = this;
    	
        System.out.println("Main - main - Application Version");
        
        fpsSync = 60;
    }
    
    public void loadOpenGL(){
        try {
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        
        GGRenderGL.initOpenGL();
    }
    
    
	/** 
	 * Start and run main thread.
	 */
    public void start(GGScene scene) {
        /* Set conditional main thread to true */
        running = true;
        
		/* Starts, opening scene */
        currentScene = scene;
        
        /* Game loop, update and render scene, calculate difTime and fps */
        while (!Display.isCloseRequested() && running) {
            currentScene.keyboardInput();
            currentScene.mouseInput();
            
            currentScene.update(difTime);
            currentScene.render();
            currentScene.renderInterface();

			if (currentTime > 0) {
				difTime = (int) (System.currentTimeMillis() - currentTime);
			}
			
			currentTime = System.currentTimeMillis();
			if (((int) (currentTime / 1000)) != lastTime) {
				FPS = sfps;
				sfps = 0;
			}
			
			sfps++;
			lastTime = (int) (currentTime / 1000);
			
            Display.update();
            /* Limit fps */ 
            Display.sync(fpsSync);
        }

        Display.destroy();
    }
}