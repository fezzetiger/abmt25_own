package week2.lectures;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

public class MinimalRun {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("\nUSAGE:\n  mvn -q exec:java -Dexec.mainClass=week2.lectures.MinimalRun -Dexec.args=\"/path/to/config.xml [lastIter] [outDir]\"\n");
            System.err.println("Tip: use a config from your class repo, e.g. ../abmt25/scenarios/siouxfalls/config.xml");
            System.exit(2);
        }
        String configPath = args[0];

        Config config = ConfigUtils.loadConfig(configPath);

        // Optional: cap number of iterations via 2nd arg (default: 0)
        int lastIter = (args.length >= 2) ? Integer.parseInt(args[1]) : 0;
        config.controller().setLastIteration(lastIter);

        // Optional: set output directory via 3rd arg (default: runs/quick)
        String out = (args.length >= 3) ? args[2] : "runs/quick";
        config.controller().setOutputDirectory(out);

        Scenario scenario = ScenarioUtils.loadScenario(config);

        // Fail fast if something is empty (your loop symptom)
        if (scenario.getNetwork().getNodes().isEmpty()) {
            throw new IllegalStateException("Config loaded but network is EMPTY. Check network input in config: " + config.network().getInputFile());
        }
        if (scenario.getPopulation().getPersons().isEmpty()) {
            System.err.println("WARNING: Population is empty. Expect 0 trips. Check plans file: " + config.plans().getInputFile());
        }

        new Controler(scenario).run();
    }
}
