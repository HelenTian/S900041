
/**
 * A sensor continually tries to identify 
 * whether the bicycle is tagged or not.
 *                    -- Added by Hailun Tian
 */

public class Sensor extends BicycleHandlingThread {

    // the belt on which the sensor is placed
    protected Belt belt;
    
	// the index of belt's segment 
    // which sensor detect bicycles 
    private int position = 2;
    
    // Set true if sensor detect a tagged bicycle
    private Boolean findTag = false;
    
    // Record the bicycle which was checked last time
    private Bicycle lastChecked;
    
    // Record the bicycle which was inspected last time
    private Bicycle lastInspected;
    
    // to help format output trace
    final private static String indentation = "-------";
    
    /**
     * Create a new sensor to detect a given belt,
     * initialize the last checked and inspected bicycle to empty
     */
	public Sensor (Belt belt){
		super();
		this.belt = belt;
		lastChecked = null;
		lastInspected = null;
	}
	
	 /**
     * @return whether the sensor detect a tagged bicycle
     */
	public Boolean returnTag(){
		return findTag;
	}

	 /**
     *  set the tag to false, means the sensor does not detect
     *  a tagged bicycle now
     */
	public void setNotTagged(){
		findTag = false;
	}
	
	/**
     *  record the last inspected bicycle
     */
	public void updateInspected(Bicycle bicycle){
		this.lastInspected = bicycle;
	}
	
	 /**
     * Loop indefinitely trying to detect the tagged bicycle
     */
    public void run() {
        while (!isInterrupted()) {
            	/**Check the bicycle
            	 * Only check when 
            	 * bicycle is on the segment and 
            	 * the bicycle has not been checked 
            	 * and is not being inspected
            	*/
                Bicycle bicycle = belt.peek(position);
                if(bicycle!= null && bicycle!= lastChecked && bicycle != lastInspected){
                	if (bicycle.isTagged()) {
                		System.out.println(indentation + 
                				bicycle.toString() + 
                				" is tagged and waiting for robot " +indentation);
                		findTag = true;	
    				} 
                	lastChecked = bicycle;
                }
        }

        System.out.println("Sensor terminated");
    }
    
  
}
