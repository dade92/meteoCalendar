package bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import control.UserManager;
import entity.User;

@ManagedBean
@SessionScoped
public class SearchUsersCheckboxView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	UserManager um;

	private List<User> users;

	private List<User> filteredUsers;

	private User selectedUser;

	@PostConstruct
	public void init() {
		users = um.findAll();
	}

	public void setSelectedUser(User users) {
		this.selectedUser = users;
	}

	public User getSelectedUser() {
		return this.selectedUser;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * chiamato quando l'utente seleziona 
	 * un altro utente nella checkbox
	 * @param event
	 */
	public void onRowSelect(SelectEvent event) {
		System.out.println("user selected");
		ConfigurableNavigationHandler cnh = (ConfigurableNavigationHandler) FacesContext
				.getCurrentInstance().getApplication().getNavigationHandler();
		cnh.performNavigation("/otherUserPage?faces-redirect=true");
		selectedUser = (User) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("user Unselected "
				+ ((User) event.getObject()).getId());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<User> getFilteredUsers() {
		return filteredUsers;
	}

	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}
}