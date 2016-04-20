package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public abstract class Notification {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Event event;
	private String info;

	public Notification() {

	}

	public Notification(User u, Event e, String info) {
		this.user = u;
		this.event = e;
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
