package bean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import control.UserManager;

@ManagedBean
@RequestScoped
public class LoginBean {

	private String username;
	private String password;
	@EJB
	UserManager um;

	public LoginBean() {
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * effettua il login dell'utente
	 * @return l'url per il redirect
	 */
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			//checks if the credentials are correct and redirect the user to the main page
			String url=um.login(username, password);
			return url;
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			System.err.println("errore. login failed");		
			context.addMessage("msgs", new FacesMessage(
					FacesMessage.SEVERITY_ERROR,"Login Failed","credentials are wrong"));
		}
		return "";
	}

	/**
	 * fa il logout dell'utente
	 * @return l'url per il redirect
	 */
	public String disconnect() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		request.getSession().invalidate();
		return "/loginPage?faces-redirect=true";
	}
}
