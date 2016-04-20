package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import control.UserManager;
import entity.User;

@ManagedBean
@ViewScoped
public class CheckboxView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	UserManager um;

	private List<User> users;

	private List<User> selectedUsers;

	@PostConstruct
	public void init() {
		users = um.findAll();
	}

	public void setSelectedUsers(List<User> users) {
		this.selectedUsers = users;
	}

	public List<User> getSelectedUsers() {
		return this.selectedUsers;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}