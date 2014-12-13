package projectiles;

import graphics.GGSprite;
import org.lwjgl.util.vector.Vector2f;

public class SimpleExampleUpdate extends GGSprite{
    
    public GGProjectile projectile;
    
    private Vector2f vectorVelocity;
    
    public SimpleExampleUpdate(float velocity, float angleDegress){
    	projectile.angleRadian = (float)Math.toRadians(angleDegress);
        
        vectorVelocity = new Vector2f((float)(velocity * Math.sin(projectile.angleRadian)),
                (float)-(velocity * Math.cos(projectile.angleRadian)));
    }
    
    public void update(int difTime) {
        projectile.position.translate(difTime/1000f * vectorVelocity.x, difTime/1000f * vectorVelocity.y);
        
        projectile.collisionBox.x = ((int)projectile.position.x);
        projectile.collisionBox.y = ((int)projectile.position.y);
    }

	@Override
	public void render() {
		
	}
}
