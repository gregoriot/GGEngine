package ai;

import graphics.GGSprite;
import java.util.ArrayList;
import utils.PVector;

public class Flocking {

	public PVector align(GGSprite basicUnit, ArrayList<GGSprite> otherUnit) {
		float neighbordist = 50;
		PVector sum = new PVector(0, 0);
		int count = 0;
		for (GGSprite other : otherUnit) {
			float d = PVector.dist(basicUnit.position, other.position);
			if ((d > 0) && (d < neighbordist)) {
				sum.add(other.velocity);
				count++;
			}
		}
		if (count > 0) {
			sum.div((float) count);
			sum.normalize();
			sum.mult(basicUnit.currentMoveSpeed);
			PVector steer = PVector.sub(sum, basicUnit.velocity);
			steer.limit(basicUnit.maxforce);
			return steer;
		} else {
			return new PVector(0, 0);
		}
	}
	
	public PVector cohesion(GGSprite basicUnit, ArrayList<GGSprite> otherUnit) {
		float neighbordist = 50;
		PVector sum = new PVector(0, 0);
		int count = 0;
		for (GGSprite other : otherUnit) {
			float d = PVector.dist(basicUnit.position, other.position);
			if ((d > 0) && (d < neighbordist)) {
				sum.add(other.position);
				count++;
			}
		}
		if (count > 0) {
			sum.div(count);

			return seek(basicUnit, sum);
		} else {
			return new PVector(0, 0);
		}
	}

	public PVector separate(GGSprite basicUnit, ArrayList<GGSprite> otherUnit) {
		float desiredseparation = 25.0f;
		PVector steer = new PVector(0, 0, 0);
		int count = 0;

		for (GGSprite other : otherUnit) {
			float d = PVector.dist(basicUnit.position, other.position);
			if ((d > 0) && (d < desiredseparation)) {
				// Calculate vector pointing away from neighbor
				PVector diff = PVector.sub(basicUnit.position, other.position);
				diff.normalize();
				diff.div(d); // Weight by distance
				steer.add(diff);
				count++; // Keep track of how many
			}
		}
		if (count > 0) {
			steer.div((float) count);
		}

		// As long as the vector is greater than 0
		if (steer.mag() > 0) {
			steer.normalize();
			steer.mult(basicUnit.currentMoveSpeed);
			steer.sub(basicUnit.velocity);
			steer.limit(basicUnit.maxforce);
		}
		return steer;
	}

	public PVector seek(GGSprite basicUnit, PVector target) {
		PVector desired = PVector.sub(target, basicUnit.position);
		desired.normalize();
		desired.mult(basicUnit.currentMoveSpeed);
		PVector steer = PVector.sub(desired, basicUnit.velocity);
		steer.limit(basicUnit.maxforce);

		return steer;
	}
}