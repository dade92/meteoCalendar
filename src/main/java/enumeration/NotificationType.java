package enumeration;

public enum NotificationType {

	INVITATION("newInvitation"), BAD_WEATHER_CONDITION("bad_weather"),UPDATE_EVENT("update_event");

	private String type;

	private NotificationType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
