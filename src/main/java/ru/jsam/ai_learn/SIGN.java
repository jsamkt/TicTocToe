package ru.jsam.ai_learn;

import java.util.Objects;

public enum SIGN {
    X("x", 1),
    O("o", -1),
    SPACE(" ", 0);

    private final String stringValue;
    private final int intValue;

    SIGN(String o, int i) {
        stringValue = o;
        intValue = i;
    }

    public static SIGN getFromString(String t) {
        if (Objects.equals(t, "x")) {
            return X;
        }
        if (Objects.equals(t, "o")) {
            return O;
        }
        return SPACE;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public SIGN getApposite() {
        if (this == X) {
            return O;
        }
        if (this == O) {
            return X;
        }

        throw new UnsupportedOperationException("The " + name().toUpperCase() + " SING doesn't have an apposite");
    }
}
