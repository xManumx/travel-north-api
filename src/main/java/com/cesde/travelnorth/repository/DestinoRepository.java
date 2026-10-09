package com.cesde.travelnorth.repository;

import com.cesde.travelnorth.model.Destino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Long> {

    boolean existsByCodigo(String codigo);

    List<Destino> findByPaisIgnoreCase(String pais);
}
