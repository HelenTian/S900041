import java.awt.print.Printable;
import java.lang.Thread.State;
import java.util.concurrent.Semaphore;

/*
 * A robot moves the bicycle between
 *  the belt and inspector
 *                    -- Added by Hailun Tian
 */

public class Robot extends BicycleHandlingThread {
	protected Belt belt;
	protected Sensor sensor;
	protected Inspector inspector;
	private int position = 2;
	private volatile Bicycle bicycle;
    // to help format output trace
    final private static String indentation = "-------";

	/**
	 * Create a new robot for a given belt and inspector
	 */
	public Robot(Belt belt, Sensor sensor, Inspector inspector) {
		this.belt = belt;
		this.inspector = inspector;
		this.sensor = sensor;
	}

	/**
	 * Move the bicycle on belt's segment 3 to inspector
	 */
	public synchronized void moveToInspector() {
		try {
			// Move only if robot is not busy and inspector is not busy
			bicycle = belt.get(position);
			if (bicycle.isTagged()) {
				sensor.setNotTagged();
				sensor.updateInspected(bicycle);
				System.out.println(indentation+ 
						"Robot is moving " + bicycle.toString() + " to inspector."
						+ indentation);
				Thread.sleep(Params.ROBOT_MOVE_TIME);

				System.out.println(indentation+
						"Robot finishes moving. Inspector will start inspection"
						+ indentation);
				bicycle = inspector.doInspect(bicycle);
				moveToBelt(bicycle);
			} else {
				System.out.println(indentation+ 
						"The robot gets the wrong bicycle!"
						+ indentation);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void moveToBelt(Bicycle bicycle) {
		try {
			System.out.println(indentation+
					"Robot is moving "
					+ bicycle.toString() 
					+ " to the belt."
					+ indentation);
			Thread.sleep(Params.ROBOT_MOVE_TIME);
			System.out.println(indentation +
					"Robot tries to put "+ bicycle.toString()
					+ " on the segment 3."
					+ " It may need to wait until segment 3 is empty" 
					+ indentation);
			belt.put(bicycle, position);
			System.out.println(indentation +
					"Robot puts " + bicycle.toString()+
					" back to belt successfully."
					+ indentation);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {

		while (!isInterrupted()) {
			if (sensor.returnTag()) {
				moveToInspector();
			}
		}
		System.out.println("Robot terminated");
	}

}
