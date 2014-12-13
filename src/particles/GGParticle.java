package particles;

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

    /** Object responsible for update the particle */
    private GGSprite update;
    
    /** Object responsible for draw the particle */
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
    public GGParticle(float x, float y, float width, float height, int lifeSpan, GGSprite update, GGSprite draw) {
        super(x, y, width, height);
        this.lifeSpan = lifeSpan;
        
        this.update = update;
        this.draw = draw;
        
        update.parent = this;
        draw.parent = this;
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