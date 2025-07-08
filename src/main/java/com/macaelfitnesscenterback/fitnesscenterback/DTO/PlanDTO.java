package com.macaelfitnesscenterback.fitnesscenterback.DTO;

import java.math.BigDecimal;

public class PlanDTO {
    private Long id;
    private String nombre;
    private BigDecimal precioMensual;
    private BigDecimal precioDiario;
    private BigDecimal descuento;
    private BigDecimal precioConDescuento;
    private String imagen;

    // Constructor
    public PlanDTO(Long id, String nombre, BigDecimal precioMensual, BigDecimal precioDiario, BigDecimal descuento, BigDecimal precioConDescuento, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precioMensual = precioMensual;
        this.precioDiario = precioDiario;
        this.descuento = descuento;
        this.precioConDescuento = precioConDescuento;
        this.imagen = imagen;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecioMensual() { return precioMensual; }
    public BigDecimal getPrecioDiario() { return precioDiario; }
    public BigDecimal getDescuento() { return descuento; }
    public BigDecimal getPrecioConDescuento() { return precioConDescuento; }
    public String getImagen() { return imagen; }
}

