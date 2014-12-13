package projectiles;

import graphics.GGSprite;
import openGL.GGTexture;
import openGL.GGRenderGL;

public class SimpleExampleDraw extends GGSprite{

    private GGProjectile projectile;
    
    private GGTexture texture;

    public SimpleExampleDraw(GGTexture texture){
        this.texture = texture;
    }
    
    @Override
    public void update(int difTime) {

    }

    @Override
    public void render() {
        GGRenderGL.drawRectWithTexture(texture, 1, projectile.position.x, projectile.position.y,
                projectile.width, projectile.height, projectile.angleRadian);
    } 
}
