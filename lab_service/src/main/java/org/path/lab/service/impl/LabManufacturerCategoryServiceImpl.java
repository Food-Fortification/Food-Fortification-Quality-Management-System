package org.path.lab.service.impl;
import org.path.lab.dto.requestDto.LabManufacturerRequestDTO;
import org.path.lab.dto.responseDto.LabListResponseDTO;
import org.path.lab.dto.responseDto.ListResponse;
import org.path.lab.entity.Lab;
import org.path.lab.entity.LabManufacturerCategoryMapping;
import org.path.lab.manager.LabManufacturerCategoryManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.LabManufacturerCategoryService;
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
