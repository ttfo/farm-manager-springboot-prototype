package ie.cct.FarmManagersbs19016;

import java.util.ArrayList;
import java.util.List;

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
		animals.add(new Pig(50.9f));
		animals.add(new Chicken(2.3f));
		animals.add(new Cow(100.3f));
	}

	/*
	 * (1) "Add a new animal" endpoints
	 */	
	
	@PostMapping("add-animal") // http://localhost:8080/add-animal
	public String addAnimal(@RequestBody Animal animal) { // accepts "type": "Cow" | "Pig" | "Chicken"
		animals.add(animal);
		return "OK";
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
	
}
