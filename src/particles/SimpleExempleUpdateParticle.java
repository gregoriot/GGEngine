package particles;

import org.lwjgl.util.vector.Vector2f;

public class SimpleExempleUpdate {
    
    public GGParticle particle;
    
    private Vector2f vectorVelocity;
    private float velocity;
    private float angleRadian;
    
    public SimpleExempleUpdate(float velocity, float angleDegress) {
        this.velocity = velocity;
        this.angleRadian = (float)Math.toRadians(angleDegress);
    }
    
    public void init(GGParticle particle){
        this.particle = particle;
        particle.angleRadian = angleRadian;
        
        vectorVelocity = new Vector2f(
                (float) (velocity * Math.sin(particle.angleRadian)),
                (float)-(velocity * Math.cos(particle.angleRadian))
            );
    }

    public void update(int difTime) {
        particle.position.translate(difTime/1000f * vectorVelocity.x,
                                    difTime/1000f * vectorVelocity.y);
    }

}
