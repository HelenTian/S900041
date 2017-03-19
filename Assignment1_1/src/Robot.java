/*
 * A robot moves the bicycle from belt to inspector
 * and move the bicycle back to the belt 
 * after inspection
 *                    -- Added by Hailun Tian
 */

public class Robot extends BicycleHandlingThread {
	// the belt which the robot works with
	protected Belt belt;

	// the sensor which the robot works with
	protected Sensor sensor;

	// the inspector which the robot works with
	protected Inspector inspector;

	// the index of segment on the belt,
	// from which robots get the bicycles
	private int position = 2;

	// the bicycle needs to moved
	private volatile Bicycle bicycle;

	// to help format output trace
	final private static String indentation = "-------";

	/**
	 * Create a new robot for a given belt, sensor and inspector
	 */
	public Robot(Belt belt, Sensor sensor, Inspector inspector) {
		this.belt = belt;
		this.inspector = inspector;
		this.sensor = sensor;
	}

	/**
	 * This method starts the entire quality checking process.
	 * 
	 * Assume the robot will not start another process until the current oneis
	 * finished.
	 * 
	 * The whole process includes: The robot gets the bicycle from belt, send it
	 * to inspector and moves it back to the belt after inspection.
	 */
	public synchronized void startQualityChecking() {
		moveToInspector();
		bicycle = inspector.doInspect(bicycle);
		moveToBelt(bicycle);
	}

	/*
	 * Robot moves the bicycle from the belt to inspector
	 */
	public synchronized void moveToInspector() {
		try {
			bicycle = belt.get(position);
			// Change the sensor state once getting the bicycle
			sensor.setNotTagged();
			sensor.updateInspected(bicycle);

			System.out.println(indentation + "Robot is moving "
					+ bicycle.toString() + " to inspector." + indentation);
			Thread.sleep(Params.ROBOT_MOVE_TIME);
			System.out.println(indentation
					+ "Robot finishes moving. Inspector will start inspection"
					+ indentation);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Robot moves the bicycle from the inspector to belt
	 */
	public synchronized void moveToBelt(Bicycle bicycle) {
		try {
			System.out.println(indentation + "Robot is moving "
					+ bicycle.toString() + " to the belt." + indentation);
			Thread.sleep(Params.ROBOT_MOVE_TIME);
			System.out.println(indentation + "Robot tries to put "
					+ bicycle.toString() + " on the segment 3."
					+ " It may need to wait until segment 3 is empty"
					+ indentation);
			belt.put(bicycle, position);
			System.out.println(indentation + "Robot puts " + bicycle.toString()
					+ " back to belt successfully." + indentation);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Loop indefinitely trying to get signal from sensor start quality checking
	 * process once the sensor find a tagged bicycle.
	 */
	public void run() {
		while (!isInterrupted()) {
			if (sensor.returnTag()) {
				startQualityChecking();
			}
		}
		System.out.println("Robot terminated");
	}

}
