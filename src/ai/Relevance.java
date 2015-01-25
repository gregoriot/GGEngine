package ai;

public class Relevance implements Comparable<Relevance>{
	
	public float relevance;
	
	public int stateToRevelance;
	public int fieldID;
	
	public Relevance(float relevance, int stateToRevelance){
		this.relevance = relevance;
		this.stateToRevelance = stateToRevelance;
		fieldID = -1;
	}
	
	public Relevance(float relevance, int stateToRevelance, int fieldID){
		this.relevance = relevance;
		this.stateToRevelance = stateToRevelance;
		
		this.fieldID = fieldID;
	}

	@Override
	public int compareTo(Relevance r1) {
		if (this.relevance < r1.relevance) {
            return -1;
        }
        if (this.relevance > r1.relevance) {
            return 1;
        }
        return 0;
	}
	
	public static float relevanceRange(int lowerThreshold, int averageThreshold, int upperThreshold, int value){
		/* Inferior infinito */
		if(lowerThreshold == Integer.MIN_VALUE){
			return lowerInfinits(lowerThreshold, averageThreshold, upperThreshold, value);
		}
		
		/* Superior infinito */
		if(upperThreshold == Integer.MAX_VALUE)
			return upperInfinits(lowerThreshold, averageThreshold, upperThreshold, value);
		
		return withoutInfinits(lowerThreshold, averageThreshold, upperThreshold, value);
	}
	
	private static float withoutInfinits(int lowerThreshold, int averageThreshold, int upperThreshold, int value){
		float relevance = 0;

		/* out of range */
		if(value < lowerThreshold || value > upperThreshold)
			return 0;
		
		/* Sem infinitos */
		float range = (upperThreshold - lowerThreshold);
		float difValueAverage = (Math.abs(averageThreshold - value))*2;
		float percentage = Math.abs((difValueAverage*100f/range)-100);//Regra de tres, depois inverte a porcentagem.
		
		relevance = percentage/100;
		
		return relevance;
	}
	
	private static float lowerInfinits(int lowerThreshold, int averageThreshold, int upperThreshold, int value){
		float relevance = 0;

		/* infinity */
		if(value < averageThreshold )
			return 1;
		
		/* out of range */
		if(value > upperThreshold)
			return 0;
		
		/* Sem infinitos */
		float range = Math.abs(upperThreshold - averageThreshold);
		float difValueAverage = (Math.abs(averageThreshold - value));
		float percentage = Math.abs((difValueAverage*100f/range)-100);//Regra de tres, depois inverte a porcentagem.

		
		relevance = percentage/100;
		
		return relevance;
	}
	
	private static float upperInfinits(int lowerThreshold, int averageThreshold, int upperThreshold, int value){
		float relevance = 0;

		/* infinity */
		if(value > averageThreshold )
			return 1;
		
		/* out of range */
		if(value < lowerThreshold)
			return 0;
		
		/* Sem infinitos */
		float range = Math.abs(lowerThreshold - averageThreshold);
		float difValueAverage = (Math.abs(averageThreshold - value));
		float percentage = Math.abs((difValueAverage*100f/range)-100);//Regra de tres, depois inverte a porcentagem.

		relevance = percentage/100;
		
		return relevance;
	}
}