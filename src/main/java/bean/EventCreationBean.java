package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import control.EventManager;
import control.NotificationManager;
import entity.Event;
import entity.OutdoorEvent;
import entity.User;
import enumeration.BadWeatherCondition;
import enumeration.EventType;
import enumeration.NotificationType;
import exception.InvalidDateException;

@ManagedBean
@ViewScoped
public class EventCreationBean {

	@EJB
	EventManager evMan;
	@EJB
	NotificationManager nm;
	@ManagedProperty("#{checkboxView}")
	CheckboxView cbw;

	private OutdoorEvent current;

	private String indoor;

	public EventCreationBean() {
	}

	public void setCurrent(OutdoorEvent current) {
		this.current = current;
	}

	public Event getCurrent() {
		if (current == null) {
			current = new OutdoorEvent();
		}
		return current;
	}

	/**
	 * crea l'evento
	 * @return l'url
	 */
	public String createEvent() {
		System.out.println(cbw.getSelectedUsers());
		try {
			evMan.create(current);
			for (User u : cbw.getSelectedUsers()) {
				nm.createNotification(u, current, NotificationType.INVITATION,"new invitation");
			}
			return "mainPage?faces-redirect=true";
		} catch (InvalidDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "the dates may be correct");
		FacesContext.getCurrentInstance().addMessage("msgs", message);
		return "";
	}



	public void setCbw(CheckboxView cbw) {
		this.cbw = cbw;
	}

	public CheckboxView getCbw() {
		return cbw;
	}

	public String mainPage() {
		return "mainPage";
	}

	public String getIndoor() {
		if (getCurrent().equals("indoor"))
			indoor = "true";
		else
			indoor = "false";
		return indoor;
	}

	public void setIndoor(String indoor) {
		this.indoor = indoor;
	}

	public BadWeatherCondition[] getBadWeatherCondition() {
		return BadWeatherCondition.values();
	}

	public EventType[] getEventType() {
		return EventType.values();
	}

}
