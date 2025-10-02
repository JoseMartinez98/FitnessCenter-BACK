package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macaelfitnesscenterback.fitnesscenterback.model.Usuarios;
import com.macaelfitnesscenterback.fitnesscenterback.model.planes;
import com.macaelfitnesscenterback.fitnesscenterback.repository.planesRepository;
import com.macaelfitnesscenterback.fitnesscenterback.repository.usuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FavoritosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private usuariosRepository usuariosRepository;

    @Autowired
    private planesRepository planesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuarios usuario;
    private planes plan;

    @BeforeEach
    void setUp() {
        usuariosRepository.deleteAll();
        planesRepository.deleteAll();

        plan = new planes();
        plan.setNombre("Plan Mensual");
        plan = planesRepository.save(plan);

        usuario = new Usuarios();
        usuario.setNombre("Carlos");
        usuario.setEmail("carlos@example.com");
        usuario.setPassword("1234");
        usuario = usuariosRepository.save(usuario);
    }

    @Test
    void agregarFavorito_DeberiaAsignarPlan() throws Exception {
        FavoritosController.FavoritoRequest request = new FavoritosController.FavoritoRequest();
        request.setIdUsuario(usuario.getId());
        request.setIdPlan(plan.getId());

        mockMvc.perform(post("/api/favoritos/agregar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Plan asignado como favorito correctamente"));
    }

    @Test
    void eliminarFavorito_CuandoUsuarioNoExiste_DeberiaRetornar404() throws Exception {
        FavoritosController.FavoritoRequest request = new FavoritosController.FavoritoRequest();
        request.setIdUsuario(999L);
        request.setIdPlan(plan.getId());

        mockMvc.perform(delete("/api/favoritos/eliminar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }
}
