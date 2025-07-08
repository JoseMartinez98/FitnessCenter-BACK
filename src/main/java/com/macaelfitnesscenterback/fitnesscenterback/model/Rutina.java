package com.macaelfitnesscenterback.fitnesscenterback.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa una rutina de entrenamiento.
 * 
 * Esta clase está mapeada a la tabla 'rutinas' en la base de datos y
 * contiene información como nombre, descripción del entrenamiento
 * y un documento adjunto relacionado.
 */
@Entity
@Table(name = "rutinas")
public class Rutina {

    /** Identificador único de la rutina (clave primaria autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de la rutina (ej. "Full Body", "Piernas", "Cardio"). */
    private String nombre;

    /**
     * Descripción del entrenamiento.
     * 
     * Se define como tipo `TEXT` en la base de datos para permitir cadenas
     * extensas.
     */
    @Column(columnDefinition = "TEXT")
    private String entrenamiento;

    /**
     * Nombre del documento o enlace relacionado con la rutina (ej. PDF, guía,
     * etc.).
     */
    private String documento;

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

    public String getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(String entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
