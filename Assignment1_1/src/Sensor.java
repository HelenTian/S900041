
/**
 * A sensor continually tries to identify 
 * whether the bicycle is tagged or not.
 *                    -- Added by Hailun Tian
 */

public class Sensor extends BicycleHandlingThread {

    // the belt on which the sensor is placed
    protected Belt belt;
    protected Robot robot;
    private int position = 2;
    private Bicycle lastChecked;
    private Bicycle beingInspexted;
    
    /**
     * Create a new sensor to detect a given belt
     */
	public Sensor (Belt belt, Robot robot){
		super();
		this.belt = belt;
		this.robot = robot;
		lastChecked = null;
		beingInspexted = null;
	}
	

    /**
     * Identify the bicycle
     */
    public void run() {
        while (!isInterrupted()) {
            try {
            	/**Check the bicycle
            	 *Only check when 
            	 *the bicycle has not been checked 
            	 *and is not being inspected
            	*/
                Bicycle bicycle = belt.peek(position);
                if(bicycle!= null && bicycle!= lastChecked && bicycle != beingInspexted){
                	if (bicycle.isTagged()) {
    					// Call Robot to move the bicycle
                    	// If Robot is not available, wait
                		System.out.println("Bicycle "+ bicycle.id+ " is tagged!");
                		
                		while(robot.isBusy()){
                			System.out.println("Robot is busy...Waiting..");
                			belt.wait();
                			wait();
                		}
                		
                		System.out.println("Robot starts to move this bicycle "+ bicycle.id + " to inspector");
                		robot.moveToInspector();
                		beingInspexted = bicycle;
    				} else {
    					// The bicycle is not tagged 
    					lastChecked = bicycle;
    				}
                }

                
                
            
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("BeltMover terminated");
    }
    
  
}
