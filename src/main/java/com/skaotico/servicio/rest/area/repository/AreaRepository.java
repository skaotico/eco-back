package com.skaotico.servicio.rest.area.repository;

import com.skaotico.servicio.rest.area.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByRecintoId(Long recintoId);
    List<Area> findByRecintoIdIn(List<Long> recintoIds);
}
