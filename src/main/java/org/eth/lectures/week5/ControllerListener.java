package org.eth.week5.exercises.example;

import org.matsim.core.controler.events.IterationEndsEvent;
import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.IterationEndsListener;
import org.matsim.core.controler.listener.StartupListener;

public class ControllerListener implements StartupListener, IterationEndsListener {

	private CounterEventHandler counter;

	public ControllerListener(CounterEventHandler counter) {
		this.counter = counter;
	}

	public void notifyStartup(StartupEvent event) {
		event.getServices().getEvents().addHandler(this.counter);
	}

	@Override
	public void notifyIterationEnds(IterationEndsEvent event) {

		// total number of link enter events in the simulation
		System.out.println(counter.getCounterEnter());
		// total number of link exit events in the simulation
		System.out.println(counter.getCounterLeave());

	}

}
