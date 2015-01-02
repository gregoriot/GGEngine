package projectiles;

import graphics.GGSprite;

public class SimpleExampleUpdateProjectile extends GGSprite{
    
	 public SimpleExampleUpdateProjectile(){

	 }
	    
     public void update(int difTime) {
         parent.position.translate(difTime/1000f * ((GGProjectile)parent).vectorVelocity.x, 
        		 difTime/1000f * ((GGProjectile)parent).vectorVelocity.y);
        
         parent.collisionBox.x = ((int)parent.position.x);
         parent.collisionBox.y = ((int)parent.position.y);
     }

	 @Override
	 public void render() {
		
	 }
}
