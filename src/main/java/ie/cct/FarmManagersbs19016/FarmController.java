package ie.cct.FarmManagersbs19016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.cct.Animals.Animal;
import ie.cct.Animals.Chicken;
import ie.cct.Animals.Cow;
import ie.cct.Animals.Pig;

@RestController
public class FarmController {
	
	private List<Animal> animals;
	
	public FarmController() {
		animals = new ArrayList<Animal>();
		// Populate array list with a few animals
		animals.add(new Pig(50.9f));
		animals.add(new Chicken(0.4f));
		animals.add(new Cow(100.3f));
		animals.add(new Chicken(3.5f));
	}

	/*
	 * (1) END-POINT => "Add a new animal"
	 */	
	
	@PostMapping("add-animal") // http://localhost:8080/add-animal
	public List<Animal> addAnimal(@RequestBody Animal animal) { // accepts "type": "Cow" | "Pig" | "Chicken"
		animals.add(animal);
		return animals;
		//return animals.toString(); <= test point
	}

	@PostMapping("add-pig") // http://localhost:8080/add-pig
	public String addPig(@RequestBody Pig pig) { // only accepts "type": "Pig"
		animals.add(pig);
		return "OK";
		//return animals.toString(); <= test point
	}	
	
	@PostMapping("add-chicken") // http://localhost:8080/add-chicken
	public String addChicken(@RequestBody Chicken chicken) { // only accepts "type": "Chicken"
		animals.add(chicken);
		return "OK";
		//return animals.toString(); <= test point
	}	
	
	@PostMapping("add-cow") // http://localhost:8080/add-cow
	public String addCow(@RequestBody Cow cow) { // only accepts "type": "Cow"
		animals.add(cow);
		return "OK";
		//return animals.toString(); <= test point
	}		
	
	/*
	 * (2) END-POINT => "Average weight of each type of animal"
	 */	
	
	@GetMapping("avg-weight") // http://localhost:8080/avg-weight
	public String avgWeight() {
		
		Float avgWeightTOT = 0.0f;
		Float avgWeightChicken = 0.0f;
		Float avgWeightCow = 0.0f;
		Float avgWeightPig = 0.0f;
		
		int chickenCount = 0;
		int cowCount = 0;
		int pigCount = 0;
		
		for (Animal animal: animals) {
			
			avgWeightTOT += animal.getWeight();
			
			// We find instances of each type of animal
			// to calculate the overall avg per animal type
			if (animal instanceof Chicken) {
				avgWeightChicken += animal.getWeight();
				chickenCount += 1;
			} else if (animal instanceof Cow) {
				avgWeightCow += animal.getWeight();
				cowCount += 1;
			} else if (animal instanceof Pig) {
				avgWeightPig += animal.getWeight();
				pigCount += 1;
			}
			
		}
		
		avgWeightTOT = avgWeightTOT/animals.size();
		avgWeightChicken = avgWeightChicken/chickenCount;
		avgWeightCow = avgWeightCow/cowCount;
		avgWeightPig = avgWeightPig/pigCount;
		
		String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		String result = 
				"{ " + "<br>" +
				tab + "avg-weight-animals: "+ avgWeightTOT + "," + "<br>" +
				tab + "avg-weight-chickens: "+ avgWeightChicken + "," + "<br>" +
				tab + "avg-weight-cows: "+ avgWeightCow + "," + "<br>" +
				tab + "avg-weight-pigs: "+ avgWeightPig + "<br>" +
				" }";
				
				// Alternative user-friendly string
//				"Average weight of all " +animals.size()+ " animals in the farm: " + avgWeightTOT + "<br>" +
//				+ "\r\n" + "Average weight of " +chickenCount+ " chickens in the farm: " + avgWeightChicken + "<br>" +
//				+ "\r\n" + "Average weight of " +cowCount+ " cows in the farm: " + avgWeightCow + "<br>" +
//				+ "\r\n" + "Average weight of " +pigCount+ " pigs in the farm: " + avgWeightPig;

		return result;
	}
	
	/*
	 * (3) END-POINT => "Count of animals of each type ready to be sold" (given weight requirements)
	 * 
	 * 	 	> The animals can only be sold if they reach a certain weight:
	 * 		- Cows - 300 KG
	 * 		- Pigs 100 KG
	 * 		- Chickens - 0.5 KG
	 */	
	
	@GetMapping("animals-ready-to-sell")  // http://localhost:8080/animals-ready-to-sell
	public Map<String, Integer> String() {
		
		Map<String, Integer> summary = new HashMap<String, Integer>();
		Float cowThreshold = 300.0f;
		Float pigThreshold = 100.0f;
		Float chickenThreshold = 0.5f;
		
		for (Animal animal : animals) {
			
			String animalClass = animal.getClass().toString(); // Example of what it returns: "class ie.cct.Animals.Chicken"
			String animalType = animalClass.substring(animalClass.lastIndexOf(".") + 1); // REF. https://stackoverflow.com/questions/14316487/java-getting-a-substring-from-a-string-starting-after-a-particular-character
			
			// setting condition to verify that animals meet the weight requirements 
			boolean sellingConditions = (animalType.contains("Chicken") && animal.getWeight() >= chickenThreshold) ||
					(animalType.contains("Cow") && animal.getWeight() >= cowThreshold) ||
					(animalType.contains("Pig") && animal.getWeight() >= pigThreshold);
			
			if (sellingConditions) {
			
				if (summary.get(animalType) == null) {
					summary.put(animalType, 1);
				} else {
					Integer count = summary.get(animalType);
					summary.put(animalType, ++count);
				}
			
			}
			
		}
		return summary;
	}
	
	/*
	 * (4) END-POINT => "Value of the full farm stock" (price of all the animals that can be sold right now)
	 */	

	@GetMapping("farm-stock-value")
	public String farmStockValue() {
		// TODO
		return "OK";
	}	

	/*
	 * (5) END-POINT => "Hypothetical value of the farm stock"
	 * Value of the farm assuming the price of each animal is set by a parameter in the HTTP request
	 * e.g. http://localhost:8080/currentValue?cow=350&pig=120&chicken=1
	 */	

	@GetMapping("farm-stock-value-hyp")
	public String farmStockValueHyp() {
		// TODO
		return "OK";	
	}
	
}
