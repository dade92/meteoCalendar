package unitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import entity.Event;
import entity.IndoorEvent;

import java.util.Date;

public class EventTest {
	
	private IndoorEvent event;

	@Before
	public void setUp() throws Exception {
		String title="title";
		String description="description";
		String visible="public";
		String location="milano";
		Date startDate=new Date();
		Date endDate=new Date();
		event=new IndoorEvent(title,description,visible,location,startDate,endDate);
	}

	@Test
	public void eventShouldBeInitialized() {
		assertNotNull(event.getTitle());
		assertNotNull(event.getDescription());
		assertNotNull(event.getLocation());
		assertNotNull(event.getVisibility());
		assertNotNull(event.getStartDate());
		assertNotNull(event.getEndDate());
	}

}
