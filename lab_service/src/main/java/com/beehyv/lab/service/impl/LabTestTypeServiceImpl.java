package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.LabTestTypeRequestDTO;
import com.beehyv.lab.dto.requestDto.SearchListRequest;
import com.beehyv.lab.dto.responseDto.LabTestTypeResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.LabTestType;
import com.beehyv.lab.manager.LabTestTypeManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.LabTestTypeService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class LabTestTypeServiceImpl implements LabTestTypeService {

    private final LabTestTypeManager labTestTypeManager;
    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    @Override
    public List<LabTestTypeResponseDTO> getLabTestTypesByCategoryId(Long categoryId, String geoId, Integer pageNumber, Integer pageSize) {
        List<LabTestType> labTestTypes = labTestTypeManager.findAllByCategoryId(categoryId, geoId, pageNumber, pageSize);
        if(labTestTypes.isEmpty()){
            labTestTypes = labTestTypeManager.findAllByCategoryId(categoryId, null, pageNumber, pageSize);
        }
        return labTestTypes.stream().map(mapper::mapEntityToDtoLabTestType)
                .collect(Collectors.toList());
    }

    @Override
    public List<LabTestTypeResponseDTO> getLabTestTypesByType(LabTestType.Type type, Long categoryId, Integer pageNumber, Integer pageSize) {
        List<LabTestType> labTestTypes = labTestTypeManager.findAllByType(type, categoryId, pageNumber, pageSize);
        return labTestTypes.stream().map(mapper::mapEntityToDtoLabTestType)
                .collect(Collectors.toList());
    }

    @Override
    public ListResponse<LabTestTypeResponseDTO> getAllLabTestTypes(
        SearchListRequest searchListRequest, Integer pageNumber, Integer pageSize) {
        List<LabTestType> labTestTypes = labTestTypeManager.findAllLabTestType(searchListRequest,
            pageNumber, pageSize);
        Long count = labTestTypeManager.getCountForAllLabTestType(searchListRequest);
        return ListResponse.from(labTestTypes, mapper::mapEntityToDtoLabTestTypeForGetAll, count);
    }

    @Override
    public LabTestTypeResponseDTO getLabTestTypeById(Long labTestTypeId) {
        LabTestType labTestType = labTestTypeManager.findById(labTestTypeId);
        if(labTestType != null) {
            return mapper.mapEntityToDtoLabTestType(labTestType);
        } else {
            return null;
        }
    }

    @Override
    public void addLabTestType(LabTestTypeRequestDTO labTestTypeRequestDTO) {
        LabTestType entity = mapper.mapDtoToEntityLabTestType(labTestTypeRequestDTO);
        entity.getLabTestReferenceMethods().forEach(d->
            d.setLabTestType(entity)
        );
        labTestTypeManager.create(entity);
    }

    @Override
    public void updateLabTestTypeById(Long labTestTypeId, LabTestTypeRequestDTO labTestTypeRequestDTO) {
        LabTestType labTestType = labTestTypeManager.findById(labTestTypeId);
        labTestTypeRequestDTO.setId(labTestTypeId);
        if (labTestType != null){
            LabTestType labTestType2 = mapper.mapDtoToEntityLabTestType(labTestTypeRequestDTO);
            labTestType2.setUuid(labTestType.getUuid());
            labTestType2.getLabTestReferenceMethods().forEach(d->{
                d.setLabTestType(labTestType2);
            });
            labTestTypeManager.update(labTestType2);
        }
    }

    @Override
    public void deleteLabTestTypeById(Long labTestTypeId) {
        LabTestType labTestType = labTestTypeManager.findById(labTestTypeId);
        if(labTestType != null) {
            labTestTypeManager.delete(labTestTypeId);
        }
    }
}
