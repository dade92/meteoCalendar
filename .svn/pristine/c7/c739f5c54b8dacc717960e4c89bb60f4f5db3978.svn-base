package control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Event;
import entity.OutdoorEvent;
import entity.User;

@Stateless
public class InvitationManager {

	@PersistenceContext
	EntityManager em;

	@EJB
	UserManager um;

	/**
	 * create an invitation entry given the id of the user and of the event
	 * 
	 * @param user
	 *            user
	 * @param event
	 *            event
	 */
	public void createInvitation(User user, Event event) {
		user.addToInvitedEvents(event);
		event.addToInvitedUsers(user);
		em.merge(event);
		em.merge(user);
	}

	/**
	 * loads the events to which the user has been invited
	 * 
	 * @return the events
	 */
	public List<Event> loadInvitedEvents() {
		return this.loadInvitedEventsByUser(um.getLoggedUser());
	}

	/**
	 * loads the participating users given the event
	 * 
	 * @param e
	 *            event
	 * @return the users that will participate
	 */
	public List<User> loadInvitedUsers(Event e) {
		TypedQuery<User> tq = (TypedQuery<User>) em.createQuery(
				"SELECT e.invitedUsers FROM Event e WHERE  e.id=:id")
				.setParameter("id", e.getId());
		return tq.getResultList();
	}

	public List<OutdoorEvent> loadInvitedOutdoorEvents(User u) {
		TypedQuery<OutdoorEvent> tq = (TypedQuery<OutdoorEvent>) em
				.createQuery(
						"SELECT e FROM OutdoorEvent e WHERE  e.invitedUsers=:user")
				.setParameter("user", u);
		return tq.getResultList();
	}

	/**
	 * load the events to which the user has been invited
	 * 
	 * @param userId
	 *            user's id
	 * @return the events to which the user has been invited
	 */
	public List<Event> loadInvitedEventsByUser(User user) {
		TypedQuery<Event> tq = (TypedQuery<Event>) em.createQuery(
				"SELECT e FROM Event e WHERE  e.invitedUsers=:user")
				.setParameter("user", user);
		return tq.getResultList();
	}
}