package com.macaelfitnesscenterback.fitnesscenterback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.macaelfitnesscenterback.fitnesscenterback.repository.usuariosRepository;
import com.macaelfitnesscenterback.fitnesscenterback.repository.RolRepository;
import com.macaelfitnesscenterback.fitnesscenterback.security.JwtUtil;
import com.macaelfitnesscenterback.fitnesscenterback.model.Rol;
import com.macaelfitnesscenterback.fitnesscenterback.model.Usuarios;

import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.List;

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

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolRepository rolRepository;

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

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar rol por defecto
        Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                .orElseGet(() -> rolRepository.save(new Rol("ROLE_USER")));
        usuario.setRoles(Set.of(rolUser));

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
     * @return Token JWT y datos del usuario si el login es exitoso; 401 si no lo
     *         es.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuarios usuarioRequest) {
        Optional<Usuarios> usuarioOpt = usuariosRepository.findByEmail(usuarioRequest.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();

            // Verifica que la contraseña sin encriptar coincida con la encriptada
            if (passwordEncoder.matches(usuarioRequest.getPassword(), usuario.getPassword())) {
                String token = jwtUtil.generateToken(usuario.getEmail(),
                        usuario.getRoles().stream().map(Rol::getNombre).toList());

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);

                Map<String, Object> userData = new HashMap<>();
                userData.put("id", usuario.getId());
                userData.put("email", usuario.getEmail());
                userData.put("nombre", usuario.getNombre());
                userData.put("roles", usuario.getRoles().stream().map(Rol::getNombre).toArray());

                response.put("usuario", userData);

                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }

@PostMapping("/solicitar-restablecimiento")
public ResponseEntity<?> solicitarRestablecimiento(@RequestBody Map<String, String> payload) {
    String email = payload.get("email");

    Optional<Usuarios> usuarioOpt = usuariosRepository.findByEmail(email);
    if (usuarioOpt.isEmpty()) {
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }

    String token = jwtUtil.generateToken(email, List.of());
    String url = "http://localhost:5173/restablecer-contrasena?token=" + token;

    try {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Restablece tu contraseña");

        String htmlContent = """
                <div style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 30px; text-align: center;">
                    <div style="background-color: white; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                        <div style="display: flex; align-items: center; justify-content: center; gap: 1rem; color: green; font-size: 24px; margin-bottom: 20px;">
                            <img src='cid:logoImage' style="width: 50px; height: 50px;" />
                            <p style="margin: 0;">Macael Fitness Center</p>
                        </div>
                        <h2 style="color: #333;">Restablecimiento de Contraseña</h2>
                        <p style="color: #555;">Haz clic en el siguiente botón para restablecer tu contraseña:</p>
                        <a href="%s" style="display: inline-block; margin-top: 20px; background-color: green; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px;">
                            Restablecer Contraseña
                        </a>
                        <p style="color: #888; margin-top: 20px; font-size: 12px;">Si no solicitaste este correo, puedes ignorarlo.</p>
                    </div>
                </div>
                """.formatted(url);

        helper.setText(htmlContent, true); // true para HTML

        // Añadir imagen embebida (opcional)
        ClassPathResource logo = new ClassPathResource("static/favicon.ico"); // Ajusta ruta si es diferente
        helper.addInline("logoImage", logo);

        mailSender.send(mimeMessage);
        return ResponseEntity.ok("Correo de restablecimiento enviado");

    } catch (MessagingException e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Error al enviar el correo");
    }
}

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String nuevaContrasena = payload.get("nuevaContrasena");

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(400).body("Token inválido o expirado");
        }

        String email = jwtUtil.extractEmail(token);
        Optional<Usuarios> usuarioOpt = usuariosRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        Usuarios usuario = usuarioOpt.get();
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuariosRepository.save(usuario);

        return ResponseEntity.ok("Contraseña restablecida correctamente");
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
