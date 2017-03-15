
/**
 * A sensor continually tries to identify 
 * whether the bicycle is tagged or not.
 *                    -- Added by Hailun Tian
 */

public class Sensor extends BicycleHandlingThread {

    // the belt on which the sensor is placed
    protected Belt belt;
    private int position = 2;
    private Boolean findTag = false;
    private Bicycle lastChecked;
    private Bicycle lastInspected;
    // to help format output trace
    final private static String indentation = "-------";
    
    /**
     * Create a new sensor to detect a given belt
     */
	public Sensor (Belt belt){
		super();
		this.belt = belt;
		lastChecked = null;
	}
	
	public Boolean returnTag(){
		return findTag;
	}

	public void setNotTagged(){
		findTag = false;
	}
	
	public void updateInspected(Bicycle bicycle){
		this.lastInspected = bicycle;
	}
    /**
     * Identify the bicycle
     */
    public void run() {
        while (!isInterrupted()) {
            	/**Check the bicycle
            	 *Only check when 
            	 *the bicycle has not been checked 
            	 *and is not being inspected
            	*/
                Bicycle bicycle = belt.peek(position);
                if(bicycle!= null && bicycle!= lastChecked && bicycle != lastInspected){
                	if (bicycle.isTagged()) {
    					// Call Robot to move the bicycle
                		System.out.println(indentation + 
                				bicycle.toString() + 
                				" is tagged "+ indentation);
                		System.out.println(indentation + 
                				bicycle.toString() + 
                				" is waiting for robot " +indentation);
                		findTag = true;
                		     		
                		
    				} 
                	lastChecked = bicycle;
                }
        }

        System.out.println("Sensor terminated");
    }
    
  
}
