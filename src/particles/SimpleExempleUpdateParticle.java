package particles;

import graphics.GGSprite;

public class SimpleExempleUpdateParticle extends GGSprite{
    
    public SimpleExempleUpdateParticle() {
    	
    }

    public void update(int difTime) {
         parent.position.translate(difTime/1000f * ((GGParticle)parent).vectorVelocity.x, 
        		 difTime/1000f * ((GGParticle)parent).vectorVelocity.y);
     }

	 @Override
	 public void render() {
		
	 }
}
