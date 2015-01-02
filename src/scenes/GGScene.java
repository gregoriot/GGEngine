package scenes;

import graphics.GGSprite;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import openGL.GGRenderGL;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import utils.GGGamePanel;

/**
* Scene, abstract class. Scene is a struct of game objects.
* 
* @author Gilvanei Gregorio
* @version 1.1
*/
public abstract class GGScene {
    
	public static final int LEFT_BUTTON = 0;
	public static final int RIGHT_BUTTON = 1;
	public static final int MIDDLE_BUTTON = 2;
	
	public static GGScene instance;
	
	/** Parent, this object control the game loop, and all game. */
    public GGGamePanel parent;
    
    /** Position of scene */
    public Vector2f position;
    
    /** Dimensions of scene in axis x*/
    public int width;
    /** Dimensions of scene in axis y*/
    public int height;
    
    /** Zoom of scene */
    public float zoom = 1f;
    
    /** Position of mouse in relation to Screen */
    protected Vector2f mousePosition;
    protected int mouseWheel;
    
    /** List with all sprites of scene in layers */
    private LinkedList<LinkedList<GGSprite>> graphicsElements;
    
    /** Auxiliary list, used to sort the sprites that will draw */
    private LinkedList<GGSprite> garbageTruck = new LinkedList<GGSprite>();
    
    /** Auxiliary list, used to sort the sprites that will draw */
    private LinkedList<GGSprite> addTruck = new LinkedList<GGSprite>();

    /** Number of list to draw in scene */
    private int numberOfLayers;
    
    /**
     * Scene constructor
     * 
     * @param GGGamePanel gamePanel
     * @param int numberOfLayers
     * */
    public GGScene(GGGamePanel gamePanel, int numberOfLayers, int width, int height){
        parent = gamePanel;
        instance = this;
        
        position = new Vector2f();
        mousePosition = new Vector2f();
        
        this.width = width;
        this.height = height;
        
        this.numberOfLayers = numberOfLayers;
        
        graphicsElements = new LinkedList<LinkedList<GGSprite>>();
        for(int i=0;i<numberOfLayers; i++)
        	graphicsElements.add(new LinkedList<GGSprite>());
    }
    
    /** 
     * Auxiliary list, sprite will add in graphic elements of scene.
     * 
     * @param Sprite s
     */
    public void attachSprite(GGSprite s, int layer){
    	try{
    		s.layerScene = layer;
    		addTruck.add(s);
    	}catch(Exception e){
    		System.out.println("Scene - attachSprite - Dont possible attach sprite, error: "+e);
    	}
    }
    
    /** 
     * Auxiliary list, sprite will remove in graphic elements of scene.
     * 
     * @param Sprite s
     */
    public void detachSprite(GGSprite detachS, int layer){
    	try{
			garbageTruck.add(detachS);
    	}catch(Exception e){
    		System.out.println("Scene - detachSprite - Dont possible detach sprite, error: "+e);
    	}
    }
    
    /** 
     * keyboardInput abstract method. keyboard input;
     */
    public abstract void keyboardInput();
    
    /** 
     * mouseInput abstract method. mouse input;
     */
    public abstract void mouseInput();
    
    /** 
     * update, traverses the element list and update each element.
     * 
     * @param int difTime
     */
    public void update(int difTime){
    	/* List elements inactive in element list and put in remove list if not update her*/
    	for (Iterator<LinkedList<GGSprite>> iteratorLs = graphicsElements.iterator(); iteratorLs.hasNext();) {
    		LinkedList<GGSprite> ls = iteratorLs.next();
	    	for (Iterator<GGSprite> iterator = ls.iterator(); iterator.hasNext();) {
				GGSprite s = (GGSprite)iterator.next();
				
				/* Put to remove */
				if(!s.active){
					garbageTruck.add(s);
					continue;
				}
				
				/* Update */
				s.update(difTime);
	    	}
    	}
    	
    	runGarbageTruck();
    	runAddTruck();
    }
    
    /** 
     * render, scale and tranlate the scene, traverses the draw list and sort it,
     * then traverses the draw list list and render each element.
     */
    public void render(){
    	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        /* Load the Identity Matrix to reset our drawing locations */
        GGRenderGL.loadIdentity(); 
        GL11.glLoadIdentity();
        
        /* Zoom  */
        GGRenderGL.scale(zoom, zoom);
    	GL11.glScalef(zoom, zoom, 1);
    	
    	 /* Position  */
    	GGRenderGL.translate(-position.x, -position.y);
    	GL11.glTranslatef(-position.x, -position.y, 0);
    	
    	/* sort elements of each list */
    	for (Iterator<LinkedList<GGSprite>> iteratorLs = graphicsElements.iterator(); iteratorLs.hasNext();) {
    		LinkedList<GGSprite> ls = iteratorLs.next();
    		
    		try {
				sortElementList(ls);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("GGScene - render - ERROR SORT ELEMENT LIST");
			}
    	}
		
		/* render each sprite already ordered of draw list */
    	for (Iterator<LinkedList<GGSprite>> iteratorLs = graphicsElements.iterator(); iteratorLs.hasNext();) {
    		LinkedList<GGSprite> ls = iteratorLs.next();
	    	for (Iterator<GGSprite> iterator = ls.iterator(); iterator.hasNext();) {
				GGSprite s = iterator.next();
				s.render();
	    	}
	    }
    };
    
    /** 
     * renderInterface abstract method. Render interface, hud;
     */
	public void renderInterface(){
    	/* Load the Identity Matrix to reset our drawing locations */
        GGRenderGL.loadIdentity(); 
        GL11.glLoadIdentity();
        
    	 /* Position  */
    	GGRenderGL.translate(-width/2f, -height/2f);
    	GL11.glTranslatef(-width/2f, -height/2f, 0);
    	
//    	FontManager.font.drawString(0, 0,"FPS: " +GamePanel.FPS, Color.red);
//		FontManager.font2.drawString(0, 0, "",Color.white);
    }
    
	 /** 
     *numberOfLayers, get number of layers using in scene.
     * 
     * @return int, number of layers in scene
     */
    public int numberOfLayers(){
    	return numberOfLayers;
    }
    
    /** 
     * mouseToWorldCoordinate, convert mouse screen position in mouse world position.
     * 
     * @return Vector2f, position of mouse in world coordinate.
     */
    public Vector2f mouseToWorldCoordinate(){
    	Vector2f result = new Vector2f();
    	
    	result.x = (mousePosition.x/zoom) - (parent.width/zoom/2) + position.x;
     	result.y = (((parent.height-mousePosition.y)/zoom) - (parent.height/zoom/2) - position.y)*-1;
     	
     	return result;
    }
    
    /** 
     * sort element list.
     */
    private void sortElementList(LinkedList<GGSprite> list) throws Exception{
		/* Sort draw list, with respect to y of sprite */
		Collections.sort(list, new Comparator<GGSprite>() {
			@Override
			public int compare(GGSprite s1, GGSprite s2) {
		        if (s1.position.y+s1.height < s2.position.y+s2.height) {
		            return -1;
		        }else {
		            return 1;
		        }
			}
		}); 
    }
    
    private void runGarbageTruck(){    	
    	for(GGSprite s : garbageTruck){
    		graphicsElements.get(s.layerScene).remove(s);
    	}
    }
    
    private void runAddTruck(){    	
    	for(int i=0; i<addTruck.size(); i++){
    		GGSprite s = addTruck.get(i);
    		graphicsElements.get(s.layerScene).add(s);
    	}
    	addTruck.clear();
    }
}
