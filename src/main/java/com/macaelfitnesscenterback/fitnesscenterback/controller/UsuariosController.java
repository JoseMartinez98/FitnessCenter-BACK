package com.macaelfitnesscenterback.fitnesscenterback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.macaelfitnesscenterback.fitnesscenterback.repository.usuariosRepository;
import com.macaelfitnesscenterback.fitnesscenterback.security.JwtUtil;
import com.macaelfitnesscenterback.fitnesscenterback.model.Usuarios;

import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

/**
 * Controlador REST para operaciones de autenticación y gestión de usuarios.
 * 
 * Este controlador incluye:
 * - Registro de nuevos usuarios.
 * - Inicio de sesión con verificación de credenciales.
 * - Consulta del perfil de usuario en sesión.
 * - Cierre de sesión.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    @Autowired
    private usuariosRepository usuariosRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint POST para registrar un nuevo usuario.
     * 
     * Verifica si el correo ya está registrado. Si no, guarda el nuevo usuario.
     *
     * URL: /api/usuarios/registrar
     * Método: POST
     * Cuerpo: JSON con los datos del usuario
     * 
     * @param usuario Objeto con los datos del usuario a registrar.
     * @return 200 si se registra con éxito; 400 si el correo ya existe.
     */
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuarios usuario) {
        if (usuariosRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        usuariosRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    /**
     * Endpoint GET para consultar el perfil del usuario autenticado.
     * 
     * Verifica si existe un usuario en la sesión HTTP. Si existe, lo retorna.
     *
     * URL: /api/usuarios/perfil
     * Método: GET
     * 
     * @param session Sesión HTTP activa.
     * @return Objeto del usuario si está en sesión; 401 si no hay sesión activa.
     */
    @GetMapping("/perfil")
    public ResponseEntity<?> perfil(HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("usuario");
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(401).body("No hay sesión activa");
        }
    }

    /**
     * Endpoint POST para login de usuario.
     * 
     * Verifica credenciales contra la base de datos y, si son válidas:
     * - Guarda al usuario en sesión.
     * - Genera y retorna un token JWT.
     *
     * URL: /api/usuarios/login
     * Método: POST
     * Cuerpo: JSON con email y password
     *
     * @param usuario Objeto con email y contraseña.
     * @param session Sesión HTTP para almacenar usuario autenticado.
     * @return Token JWT y datos del usuario si el login es exitoso; 401 si no lo es.
     */
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Usuarios usuarioRequest) {
    Optional<Usuarios> usuarioOpt = usuariosRepository.findByEmailAndPassword(
        usuarioRequest.getEmail(),
        usuarioRequest.getPassword()
    );

    if (usuarioOpt.isPresent()) {
        Usuarios usuario = usuarioOpt.get();

        String token = jwtUtil.generateToken(usuario.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);

        // Devuelve solo información no sensible del usuario
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", usuario.getId());
        userData.put("email", usuario.getEmail());
        userData.put("nombre", usuario.getNombre());

        response.put("usuario", userData);

        return ResponseEntity.ok(response);
    }

    return ResponseEntity.status(401).body("Credenciales incorrectas");
}


    /**
     * Endpoint POST para cerrar sesión.
     * 
     * Invalida la sesión actual.
     *
     * URL: /api/usuarios/logout
     * Método: POST
     * 
     * @param session Sesión HTTP actual.
     * @return Mensaje de confirmación de cierre de sesión.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada correctamente");
    }
}
