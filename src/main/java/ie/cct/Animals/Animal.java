package ie.cct.Animals;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.*;

// REF. https://stackoverflow.com/questions/27170298/spring-reponsebody-requestbody-with-abstract-class &
// https://stackoverflow.com/questions/17247189/spring-requestbody-containing-a-list-of-different-types-but-same-interface
// Jackson annotations are needed to achieve polymorphic json
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
@JsonSubTypes.Type(value = Chicken.class, name = "Chicken"),
@JsonSubTypes.Type(value = Cow.class, name = "Cow"),
@JsonSubTypes.Type(value = Pig.class, name = "Pig")
})
public abstract class Animal {
	
	protected Float weight; // weight of the individual animal
	protected Float weightThreshold; // threshold at which animals can be sold (Cows - 300 KG, Pigs 100 KG, Chickens - 0.5 KG)
	protected Float marketPriceEstimate; // market price estimate per unit of animal type (e.g. for 1 cow, 1017.5 EUR)
	protected Float marketPriceEstimateCustom; // custom value to perform hypothesis
	
	public Animal() {
		this.setWeightThreshold(weightThreshold); // this is required to populate correctly payload missing weightThreshold in POST
	}

	// SETTERS AND GETTERS
	
	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getWeightThreshold() {
		return weightThreshold;
	}
	
    public void setWeightThreshold(Float weightThreshold) {
    	
    	String animalType = this.getClass().toString();
    	
    	if (animalType != null && animalType.contentEquals("Cow")) {
    		this.weightThreshold = weightThreshold != null ? weightThreshold : 300.0f; // setting default value if payload is missing weightThreshold
    	}																				// REF. https://stackoverflow.com/questions/51268835/spring-rest-controller-having-a-request-body-with-generated-default-value
    	else if (animalType != null && animalType.contentEquals("Pig")) {
    		this.weightThreshold = weightThreshold != null ? weightThreshold : 100.0f; 
    	}
    	else if (animalType != null && animalType.contentEquals("Chicken")) {
    		this.weightThreshold = weightThreshold != null ? weightThreshold : 0.5f; 
    	}
    	else {
    		this.weightThreshold = weightThreshold != null ? weightThreshold : -1f; // we can implement weightThreshold>=0 to validate entries
    	}
    	
    }

	public Float getMarketPriceEstimate() {
		return marketPriceEstimate;
	}

	public void setMarketPriceEstimate(Float marketPriceEstimate) {

		// TODO possibly not needed
    	String animalType = this.getClass().toString();
    	
    	if (animalType != null && animalType.contentEquals("Cow")) {
    		this.marketPriceEstimate = marketPriceEstimate != null ? marketPriceEstimate : 1050.0f; // setting default value if payload is missing weightThreshold
    	}																				// REF. https://stackoverflow.com/questions/51268835/spring-rest-controller-having-a-request-body-with-generated-default-value
    	else if (animalType != null && animalType.contentEquals("Pig")) {
    		this.marketPriceEstimate = marketPriceEstimate != null ? marketPriceEstimate : 399.9f; 
    	}
    	else if (animalType != null && animalType.contentEquals("Chicken")) {
    		this.marketPriceEstimate = marketPriceEstimate != null ? marketPriceEstimate : 3.5f; 
    	}
    	else {
    		this.marketPriceEstimate = marketPriceEstimate != null ? marketPriceEstimate : 0.0f; // this should nullify invalid entries
    	}
		
	}

	public Float getMarketPriceEstimateCustom() {
		return marketPriceEstimateCustom;
	}

	public void setMarketPriceEstimateCustom(Float marketPriceEstimateCustom) {
		this.marketPriceEstimateCustom = marketPriceEstimateCustom;
	}

}
