package com.macaelfitnesscenterback.fitnesscenterback.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entidad JPA que representa un plan de membresía en el sistema.
 * 
 * Esta clase está mapeada a la tabla 'planes' en la base de datos e incluye
 * información relevante sobre precios, descuentos e imagen asociada.
 */
@Entity
@Table(name = "planes")
public class planes {

    /** Identificador único del plan (clave primaria autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del plan (ej. 'Mensual', 'Diario', 'Premium'). */
    private String nombre;

    /** Precio mensual del plan. */
    @Column(name = "precio_mensual")
    private BigDecimal precioMensual;

    /** Precio diario del plan. */
    @Column(name = "precio_diario")
    private BigDecimal precioDiario;

    /** Porcentaje de descuento aplicado al plan (en decimal, ej. 0.15 para 15%). */
    private BigDecimal descuento;

    /**
     * Precio final con descuento aplicado.
     * 
     * Este campo es calculado en la base de datos (por triggers o vistas),
     * por lo que no se inserta ni actualiza desde la aplicación.
     */
    @Column(name = "precio_con_descuento", insertable = false, updatable = false)
    private BigDecimal precioConDescuento;

    /** URL o nombre de archivo de imagen asociado al plan. */
    @Column(name = "imagen")
    private String imagen;

    @OneToMany(mappedBy = "plan")
    @JsonIgnore
    private Set<Usuarios> usuarios;

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioMensual() {
        return precioMensual;
    }

    public void setPrecioMensual(BigDecimal precioMensual) {
        this.precioMensual = precioMensual;
    }

    public BigDecimal getPrecioDiario() {
        return precioDiario;
    }

    public void setPrecioDiario(BigDecimal precioDiario) {
        this.precioDiario = precioDiario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPrecioConDescuento() {
        return precioConDescuento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Set<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }
}
