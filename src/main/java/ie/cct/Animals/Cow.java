package ie.cct.Animals;

import com.fasterxml.jackson.annotation.JsonRootName;

//About the below annotation (needed to achieve polymorphic json) ...
//REF https://stackoverflow.com/questions/17247189/spring-requestbody-containing-a-list-of-different-types-but-same-interface
@JsonRootName("Cow")
public class Cow extends Animal {

	public Cow() {} // was facing error "cannot deserialize from Object value" in Postman without default constructor
	// found solution at the link below
	// ref. https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu

	
	public Cow(Float weight) {

		type = "Cow";
		this.weight = weight;
		
	}

}
