
/**
 * A sensor continually tries to identify 
 * whether the bicycle is tagged or not.
 *                    -- Added by Hailun Tian
 */

public class Sensor extends BicycleHandlingThread {

    // the belt on which the sensor is placed
    protected Belt belt;
    
    /**
     * Create a new sensor to detect a given belt
     */
	public Sensor (Belt belt){
		super();
		this.belt = belt;		
	}
	

    /**
     * Identify the bicycle
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                
                Thread.sleep(Params.BELT_MOVE_TIME);
                belt.move();
            } catch (OverloadException e) {
                terminate(e);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("BeltMover terminated");
    }
}
