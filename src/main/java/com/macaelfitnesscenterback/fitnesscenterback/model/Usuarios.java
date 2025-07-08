package com.macaelfitnesscenterback.fitnesscenterback.model;

import jakarta.persistence.*;
import java.util.Set;
/**
 * Entidad JPA que representa un usuario del sistema.
 * 
 * Esta clase está mapeada a una tabla en la base de datos que almacena
 * la información básica de los usuarios, como nombre, correo electrónico
 * y contraseña.
 */
@Entity
public class Usuarios {

    /** Identificador único del usuario (clave primaria autogenerada). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre completo del usuario. */
    private String nombre;

    /** Correo electrónico del usuario, utilizado para autenticación. */
    private String email;

    /** Contraseña del usuario (recomendada su encriptación fuera de esta clase). */
    private String password;
    
    @ManyToMany
    @JoinTable(
        name = "usuarios_planes", // nombre de la tabla unión
        joinColumns = @JoinColumn(name = "usuario_id"), // FK de esta entidad
        inverseJoinColumns = @JoinColumn(name = "plan_id") // FK de la otra entidad
    )
    private Set<planes> planes;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<planes> getPlanes() {
        return planes;
    }

    public void setPlanes(Set<planes> planes) {
        this.planes = planes;
    } 
}
