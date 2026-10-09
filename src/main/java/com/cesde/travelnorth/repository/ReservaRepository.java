package com.cesde.travelnorth.repository;

import com.cesde.travelnorth.model.EstadoReserva;
import com.cesde.travelnorth.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT COALESCE(SUM(r.cantidadPersonas), 0L) FROM Reserva r WHERE r.paquete.id = :paqueteId AND r.estado = :estado")
    Long sumarPersonas(@Param("paqueteId") Long paqueteId, @Param("estado") EstadoReserva estado);

    List<Reserva> findByEmailClienteIgnoreCase(String email);
}
