package com.skaotico.servicio.rest.esp.mapper;



import com.skaotico.servicio.rest.esp.dto.Esp32DeviceRequestDto;
import com.skaotico.servicio.rest.esp.dto.Esp32DeviceResponseDto;
import com.skaotico.servicio.rest.esp.model.Esp32DeviceModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface Esp32DeviceMapper {

    // Mapear de RequestDto a Model
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    Esp32DeviceModel toModel(Esp32DeviceRequestDto dto);

    // Mapear de Model a ResponseDto
    Esp32DeviceResponseDto toDto(Esp32DeviceModel model);

    // Actualizar un Model existente con datos del RequestDto
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    void updateModelFromDto(Esp32DeviceRequestDto dto, @MappingTarget Esp32DeviceModel model);
}