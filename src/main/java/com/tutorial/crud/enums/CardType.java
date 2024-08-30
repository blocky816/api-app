package com.tutorial.crud.enums;

public enum CardType {
    MASTERCARD,
    VISA,
    AMEX,
    MEXICOLOCAL,
    N_A,
    UNKNOWN; // Valor predeterminado para manejar valores no conocidos

    public static CardType fromString(String value) {
        try {
            return CardType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Valor no conocido, retorna el valor predeterminado
            return CardType.UNKNOWN;
        }
    }
}
