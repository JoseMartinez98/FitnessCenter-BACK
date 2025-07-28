package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.macaelfitnesscenterback.fitnesscenterback.model.Horario;
import com.macaelfitnesscenterback.fitnesscenterback.repository.HorarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/horario")
public class HorarioController {

    private final Path RUTA_HORARIO = Paths.get("uploads/images/Horario/");

    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping("/imagen")
    public ResponseEntity<List<Horario>> obtenerTodosLosHorarios() {
        List<Horario> horarios = horarioRepository.findAll();
        return ResponseEntity.ok(horarios);
    }

    @PostMapping("/subir-imagen")
    public ResponseEntity<String> subirImagenHorario(@RequestParam("file") MultipartFile file) {
        try {
            String nombreArchivo = StringUtils.cleanPath(file.getOriginalFilename());
            Path destino = RUTA_HORARIO.resolve(nombreArchivo);
            Files.createDirectories(RUTA_HORARIO);
            Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
            String url = "/images/Horario/" + nombreArchivo;
            Horario horario = new Horario();
            horario.setImagen(url);
            horarioRepository.save(horario);
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al subir la imagen del horario.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarHorarioPorId(@PathVariable Long id) {
        if (horarioRepository.existsById(id)) {
            horarioRepository.deleteById(id);
            return ResponseEntity.ok("Horario eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("Horario no encontrado.");
        }
    }

    @GetMapping("/verificar-imagenes")
    public ResponseEntity<?> verificarImagenesHorario() {
        try {
            if (!Files.exists(RUTA_HORARIO)) {
                return ResponseEntity.badRequest().body("La carpeta no existe.");
            }

            List<String> resultados = Files.list(RUTA_HORARIO)
                    .filter(Files::isRegularFile)
                    .map(path -> {
                        boolean legible = Files.isReadable(path);
                        return path.getFileName().toString() + " -> " + (legible ? "OK" : "NO LEIBLE");
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(resultados);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al acceder a la carpeta: " + e.getMessage());
        }
    }
}
