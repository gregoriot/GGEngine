package projectiles;

import graphics.GGSprite;

/**
* Projectile
* 
* @author Gilvanei Gregorio
* @version 1.1
*/
public class GGProjectile extends GGSprite{

    private GGSprite update;
    private GGSprite draw;
    
    /**
     * Projectile constructor
     * 
     * @param float x
     * @param float y
     * @param float width
     * @param float height
     * @param ProjectileUpdate update
     * @param ProjectileDraw draw
     * */
    public GGProjectile(float x, float y, float width, float height, GGSprite update, GGSprite draw) {
        super(x, y, width, height);
        
        this.update = update;
        this.draw = draw;
        
        update.parent = this;
        draw.parent = this;
    }

    /** 
     * Update,call component responsible to update position and states of projectile
     * 
     * @param int difTime
     */
    @Override
    public void update(int difTime) {
        update.update(difTime);
        draw.update(difTime);
    }

    /** 
     * Render,call component responsible to render and draw of projectile
     */
    @Override
    public void render() {
        draw.render();
    }
}