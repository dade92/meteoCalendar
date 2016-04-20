package enumeration;

public enum EventType {

	INDOOR("indoor"), OUTDOOR("outdoor");

	private String type;

	private EventType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
