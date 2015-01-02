package particles;

import openGL.GGTexture;
import openGL.GGRenderGL;

public class SimpleExampleDraw{

    public GGParticle particle;

    private GGTexture texture;
    
    public SimpleExampleDraw(GGTexture texture){
        this.texture = texture;
    }
    
    public void init(GGParticle particle){
        this.particle = particle;
    }
    
    public void update(int difTime) {

    }

    public void render() {
        GGRenderGL.drawRectWithTexture(texture, 1, particle.position.x, particle.position.y,
                particle.width, particle.height, particle.angleRadian);
    }
}
