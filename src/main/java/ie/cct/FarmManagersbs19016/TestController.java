package ie.cct.FarmManagersbs19016;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


public class TestController {
	
	private List<String> names;
	
	public TestController() {
		names = new ArrayList<>();
		names.add("Giacomo");
		names.add("Umberto");
		names.add("Fabio");
		names.add("Gioia");
	}
	
	// Example #1
//	@GetMapping("test") // end-point, e.g. http://localhost:8080/test
//	public String test(String param1, String param2) { // GET request http://localhost:8080/test?param1=test&param2=test
//		//System.out.println("Hello. This goes into terminal"); //<= TEST - System.out.println is printed to console
//		return "Hello world" + param1 + param2; // servers sends back to browser "Hello world" + param1 + param2, e.g. Hello worldtesttest
//	}
	
	// Example #2
	// http://localhost:8080/test?param1=test&param2=test2
	@GetMapping("test") 
	public String test(String param1, @RequestParam(name = "param2", required = true) String test) { // param2 needs to be passed in URL
		return "Hello world " + param1 + " " + test; // returns> Hello world test test2
	}
	
	// Example #3
	// http://localhost:8080/count?filter=name
	@GetMapping("count") 
	public Integer test(@RequestParam(required = true) String filter) { // with 'required = true' http://localhost:8080/count will throw a 400, instead of a 500
		
		// E.g. http://localhost:8080/count?filter=Giacomo
		// will return 1
		Integer count = 0;
		for(String name : names) {
			if (filter.contentEquals(name)) {
				count++;
			}
		}
		//System.out.println(names.toString()); //<= just a test point
		
		return count; // 
	}
	
	@GetMapping("add")
	public String add(@RequestParam(required = true) String name) {
		//System.out.println(names.toString()); //<= just a test point
		names.add(name);
		return "OK"; // e.g. http://localhost:8080/add?name=Antonio would return 'OK'
	}

}
