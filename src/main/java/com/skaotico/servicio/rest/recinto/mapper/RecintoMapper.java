package com.skaotico.servicio.rest.recinto.mapper;

import com.skaotico.servicio.rest.recinto.dto.RecintoRequestDto;
import com.skaotico.servicio.rest.recinto.dto.RecintoResponseDTO;
import com.skaotico.servicio.rest.recinto.model.RecintoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper para convertir entre {@link RecintoRequestDto} y {@link RecintoModel}.
 * <p>
 * Este mapper se encarga de:
 * <ul>
 *     <li>Convertir DTO a entidad y viceversa.</li>
 *     <li>Ignorar el campo {@code id} al mapear a la entidad.</li>
 *     <li>Convertir listas de entidades a listas de DTOs de respuesta.</li>
 * </ul>
 */
@Mapper(componentModel = "spring")
public interface RecintoMapper {

    /**
     * Convierte un {@link RecintoRequestDto} a {@link RecintoModel}.
     * <p>
     * El campo {@code id} se ignora intencionadamente porque se genera automáticamente en la base de datos.
     *
     * @param dto DTO con los datos del recinto a crear
     * @return entidad {@link RecintoModel} correspondiente
     */
    @Mapping(target = "id", ignore = true)
    RecintoModel toModel(RecintoRequestDto dto);

    /**
     * Convierte una entidad {@link RecintoModel} a {@link RecintoResponseDTO}.
     *
     * @param model entidad a convertir
     * @return DTO de respuesta correspondiente
     */
    RecintoResponseDTO toResponse(RecintoModel model);

    /**
     * Convierte una lista de entidades {@link RecintoModel} a una lista de DTOs de respuesta.
     *
     * @param models lista de entidades a convertir
     * @return lista de DTOs de respuesta
     */
    List<RecintoResponseDTO> toResponseList(List<RecintoModel> models);
}
