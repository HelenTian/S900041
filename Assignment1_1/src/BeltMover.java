/**
 * A belt-mover moves a belt along as often as possible, but only when there is
 * a bicycle on the belt not at the last position.
 */

public class BeltMover extends BicycleHandlingThread {

	// the belt to be handled
	protected Belt belt;
	private volatile Boolean wait = false;

	/**
	 * Create a new BeltMover with a belt to move
	 */
	public BeltMover(Belt belt) {
		super();
		this.belt = belt;
	}

	/**
	 * Tell mover to wait
	 */
	public synchronized void notifyWait() {
		wait = true;
	}

	/*
	 * Tell mover to continue
	 */
	public synchronized void notifyContinue() {
		wait = false;
		System.out.println("BeltMover continues to work...");
	}

	/**
	 * Move the belt as often as possible, but only if there is a bicycle on the
	 * belt which is not in the last position.
	 */
	public void run() {
		while (!isInterrupted()) {
			try {
				while (!wait) {
					// spend BELT_MOVE_TIME milliseconds moving the belt
					Thread.sleep(Params.BELT_MOVE_TIME);
					if (!wait) {
						belt.move();
					} else {
						System.out.println("Mover is waiting...");
					}
				}
			} catch (OverloadException e) {
				terminate(e);
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}

		System.out.println("BeltMover terminated");
	}
}
