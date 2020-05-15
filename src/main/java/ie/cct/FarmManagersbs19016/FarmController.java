package ie.cct.FarmManagersbs19016;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ie.cct.Animals.Animal;
import ie.cct.Animals.Chicken;
import ie.cct.Animals.Cow;
import ie.cct.Animals.Pig;

// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html
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
	 * ALL END-POINTS:
	 * [0 - GET] http://localhost:8080/get-livestock (just for testing)
	 * [1 - POST] http://localhost:8080/add-animal (add new animal to livestock / POST, e.g. through Postman)
	 * [2 - GET] http://localhost:8080/avg-weight?type=chicken (avg. weight by animal type)
	 * [3 - GET] http://localhost:8080/animals-ready-to-sell (count of animals that meet min weight requirements and can be sold)
	 * [4 - GET] http://localhost:8080/farm-stock-value (value of owned livestock)
	 * [5 - GET] http://localhost:8080/farm-stock-value-hyp?cow=950&pig=420&chicken=6 (hyp value of owned livestock)
	 */	
	
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
	@ResponseBody
	public ResponseEntity<String> addAnimal(@RequestBody Animal animal) { // accepts "type": "Cow" | "Pig" | "Chicken"
		animals.add(animal);
		System.out.println(animals); // <= test point
		return new ResponseEntity<String>("Animal successfully added to livestock",HttpStatus.OK);
	}

	@PostMapping("add-pig") // http://localhost:8080/add-pig
	@ResponseBody
	public ResponseEntity<String> addPig(@RequestBody Pig pig) { // only accepts "type": "Pig"
		animals.add(pig);
		return new ResponseEntity<String>("Animal successfully added to livestock",HttpStatus.OK);
	}	
	
	@PostMapping("add-chicken") // http://localhost:8080/add-chicken
	@ResponseBody
	public ResponseEntity<String> addChicken(@RequestBody Chicken chicken) { // only accepts "type": "Chicken"
		animals.add(chicken);
		return new ResponseEntity<String>("Animal successfully added to livestock",HttpStatus.OK);
	}	
	
	@PostMapping("add-cow") // http://localhost:8080/add-cow
	@ResponseBody
	public ResponseEntity<String> addCow(@RequestBody Cow cow) { // only accepts "type": "Cow"
		animals.add(cow);
		return new ResponseEntity<String>("Animal successfully added to livestock",HttpStatus.OK);
	}		
	
	/*
	 * (2) END-POINT => "Average weight of each type of animal"
	 */	
	
	@GetMapping("avg-weight") // e.g http://localhost:8080/avg-weight?type=cow
	public ResponseEntity<Float> avgWeight(@RequestParam(name="type", required=true) String animalType) {
		
		List<String> validTypes = Arrays.asList("chicken","cow","pig","all");
		
		if (!validTypes.contains(animalType)) {
			
			return new ResponseEntity<Float>(HttpStatus.NOT_FOUND);	
			
		} else {
		
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
			
			Map<String, Float> livestockAvgWeight = new HashMap<String, Float>();
			livestockAvgWeight.put("chicken", avgWeightChicken);
			livestockAvgWeight.put("cow", avgWeightCow);
			livestockAvgWeight.put("pig", avgWeightPig);
			livestockAvgWeight.put("all", avgWeightTOT);
	
			return new ResponseEntity<Float>(livestockAvgWeight.get(animalType),HttpStatus.OK);
		
		}
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
					livestockValue.put(animalType, animal.getMarketPriceEstimate()); // starts up map with value of initial pair for each animal type
																						// est. price is the same across same animal types (e.g. for all chickens)
																						// so we only need to retrieve it once
				} else {
					Integer count = livestockSummary.get(animalType);
					livestockSummary.put(animalType, ++count);
				}
			}	
		}
		
		Float chickenValue = (float) 0;
		Float cowValue = (float) 0;
		Float pigValue = (float) 0;
		
		// Trying to avoid Null Pointer Exception
		if (livestockSummary.get("Chicken") != null) {
			chickenValue = livestockSummary.get("Chicken") * livestockValue.get("Chicken");
		}			
		if (livestockSummary.get("Cow") != null) {		
			cowValue = livestockSummary.get("Cow") * livestockValue.get("Cow");
		}
		if (livestockSummary.get("Pig") != null) {		
			pigValue = livestockSummary.get("Pig") * livestockValue.get("Pig");
		}
		
		// Just for testing, System.out.println below can be safely removed if not needed
		System.out.println("chickenValue: " + chickenValue + "\r\n" + 
				"cowValue: " + cowValue + "\r\n" + 
				"pigValue: " + pigValue + "\r\n");
		
		return (chickenValue + cowValue + pigValue);
	}

	/*
	 * (5) END-POINT => "Hypothetical value of the farm stock"
	 * Value of the farm assuming the price of each animal is set by a parameter in the HTTP request
	 * Differently than end-point (4), as per CA specs weight threshold is NOT applied for this end-point
	 * e.g. http://localhost:8080/currentValue?cow=350&pig=120&chicken=1
	 */	

	@GetMapping("farm-stock-value-hyp") // http://localhost:8080/farm-stock-value-hyp?cow=950&pig=420&chicken=6
										// http://localhost:8080/farm-stock-value-hyp?chicken=6 <= would also work
	public ResponseEntity<Integer> farmStockValueHyp(@RequestParam(name="cow", required=false) Integer cowPrice, @RequestParam(name="pig", required=false) Integer pigPrice, @RequestParam(name="chicken", required=false) Integer chickenPrice)  {
		
		Map<String, Integer> livestockSummary = new HashMap<String, Integer>();
		
		for (Animal animal : animals) {
			
			String animalClass = animal.getClass().toString(); // Example of what it returns: "class ie.cct.Animals.Chicken"
			String animalType = animalClass.substring(animalClass.lastIndexOf(".") + 1); // REF. https://stackoverflow.com/questions/14316487/java-getting-a-substring-from-a-string-starting-after-a-particular-character			
			
			if (livestockSummary.get(animalType) == null) {
				livestockSummary.put(animalType, 1);

			} else {
				Integer count = livestockSummary.get(animalType);
				livestockSummary.put(animalType, ++count);
			}			
			
		}
		
		// REF. http://www.cafeaulait.org/course/week2/43.html
		// example - (a > b) ? a(true) : b(false)
		// if retrieved attributes are null, make them 0
		// as we can still calculate the total, based on other valid attributes' values
		
		int pigCount = (livestockSummary.get("Pig") == null ? 0 : livestockSummary.get("Pig"));
		int cowCount = (livestockSummary.get("Cow") == null ? 0 : livestockSummary.get("Cow"));
		int chickenCount = (livestockSummary.get("Chicken") == null ? 0 : livestockSummary.get("Chicken"));
		
		int pigValue = pigCount * (pigPrice == null ? 0 : pigPrice);
		int chickenValue = chickenCount * (chickenPrice == null ? 0 : chickenPrice);
		int cowValue = cowCount * (cowPrice == null ? 0 : cowPrice);
		
		Integer farmValueHyp = pigValue + chickenValue + cowValue;
		
		return new ResponseEntity<Integer>(farmValueHyp, HttpStatus.OK);
	}
	
}
