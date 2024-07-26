package com.beehyv.lab.mapper;

import com.beehyv.lab.dto.requestDto.*;
import com.beehyv.lab.dto.responseDto.*;
import com.beehyv.lab.entity.*;
import java.util.*;
import java.util.stream.Stream;

import com.beehyv.parent.utils.FileValidator;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DTOMapper {

    public abstract  Country mapDtoToEntityCountry(CountryRequestDTO countryRequestDTO);
    public abstract  CountryResponseDTO mapEntityToDtoCountry(Country country);

    @Mapping(target = "country.id" , source = "countryId")
    public abstract  State mapDtoToEntityState(StateRequestDTO stateRequestDTO);

    @Mapping(target = "state.id" , source = "stateId")
    public abstract District mapDtoToEntityDistrict(DistrictRequestDTO districtRequestDTO);

    @Mapping(target = "district.id" , source = "districtId")
    public abstract Village mapDtoToEntityVillage(VillageRequestDTO villageRequestDTO);

    @Mapping(target="village.id" , source = "villageId")
    public abstract Address mapDtoToEntityAddress(AddressRequestDTO addressRequestDTO);
    @Mapping(target = "village" , source = "village")
    public abstract AddressResponseDTO mapEntityToDtoAddress(Address address);


    @Mapping(target = "status", expression = "java(new Status(labRequestDTO.getStatusId()))")
    @Mapping(target = "labDocuments", source = "labDocs")
    @Mapping(target = "labManufacturerCategoryMapping", expression = "java(setLabManufacturerCategoryDtoToEntity(labRequestDTO.getLabManufacturers()))")
    @Mapping(target = "labCategoryMapping", expression = "java(setLabCategoryDtoToEntity(labRequestDTO.getLabCategories()))")
    public abstract Lab mapDtoToEntityLab(LabRequestDTO labRequestDTO);

    public Set<LabManufacturerCategoryMapping> setLabManufacturerCategoryDtoToEntity(Set<LabManufacturerRequestDTO> labManufacturers){
        if (labManufacturers == null) {
            return new HashSet<>();
        }
        LabRequestDTO labRequestDTO = new LabRequestDTO();
        Set<LabManufacturerCategoryMapping> labManufacturerCategories = new HashSet<>();
        labManufacturers.forEach(dto->{
            LabManufacturerCategoryMapping model = new LabManufacturerCategoryMapping();
            model.setId(dto.getId());
            model.setCategoryId(dto.getCategoryId());
            model.setManufacturerId(dto.getManufacturerId());
            if (dto.getLabId()!=null) model.setLab(new Lab(dto.getLabId()));
            labManufacturerCategories.add(model);
        });
        return labManufacturerCategories;
    }

    public Set<LabCategory> setLabCategoryDtoToEntity(Set<LabCategoryRequestDto> labCategoryRequestDtos){
        if (labCategoryRequestDtos == null) {
            return new HashSet<>();
        }
        Set<LabCategory> labCategories = new HashSet<>();
        labCategoryRequestDtos.forEach(dto->{
            LabCategory model = new LabCategory();
            model.setId(dto.getId());
            model.setIsEnabled(dto.getIsEnabled() == null || dto.getIsEnabled());
            model.setCategoryId(dto.getCategoryId());
            if (dto.getLabId()!=null)model.setLab(new Lab(dto.getLabId()));
            model.setIsEnabled(dto.getIsEnabled()!=null ? dto.getIsEnabled():true);
            labCategories.add(model);
        });
        return labCategories;
    }

    public abstract LocationResponseDto mapToDto(State state);
    public abstract LocationResponseDto mapToDto(District district);
    public abstract StateResponseDTO mapToResponseDto(State state);
    public abstract DistrictResponseDTO mapToResponseDto(District district);
    public abstract VillageResponseDTO mapToResponseDto(Village village);
    public abstract LocationResponseDto mapToDto(Village village);

    @Mapping(target = "status", expression = "java(mapToResponseDto(lab.getStatus()))")
    @Mapping(target = "labDocs", source = "labDocuments")
    @Mapping(target = "address" , source = "address")
    @Mapping(target = "labManufacturers", expression = "java(mapLabManufacturerEntityToDto(lab.getLabManufacturerCategoryMapping()))")
    @Mapping(target = "labCategories", expression = "java(mapLabCategoryEntityToDto(lab.getLabCategoryMapping()))")
    public abstract LabResponseDTO mapEntityToDtoLab(Lab lab);
    public abstract LabListResponseDTO mapEntityToLabListDto(Lab lab);

    public Set<LabManufacturerResponseDTO> mapLabManufacturerEntityToDto (Set<LabManufacturerCategoryMapping> labManufacturers){
        if(labManufacturers == null) return new HashSet<>();
        Set<LabManufacturerResponseDTO> labManufacturerResponseDTOS = new HashSet<>();
        labManufacturers.forEach(m ->{
            LabManufacturerResponseDTO dto = new LabManufacturerResponseDTO();
            dto.setId(m.getId());
            dto.setCategoryId(m.getCategoryId());
            dto.setManufacturerId(m.getManufacturerId());
            labManufacturerResponseDTOS.add(dto);
        });
        return labManufacturerResponseDTOS;
    }

    public Set<LabCategoryResponseDto> mapLabCategoryEntityToDto (Set<LabCategory> labCategories){
        if(labCategories == null) return new HashSet<>();
        Set<LabCategoryResponseDto> labCategoryResponseDtos = new HashSet<>();
        labCategories.forEach(m ->{
            LabCategoryResponseDto dto = new LabCategoryResponseDto();
            dto.setId(m.getId());
            dto.setCategoryId(m.getCategoryId());
            dto.setIsEnabled(m.getIsEnabled());
            labCategoryResponseDtos.add(dto);
        });
        return labCategoryResponseDtos;
    }

    @Mapping(target = "sampleProperties", source = "sampleProperties")
    @Mapping(target = "labTests", source = "labTests")
    @Mapping(target = "lab.id", source = "labId")
    @Mapping(target = "state.id" ,  source = "stateId")
    @Mapping(target= "sampleTestDocuments" , source = "sampleTestDocuments")
    public abstract LabSample mapDtoToEntityLabSample(LabSampleRequestDTO labSampleRequestDTO);

    public LabSample mapDtoToEntityLabSample(LabSampleRequestDTO requestDTO, LabSample prev) {
        LabSample labSample = this.mapDtoToEntityLabSample(requestDTO);
        BeanUtils.copyProperties(prev, labSample, "labTests", "performedBy",
                "sampleTestDocuments", "sampleProperties", "testDate", "requestStatusId");
        labSample.getSampleProperties().forEach(d -> d.setLabSample(labSample));
        labSample.getLabTests().forEach(d -> d.setLabSample(labSample));
        labSample.getSampleTestDocuments().forEach(d -> {
            if(d.getCategoryDocumentRequirement() != null && d.getCategoryDocumentRequirement().getId() == null) {
                d.setCategoryDocumentRequirement(null);
            }
            d.setLabSample(labSample);
        });
        return labSample;
    }
    @Mapping(target = "isInspectionSample", expression = "java(setIsInspectionSample(labSample))")
    @Mapping(target = "isExternalTest", expression = "java(setIsExternalTest(labSample))")
    @Mapping(target = "sampleProperties", source = "sampleProperties")
    @Mapping(target = "labTests", source = "labTests")
    @Mapping(target = "lab", source = "lab")
    @Mapping(target= "sampleTestDocuments" , source = "sampleTestDocuments")
    @Mapping(target = "state" , source = "state")
    @Mapping(target = "inspectionId", source = "inspection.id")
    public abstract LabSampleResponseDto mapEntityToDtoLabSample(LabSample labSample);

    public Boolean setIsInspectionSample(LabSample labSample){
        return labSample.getInspection() != null && labSample.getInspection().getId()!=0L;
    }
    public Boolean setIsExternalTest(LabSample labSample){
        return labSample.getInspection() != null && labSample.getInspection().getId()!=0L && labSample.getInspection().getIsExternalTest();
    }

    @Mapping(source = "labId" , target = "lab.id")
    @Mapping(target = "categoryDoc", expression = "java(labDocumentRequestDTO.getCategoryDocId()!=null ? new CategoryDocumentRequirement(labDocumentRequestDTO.getCategoryDocId()) : null)")
    public abstract LabDocument mapDtoToEntityLabDocument(LabDocumentRequestDTO labDocumentRequestDTO);

    public abstract LabDocumentResponseDTO mapEntityToResDtoLabDocument(LabDocument labDocument);


    @Mapping(target = "labSample.id", source = "labSampleId")
    public abstract SampleProperty mapDtoToEntitySampleProperty(SamplePropertyRequestDTO samplePropertyRequestDTO);
    public abstract SamplePropertyRequestDTO mapEntityToDtoSampleProperty(SampleProperty sampleProperty);
    @Mapping(target = "labSampleId", source = "labSample.id")
    public abstract SamplePropertyResponseDTO mapEntityToResDtoSampleProperty(SampleProperty sampleProperty);

    @Mapping(source = "labSampleId" , target = "labSample.id")
    @Mapping(source = "categoryDocId" , target = "categoryDocumentRequirement.id")
    public abstract SampleTestDocument mapDtoToEntitySampleTestDocument(SampleTestDocumentRequestDTO sampleTestDocumentRequestDTO);
    @Mapping(source = "labSample.id",target = "labSampleId")
    @Mapping(source = "categoryDocumentRequirement", target = "categoryDoc")
    public abstract SampleTestDocumentResponseDTO mapEntityToResDtoSampleTestDocument(SampleTestDocument sampleTestDocument);
    @Mapping(target = "country",source = "village.district.state.country")
    @Mapping(target = "state",source = "village.district.state")
    @Mapping(target = "district",source = "village")
    public abstract AddressLocationResponseDto mapToDto(AddressResponseDTO addressResponseDTO);

    public Set<LabDocument> mapDtoToEntityLabDocumentsEntity(Set<LabDocumentRequestDTO> labDocumentRequestDTOS) {
        if(labDocumentRequestDTOS == null) return new HashSet<>();
        labDocumentRequestDTOS.forEach(d -> FileValidator.validateFileUpload(d.getPath()));
        return labDocumentRequestDTOS.stream().map(this::mapDtoToEntityLabDocument)
                .collect(Collectors.toSet());
    }
    public Set<LabDocumentResponseDTO> mapDtoToEntityLabDocumentsDto(Set<LabDocument> labDocuments) {
        if(labDocuments == null|| labDocuments.size()==0) return new HashSet<>();
        return labDocuments.stream()
                .peek(d -> d.setLab(d.getLab()))
                .map(this::mapEntityToResDtoLabDocument)
                .collect(Collectors.toSet());
    }

    public Set<LabTest> mapDtoToEntityLabTestEntity(Set<LabTestRequestDTO> labTestRequestDTOS) {
        if(labTestRequestDTOS == null) return new HashSet<>();
        return labTestRequestDTOS.stream().map(this::mapDtoToEntityLabTest).collect(Collectors.toSet());
    }
    public Set<LabTestResponseDTO> mapEntityToDtoLabTestDto(Set<LabTest> labTests) {
        if(labTests == null) return new HashSet<>();
        return labTests.stream()
                .peek(d -> d.setLabSample(d.getLabSample()))
                .map(this::mapEntityToDtoLabTest)
                .collect(Collectors.toSet());
    }



    public Set<SampleProperty> mapDtoToEntitySamplePropertyEntity(Set<SamplePropertyRequestDTO> samplePropertyRequestDTOS) {
        if(samplePropertyRequestDTOS == null) return new HashSet<>();
        return samplePropertyRequestDTOS.stream().map(this::mapDtoToEntitySampleProperty)
                .collect(Collectors.toSet());
    }
    public Set<SamplePropertyResponseDTO> mapDtoToResEntitySamplePropertyDto(Set<SampleProperty> sampleProperties) {
        if(sampleProperties == null || sampleProperties.size()==0) return new HashSet<>();
        return sampleProperties.stream()
                .peek(d -> d.setLabSample(d.getLabSample()))
                .map(this::mapEntityToResDtoSampleProperty)
                .collect(Collectors.toSet());
    }

    public Set<SampleTestDocument> mapDtoToEntitySampleTestDocumentEntity(Set<SampleTestDocumentRequestDTO> sampleTestDocumentRequestDTOS) {
        if(sampleTestDocumentRequestDTOS == null) return new HashSet<>();
        sampleTestDocumentRequestDTOS.forEach(d -> FileValidator.validateFileUpload(d.getPath()));
        return sampleTestDocumentRequestDTOS.stream().map(this::mapDtoToEntitySampleTestDocument)
                .collect(Collectors.toSet());
    }
    public Set<SampleTestDocumentResponseDTO> mapDtoToResEntitySampleTestDocumentDto(Set<SampleTestDocument> sampleTestDocuments) {
        if(sampleTestDocuments == null) return new HashSet<>();
        return sampleTestDocuments.stream()
                .peek(d -> d.setLabSample(d.getLabSample()))
                .map(this::mapEntityToResDtoSampleTestDocument)
                .collect(Collectors.toSet());
    }

    public Set<LabTestDocument> mapDtoToEntityLabTestDocumentEntity(Set<LabTestDocumentRequestDTO> samplePropertyDTOS) {
        if(samplePropertyDTOS == null) return new HashSet<>();
        samplePropertyDTOS.forEach(d -> FileValidator.validateFileUpload(d.getPath()));
        return samplePropertyDTOS.stream().map(this::mapDtoToEntityLabTestDocument)
                .peek(d -> {
                    if(d.getCategoryDocumentRequirement() != null && d.getCategoryDocumentRequirement().getId() == null) {
                        d.setCategoryDocumentRequirement(d.getCategoryDocumentRequirement());
                    }
                })
                .collect(Collectors.toSet());
    }
    public Set<LabTestDocumentResponseDTO> mapDtoToResEntityLabTestDocumentDto(Set<LabTestDocument> labTestDocuments) {
        if(labTestDocuments == null) return new HashSet<>();
        return labTestDocuments.stream()
                .peek(d -> d.setLabTest(d.getLabTest()))
                .map(this::mapEntityToResDtoLabTestDocument)
                .collect(Collectors.toSet());
    }

    public LabTest mapDtoToEntityLabTest(LabTestRequestDTO labTestRequestDTO){
        if ( labTestRequestDTO == null ) {
            return null;
        }
        LabTest labTest = new LabTest();
        if(labTestRequestDTO.getLabSampleId() != null) labTest.setLabSample(labTestRequestDTOToLabSample( labTestRequestDTO ));
        labTest.setId( labTestRequestDTO.getId() );
        labTest.setTestName( labTestRequestDTO.getTestName() );
        labTest.setValue( labTestRequestDTO.getValue() );
        labTest.setDefaultPresent( labTestRequestDTO.getDefaultPresent() );
        labTest.setTestMethodFollowed(labTestRequestDTO.getTestMethodFollowed());
        labTest.setUom( labTestRequestDTO.getUom() );
        labTest.setMaxValue(labTestRequestDTO.getMaxValue());
        labTest.setMinValue(labTestRequestDTO.getMinValue());
        labTest.setReferenceMethod(labTestRequestDTO.getReferenceMethod());
        labTest.setTestResult(labTestRequestDTO.getTestResult());
        labTest.setIsMandatory(labTestRequestDTO.getIsMandatory());
        return labTest;
    }

    protected LabSample labTestRequestDTOToLabSample(LabTestRequestDTO labTestRequestDTO) {
        if ( labTestRequestDTO == null ) {
            return null;
        }
        LabSample labSample = new LabSample();
        labSample.setId( labTestRequestDTO.getLabSampleId() );
        return labSample;
    }


    @Mapping(target = "testResult", expression="java(labTest.getTestResult())")
    @Mapping(source = "labSample.id",target="labSampleId")
    public abstract LabTestResponseDTO mapEntityToDtoLabTest(LabTest labTest);

    @Mapping(source = "labTestTypeId", target = "labTestType.id")
    public abstract LabTestReferenceMethod mapDtoToEntityLabTestReferenceMethod(LabTestReferenceMethodRequestDTO labTestReferenceMethodRequestDTO);
    @Mapping(target = "labTestType", expression = "java(converter(labTestReferenceMethod.getLabTestType()))")
    public abstract LabTestReferenceMethodResponseDTO mapEntityToDtoLabTestReferenceMethod(LabTestReferenceMethod labTestReferenceMethod);
    public LabTestTypeResponseDTO converter(LabTestType labTestType){
        LabTestTypeResponseDTO labTestTypeResponseDTO= new LabTestTypeResponseDTO();
        labTestTypeResponseDTO.setCategoryId(labTestType.getCategoryId());
        labTestTypeResponseDTO.setName(labTestType.getName());
        labTestTypeResponseDTO.setType(labTestType.getType());
        labTestTypeResponseDTO.setId(labTestType.getId());
        labTestTypeResponseDTO.setIsMandatory(labTestType.getIsMandatory());
        return labTestTypeResponseDTO;
    }

    public abstract LabTestType mapDtoToEntityLabTestType(LabTestTypeRequestDTO labTestTypeRequestDTO);

    @Mapping(target = "labTestReferenceMethods", ignore = true)
    public abstract LabTestTypeResponseDTO mapEntityToDtoLabTestTypeForGetAll(LabTestType labTestType);
    @Mapping(target = "labTestReferenceMethods", expression = "java(setEntityToDto(labTestType.getLabTestReferenceMethods()))")
    public abstract LabTestTypeResponseDTO mapEntityToDtoLabTestType(LabTestType labTestType);

    public List<LabTestReferenceMethodResponseDTO> setEntityToDto(
        List<LabTestReferenceMethod> labTestReferenceMethods) {
        if (labTestReferenceMethods == null) {
            return new ArrayList<>();
        }
        List<LabTestReferenceMethodResponseDTO> labTestReferenceMethodResponseDTO = new ArrayList<>();
        labTestReferenceMethods.forEach(d->{
            LabTestReferenceMethodResponseDTO dto = new LabTestReferenceMethodResponseDTO();
            dto.setName(d.getName());
            dto.setId(d.getId());
            dto.setUom(d.getUom());
            dto.setDefaultPresent(d.getDefaultPresent());
            dto.setMaxValue(d.getMaxValue());
            dto.setMinValue(d.getMinValue());
            dto.setReferenceValue(d.getReferenceValue());
            labTestReferenceMethodResponseDTO.add(dto);
        });
        return labTestReferenceMethodResponseDTO;
    }
    public abstract DocType mapDtoToEntityDocType(DocTypeRequestDTO docTypeRequestDTO);
    public abstract DocTypeResponseDTO mapEntityToDtoDocType(DocType docType);


    @InheritInverseConfiguration
    public abstract LabTestDocument mapDtoToEntityLabTestDocument(LabTestDocumentRequestDTO labTestDocumentRequestDTO);
    @Mapping(source = "labTest.id", target = "labTestId")
    @Mapping(source = "categoryDocumentRequirement.id", target = "categoryDocumentRequirementId")
    public abstract LabTestDocumentRequestDTO mapEntityToDtoLabTestDocument(LabTestDocument labTestDocument);
    @Mapping(source = "labTest", target = "labTest")
    @Mapping(source = "categoryDocumentRequirement", target = "categoryDocumentRequirement")
    public abstract LabTestDocumentResponseDTO mapEntityToResDtoLabTestDocument(LabTestDocument labTestDocument);



    @Mapping(source = "docTypeId",target = "docType.id")
    public abstract CategoryDocumentRequirement mapDtoToEntityCategoryDocumentRequirement(CategoryDocumentRequirementRequestDTO categoryDocumentRequirementRequestDTO);

    @Mapping(source = "docType",target = "docType")
    @Mapping(source = "categoryDocRequirementType", target = "categoryDocRequirementType")
    public abstract CategoryDocumentRequirementResponseDTO mapEntityToDtoCategoryDocumentRequirement(CategoryDocumentRequirement categoryDocumentRequirement);
    @Mapping(target = "lab.id",source = "labId")
    public abstract LabCategory mapDtoToEntityLabCategory(LabCategoryRequestDto dto);

    @Mapping(target = "lab",expression = "java(mapEntityToDtoLab(labCategory.getLab()))")
    public abstract LabCategoryResponseDto mapEntityToDtoLabCategory(LabCategory labCategory);

    public abstract SampleState mapDtoToEntitySampleState(SampleStateRequestDto dto);
    public abstract SampleStateResponseDTO mapEntityToDtoSampleState(SampleState entity);

    public String convertAddressToCompleteAddress(Address address){
        if (address==null) return null;
        String laneOne = address.getLaneOne();
        String laneTwo= address.getLaneTwo();
        String latitude= String.valueOf(address.getLatitude());
        String longitude = String.valueOf(address.getLongitude());
        Village village = address.getVillage();
        String village_name =village.getName();
        String district_name =village.getDistrict().getName();
        String state_name = village.getDistrict().getState().getName();
        String country_name =village.getDistrict().getState().getCountry().getName();
        String pincode = address.getPinCode();
        String completeAddress =  Stream.of(laneOne, laneTwo, village_name,district_name,state_name,country_name,pincode,latitude,longitude)
                .filter(str -> !Objects.equals(str, "null") && !StringUtils.isEmpty(str))
                .collect(Collectors.joining(", "));
        return completeAddress;
    }

    public abstract StatusResponseDto mapToResponseDto(Status status);
    public abstract Status mapToEntity(StatusRequestDTO dto);

    @Mapping(target = "labSample", expression = "java(mapEntityToDtoLabSample(inspection.getLabSample()))")
    public abstract InspectionResponseDTO mapEntityToDtoInspection(Inspection inspection);

    @Mapping(target = "labSample", expression = "java(mapDtoToEntityLabSample(inspectionRequestDTO.getLabSample()))")
    public abstract Inspection mapDtoToEntityInspection(InspectionRequestDTO inspectionRequestDTO);
}
