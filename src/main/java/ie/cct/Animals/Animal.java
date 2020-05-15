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
	
	//protected String type; // protected, as the value is defined in the sub-classes
	protected Float weight;
	
	public Animal() {}

	// SETTERS AND GETTERS

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}	
	
	

}
