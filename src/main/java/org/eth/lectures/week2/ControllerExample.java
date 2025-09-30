package org.eth.lectures.week2;

import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;

public class ControllerExample {
    public static void main(String[] args) {
        Config config = ConfigUtils.createConfig();
        config.controller().setLastIteration(1); 

        Controler controller = new Controler(config);

        controller.run();

    }
}