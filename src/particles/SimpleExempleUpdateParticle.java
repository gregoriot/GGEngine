package particles;

import graphics.GGSprite;

public class SimpleExempleUpdateParticle extends GGSprite{
    
    public SimpleExempleUpdateParticle() {
    	
    }
    
	@Override
	protected void solid() {
		solid = false;
	}

    public void update(int difTime) {
         parent.position.add(difTime/1000f * ((GGParticle)parent).vectorVelocity.x, 
        		 difTime/1000f * ((GGParticle)parent).vectorVelocity.y, 0);
     }

	 @Override
	 public void render() {
		
	 }
}
