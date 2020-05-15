package ie.cct.FarmManagersbs19016;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	 * (0) END-POINT => "Get my livestock"
	 * This method is only for testing purposes, can be safely removed
	 */	
	@GetMapping("get-livestock") // http://localhost:8080/get-livestock
	public List<Animal> getLivestock() { 
		return animals;
		//return animals.toString(); <= test point
	}	
	

	/*
	 * (1) END-POINT => "Add a new animal"
	 */	
	
	@PostMapping("add-animal") // http://localhost:8080/add-animal
	// Use Postman to post new 'Animal' objects (Postman collection available on the Github README.md)  
	// RequestBody annotation - REF. https://javatutorial.net/requestbody-annotation-in-spring
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
	public Map<String, Integer> animalsToBeSold() {
		
		Map<String, Integer> summary = new HashMap<String, Integer>();
		
		for (Animal animal : animals) {
			
			String animalClass = animal.getClass().toString(); // Example of what it returns: "class ie.cct.Animals.Chicken"
			String animalType = animalClass.substring(animalClass.lastIndexOf(".") + 1); // REF. https://stackoverflow.com/questions/14316487/java-getting-a-substring-from-a-string-starting-after-a-particular-character
			
			if (animal.getWeight() >= animal.getWeightThreshold()) {
			
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

	@GetMapping("farm-stock-value") // http://localhost:8080/farm-stock-value
	public Float farmStockValue() {
		
		Map<String, Integer> livestockSummary = new HashMap<String, Integer>();
		Map<String, Float> livestockValue = new HashMap<String, Float>();
		
		for (Animal animal : animals) {
			
			String animalClass = animal.getClass().toString(); // Example of what it returns: "class ie.cct.Animals.Chicken"
			String animalType = animalClass.substring(animalClass.lastIndexOf(".") + 1); // REF. https://stackoverflow.com/questions/14316487/java-getting-a-substring-from-a-string-starting-after-a-particular-character
			
			if (animal.getWeight() >= animal.getWeightThreshold()) {
			
				if (livestockSummary.get(animalType) == null) {
					livestockSummary.put(animalType, 1);
					livestockValue.put(animalType, 0.0f); // TODO not 100% sure here
				} else {
					Integer count = livestockSummary.get(animalType);
					Float aggrValue = count * animal.getMarketPriceEstimate();
					livestockSummary.put(animalType, ++count);
					livestockValue.put(animalType, ++aggrValue);
				}
			}	
		}
		
		Float chickenValue = (float) 0;
		Float cowValue = (float) 0;
		Float pigValue = (float) 0;
		
		// Trying to avoid Null Pointer Exception
		if (livestockValue.get("Chicken") != null) {
			chickenValue = (livestockValue.get("Chicken"));
		}			
		if (livestockValue.get("Cow") != null) {
			cowValue = (livestockValue.get("Cow"));
		}
		if (livestockValue.get("Pig") != null) {
			pigValue = (livestockValue.get("Pig"));
		}
		
		return (chickenValue + cowValue + pigValue);
	}

	/*
	 * (5) END-POINT => "Hypothetical value of the farm stock"
	 * Value of the farm assuming the price of each animal is set by a parameter in the HTTP request
	 * e.g. http://localhost:8080/currentValue?cow=350&pig=120&chicken=1
	 */	

	@GetMapping("farm-stock-value-hyp") // http://localhost:8080/currentValue?cow=950&pig=420&chicken=6
	public ResponseEntity<String> farmStockValueHyp(@RequestParam(required=false) Integer cowPrice, @RequestParam(required=false) Integer pigPrice, @RequestParam(required=false) Integer chickenPrice)  {
		
		Map<String, Integer> summary = new HashMap<String, Integer>();
		
		for (Animal animal : animals) {

			String animalClass = animal.getClass().toString(); // Example of what it returns: "class ie.cct.Animals.Chicken"
			String animalType = animalClass.substring(animalClass.lastIndexOf(".") + 1); // REF.
																							// https://stackoverflow.com/questions/14316487/java-getting-a-substring-from-a-string-starting-after-a-particular-character

			if (summary.get(animalType) == null) {
				summary.put(animalType, 1);
			} else {
				Integer count = summary.get(animalType);
				summary.put(animalType, ++count);
			}
		}
		
		Integer chickenValue = 0;
		Integer cowValue = 0;
		Integer pigValue = 0;
		
		// Trying to avoid Null Pointer Exception
		if (summary.get("Chicken") != null) {
			if (chickenPrice == null) {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			} else {
				chickenValue = (summary.get("Chicken")*chickenPrice);
			}
		}			
		if (summary.get("Cow") != null && cowPrice != null) {
			if (chickenPrice == null) {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			} else {			
				cowValue = (summary.get("Cow")*cowPrice);
			}
		}
		if (summary.get("Pig") != null && pigPrice != null) {
			if (chickenPrice == null) {
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
			} else {		
				pigValue = (summary.get("Pig")*pigPrice);
			}
		}
		
		//return new ResponseEntity<String>((chickenValue + cowValue + pigValue).toString(), HttpStatus.OK);
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
	
}
