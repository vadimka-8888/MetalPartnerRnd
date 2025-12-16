package ru.metal_partner_rnd.ecommerce_core.startup_checkers;

import java.util.Map;

public interface Checker {

    String getName();

    Map<String, String> check();

    default String getStringResult() {
        StringBuilder result = new StringBuilder("");
        Map<String, String> map = check();
        result.append("------ " + getName() + " checker ------\n");
        for (Map.Entry entry: map.entrySet()) {
            result.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
        result.append("------------\n");
        return result.toString();
    }
}