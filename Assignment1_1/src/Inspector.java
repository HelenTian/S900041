/*
 * The inspector inspect the tagged bicycle,
 * remove the tags from non-defective bicycles
 * and leave tags on defective bicycles. 
 *                    -- Added by Hailun Tian
 */

public class Inspector extends BicycleHandlingThread {
	// to help format output trace
	final private static String indentation = "-------";

	/*
	 * Inspector start to inspect the bicycle Remove the tag is the bicycle is
	 * not defective
	 * 
	 * @return bicycle which is inspected
	 */
	public synchronized Bicycle doInspect(Bicycle bicycleToBeInpected) {
		Bicycle bicycle = bicycleToBeInpected;
		// Check again the bicycle is tagged
		if (!bicycle.isTagged()) {
			System.out.println(indentation
					+ "Inspector gets an untagged bicycle" + indentation);

			DefKnownException e = new DefKnownException(
					"Inspector gets an untagged bicycle");
			terminate(e);
		} else {
			try {
				System.out.println(indentation
						+ "The inspector needs some time to inspect "
						+ bicycle.toString() + indentation);

				sleep(Params.INSPECT_TIME);
				if (!bicycle.defective) {
					bicycle.setNotTagged();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bicycle.setInspected();
			System.out.println(indentation + bicycle.toString()
					+ ": is already inspected,"
					+ " will be returned to robot. " + indentation);
		}
		return bicycle;
	}

	/**
	 * Loop indefinitely, work if the robot put a bicycle here, until being
	 * interrupted
	 */
	public void run() {
		while (!isInterrupted())
			;
		System.out.println("Inspector terminated");
	}

}
