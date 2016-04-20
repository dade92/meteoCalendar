package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import control.UserManager;
import entity.User;

@Named
@SessionScoped
public class UserBean {

	@EJB
	UserManager um;

	private User user;

	@PostConstruct
	public void init() {
		user = um.getLoggedUser();
	}

	public UserBean() {
	}

	public String getUsername() {
		return user.getUsername();
	}

	public String getFirstname() {
		return user.getFirstname();
	}

	public String getLastname() {
		return user.getLastname();
	}

	public String getEmail() {
		return user.getEmail();
	}

	public boolean getVisibility() {
		return user.isVisible();
	}

	public void setVisibility(boolean visibility) {
		user.setVisible(visibility);
	}

	public List<User> getUsers() {
		return um.findAll();
	}

}