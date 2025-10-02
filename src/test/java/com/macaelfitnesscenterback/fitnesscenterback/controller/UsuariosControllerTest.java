package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macaelfitnesscenterback.fitnesscenterback.model.Usuarios;
import com.macaelfitnesscenterback.fitnesscenterback.repository.usuariosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuariosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private usuariosRepository usuariosRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrarUsuario_CuandoCorreoNoExiste_DeberiaRegistrar() throws Exception {
        Usuarios usuario = new Usuarios();
        usuario.setNombre("Test User");
        usuario.setEmail("test@example.com");
        usuario.setPassword("123456");

        mockMvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario registrado correctamente"));
    }

    @Test
    void login_CuandoCredencialesInvalidas_DeberiaDevolver401() throws Exception {
        Usuarios usuario = new Usuarios();
        usuario.setEmail("noexiste@example.com");
        usuario.setPassword("badpass");

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciales incorrectas"));
    }
}
