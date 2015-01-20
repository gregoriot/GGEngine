package projectiles;

import graphics.GGSprite;
import openGL.GGTexture;
import openGL.GGRenderGL;

public class SimpleExampleDrawProjectile extends GGSprite{

	  private GGTexture texture;

	    public SimpleExampleDrawProjectile(GGTexture texture){
	        this.texture = texture;
	    }
	    
		@Override
		protected void solid() {
			solid = true;
		}
		
	    @Override
	    public void update(int difTime) {

	    }

	    @Override
	    public void render() {
	        GGRenderGL.drawRectWithTexture(texture, 1, parent.position.x, parent.position.y,
	                parent.width, parent.height, parent.angleRadian);
	    } 
}
