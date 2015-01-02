package particles;

import graphics.GGSprite;
import openGL.GGTexture;
import openGL.GGRenderGL;

public class SimpleExampleDrawParticle extends GGSprite{

	private GGTexture texture;

    public SimpleExampleDrawParticle(GGTexture texture){
        this.texture = texture;
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
