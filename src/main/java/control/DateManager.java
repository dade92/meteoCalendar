package control;

import java.util.Date;

import javax.ejb.Stateless;

@Stateless
public class DateManager {

	/**
	 * get the end date given the new starting date
	 * 
	 * @param oldStart
	 *            old starting date
	 * @param newStart
	 *            new starting date
	 * @return the new end date
	 */
	public static Date getEndDate(Date oldStart, Date newStart, Date oldEnd) {
		long oldStartTime = oldStart.getTime();
		long newStartTime = newStart.getTime();
		long oldEndTime = oldEnd.getTime();
		long delta = newStartTime - oldStartTime;
		long newEndTime = oldEndTime + delta;
		return new Date(newEndTime);
	}

}
