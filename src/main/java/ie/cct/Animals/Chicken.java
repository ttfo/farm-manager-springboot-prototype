package ie.cct.Animals;

// About the below annotation (needed to achieve polymorphic json) ...
//REF https://stackoverflow.com/questions/17247189/spring-requestbody-containing-a-list-of-different-types-but-same-interface
//@JsonRootName("Chicken") - This would be an alternative to @JsonTypeInfo and @JsonSubTypes used in parent class
public class Chicken extends Animal {

	Float weightThresholdChicken = 0.5f;
	Float marketPriceEstimateChicken = 3.5f;
	
	// I was facing error "cannot deserialize from Object value" in Postman without default constructor
	// I've then found the solution at the link below
	// ref. https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu
	// This was also mentioned in 28/03 class
	public Chicken() {}
	
	public Chicken(Float weight) {

		this.weight = weight;
		this.weightThreshold = weightThresholdChicken; // a chicken can only be sold if it weights more than 0.5kg - value defined in CA specs
		this.marketPriceEstimate = marketPriceEstimateChicken; // market price estimate of 1 chicken
		
	}
	
    public void setWeightThreshold(Float weightThreshold) {
    	
    	// setting default value, regardless of value provided in payload
    	// weightThreshold is a set value and a custom value cannot be provided
    	// REF. https://stackoverflow.com/questions/51268835/spring-rest-controller-having-a-request-body-with-generated-default-value
    	
    	this.weightThreshold = weightThresholdChicken;
        																			
    }	
    
    public void setMarketPriceEstimate(Float marketPriceEstimate) {
    	
    	// setting default value, regardless of value provided in payload
    	// marketPriceEstimate is a set value and a custom value cannot be provided
    	// REF. https://stackoverflow.com/questions/51268835/spring-rest-controller-having-a-request-body-with-generated-default-value
    	// For calculations, marketPriceEstimateCustom attribute can be used instead
    	
        this.marketPriceEstimate = marketPriceEstimateChicken; 
        																			
    } 
	
}
