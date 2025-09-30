package org.eth.lectures.week2;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

public class RunSimulationObjects {

    public static void main(String[] args) {

        // 1) Config laden (RELATIVER Pfad)
        String configPath = "scenarios/SiouxFalls/config_default.xml";
        Config config = ConfigUtils.loadConfig(configPath);

        System.out.println("********************************************************************************");
        System.out.println("alte lastIteration: " + config.controller().getLastIteration());

        // 2) Config anpassen (hier: 1 Iteration, eigener Output-Ordner, Overwrite an)
        config.controller().setLastIteration(1);
        config.controller().setOutputDirectory("output2");
        config.controller().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);

        // 3) Beispiel: Verkehrsfluss skalieren (QSim-FlowCapFactor, z.B. 80% der Kapazität)
        config.qsim().setFlowCapFactor(0.8);

        System.out.println("neue lastIteration: " + config.controller().getLastIteration());
        System.out.println("Output: " + config.controller().getOutputDirectory());

        // 4) Scenario laden (liest Network/Population/Facilities/Transit usw. aus den in der Config gesetzten Pfaden)
        Scenario scenario = ScenarioUtils.loadScenario(config);

        // 5) Zugriff auf Scenario-Daten (nur als Demo; hier noch keine Änderung)
        Population population = scenario.getPopulation();
        System.out.println("Population size (vor Änderungen): " + population.getPersons().size());

        // 6) Controller bauen und Simulation starten
        Controler controller = new Controler(scenario);
        controller.run();
    }
}
