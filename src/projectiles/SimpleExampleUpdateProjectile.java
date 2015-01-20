package projectiles;

import graphics.GGSprite;

public class SimpleExampleUpdateProjectile extends GGSprite{
    
	 public SimpleExampleUpdateProjectile(){

	 }
	 
	 @Override
	 protected void solid() {
		solid = true;
	 }
	    
     public void update(int difTime) {
         parent.position.add(difTime/1000f * ((GGProjectile)parent).vectorVelocity.x, 
        		 difTime/1000f * ((GGProjectile)parent).vectorVelocity.y, 0);
        
         parent.collisionBox.x = ((int)parent.position.x);
         parent.collisionBox.y = ((int)parent.position.y);
     }

	 @Override
	 public void render() {
		
	 }
}
