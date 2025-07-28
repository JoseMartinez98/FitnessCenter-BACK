package com.macaelfitnesscenterback.fitnesscenterback.controller;

import com.macaelfitnesscenterback.fitnesscenterback.DTO.PlanDTO;
import com.macaelfitnesscenterback.fitnesscenterback.model.planes;
import com.macaelfitnesscenterback.fitnesscenterback.repository.planesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.math.RoundingMode;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/planes")
public class planesController {

    @Autowired
    private planesRepository planesRepository;

    @GetMapping
    public List<PlanDTO> getAllPlanes() {
        return planesRepository.findAll().stream()
                .map(plan -> new PlanDTO(
                        plan.getId(),
                        plan.getNombre(),
                        plan.getPrecioMensual(),
                        plan.getPrecioDiario(),
                        plan.getDescuento(),
                        plan.getPrecioConDescuento(),
                        plan.getImagen()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@PathVariable Long id) {
        planesRepository.deleteById(id);
    }

    @PutMapping("editarPlan/{id}")
    public PlanDTO updatePlan(@PathVariable Long id, @RequestBody PlanDTO planDTO) {
        return planesRepository.findById(id).map(plan -> {
            plan.setNombre(planDTO.getNombre());
            plan.setPrecioMensual(planDTO.getPrecioMensual());
            plan.setPrecioDiario(planDTO.getPrecioDiario());
            plan.setDescuento(planDTO.getDescuento());
 
            // ✅ Recalcular el precio con descuento
            if (planDTO.getPrecioMensual() != null && planDTO.getDescuento() != null) {
                BigDecimal cien = new BigDecimal("100");
                BigDecimal descuento = planDTO.getDescuento();
                BigDecimal precioBase = planDTO.getPrecioMensual();

                BigDecimal precioConDescuento = precioBase
                        .multiply(cien.subtract(descuento))
                        .divide(cien, 2, RoundingMode.HALF_UP);

                plan.setPrecioConDescuento(precioConDescuento);
            }

            planesRepository.save(plan);

            planDTO.setPrecioConDescuento(plan.getPrecioConDescuento());

            return planDTO;
        }).orElseThrow(() -> new RuntimeException("Plan no encontrado con ID: " + id));
    }

    @PostMapping("/upload-plan")
    public ResponseEntity<String> uploadPlanImagen(@RequestParam("file") MultipartFile file) {
        try {
            String nombre = StringUtils.cleanPath(file.getOriginalFilename());
            Path ruta = Paths.get("uploads/images/planes/").resolve(nombre);
            System.out.println("ESTA ES LA COMPROBACION" + Files.isReadable(ruta));
            Files.createDirectories(ruta.getParent());
            Files.copy(file.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
            String url = "/images/planes/" + nombre;
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al subir la imagen del plan");
        }
    }

    @PostMapping("/subirPlan")
    public ResponseEntity<PlanDTO> subirPlan(@RequestBody PlanDTO planDTO) {
        planes nuevoPlan = new planes();
        nuevoPlan.setNombre(planDTO.getNombre());
        nuevoPlan.setPrecioMensual(planDTO.getPrecioMensual());
        nuevoPlan.setPrecioDiario(planDTO.getPrecioDiario());
        nuevoPlan.setDescuento(planDTO.getDescuento());

        if (planDTO.getPrecioMensual() != null && planDTO.getDescuento() != null) {
            BigDecimal cien = new BigDecimal("100");
            BigDecimal precioConDescuento = planDTO.getPrecioMensual()
                    .multiply(cien.subtract(planDTO.getDescuento()))
                    .divide(cien, 2, RoundingMode.HALF_UP);
            nuevoPlan.setPrecioConDescuento(precioConDescuento);
        }

        nuevoPlan.setImagen(planDTO.getImagen());

        planes guardado = planesRepository.save(nuevoPlan);

        planDTO.setId(guardado.getId());
        planDTO.setPrecioConDescuento(guardado.getPrecioConDescuento());

        return ResponseEntity.ok(planDTO);
    }

    @GetMapping("/verificar-imagenes")
    public ResponseEntity<?> verificarImagenes() {
        try {
            Path directorio = Paths.get("uploads/images/planes/");
            if (!Files.exists(directorio)) {
                return ResponseEntity.badRequest().body("La carpeta no existe.");
            }

            List<String> resultados = Files.list(directorio)
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
