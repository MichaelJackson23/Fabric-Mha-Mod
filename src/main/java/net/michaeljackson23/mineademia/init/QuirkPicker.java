package net.michaeljackson23.mineademia.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuirkPicker {
    public QuirkPicker() {
        List<String> quirks = new ArrayList<>();
        quirks.add("ofa");
        quirks.add("hchh");
        quirks.add("explosion");

        int randomInt = 0;

        Random rand = new Random();
        randomInt = rand.nextInt(quirks.size());

        System.out.println(randomInt);
        System.out.println(randomInt);
        System.out.println(randomInt);
    }
}
