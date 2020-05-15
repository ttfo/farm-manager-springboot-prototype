package ie.cct.Animals;

//About the below annotation (needed to achieve polymorphic json) ...
// REF https://stackoverflow.com/questions/17247189/spring-requestbody-containing-a-list-of-different-types-but-same-interface
//@JsonRootName("Pig") - This would be an alternative to @JsonTypeInfo and @JsonSubTypes used in parent class
public class Pig extends Animal {
	
	// was facing error "cannot deserialize from Object value" in Postman without default constructor
	// found solution at the link below
	// ref. https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu
	// Also mentioned in 28/03 class
	public Pig() {} 
	
	public Pig(Float weight) {

		//type = "Pig";
		this.weight = weight;
		
	}

}
