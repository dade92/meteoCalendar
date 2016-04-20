package test;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import control.EventManager;
import control.UserManager;
import entity.Event;
import entity.IndoorEvent;
import entity.User;

@RunWith(Arquillian.class)
public class EventManagerIT {

	@EJB
	EventManager em;
	@EJB
	UserManager um;
	@PersistenceContext
	EntityManager enm;

	private User user;

	@Deployment
	public static WebArchive createArchiveAndDeploy() {
		return ShrinkWrap
				.create(WebArchive.class)
				.addPackage(EventManager.class.getPackage())
				.addPackage(Event.class.getPackage())
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void eventManagerShouldBeInjected() {
		assertNotNull(em);
	}

	@Test
	public void entityManagerShouldBeInjected() {
		assertNotNull(enm);
	}

	@Test
	public void eventManagerShouldCreateEvents() {

		enm.persist(new IndoorEvent("prova", "", "public", "milano",
				new Date(), new Date()));

	}

	@Test
	public void eventManagerShouldLoadEventsByUser() {

		assertNotNull(em.loadEventsByUser(user));
	}

}
