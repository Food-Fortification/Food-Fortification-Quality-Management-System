package com.beehyv.fortification.mapper;

import com.beehyv.fortification.dto.requestDto.DocTypeRequestDto;
import com.beehyv.fortification.dto.responseDto.DocTypeResponseDto;
import com.beehyv.fortification.dto.responseDto.StateResponseDto;
import com.beehyv.fortification.entity.DocType;

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
