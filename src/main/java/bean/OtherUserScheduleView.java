package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import control.EventManager;
import control.InvitationManager;
import control.WeatherManager;
import entity.Event;
import entity.IndoorEvent;
import entity.OutdoorEvent;
import entity.User;
import entity.WeatherInformation;

@ManagedBean
@ViewScoped
public class OtherUserScheduleView implements Serializable {

	private static final long serialVersionUID = 1L;

	private ScheduleModel lazyEventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();

	private Event calendarEvent;

	@EJB
	EventManager em;
	@EJB
	InvitationManager im;
	@EJB
	WeatherManager wm;
	//inietta la checkbox contenente lo user selezionato
	@ManagedProperty("#{searchUsersCheckboxView}")
	SearchUsersCheckboxView cbw;

	@PostConstruct
	public void init() {

		System.out.println("inizializzazione scheduleview");
		final User searchedUser = cbw.getSelectedUser();
		lazyEventModel = new LazyScheduleModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void loadEvents(Date start, Date end) {
				// loads only if the calendar is visible
				if (searchedUser.isVisible()) {
					for (Event e : em.loadEventsByUser(searchedUser)) {
						// se l'evento è pubblico, lo visualizzo
						if (e.getVisibility().equals("public"))
							addEvent(new DefaultScheduleEvent(e.getTitle(),
									e.getStartDate(), e.getEndDate(), e.getId()));
						// altrimenti se è privato, faccio vedere la casella
						// senza visualizzarlo
						else if (e.getVisibility().equals("private"))
							addEvent(new DefaultScheduleEvent("private event",
									e.getStartDate(), e.getEndDate(), e.getId()));
					}
					//the same for the invited events
					for (Event e : im.loadInvitedEventsByUser(searchedUser)) {
						if (e.getVisibility().equals("public"))
							addEvent(new DefaultScheduleEvent(e.getTitle(),
									e.getStartDate(), e.getEndDate(),e.getId()));
						else if (e.getVisibility().equals("private"))
							addEvent(new DefaultScheduleEvent("private event",
									e.getStartDate(), e.getEndDate(),e.getId()));
					}
				}
				// otherwise the users will see the blocks in which the user is
				// busy
				else {
					for (Event e : em.loadEventsByUser(searchedUser)) {
						addEvent(new DefaultScheduleEvent("", e.getStartDate(),
								e.getEndDate(), e.getId()));
					}

					for (Event e : im.loadInvitedEventsByUser(searchedUser)) {
						addEvent(new DefaultScheduleEvent("", e.getStartDate(),
								e.getEndDate(),e.getId()));
					}

				}
			}
		};

	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public SearchUsersCheckboxView getCbw() {
		return cbw;
	}

	public void setCbw(SearchUsersCheckboxView cbw) {
		this.cbw = cbw;
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		System.out.println(event.getData());
		calendarEvent = em.find((int) event.getData());
		// if the event is private, or if the calendar is private,it doesn't
		// show the details
		if (calendarEvent.getVisibility().equals("private")
				|| !cbw.getSelectedUser().isVisible()) {
			calendarEvent.setTitle("");
			calendarEvent.setDescription("");
			calendarEvent.setStartDate(null);
			calendarEvent.setEndDate(null);
			calendarEvent.setLocation("");
			calendarEvent.setEventNotifications(null);
			calendarEvent.setInvitedUsers(null);
			calendarEvent.setVisibility("");
			calendarEvent.setOrganizerUser(null);
		}
	}

	public Event getCalendarEvent() {
		return calendarEvent;
	}

	public void setCalendarEvent(Event calendarEvent) {
		this.calendarEvent = calendarEvent;
	}
	
	public String getEventType() {
		if(calendarEvent!=null && calendarEvent.getVisibility().equals("public") && cbw.getSelectedUser().isVisible()  ) {
		if(calendarEvent instanceof IndoorEvent)
			return "indoor";
		else if(calendarEvent instanceof OutdoorEvent)
			return "outdoor";
		}
		return "";
	}
	
	public List<WeatherInformation> getWeatherOfCalendarEvent() {
		if(calendarEvent!=null && calendarEvent instanceof OutdoorEvent && 
				calendarEvent.getVisibility().equals("public") &&
				cbw.getSelectedUser().isVisible()) {
			return wm.getWeatherByEvent((OutdoorEvent) calendarEvent);
		} else
			return null;
	}

}
