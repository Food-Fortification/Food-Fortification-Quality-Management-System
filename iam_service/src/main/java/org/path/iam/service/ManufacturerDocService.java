package org.path.iam.service;

import org.path.iam.dto.requestDto.ManufacturerDocsRequestDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.dto.responseDto.ManufacturerDocsResponseDto;
import org.path.iam.manager.ManufacturerDocManager;
import org.path.iam.mapper.BaseMapper;
import org.path.iam.mapper.ManufacturerDocsMapper;
import org.path.iam.model.ManufacturerDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ManufacturerDocService {
    private final ManufacturerDocManager manufacturerDocManager;
    private final BaseMapper<ManufacturerDocsResponseDto, ManufacturerDocsRequestDto,ManufacturerDoc> mapper = BaseMapper.getForClass(ManufacturerDocsMapper.class);

    public void create(ManufacturerDocsRequestDto manufacturerDocsRequestDto){
        manufacturerDocManager.create(mapper.toEntity(manufacturerDocsRequestDto));
    }
    public ListResponse<ManufacturerDocsResponseDto> getALlManufacturerDocs(Integer pageNumber,Integer pageSize){
        List<ManufacturerDoc> entities = manufacturerDocManager.findAll(pageNumber,pageSize);
        Long count = manufacturerDocManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities,mapper::toDto,count);
    }

    public ManufacturerDocsResponseDto getById(Long manufacturerDocId){
        return mapper.toDto(manufacturerDocManager.findById(manufacturerDocId));
    }

    public void updateManufacturerDoc(ManufacturerDocsRequestDto manufacturerDocsRequestDto){
        manufacturerDocManager.update( mapper.toEntity(manufacturerDocsRequestDto));
    }

    public void deleteManufacturer(Long manufacturerDocId){
        manufacturerDocManager.delete(manufacturerDocId);
    }

}
