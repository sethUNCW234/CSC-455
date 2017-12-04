import java.util.ArrayList;
import java.util.Random;

public class Scheduler {
	private static ArrayList<Event> events; // actual events
	private static ArrayList<Employee> employees = new ArrayList<Employee>();
	private static ArrayList<Venue> venues = new ArrayList<Venue>();
	ArrayList<Venue> totalVenueSize = new ArrayList<Venue>();

	Event event;

	Random randomizer = new Random();

	public Scheduler() {
		try {
			employees = Database.getEmployees();
			venues = Database.getVenues();
			
			for (Venue venue : venues) {
				int tables = venue.getTables();
				for (int i = 0; i < tables; i++) {
					totalVenueSize.add(venue);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Event> scheduleGenerator() {
		/**
		 * Generates one day's worth of scheduling, adding employees to all
		 * events.
		 */

		// Creates a list of venues based on the total number of tables
		// across all venues.
		ArrayList<Employee> usedEmployees = (ArrayList<Employee>) employees.clone();
		events = new ArrayList<Event>();
		for (int i = 0; i < totalVenueSize.size() && usedEmployees.size() > 0; i++) {
			int index = randomizer.nextInt(usedEmployees.size());
			event = new Event(totalVenueSize.get(i), usedEmployees.get(index));
			events.add(event);
			usedEmployees.remove(index);
		}

		return events;

	}
}