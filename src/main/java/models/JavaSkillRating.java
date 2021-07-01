package models;

import java.util.HashMap;
import java.util.Map;

public enum JavaSkillRating {
    NONE(0),
    BEGINNER(1),
    INTERMEDIATE(2),
    ADVANCED(3),
    SEBASTIAN(4);

    private int value;
    private static Map<Integer, JavaSkillRating> skillValueSkillRatingMap = new HashMap<>();

    private JavaSkillRating(int value) {
        this.value = value;
    }

    static {
        for (JavaSkillRating javaSkillRating : JavaSkillRating.values()) {
            skillValueSkillRatingMap.put(javaSkillRating.value, javaSkillRating);
        }
    }

    public static JavaSkillRating valueOf(int value) {
        return skillValueSkillRatingMap.get(value);
    }

    public int getValue() {
        return value;
    }
}
