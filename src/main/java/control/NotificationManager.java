package control;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.InviteNotification;
import entity.Notification;
import entity.RescheduledNotification;
import entity.UpdateNotification;
import entity.User;
import entity.Event;
import enumeration.NotificationType;

import java.util.List;

@Stateless
public class NotificationManager {

	@PersistenceContext(unitName = "meteoCalPU")
	EntityManager em;

	public void createNotification(Notification n) {
		em.persist(n);
	}

	/**
	 * creates a notification
	 * 
	 * @param u the user
	 * @param e the event
	 * @param nt type of notification
	 * @param info some additional info
	 */
	public void createNotification(User u, Event e, NotificationType nt,
			String info) {
		switch (nt) {
		case INVITATION:
			InviteNotification in = new InviteNotification(u, e, info);
			em.persist(in);
			break;
		case BAD_WEATHER_CONDITION:
			RescheduledNotification rn = new RescheduledNotification(u, e, info);
			em.persist(rn);
			break;
		case UPDATE_EVENT:
			UpdateNotification un=new UpdateNotification(u,e,info);
			em.persist(un);
			break;
		}
	}

	/**
	 * finds the notifications related to a user
	 * 
	 * @param user
	 *            user
	 * @return the notifications
	 */
	public List<Notification> findNotificationsByUser(User user) {
		TypedQuery<Notification> tq = (TypedQuery<Notification>) em
				.createQuery("SELECT n FROM Notification n WHERE  n.user=:user")
				.setParameter("user", user);
		return tq.getResultList();
	}

	/**
	 * finds the notification related to the user and to a event
	 * 
	 * @param user
	 *            the user
	 * @return the notifications
	 */
	public List<InviteNotification> findNewInvitation(User user) {
		TypedQuery<InviteNotification> tq = (TypedQuery<InviteNotification>) em
				.createQuery(
						"SELECT n FROM InviteNotification n WHERE  n.user=:user")
				.setParameter("user", user);
		return tq.getResultList();
	}

	/**
	 * find new notifications concerning 
	 * an event rescheduled by the system
	 * @param user the user related to the notification
	 * @return the list of notifications
	 */
	public List<RescheduledNotification> findNewRescheduledEvents(User user) {
		TypedQuery<RescheduledNotification> tq = (TypedQuery<RescheduledNotification>) em
				.createQuery(
						"SELECT n FROM RescheduledNotification n WHERE  n.user=:user")
				.setParameter("user", user);
		return tq.getResultList();
	}
	
	/**
	 * find notifications related to updates 
	 * of some events
	 * @param user the user 
	 * @return list of notifications
	 */
	public List<UpdateNotification> findNewUpdateNotifications(User user) {
		TypedQuery<UpdateNotification> tq = (TypedQuery<UpdateNotification>) em
				.createQuery(
						"SELECT n FROM UpdateNotification n WHERE  n.user=:user")
				.setParameter("user", user);
		return tq.getResultList();
	}

	/**
	 * load the users notified by this
	 * particular event
	 * @param calendarEvent the event
	 * @return the list of users notified
	 */
	public List<User> loadNotifiedUsers(Event calendarEvent) {
		TypedQuery<User> tq = (TypedQuery<User>) em
				.createQuery(
						"SELECT n.user FROM InviteNotification n WHERE  n.event=:event")
				.setParameter("event", calendarEvent);
		return tq.getResultList();
	}

	/**
	 * removes a notification
	 * 
	 * @param n
	 */
	public void removeNotification(Notification n) {
		em.remove(em.merge(n));
	}
}