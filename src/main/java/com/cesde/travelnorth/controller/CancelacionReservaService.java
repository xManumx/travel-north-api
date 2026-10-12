package com.cesde.travelnorth.service;

import com.cesde.travelnorth.exception.RecursoNoEncontradoException;
import com.cesde.travelnorth.exception.ReglaNegocioException;
import com.cesde.travelnorth.model.EstadoReserva;
import com.cesde.travelnorth.model.Reserva;
import com.cesde.travelnorth.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class CancelacionReservaService {
    private static final long DIAS_MINIMOS = 3;
    private final ReservaRepository reservaRepository;

    public CancelacionReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Reserva cancelar(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no existe: " + reservaId));

        if (reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new ReglaNegocioException("La reserva " + reservaId + " ya está cancelada");
        }

        long diasParaViaje = ChronoUnit.DAYS.between(LocalDate.now(), reserva.getPaquete().getFechaInicio());
        if (diasParaViaje < DIAS_MINIMOS) {
            throw new ReglaNegocioException("Solo se puede cancelar hasta " + DIAS_MINIMOS + " días antes del viaje. Faltan " + diasParaViaje);
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        return reservaRepository.save(reserva);
    }
}
