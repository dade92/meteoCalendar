package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Pattern;

import control.PasswordEncrypter;

@Entity(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull(message = "First Name cannot be empty")
	@Pattern(regexp="[[A-Z]a-z]+",message="invalid firstname")
	private String firstname;
	@NotNull(message = "Last Name cannot be empty")
	@Pattern(regexp="[[A-Z]a-z]+[ [[A-Z]a-z]]*",message="invalid lastname")
	private String lastname;
	@NotNull(message = "username cannot be empty")
	@Column(unique = true)
	@Pattern(regexp="[[[A-Z]a-z][0-9]]+", message="invalid username")
	private String username;
	@NotNull(message = "password cannot be empty")
	private String password;
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
	private String email;
	private String groupName;
	private boolean visible;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organizerUser")
	private Set<Event> createdEvents;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "INVITED_TO", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "EVENT_ID") })
	private List<Event> invitedEvents;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Notification> userNotifications;

	// costruttore vuoto richiesto
	public User() {

	}

	public User(String username, String password, String firstName,
			String lastName, String email) {
		this.firstname = firstName;
		this.lastname = lastName;
		this.email = email;
		this.username = username;
		setPassword(password);
	}

	// getter/setter

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastName) {
		this.lastname = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = PasswordEncrypter.encryptPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organizerUser")
	public Set<Event> getCreatedEvents() {
		return createdEvents;
	}

	public void setCreatedEvents(Set<Event> createdEvents) {
		this.createdEvents = createdEvents;
	}

	public List<Event> getInvitedEvents() {
		return invitedEvents;
	}

	public void setInvitedEvents(List<Event> invitedEvents) {
		this.invitedEvents = invitedEvents;
	}

	public void addToInvitedEvents(Event e) {
		if (invitedEvents == null)
			invitedEvents = new ArrayList<Event>();
		invitedEvents.add(e);
	}

	public void removeFromInvitedEvents(Event e) {
		if (invitedEvents != null) {
			invitedEvents.remove(e);
		}
	}

	@OneToMany
	public List<Notification> getUserNotifications() {
		return userNotifications;
	}

	public void setUserNotifications(List<Notification> userNotifications) {
		this.userNotifications = userNotifications;
	}

	public String toString() {
		return username;
	}

}
