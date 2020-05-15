package ie.cct.Animals;

// About the below annotation (needed to achieve polymorphic json) ...
//REF https://stackoverflow.com/questions/17247189/spring-requestbody-containing-a-list-of-different-types-but-same-interface
//@JsonRootName("Chicken") - This would be an alternative to @JsonTypeInfo and @JsonSubTypes used in parent class
public class Chicken extends Animal {

	// I was facing error "cannot deserialize from Object value" in Postman without default constructor
	// I've then found the solution at the link below
	// ref. https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu
	// This was also mentioned in 28/03 class
	public Chicken() {}
	
	public Chicken(Float weight) {

		this.weight = weight;
		
	}
	
}
