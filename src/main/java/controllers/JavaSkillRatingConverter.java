package controllers;

import javafx.util.StringConverter;
import models.JavaSkillRating;

public class JavaSkillRatingConverter extends StringConverter<JavaSkillRating> {

    private JavaSkillRating javaSkillStringToEnum(String javaSkill) {
        if (javaSkill == null)
            return JavaSkillRating.NONE;

        return JavaSkillRating.valueOf(javaSkill.toUpperCase());
    }

    @Override
    public String toString(final JavaSkillRating javaSkill) {
        return switch (javaSkill) {
            case NONE -> "None";
            case BEGINNER -> "Beginner";
            case INTERMEDIATE -> "Intermediate";
            case ADVANCED -> "Advanced";
            case SEBASTIAN -> "Sebastian";
        };
    }

    @Override
    public JavaSkillRating fromString(final String javaSkill) {
        return javaSkillStringToEnum(javaSkill);
    }
}
