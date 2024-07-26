package com.beehyv.lab.service.impl;
import com.beehyv.lab.dto.requestDto.LabManufacturerRequestDTO;
import com.beehyv.lab.dto.responseDto.LabListResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.Lab;
import com.beehyv.lab.entity.LabManufacturerCategoryMapping;
import com.beehyv.lab.manager.LabManufacturerCategoryManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.LabManufacturerCategoryService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;



@Transactional
@Service
@AllArgsConstructor
public class LabManufacturerCategoryServiceImpl implements LabManufacturerCategoryService {

    private final LabManufacturerCategoryManager labManufacturerCategoryManager;
    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);
    @Override
    public void create(LabManufacturerRequestDTO labManufacturerRequestDTO) {
        LabManufacturerCategoryMapping labManufacturerCategoryMapping =new LabManufacturerCategoryMapping();
        labManufacturerCategoryMapping.setCategoryId(labManufacturerRequestDTO.getCategoryId());
        labManufacturerCategoryMapping.setManufacturerId(labManufacturerRequestDTO.getManufacturerId());
        Long labId=labManufacturerRequestDTO.getLabId();

        labManufacturerCategoryMapping.setLab(new Lab(labId));
        labManufacturerCategoryManager.create(labManufacturerCategoryMapping);
    }




    @Override
    public void delete(Long id) {
        labManufacturerCategoryManager.delete(id);
    }

    @Override
    public ListResponse<LabListResponseDTO> getLabsByManufacturerId(String search,Long manufacturerId, Integer pageNumber, Integer pageSize){
        List<LabManufacturerCategoryMapping> labManufacturerCategoryMappingList= labManufacturerCategoryManager.findAllLabsByManufacturerIdAndCategoryId(
            search, manufacturerId,null, pageNumber, pageSize);
        Long count = labManufacturerCategoryManager.getCountForLabsByManufacturerIdAndCategoryId(search,manufacturerId,null);
        List<Lab> labs = labManufacturerCategoryMappingList.stream().map(l -> l.getLab()).toList();
        return ListResponse.from(labs, mapper::mapEntityToLabListDto, count);
    }
}
