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
	
	public Animal() {}

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
    	// this is set in sub-class
    }

	public Float getMarketPriceEstimate() {
		return marketPriceEstimate;
	}

	public void setMarketPriceEstimate(Float marketPriceEstimate) {
		// this is set in sub-class
	}

}
