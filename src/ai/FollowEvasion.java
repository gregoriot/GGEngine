package ai;

import utils.PVector;

public class FollowEvasion {
	
	public float moduleVector;
	
	public FollowEvasion(){}
	
	public PVector follow(PVector origin, PVector destiny){
		PVector dif = new PVector(destiny.x - origin.x,  destiny.y - origin.y);
        moduleVector = (float)Math.sqrt((dif.x * dif.x)+(dif.y * dif.y));
        
        return new PVector((dif.x/moduleVector), (dif.y/moduleVector));
	}
	
	public PVector evasion(PVector origin, PVector destiny){
		PVector dif = new PVector(origin.x - destiny.x,  origin.y - destiny.y);
        moduleVector = (float)Math.sqrt((dif.x * dif.x)+(dif.y * dif.y));
        
        return new PVector((dif.x/moduleVector), (dif.y/moduleVector));
	}
}
