package controllers;

import javafx.util.StringConverter;

public class RestrictToOnlyNumbersStringConverter extends StringConverter<String> {

    private String getOnlyNumbers(final String alphaNumericString) {

        return alphaNumericString.replaceAll("[^\\d]", "");
    }

    @Override
    public String toString(final String alphaNumericString) {
        return getOnlyNumbers(alphaNumericString);
    }

    @Override
    public String fromString(final String alphaNumericString) {
        return getOnlyNumbers(alphaNumericString);
    }
}
