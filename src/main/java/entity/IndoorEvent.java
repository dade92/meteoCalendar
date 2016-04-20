package entity;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class IndoorEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IndoorEvent() {

	}

	public IndoorEvent(String title, String descr, String visible,
			String location, Date startDate, Date endDate) {
		super(title, descr, visible, location, startDate, endDate);
	}

}
