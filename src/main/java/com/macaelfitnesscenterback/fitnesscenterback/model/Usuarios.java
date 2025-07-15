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
    private String altura;
    private String peso;
    private String edad;
    private String objetivoPeso;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private planes plan;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles;

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

    public planes getPlan() {
        return plan;
    }

    public void setPlan(planes plan) {
        this.plan = plan;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getObjetivoPeso() {
        return objetivoPeso;
    }

    public void setObjetivoPeso(String objetivoPeso) {
        this.objetivoPeso = objetivoPeso;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
    
}
