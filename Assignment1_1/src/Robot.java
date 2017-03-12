import java.awt.print.Printable;
import java.util.concurrent.Semaphore;

/*
 * A robot moves the bicycle between
 *  the belt and inspector
 *                    -- Added by Hailun Tian
 */

 
public class Robot extends BicycleHandlingThread {
	private volatile Boolean isBusy = false;
	
	public synchronized void moveToInspector(){
//		isBusy = true;	
		try {
			System.out.println("Robot is doing moveToInspector....Strart sleeping...");
			Thread.sleep(Params.ROBOT_MOVE_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		isBusy = false;
		System.out.println("Robot finishes moveToInspector....");
		notifyAll();
	}
	
	public synchronized void moveToBelt(){
		
		
	}
	
	public synchronized Boolean isBusy(){
		return isBusy;	
	}
	
	public synchronized void notifyMove(){
		isBusy = true;
	}
	
	
	public void run(){
		
		 while (!isInterrupted()) {
			
	         if(isBusy) {
	        	 moveToInspector(); 
	        	 System.out.println("我挪完一次了。。。");
	         }
	        
	      }

	        System.out.println("Robot terminated");
	}
	
	
}

