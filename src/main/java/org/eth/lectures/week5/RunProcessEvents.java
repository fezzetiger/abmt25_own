package org.eth.week5.exercises.example;

import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;

public class RunProcessEvents {

	public static void main(String[] args) {
		String eventsFile = args[0];

		EventsManager eventsManager = EventsUtils.createEventsManager();
		
		// create an object for the EventHandler and add to the event manager
		CounterEventHandler counter = new CounterEventHandler();
		eventsManager.addHandler(counter);
		
		// create an events reader and pass the manager that should process events
		MatsimEventsReader matsimEventsReader = new MatsimEventsReader(eventsManager);
		
		matsimEventsReader.readFile(eventsFile);
		
		// total number of link enter events in the simulation
		System.out.println(counter.getCounterEnter());
		// total number of link exit events in the simulation
		System.out.println(counter.getCounterLeave());
				
	}

}