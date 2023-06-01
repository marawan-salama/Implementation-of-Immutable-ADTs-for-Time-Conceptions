package edu.uwm.cs351;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A point in time.
 */
public class Time implements Comparable<Time> {
	/**
	 * Create a time for now.
	 */
	private final long point;

	private Time(long milliseconds) {
		point = milliseconds;
	}

	/**
	 * Create a time object representing the current time.
	 */
	public Time() {
		this(System.currentTimeMillis());
	}

	/**
	 * Create a time according to the specified calendar object.
	 * 
	 * @param c calendar object representing a time, must not be null
	 */
	public Time(Calendar c) {
		point = c.getTimeInMillis();
	}

	// Override/implement methods standard for immutable classes.

	/**
	 * Return the difference between two time points. The order doesn't matter ---
	 * the difference is always a (positive) Duration.
	 * 
	 * @param t time point to compute difference with
	 * @return duration between two time points
	 */
	public Duration difference(Time t) {
		return Duration.MILLISECOND.scale(Math.abs(t.point - point));
	}

	/**
	 * Return the time point after a particular duration. If the point advances too
	 * far into the future, more than a hundred million years from now, this method
	 * may malfunction.
	 * 
	 * @param d duration to advance, must not be null
	 * @return new time after the given duration
	 */
	public Time add(Duration d) {
		return new Time((long) d.divide(Duration.MILLISECOND) + point);
	}

	/**
	 * Return the time point before a particular duration. If a point regresses too
	 * far into the past, more than a hundred million years from now, this method
	 * may malfunction.
	 * 
	 * @param d duration to regress, must not be null
	 * @return new time before this one by the given duration
	 */
	public Time subtract(Duration d) {
		return new Time((long) (point - d.divide(Duration.MILLISECOND)));
	}

	/**
	 * Return the time as a mutable `Calendar` object.
	 * 
	 * @return new `Calendar` object representing the time
	 */
	public Calendar asCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(point);
		return cal;
	}

	@Override
	public int compareTo(Time other) {
		/** implementing */
		return Long.valueOf(point).compareTo(other.point);
	}

	@Override
	public boolean equals(Object x) {
		/** implementing */
		if (!(x instanceof Time))
			return false;
		Time other = (Time) x;
		return point == other.point;
	}

	@Override
	public int hashCode() {
		/** implementing */
		return Long.valueOf(point).hashCode();
	}

	@Override
	public String toString() {
		/** implementing */
		DateFormat df = new SimpleDateFormat("'UTC' G yyyy/MM/dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(new Date(point));
	}
}
