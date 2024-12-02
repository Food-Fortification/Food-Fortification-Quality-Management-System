package org.path.iam.service;

import org.path.iam.dto.requestDto.DocTypeRequestDto;
import org.path.iam.dto.responseDto.DocTypeResponseDto;
import org.path.iam.dto.responseDto.ListResponse;
import org.path.iam.manager.DocTypeManager;
import org.path.iam.mapper.BaseMapper;
import org.path.iam.mapper.DocTypeMapper;
import org.path.iam.model.DocType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocTypeService {
    private final DocTypeManager docTypeManager;
    private final BaseMapper<DocTypeResponseDto, DocTypeRequestDto,DocType> mapper = BaseMapper.getForClass(DocTypeMapper.class);
    public void create(DocTypeRequestDto docTypeRequestDto){
        DocType docType = mapper.toEntity(docTypeRequestDto);
        docTypeManager.create(docType);
    }
    public ListResponse<DocTypeResponseDto> getAllDocTpe(Integer pageNumber, Integer pageSize){
        List<DocType> entities = docTypeManager.findAll(pageNumber,pageSize);
        Long count = docTypeManager.getCount(entities.size(),pageNumber,pageSize);
        return ListResponse.from(entities,mapper::toDto,count);
    }
    public DocTypeResponseDto getById(Long docTypeId){
        return mapper.toDto(docTypeManager.findById(docTypeId));
    }
    public void update(DocTypeRequestDto docTypeRequestDto){
        docTypeManager.update(mapper.toEntity(docTypeRequestDto));
    }
    public void delete(Long docTypeId){
        docTypeManager.delete(docTypeId);
    }
}
