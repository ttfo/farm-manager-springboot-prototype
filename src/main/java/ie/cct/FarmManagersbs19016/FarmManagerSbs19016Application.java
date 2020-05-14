package ie.cct.FarmManagersbs19016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

	/*
	 * <<< CA SPECS >>>
	 * The system will allow us to add new animals to the farm stock. 
	 * The farm will only contain three types of animals:
	 * ○ Cows
	 * ○ Pigs
	 * ○ Chickens
	 * ● Each animal has 2 attributes:
	 * ○ Type
	 * ○ Weight
	 * ● Each animal has a market value:
	 * ○ Cows - €500
	 * ○ Pigs - €250
	 * ○ Chickens - €5
	 * ● The animals can only be sold if they reach a certain weight:
	 * ○ Cows - 300 KG
	 * ○ Pigs 100 KG
	 * ○ Chickens - 0.5 KG
	 * The system that we are going to build need to have the necessary endpoints for the following operations:
	 * - Add a new animal.
	 * - Calculate the average weight of each type of animal (one endpoint is sufficient, no need to build one per type of animal).
	 * - How many animals of each type can be sold (weight requirements above) right now.
	 * - What is the current value of the full farm stock: That is, the price of all the animals that can be sold right now.
	 * - What is the current value of the farm assuming the price of each animal is set by a parameter in the HTTP request. 
	 * This is an example:- http://localhost:8080/currentValue?cow=350&pig=120&chicken=1
*/

// REF. VIDEO RECORDING CLASS WEEK6 (Mar23-29)

@SpringBootApplication
public class FarmManagerSbs19016Application {

	public static void main(String[] args) {
		SpringApplication.run(FarmManagerSbs19016Application.class, args);
	}

}
