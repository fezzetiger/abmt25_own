package org.eth.lectures.week5;

import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.ActivityStartEvent;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.api.core.v01.events.handler.ActivityStartEventHandler;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;

// we want to analyze link enter and link leave events
// so we implement two interfaces that will allow us to capture two types of events
public class ActivityDurationEventHandler implements ActivityEndEventHandler, ActivityStartEventHandler{
	// we want to have two counters of these events
	private double totalDuration = 0.0;
	private int activityCount = 0;

	@Override
	public void handleEvent(ActivityStartEvent event) {
		// nothing to do here
	}

	@Override
	public void handleEvent(ActivityEndEvent event) {
		double duration = event.getTime();
		if(duration > 0.0) {
			totalDuration += duration;
			activityCount++;
		}
	}

	@Override
	public void reset(int iteration) {
		this.totalDuration = 0.0;
		this.activityCount = 0;
	}
	
	public double getAverageDuration() {
		if(activityCount == 0) {
			return 0.0;
		}
		return totalDuration / activityCount;
	}

}