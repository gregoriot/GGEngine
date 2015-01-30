package particles;

import org.newdawn.slick.Color;

import graphics.GGSprite;
import openGL.GGTexture;
import openGL.GGRenderGL;

public class SimpleExampleDrawParticle extends GGSprite{

	private GGTexture texture;

    public SimpleExampleDrawParticle(GGTexture texture){
        this.texture = texture;
    }
    
	@Override
	protected void solid() {
		solid = false;
	}
    
    @Override
    public void update(int difTime) {

    }

    @Override
    public void render() {
        GGRenderGL.drawRectWithTexture(texture, Color.white, parent.position.x, parent.position.y,
                parent.width, parent.height, parent.angleRadian);
    } 
}
