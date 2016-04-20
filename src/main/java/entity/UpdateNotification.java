package entity;

import javax.persistence.Entity;

@Entity
public class UpdateNotification extends Notification {

	public UpdateNotification() {
		
	}
	
	public UpdateNotification(User u, Event e, String info) {
		super(u,e,info);
	}
}
