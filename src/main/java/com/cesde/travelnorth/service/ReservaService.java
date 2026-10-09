package com.cesde.travelnorth.service;

import com.cesde.travelnorth.exception.RecursoNoEncontradoException;
import com.cesde.travelnorth.exception.ReglaNegocioException;
import com.cesde.travelnorth.model.EstadoReserva;
import com.cesde.travelnorth.model.PaqueteTuristico;
import com.cesde.travelnorth.model.Reserva;
import com.cesde.travelnorth.repository.PaqueteTuristicoRepository;
import com.cesde.travelnorth.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final PaqueteTuristicoRepository paqueteRepository;

    public ReservaService(ReservaRepository reservaRepository, PaqueteTuristicoRepository paqueteRepository) {
        this.reservaRepository = reservaRepository;
        this.paqueteRepository = paqueteRepository;
    }

    @Transactional
    public Reserva crear(Long paqueteId, Reserva datos) {
        PaqueteTuristico paquete = paqueteRepository.findById(paqueteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paquete no existe: " + paqueteId));

        if (!Boolean.TRUE.equals(paquete.getActivo()) || !paquete.getFechaInicio().isAfter(LocalDate.now())) {
            throw new ReglaNegocioException("El paquete no está disponible: está inactivo o ya empezó");
        }

        Integer personas = datos.getCantidadPersonas();
        long ocupados = reservaRepository.sumarPersonas(paqueteId, EstadoReserva.CONFIRMADA);
        long disponibles = paquete.getCupoMaximo() - ocupados;
        if (personas == null || personas < 1 || personas > disponibles) {
            throw new ReglaNegocioException("Cantidad de personas no válida. Cupos disponibles: " + disponibles);
        }

        datos.setPaquete(paquete);
        datos.setTotal(paquete.getPrecio().multiply(BigDecimal.valueOf(personas)));
        datos.setEstado(EstadoReserva.CONFIRMADA);
        datos.setFechaReserva(LocalDateTime.now());
        return reservaRepository.save(datos);
    }

    public List<Reserva> listarPorEmail(String email) {
        return reservaRepository.findByEmailClienteIgnoreCase(email);
    }
}
