package org.path.iam.mapper;

import org.path.iam.dto.requestDto.DocTypeRequestDto;
import org.path.iam.dto.responseDto.DocTypeResponseDto;
import org.path.iam.model.DocType;

public class DocTypeMapper implements Mappable<DocTypeResponseDto, DocTypeRequestDto, DocType>{
    @Override
    public DocTypeResponseDto toDto(DocType entity) {
        DocTypeResponseDto dto = new DocTypeResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public DocType toEntity(DocTypeRequestDto dto) {
        DocType entity = new DocType();
        if (dto.getId()!=null)entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }
}
