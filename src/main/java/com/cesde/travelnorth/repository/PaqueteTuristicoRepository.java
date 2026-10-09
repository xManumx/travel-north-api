package com.cesde.travelnorth.repository;

import com.cesde.travelnorth.model.PaqueteTuristico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaqueteTuristicoRepository extends JpaRepository<PaqueteTuristico, Long> {

    List<PaqueteTuristico> findByActivoTrue();
}
