package com.macaelfitnesscenterback.fitnesscenterback.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa una noticia en el sistema.
 * 
 * Esta clase está mapeada a la tabla 'noticias' en la base de datos y
 * contiene campos típicos como título, imagen y fecha de registro.
 */
@Entity
@Table(name = "noticias")
public class Noticia {

    /** Identificador único de la noticia (clave primaria, autoincremental). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Título de la noticia. */
    private String titulo;

    /** URL o nombre de la imagen asociada a la noticia. */
    private String imagen;

    /**
     * Fecha de registro de la noticia.
     * 
     * Se almacena como un TIMESTAMP y por defecto se inicializa al momento de
     * inserción.
     */
    @Column(name = "fecha_registro", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaRegistro;

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
