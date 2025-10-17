package org.eth.week5.exercises.example;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

public class RunSimulation {

	public static void main(String[] args) {
		Config config = ConfigUtils.loadConfig(args[0]);
		config.controller().setLastIteration(5);
		config.controller().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);
		Scenario scenario = ScenarioUtils.loadScenario(config);
		Controler controller = new Controler(scenario);
		controller.run();

	}

}
