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
import entity.IndoorEvent;
import entity.User;
import enumeration.NotificationType;
import exception.InvalidDateException;

@ManagedBean
@ViewScoped
public class IndoorEventCreationBean {

	@EJB
	EventManager evMan;
	@EJB
	NotificationManager nm;
	@ManagedProperty("#{checkboxView}")
	CheckboxView cbw;

	private IndoorEvent current;

	public IndoorEventCreationBean() {
	}

	public void setCurrent(IndoorEvent current) {
		this.current = current;
	}

	public Event getCurrent() {
		if (current == null) {
			current = new IndoorEvent();
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

		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "the dates may be correct");
		FacesContext.getCurrentInstance().addMessage("msgs", message);
		return "";
	}

	public String updateEvent() {

		return "mainPage";
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

}