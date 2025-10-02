package com.macaelfitnesscenterback.fitnesscenterback.repository;

import com.macaelfitnesscenterback.fitnesscenterback.model.Usuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuariosRepositoryTest {

    @Autowired
    private usuariosRepository usuariosRepository;

    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        usuariosRepository.deleteAll();

        usuario = new Usuarios();
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("1234");
        usuario.setAltura("175");
        usuario.setPeso("70");
        usuario.setEdad("30");
        usuario.setObjetivoPeso("65");

        usuariosRepository.save(usuario);
    }

    @Test
    void existsByEmail_CuandoEmailExiste_DeberiaRetornarTrue() {
        boolean existe = usuariosRepository.existsByEmail("juan@example.com");
        assertThat(existe).isTrue();
    }

    @Test
    void existsByEmail_CuandoEmailNoExiste_DeberiaRetornarFalse() {
        boolean existe = usuariosRepository.existsByEmail("otro@example.com");
        assertThat(existe).isFalse();
    }

    @Test
    void findByEmail_CuandoEmailExiste_DeberiaRetornarUsuario() {
        Optional<Usuarios> encontrado = usuariosRepository.findByEmail("juan@example.com");
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Juan Pérez");
    }

    @Test
    void findByEmail_CuandoEmailNoExiste_DeberiaRetornarVacio() {
        Optional<Usuarios> encontrado = usuariosRepository.findByEmail("noexiste@example.com");
        assertThat(encontrado).isEmpty();
    }
}
