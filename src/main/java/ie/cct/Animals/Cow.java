package ie.cct.Animals;

//About the below annotation (needed to achieve polymorphic json) ...
//REF https://stackoverflow.com/questions/17247189/spring-requestbody-containing-a-list-of-different-types-but-same-interface
// @JsonRootName("Cow") - This would be an alternative to @JsonTypeInfo and @JsonSubTypes used in parent class
public class Cow extends Animal {
	
	Float weightThresholdCow = 300.0f;
	Float marketPriceEstimateCow = 1050.0f;
	
	// I was facing error "cannot deserialize from Object value" in Postman without default constructor
	// I've then found the solution at the link below
	// ref. https://stackoverflow.com/questions/53191468/no-creators-like-default-construct-exist-cannot-deserialize-from-object-valu
	// This was also mentioned in 28/03 class
	public Cow() {
		
		this.weightThreshold = weightThresholdCow; // a cow can only be sold if it weights more than 300kg - value defined in CA specs
		this.marketPriceEstimate = marketPriceEstimateCow; // market price estimate of 1 cow	
		
	} 
	
	public Cow(Float weight) {
		
		this.weightThreshold = weightThresholdCow; // a cow can only be sold if it weights more than 300kg - value defined in CA specs
		this.marketPriceEstimate = marketPriceEstimateCow; // market price estimate of 1 cow			
		this.weight = weight;	
		
	}
	
    public void setWeightThreshold(Float weightThreshold) {
    	
    	// setting default value, regardless of value provided in payload
    	// weightThreshold is a set value and a custom value cannot be provided
    	// REF. https://stackoverflow.com/questions/51268835/spring-rest-controller-having-a-request-body-with-generated-default-value
    	
    	this.weightThreshold = weightThresholdCow;
        																			
    }
    
    public void setMarketPriceEstimate(Float marketPriceEstimate) {
    	
    	// setting default value, regardless of value provided in payload
    	// marketPriceEstimate is a set value and a custom value cannot be provided
    	// REF. https://stackoverflow.com/questions/51268835/spring-rest-controller-having-a-request-body-with-generated-default-value
    	// For calculations, marketPriceEstimateCustom attribute can be used instead
    	
        this.marketPriceEstimate = marketPriceEstimateCow; 
        																			
    }    

}
