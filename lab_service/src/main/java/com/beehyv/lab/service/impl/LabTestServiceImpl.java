package com.beehyv.lab.service.impl;

import com.beehyv.lab.dto.requestDto.LabTestRequestDTO;
import com.beehyv.lab.dto.responseDto.LabTestResponseDTO;
import com.beehyv.lab.dto.responseDto.ListResponse;
import com.beehyv.lab.entity.LabTest;
import com.beehyv.lab.manager.LabTestManager;
import com.beehyv.lab.mapper.DTOMapper;
import com.beehyv.lab.service.LabTestService;
import com.beehyv.lab.dto.requestDto.CreateSampleRequestDto;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
@AllArgsConstructor
public class LabTestServiceImpl implements LabTestService {

    private final LabTestManager labTestManager;

    private DTOMapper mapper = Mappers.getMapper(DTOMapper.class);

    @Override
    public ListResponse<LabTestResponseDTO> getAllLabTests(Integer pageNumber, Integer pageSize) {
        List<LabTest> labTests = labTestManager.findAll(pageNumber, pageSize);

        Long count = labTestManager.getCount(labTests.size(), pageNumber, pageSize);
        return ListResponse.from(labTests, mapper::mapEntityToDtoLabTest, count);
    }

    @Override
    public LabTestResponseDTO getLabTestById(Long sampleId) {
        LabTest labTest = labTestManager.findById(sampleId);
        if (labTest != null) {
            return mapper.mapEntityToDtoLabTest(labTest);
        } else {
            return null;
        }
    }

    @Override
    public void addLabTest(CreateSampleRequestDto createSampleRequestDto) {
        long batchId = createSampleRequestDto.getBatchId();
        for (LabTestRequestDTO dto : createSampleRequestDto.getTests()) {
            LabTestRequestDTO labTestRequestDTO = new LabTestRequestDTO();
            if (dto.getLabTestReferenceMethodId() != null) {
                labTestRequestDTO.setLabTestReferenceMethodId(dto.getLabTestReferenceMethodId());
            } else {
                labTestRequestDTO.setTestName(dto.getTestName());
            }
            labTestRequestDTO.setValue(dto.getValue());
            LabTest l = mapper.mapDtoToEntityLabTest(labTestRequestDTO);
            if (labTestRequestDTO.getLabSampleId() == null) {
                l.setLabSample(null);
            }
            labTestManager.create(l);
        }
    }

    @Override
    public void updateLabTestById(Long sampleId, LabTestRequestDTO labTestRequestDTO) {
        LabTest labTest = labTestManager.findById(sampleId);
        labTestRequestDTO.setId(sampleId);
        if (labTest != null) {
            LabTest l = mapper.mapDtoToEntityLabTest(labTestRequestDTO);
            l.setUuid(labTest.getUuid());
            labTestManager.update(l);
        }
    }

    @Override
    public void deleteLabTestById(Long sampleId) {
        LabTest labTest = labTestManager.findById(sampleId);
        if (labTest != null) {
            labTestManager.delete(sampleId);
        }
    }

    @Override
    public ListResponse<LabTestResponseDTO> getDetailsByBatchId(Long batchId,Integer pageNumber, Integer pageSize) {
        List<LabTest> labTests = labTestManager.findDetailsByBatchId(batchId,pageNumber, pageSize);
        Long count = labTestManager.getCount(labTests.size(), pageNumber, pageSize);
        return ListResponse.from(labTests, mapper::mapEntityToDtoLabTest, count);
    }

}
