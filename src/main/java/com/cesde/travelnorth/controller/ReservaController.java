package com.cesde.travelnorth.controller;

import com.cesde.travelnorth.model.Reserva;
import com.cesde.travelnorth.service.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/api/paquetes/{paqueteId}/reservas")
    public ResponseEntity<Reserva> crear(@PathVariable Long paqueteId, @RequestBody Reserva reserva) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(paqueteId, reserva));
    }

    @GetMapping("/api/reservas")
    public ResponseEntity<List<Reserva>> listarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(reservaService.listarPorEmail(email));
    }
}
