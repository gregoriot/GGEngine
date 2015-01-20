package particles;

import utils.PVector;
import graphics.GGSprite;

/**
* Particle
* 
* @author Gilvanei Gregorio
* @version 1.1
*/
public class GGParticle extends GGSprite{
	/** Duration of particle */
    public int lifeSpan;
    
    public PVector vectorVelocity;
    public float velocity;
    public float angleDegress;
    
    private GGSprite update;
    private GGSprite draw;
    
    /**
     * Particle constructor
     * 
     * @param float x
     * @param float y
     * @param float width
     * @param float height
     * @param int lifeSpan
     * @param GGSprite update
     * @param GGSprite draw
     * */
    public GGParticle(GGSprite parent, float x, float y, float width, float height, int lifeSpan, float velocity, float angleDegress,
    		GGSprite update, GGSprite draw) {
        super(x, y, width, height);
        this.parent = parent;
        
        this.velocity = velocity;
        this.angleDegress = angleDegress;
        
        this.update = update;
        this.draw = draw;
        
        this.update.parent = this;
        this.draw.parent = this;
        
    	angleRadian = (float)Math.toRadians(angleDegress);
        
        vectorVelocity = new PVector((float)(velocity * Math.sin(angleRadian)),
                (float)-(velocity * Math.cos(angleRadian)));
        
        this.lifeSpan = lifeSpan;
    }
    
	@Override
	protected void solid() {
		solid = false;
	}
    
    /**
     * Update particle while life span most zero, using update s
     * sprite and update draw object too.
     * 
     * @param int difTime
     * */
    public void update(int difTime) {
        lifeSpan -= difTime;
        
        if(lifeSpan > 0){
        	update.update(difTime);
            draw.update(difTime);
        }else{
            active = false;
        }
    }

    /**
     * Render particle using draw sprite.
     * */
    public void render() {
        draw.render();
    }
}