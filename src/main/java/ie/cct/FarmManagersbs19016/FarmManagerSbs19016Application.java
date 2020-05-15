package ie.cct.FarmManagersbs19016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

	/*
	 * <<< CA SPECS >>>
	 * 
	 * The system will allow to add new animals to the farm stock. 
	 * The farm will only contain three types of animals: 1) Cows; 2) Pigs; 3) Chickens
	 * 
	 * > Each animal has 2 attributes: 1) Type; 2) Weight
	 * > Each animal has a market value:
	 * 		- Cows - €500
	 * 		- Pigs - €250
	 * 		- Chickens - €5
	 * > The animals can only be sold if they reach a certain weight:
	 * 		- Cows - 300 KG
	 * 		- Pigs 100 KG
	 * 		- Chickens - 0.5 KG
	 * 
	 * The system that we are going to build need to have the necessary end-points for the following operations:
	 * 
	 * 1) Add a new animal.
	 * 2) Calculate the average weight of each type of animal (one endpoint is sufficient, no need to build one per type of animal).
	 * 3) How many animals of each type can be sold (weight requirements above) right now.
	 * 4) What is the current value of the full farm stock: That is, the price of all the animals that can be sold right now.
	 * 5) What is the current value of the farm assuming the price of each animal is set by a parameter in the HTTP request. 
	 * 
	 * This is an example:- http://localhost:8080/currentValue?cow=350&pig=120&chicken=1
	 */

// REF. VIDEO RECORDING CLASS WEEK6 (Mar23-29) ... Controller explanation from ~min 34
// also VIDEO RECORDING CLASS SAT 28/03 from ~min 50 (1:23 - 1:46 can be skipped; ~2:55 HTTP Status Code)

@SpringBootApplication
@ComponentScan("ie.cct.*") // scans all packages under 'ie.cct, e.g. package ie.cct.FarmManagersbs19016
public class FarmManagerSbs19016Application {
	
	public static void main(String[] args) {
		
		//new TestController().test("test", "test"); // <= just an example
		
		SpringApplication.run(FarmManagerSbs19016Application.class, args);
	}

}
