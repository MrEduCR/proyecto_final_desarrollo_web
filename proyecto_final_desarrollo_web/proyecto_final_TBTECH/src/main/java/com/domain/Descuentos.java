package com.domain;

import java.math.BigDecimal;

public enum Descuentos {

    SIN_DESCUENTO(BigDecimal.valueOf(0)),
    DESCUENTO_5(BigDecimal.valueOf(5)),
    DESCUENTO_10(BigDecimal.valueOf(10)),
    DESCUENTO_15(BigDecimal.valueOf(15)),
    DESCUENTO_20(BigDecimal.valueOf(20));

    private final BigDecimal porcentaje;

    Descuentos(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }
}
