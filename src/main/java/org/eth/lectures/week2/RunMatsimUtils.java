package org.eth.lectures.week2;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.ArrayList;
import java.util.List;

public class RunMatsimUtils {

    public static void main(String[] args) {

        // ---------- 1) Config & Scenario vorbereiten ----------
        // Pfad via Program Arguments übergeben (Run Configuration):
        // Program argument: scenarios/SiouxFalls/config_default.xml
        String configPath = args[0];

        Config config = ConfigUtils.loadConfig(configPath);
        config.controller().setOutputDirectory("output1");
        // config.controller().setOverwriteFileSetting(OverwriteFileSetting.deleteDirectoryIfExists);
        config.controller().setLastIteration(1);

        Scenario scenario = ScenarioUtils.loadScenario(config);

        // ---------- 2) Population & Factory besorgen ----------
        Population population = scenario.getPopulation();
        PopulationFactory pf = population.getFactory();

        // ---------- 3) Person anlegen ----------
        Id<Person> personId = Id.createPersonId("new_agent_001");
        Person person = pf.createPerson(personId);

        // ---------- 4) Plan anlegen ----------
        Plan plan = pf.createPlan();

        // Wir wählen zwei existierende Links aus dem geladenen Netzwerk,
        // damit wir keine Koordinaten raten müssen und sicher auf dem Netz liegen.
        List<Link> links = new ArrayList<>(scenario.getNetwork().getLinks().values());
        if (links.size() < 2) throw new IllegalStateException("Netz hat zu wenige Links.");

        Link homeLink = links.get(0);
        Link workLink = links.get(links.size() / 2); // irgendein anderer Link

        // ---------- 5) Aktivitäten erzeugen ----------
        // Wichtig: Die erste Aktivität braucht eine Endzeit, sonst bleibt die Person zuhause.
        Activity home = pf.createActivityFromLinkId("home", homeLink.getId());
        home.setEndTime(8 * 3600); // 08:00 Uhr

        Activity work = pf.createActivityFromLinkId("work", workLink.getId());
        // (Keine Endzeit nötig, Work kann "offen" bleiben für diesen simplen Plan)

        // ---------- 6) Leg (Weg) zwischen den Aktivitäten ----------
        Leg leg = pf.createLeg(TransportMode.car);

        // ---------- 7) Plan zusammenbauen ----------
        plan.addActivity(home);
        plan.addLeg(leg);
        plan.addActivity(work);

        // ---------- 8) Plan der Person zuweisen und Person zur Population hinzufügen ----------
        person.addPlan(plan);                 // bei nur einem Plan ist dieser automatisch "selected"
        person.getAttributes().putAttribute("age", 29);  // Beispiel-Attribut
        population.addPerson(person);

        // ---------- 9) Simulation laufen lassen ----------
        Controler controller = new Controler(scenario);
        controller.run();
    }
}
