package com.skaotico.servicio.rest.esp.repository;


import com.skaotico.servicio.rest.esp.model.Esp32DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Esp32DeviceRepository extends JpaRepository<Esp32DeviceModel, Long> {
    Optional<Esp32DeviceModel> findByMac(String mac);
    Optional<Esp32DeviceModel> findByChipId(String chipId);

}