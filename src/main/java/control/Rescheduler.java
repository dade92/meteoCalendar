package control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import entity.WeatherInformation;
import entity.OutdoorEvent;
import entity.User;
import enumeration.NotificationType;
import exception.InvalidDateException;
import exception.NotCreatorException;

@Singleton
public class Rescheduler {

	@EJB
	NotificationManager nm;
	@EJB
	UserManager um;
	@EJB
	EventManager em;
	@EJB
	WeatherManager wm;
	@EJB
	InvitationManager im;
	// format object by which i can parse the data
	private static final SimpleDateFormat format = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");

	@SuppressWarnings("deprecation")
	public ArrayList<String> checkForBadWeather(User currentUser) {

		ArrayList<String> eventTitles = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();
		// obtains the corresponding date for today
		Date today = calendar.getTime();
		// obtains the time in millis that corresponds to tomorrow
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date tomorrow = calendar.getTime();
		// obtains the time in millis that corresponds to three days after
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		Date threeDays = calendar.getTime();

		List<WeatherInformation> weatherData;
		// for every event created
		for (OutdoorEvent e : em.loadOutdoorEvents(currentUser)) {
			// stores the information about date,location and weather of the
			// date
			weatherData = wm.getWeatherByEvent(e);
			// if tomorrow there is an event with bad weather conditions
			if (isNotifiable(e, weatherData, today, tomorrow))
				eventTitles.add(e.getTitle());

			// checks also for bad weather condition of three days after and
			// events that were not been rescheduled
			else if (isReschedulable(e, weatherData, threeDays)) {
				boolean rightStartingDate = false;
				String badWeather = e.getBadWeatherCondition();
				// gets the current day
				calendar.setTime(today);
				for (int i = 0; i < 15; i++) {
					rightStartingDate = true;
					// partendo da domani
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					// tutti i weather devono essere diversi dalla
					// badweathercondition
					for (String w : wm
							.getWeatherByStartAndEndDate(e.getLocation(),
									calendar.getTime(), DateManager.getEndDate(e.getStartDate(), calendar.getTime(), e.getEndDate()))) {
						if (badWeather.equals(w)) {
							rightStartingDate=false;
							break;
						}
						
					}
					//se trova una data giusta
					if (rightStartingDate) {
						calendar.set(Calendar.HOUR_OF_DAY, e.getStartDate().getHours());
						calendar.set(Calendar.MINUTE, e.getStartDate().getMinutes());
						nm.createNotification(currentUser, e,
								NotificationType.BAD_WEATHER_CONDITION,
								format.format(calendar.getTime()));
						e.setRescheduled(true);
						try {
							em.updateEvent(e,false);
							break;
						} catch (NotCreatorException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidDateException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}

			}
		}
		for (OutdoorEvent e : im.loadInvitedOutdoorEvents(currentUser)) {

			weatherData = wm.getWeatherByEvent(e);
			// if tomorrow there is an event with bad weather conditions
			if (isNotifiable(e, weatherData, today, tomorrow))
				eventTitles.add(e.getTitle());
		}

		return eventTitles;
	}

	
	private boolean isNotifiable(OutdoorEvent e,
			List<WeatherInformation> weather, Date today, Date tomorrow) {
		Calendar t=Calendar.getInstance();
		t.setTime(tomorrow);
		Calendar eventDate=Calendar.getInstance();
		eventDate.setTime(e.getStartDate());
		// se l'evento è domani
		if (t.get(Calendar.DAY_OF_YEAR)==eventDate.get(Calendar.DAY_OF_YEAR)) {
			if (weather.size() > 0) {
				// if it finds one day in which there is a bad weather condition
				for (WeatherInformation w : weather) {
					if (w.getWeather().equals(e.getBadWeatherCondition())) {
						// returns true
						return true;
					}
				}

			}

		}
		// se invece l'evento è in corso
		else if (e.getStartDate().getTime() <= today.getTime()
				&& e.getEndDate().getTime() >= today.getTime()) {
			// if it finds one day in which there is a bad weather condition
			for (WeatherInformation w : wm
					.getWeatherByEventAndDate(e, tomorrow)) {
				if (w.getWeather().equals(e.getBadWeatherCondition())) {
					// sets the attribute to true
					return true;
				}
			}
		}
		// se l'evento è un altro giorno che non sia domani e nemmeno è in corso
		return false;
	}

	
	private static boolean isReschedulable(OutdoorEvent e,
			List<WeatherInformation> weather, Date threeDays) {
		Calendar t=Calendar.getInstance();
		t.setTime(threeDays);
		Calendar eventDate=Calendar.getInstance();
		eventDate.setTime(e.getStartDate());
		// se l'evento è tra tre giorni
		if (eventDate.get(Calendar.DAY_OF_YEAR)==t.get(Calendar.DAY_OF_YEAR) && !e.isRescheduled()) {
			// if there are the weather information
			if (weather.size() > 0) {
				// if it finds one day in which there is a bad weather condition
				for (WeatherInformation w : weather) {
					if (w.getWeather().equals(e.getBadWeatherCondition())) {
						// return true
						return true;
					}
				}

			}
		}
		return false;
	}

}