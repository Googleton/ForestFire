package com.hv;

import com.hv.ff.Forest;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    public static ForestFireConfig Config;

    public static void main(String[] args) {
	    Main main = new Main();
	    main.run();
    }

    public void run() {
        try {
            LoadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Forest forest = new Forest(Config.SizeX, Config.SizeY);

        for (Pair<Integer, Integer> pos : Config.StartingFires) {
            forest.Ignite(pos.getKey(), pos.getValue());
        }

        System.out.println("Simulation démarrée");
        int stepCount = 0;
        System.out.println(forest.DrawForest());

        while (forest.HasBurningTrees()) {
            forest.Tick();
            stepCount++;
            System.out.println("Étape " + stepCount);
            System.out.println(forest.DrawForest());
        }

        System.out.println("Simulation terminée en " + stepCount + " étapes.");
    }

    public void LoadConfig() throws IOException {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream = new FileInputStream("./" + propFileName);
        prop.load(inputStream);

        Config = new ForestFireConfig();
        Config.SpreadChance = Float.parseFloat(prop.getProperty("spread"));
        Config.SizeX = Integer.parseInt(prop.getProperty("sizeX"));
        Config.SizeY = Integer.parseInt(prop.getProperty("sizeY"));

        Config.StartingFires = new ArrayList<>();
        for (String position : prop.getProperty("initfire").split(";")) {
            String[] split = position.split(",");
            Pair<Integer, Integer> pos = new Pair<>(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            Config.StartingFires.add(pos);
        }
    }
}
