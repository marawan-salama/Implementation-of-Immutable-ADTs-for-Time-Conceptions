package edu.uwm.cs351;

/**
 * An amount of time, always positive. To create a duration, scale an existing
 * duration.
 */
public class Duration implements Comparable<Duration> {
	private final long extent;
	public static final Duration INSTANTANEOUS = new Duration(0);
	public static final Duration MILLISECOND = new Duration(1);
	public static final Duration SECOND = new Duration(1000);
	public static final Duration MINUTE = new Duration(SECOND.extent * 60);
	public static final Duration HOUR = new Duration(SECOND.extent * 3600);
	public static final Duration DAY = new Duration(HOUR.extent * 24);
	public static final Duration YEAR = new Duration(DAY.extent * 365 + HOUR.extent * 6);

	private Duration(long milliseconds) {
		extent = milliseconds;
	}

	/**
	 * Checks if this duration is equal to another object.
	 * 
	 * @param x the object to compare
	 * @return true if the object is a Duration and has the same extent, false
	 *         otherwise
	 */
	@Override
	public boolean equals(Object x) {
		if (!(x instanceof Duration))
			return false;
		Duration other = (Duration) x;
		return extent == other.extent;
	}

	/**
	 * Computes the hash code of this duration.
	 * 
	 * @return the hash code value for this duration
	 */
	@Override
	public int hashCode() {
		return Long.valueOf(extent).hashCode();
	}

	/**
	 * Compares this duration with another duration.
	 * 
	 * @param other the duration to compare
	 * @return 0 if the durations are equal, a negative value if this duration is
	 *         less than the other, a positive value if this duration is greater
	 *         than the other
	 */
	@Override
	public int compareTo(Duration other) {
		if (extent == other.extent)
			return 0;
		if (extent < other.extent)
			return -1;
		else
			return 1;
	}

	/**
	 * Returns a string representation of this duration.
	 * 
	 * @return a string representation of this duration
	 */
	@Override
	public String toString() {
		if (extent < SECOND.extent)
			return extent + ".0 ms.";
		if (extent < MINUTE.extent)
			return divide(SECOND) + " s.";
		if (extent < HOUR.extent)
			return divide(MINUTE) + " min.";
		if (extent < DAY.extent)
			return divide(HOUR) + " hr.";
		if (extent < YEAR.extent)
			return divide(DAY) + " days";
		return (((double) extent) / YEAR.extent) + " years";
	}

	/**
	 * Adds another duration to this duration.
	 * 
	 * @param d the duration to add
	 * @return a new duration equal to the combined duration of this and the
	 *         argument
	 * @throws NullPointerException if the argument is null
	 */
	public Duration add(Duration d) {
		return new Duration(d.extent + extent);
	}

	/**
	 * Subtracts another duration from this duration.
	 * 
	 * @param d the duration to subtract
	 * @return a new duration equal to the difference between this duration and the
	 *         argument
	 * @throws ArithmeticException if the argument is larger than this duration
	 */
	public Duration subtract(Duration d) {
		if (d.extent > extent)
			throw new ArithmeticException("Cannot subtract a larger duration");
		return new Duration(extent - d.extent);
	}

	/**
	 * Scales this duration by a factor.
	 * 
	 * @param d the scaling factor
	 * @return a new duration equal to this duration scaled by the factor
	 * @throws IllegalArgumentException if the scaling factor is negative
	 */
	public Duration scale(double d) {
		if (d < 0)
			throw new IllegalArgumentException("Cannot scale negatively");
		return new Duration((long) Math.round(extent * d));
	}

	/**
	 * Divides this duration by another duration.
	 * 
	 * @param d the duration to divide by
	 * @return the quotient of this duration divided by the argument
	 * @throws ArithmeticException if the argument is zero
	 */
	public double divide(Duration d) {
		if (d.extent == 0)
			throw new ArithmeticException("Cannot divide by zero");
		return ((double) extent) / d.extent;
	}
}
