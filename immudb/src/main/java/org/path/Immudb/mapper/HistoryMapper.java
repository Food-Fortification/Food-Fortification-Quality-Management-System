package org.path.Immudb.mapper;
import org.path.Immudb.dto.HistoryResponseDto;
import org.path.Immudb.entity.BatchEventEntity;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {

    public HistoryResponseDto mapHistoryEntityToDto(BatchEventEntity event){
        HistoryResponseDto dto = new HistoryResponseDto();
        dto.setId(event.getId());
        dto.setManufacturerName(event.getManufacturerName());
        dto.setManufacturerAddress(event.getManufacturerAddress());
        dto.setEntityId(event.getEntityId());
        dto.setType(event.getType());
        dto.setState(event.getState());
        dto.setDateOfAction(event.getDateOfAction());
        dto.setComments(event.getComments());
        dto.setCreatedDate(event.getCreatedDate());
        dto.setCreatedBy(event.getCreatedBy());
        return dto;
    }

}
