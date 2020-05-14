package ie.cct.Animals;

import com.fasterxml.jackson.annotation.JsonRootName;

//REF https://stackoverflow.com/questions/17247189/spring-requestbody-containing-a-list-of-different-types-but-same-interface
@JsonRootName("Cow")
public class Cow extends Animal {

	public Cow(Float weight) {

		type = "Cow";
		this.weight = weight;
		
	}

}
