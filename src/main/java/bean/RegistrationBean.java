package bean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import control.UserManager;
import entity.User;

@Named(value = "registrationBean")
@RequestScoped
public class RegistrationBean {

	@EJB
	private UserManager um;

	private User user;

	public RegistrationBean() {
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * registra l'utente
	 * @return l'url per il redirect
	 */
	public String register() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			um.createUser(user);
			return "loginPage?faces-redirect=true";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("errore di registrazione");
			context.addMessage("msgs", new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"registration failed","the username is not available"));
			e.printStackTrace();
		}
		return "";
	}

}
