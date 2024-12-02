package org.path.lab.service.impl;

import org.path.lab.dto.requestDto.AddressRequestDTO;
import org.path.lab.dto.responseDto.AddressResponseDTO;
import org.path.lab.dto.responseDto.LabNameAddressResponseDto;
import org.path.lab.entity.Address;
import org.path.lab.entity.Lab;
import org.path.lab.helper.ServiceUtils;
import org.path.lab.manager.AddressManager;
import org.path.lab.manager.LabManager;
import org.path.lab.mapper.DTOMapper;
import org.path.lab.service.AddressService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    private final AddressManager addressManager;
    private final LabManager labManager;


    @Override
    public void create(AddressRequestDTO addressRequestDTO) {
        addressManager.create(mapper.mapDtoToEntityAddress(addressRequestDTO));
    }

    @Override
    public AddressResponseDTO getById(Long id) {
        Address address = addressManager.findById(id);
        if(address != null) {
            return mapper.mapEntityToDtoAddress(address);
        } else {
            return null;
        }
    }

    @Override
    public void update(AddressRequestDTO addressRequestDTO) {
        addressManager.update(mapper.mapDtoToEntityAddress(addressRequestDTO));
    }

    @Override
    public void delete(Long id) {
        addressManager.delete(id);
    }




    @Override
    public Map<Long, Map<String, String>> getCompleteAddressForLab(List<Long> labIds) {
        Map<Long, Lab> labs = labManager.findAllByIds(new HashSet<>(labIds));
        Map<Long, Map<String, String>> result = new HashMap<>();
        labs.forEach((k,v)->{
            Map<String, String> nameAddressMap = new HashMap<>();
            String name = v.getName();
            String address = ServiceUtils.getCompleteAddressForLab(v);
            nameAddressMap.put("name",name);
            nameAddressMap.put("address",address);
            result.put(k,nameAddressMap);
        });
        return result;
    }

    @Override
    public LabNameAddressResponseDto getNameAndCompleteAddressForLab(Long labId) {
        Lab lab = labManager.findById(labId);
        LabNameAddressResponseDto dto = new LabNameAddressResponseDto();
        dto.setId(lab.getId());
        dto.setName(lab.getName());
        dto.setCompleteAddress(ServiceUtils.getCompleteAddressForLab(lab));
        dto.setLabCertificateNumber(lab.getCertificateNo());
        return dto;
    }

}
