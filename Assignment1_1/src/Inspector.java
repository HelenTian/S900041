import java.util.Random;


/*
 * The inspector inspect the tagged bicycle,
 * remove the tags from non-defective bicycles
 * and leave tags on defective bicycles. 
 * Then call robot to move it back to belt.
 *                    -- Added by Hailun Tian
 */

public class Inspector extends BicycleHandlingThread{
	private  Boolean isBusy = false;
	
    // to help format output trace
    final private static String indentation = "-------";
	
	/*
	 * Inspector start to inspect the bicycle
	 * Remove the tag is the bicycle is not defective
	 * @return bicycle which is inspected
	 */
	public Boolean isBusy(){
		return isBusy;
	}
	
	public synchronized Bicycle doInspect(Bicycle bicycleToBeInpected){
		Bicycle bicycle = bicycleToBeInpected;
		
		
		try {
			System.out.println(indentation +
					"The inspector needs some time to inspect " 
					+ bicycle.toString()
					+ indentation);
			
			sleep(Params.INSPECT_TIME);
			if(!bicycle.defective){
				bicycle.setNotTagged();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(indentation + 
				bicycle.toString() + ": is already inspected,"
				+ " will be returned to robot. "
				+ indentation);
		return bicycle;	
	}
	
	public void run() {
        while (!isInterrupted());
        System.out.println("Inspector terminated");
    }
	
	
}
