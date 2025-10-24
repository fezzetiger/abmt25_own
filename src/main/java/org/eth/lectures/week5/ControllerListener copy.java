package org.eth.lectures.week5;

import java.io.BufferedWriter;
import java.io.IOException;

import org.matsim.core.controler.events.IterationEndsEvent;
import org.matsim.core.controler.events.StartupEvent;
import org.matsim.core.controler.listener.IterationEndsListener;
import org.matsim.core.controler.listener.StartupListener;
import org.matsim.core.utils.io.IOUtils;

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

		BufferedWriter writer = IOUtils.getAppendingBufferedWriter(
			event.getServices().getControllerIO().getIterationFilename(event.getIteration(), "counts.csv"));
		
		try {
			writer.write("Iterationnumber; CounterEnter; CounterExit\n");
			writer.write(event.getIteration() + ";" + counter.getCounterEnter() + ";" + counter.getCounterLeave()+"\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
