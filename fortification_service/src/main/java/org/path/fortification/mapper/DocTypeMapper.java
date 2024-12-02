package org.path.fortification.mapper;

import org.path.fortification.dto.requestDto.DocTypeRequestDto;
import org.path.fortification.dto.responseDto.DocTypeResponseDto;
import org.path.fortification.entity.DocType;

public class DocTypeMapper implements Mappable<DocTypeResponseDto, DocTypeRequestDto, DocType>{
    @Override
    public DocTypeResponseDto toDto(DocType entity) {
        return new DocTypeResponseDto(
                entity.getId(),
                entity.getName()
        );
    }

    @Override
    public DocType toEntity(DocTypeRequestDto dto) {
        return new DocType(
                dto.getId(),
                dto.getName()
        );
    }
}
