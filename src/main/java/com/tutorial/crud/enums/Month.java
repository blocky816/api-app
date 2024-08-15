package com.tutorial.crud.enums;

public enum Month {
    JANUARY(1, "ENERO"),
    FEBRUARY(2, "FEBRERO"),
    MARCH(3, "MARZO"),
    APRIL(4, "ABRIL"),
    MAY(5, "MAYO"),
    JUNE(6, "JUNIO"),
    JULY(7, "JULIO"),
    AUGUST(8, "AGOSTO"),
    SEPTEMBER(9, "SEPTIEMBRE"),
    OCTOBER(10, "OCTUBRE"),
    NOVEMBER(11, "NOVIEMBRE"),
    DECEMBER(12, "DICIEMBRE");

    private final int number;
    private final String name;

    Month(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the name of the month in English given the month number.
     *
     * @param number The month number (1-12).
     * @return The name of the month in English.
     * @throws IllegalArgumentException If the month number is not in the valid range.
     */
    public static String getMonthName(int number) {
        for (Month month : values()) {
            if (month.getNumber() == number) {
                return month.getName();
            }
        }
        throw new IllegalArgumentException("Month number must be between 1 and 12.");
    }
}
