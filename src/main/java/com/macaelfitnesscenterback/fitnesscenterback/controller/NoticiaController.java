package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.macaelfitnesscenterback.fitnesscenterback.model.Noticia;
import com.macaelfitnesscenterback.fitnesscenterback.repository.NoticiaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.io.*;

@RestController
@RequestMapping("/api")
public class NoticiaController {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @GetMapping("/noticias")
    public Page<Noticia> getNoticias(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return noticiaRepository.findAllByOrderByFechaRegistroDesc(pageable);
    }

    @DeleteMapping("/noticias/{id}")
    public void deleteNoticia(@PathVariable Long id) {
        noticiaRepository.deleteById(id);
    } 

    @PostMapping("/subirNoticias")
    public ResponseEntity<Noticia> crearNoticia(@RequestBody Noticia noticia) {
        noticia.setFechaRegistro(LocalDateTime.now());
        Noticia nueva = noticiaRepository.save(noticia);
        return ResponseEntity.ok(nueva);
    }

    @PostMapping("/upload-imagen")
    public ResponseEntity<String> uploadImagen(@RequestParam("file") MultipartFile file) {
        try {
            String nombreArchivo = StringUtils.cleanPath(file.getOriginalFilename());

            Path ruta = Paths.get("uploads/images/noticias/").resolve(nombreArchivo);
            Files.createDirectories(ruta.getParent()); 
            Files.copy(file.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);

            String urlImagen = "/images/noticias/" + nombreArchivo;

            return ResponseEntity.ok(urlImagen);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al subir la imagen");
        }
    }

}
