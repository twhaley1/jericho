package com.jericho.model;

/**
 * Adjusts the a speed value based on an upper and
 * lower bound value.
 * 
 * @author thomaswhaley
 *
 */
public final class SpeedAdjuster {

	private int lower;
	private int upper;
	
	/**
	 * Creates a new SpeedAdjuster with the two integers.
	 * 
	 * @precondition arg0 != arg1
	 * 
	 * @param arg0 an integer.
	 * @param arg1 an integer.
	 */
	public SpeedAdjuster(int arg0, int arg1) {
		if (arg0 == arg1) {
			throw new IllegalArgumentException();
		}
		
		this.lower = arg0 > arg1 ? arg1 : arg0;
		this.upper = arg0 > arg1 ? arg0 : arg1;
	}
	
	/**
	 * Adjusts the specified speed value by applying a 
	 * mirror transformation across the midpoint of the upper
	 * and lower bound values. For example, for a lower bound value 
	 * of 15 and an upper bound value of 20, the range between them is
	 * [15, 16, 17, 18, 19, 20]. So, adjust(15) == 20, adjust(16) == 19,
	 * adjust(17) == 18, adjust(18) == 17, adjust(19) == 16, and adjust(20)
	 * == 15.
	 * 
	 * @precondition speed >= getLower() && speed <= getUpper()
	 * 
	 * @param speed the speed to adjust.
	 * @return the adjusted speed.
	 */
	public int adjust(int speed) {
		if (speed > this.upper || speed < this.lower) {
			throw new IllegalArgumentException();
		}
		
		return this.upper + this.lower - speed;
	}
	
	/**
	 * Gets the lower bound value in this adjuster.
	 * 
	 * @return the lower bound value.
	 */
	public int getLower() {
		return this.lower;
	}
	
	/**
	 * Gets the upper bound value in this adjuster.
	 * 
	 * @return the upper bound value.
	 */
	public int getUpper() {
		return this.upper;
	}
}
