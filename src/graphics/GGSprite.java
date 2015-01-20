package graphics;

import java.awt.geom.Rectangle2D;

import utils.PVector;

/**
* Sprite, abstract class. Sprite is a most simple strut of game.
* 
* @author Gilvanei Gregorio
* @version 1.2
*/
public abstract class GGSprite {
    
	/** Position in scene */
    public PVector position;
    
    /** Dimensions of scene in axis x*/
    public float width;
    /** Dimensions of scene in axis y*/
    public float height;
    
    /** Angle in radiant */
    public float angleRadian;
    
    /** collision box of sprite to do collide*/
    public Rectangle2D.Float collisionBox;
    
    /** death or alive in scene */
    public boolean active;
    
    /** solid */
    public boolean solid;
    
    /** If have parent */
    public GGSprite parent;
    
    public int layerScene;
    
	public float currentMoveSpeed;
	public PVector velocity;
	public float maxforce;

    /**
     * Sprite constructor
     * 
     * @param float x
     * @param float y
     * @param float width
     * @param float height
     * */
    public GGSprite(float x, float y, float width, float height){
        this.position = new PVector	(x, y);
        this.width = width;
        this.height = height;
        
        this.collisionBox = new Rectangle2D.Float (x, y, width, height);
        
        this.active = true;
        this.parent = null;
        
        solid();
    }
    
    protected abstract void solid();
    
    /**
     * Sprite empty constructor
     * */
    public GGSprite(){

    }
    
    public abstract void update(int difTime);
    public abstract void render();
}