package ie.cct.Animals;

import com.fasterxml.jackson.annotation.JsonRootName;

//REF https://stackoverflow.com/questions/17247189/spring-requestbody-containing-a-list-of-different-types-but-same-interface
@JsonRootName("Chicken")
public class Chicken extends Animal {

	public Chicken(Float weight) {

		type = "Chicken";
		this.weight = weight;
		
	}
	
}
