package com.beehyv.lab.mapper;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.beehyv.lab.dto.requestDto.*;
import com.beehyv.lab.dto.responseDto.*;
import com.beehyv.lab.entity.*;
import com.beehyv.lab.mapper.DTOMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

public class DTOMapperTest {

    private DTOMapper dtoMapper;
    private final DTOMapperImpl dtoMapperimpl = new DTOMapperImpl();
    @Before
    public void setUp() {
        dtoMapper = Mappers.getMapper(DTOMapper.class);
    }

    // Country Tests
    @Test
    public void testMapDtoToEntityCountry() {
        CountryRequestDTO dto = new CountryRequestDTO(1L,"m");
        dto.setName("Test Country");

        Country entity = dtoMapper.mapDtoToEntityCountry(dto);

        assertEquals(dto.getName(), entity.getName());
    }

    @Test
    public void testMapEntityToDtoCountry() {
        Country entity = new Country();
        entity.setName("Test Country");

        CountryResponseDTO dto = dtoMapper.mapEntityToDtoCountry(entity);

        assertEquals(entity.getName(), dto.getName());
    }

    // State Tests
    @Test
    public void testMapDtoToEntityState() {
        StateRequestDTO dto = new StateRequestDTO();
        dto.setName("Test State");
        dto.setCountryId(1L);

        State entity = dtoMapper.mapDtoToEntityState(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCountryId(), entity.getCountry().getId());
    }

    @Test
    public void testMapToDtoState() {
        State entity = new State();
        entity.setName("Test State");

        LocationResponseDto dto = dtoMapper.mapToDto(entity);

        assertEquals(entity.getName(), dto.getName());
    }

    // District Tests
    @Test
    public void testMapDtoToEntityDistrict() {
        DistrictRequestDTO dto = new DistrictRequestDTO(1L,"L",1L);
        dto.setName("Test District");
        dto.setStateId(1L);

        District entity = dtoMapper.mapDtoToEntityDistrict(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getStateId(), entity.getState().getId());
    }

    @Test
    public void testMapToDtoDistrict() {
        District entity = new District();
        entity.setName("Test District");

        LocationResponseDto dto = dtoMapper.mapToDto(entity);

        assertEquals(entity.getName(), dto.getName());
    }

    // Village Tests
    @Test
    public void testMapDtoToEntityVillage() {
        VillageRequestDTO dto = new VillageRequestDTO();
        dto.setName("Test Village");
        dto.setDistrictId(1L);

        Village entity = dtoMapper.mapDtoToEntityVillage(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDistrictId(), entity.getDistrict().getId());
    }

    @Test
    public void testMapToDtoVillage() {
        Village entity = new Village();
        entity.setName("Test Village");

        LocationResponseDto dto = dtoMapper.mapToDto(entity);

        assertEquals(entity.getName(), dto.getName());
    }

    // Address Tests
    @Test
    public void testMapDtoToEntityAddress() {
        AddressRequestDTO dto = new AddressRequestDTO(null,null,null,null,null,null,null);
        dto.setLaneOne("Lane 1");
        dto.setVillageId(1L);

        Address entity = dtoMapper.mapDtoToEntityAddress(dto);

        assertEquals(dto.getLaneOne(), entity.getLaneOne());
        assertEquals(dto.getVillageId(), entity.getVillage().getId());
    }

    @Test
    public void testMapEntityToDtoAddress() {
        Address entity = new Address();
        entity.setLaneOne("Lane 1");

        AddressResponseDTO dto = dtoMapper.mapEntityToDtoAddress(entity);

        assertEquals(entity.getLaneOne(), dto.getLaneOne());
    }

    // Lab Tests
    @Test
    public void testMapDtoToEntityLab() {
        LabRequestDTO dto = new LabRequestDTO();
        dto.setName("Test Lab");
        dto.setStatusId(1L);

        Lab entity = dtoMapper.mapDtoToEntityLab(dto);

        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getStatusId(), entity.getStatus().getId());
    }

    @Test
    public void testMapEntityToDtoLab() {
        Lab entity = new Lab();
        entity.setName("Test Lab");

        LabResponseDTO dto = dtoMapper.mapEntityToDtoLab(entity);

        assertEquals(entity.getName(), dto.getName());
    }

    // LabSample Tests
    @Test
    public void testMapDtoToEntityLabSample() {
        LabSampleRequestDTO dto = new LabSampleRequestDTO();
        dto.setLabId(1L);

        LabSample entity = dtoMapper.mapDtoToEntityLabSample(dto);

        assertEquals(dto.getLabId(), entity.getLab().getId());
    }

    @Test
    public void testMapEntityToDtoLabSample() {
        LabSample entity = new LabSample();

        LabSampleResponseDto dto = dtoMapper.mapEntityToDtoLabSample(entity);

        assertNotNull(dto);
    }

    // LabDocument Tests
    @Test
    public void testMapDtoToEntityLabDocument() {
        LabDocumentRequestDTO dto = new LabDocumentRequestDTO(null,null,null,null,null);
        dto.setLabId(1L);

        LabDocument entity = dtoMapper.mapDtoToEntityLabDocument(dto);

        assertEquals(dto.getLabId(), entity.getLab().getId());
    }

    @Test
    public void testMapEntityToResDtoLabDocument() {
        LabDocument entity = new LabDocument();

        LabDocumentResponseDTO dto = dtoMapper.mapEntityToResDtoLabDocument(entity);

        assertNotNull(dto);
    }

    // SampleProperty Tests
    @Test
    public void testMapDtoToEntitySampleProperty() {
        SamplePropertyRequestDTO dto = new SamplePropertyRequestDTO();
        dto.setLabSampleId(1L);

        SampleProperty entity = dtoMapper.mapDtoToEntitySampleProperty(dto);

        assertEquals(dto.getLabSampleId(), entity.getLabSample().getId());
    }

    @Test
    public void testMapEntityToResDtoSampleProperty() {
        SampleProperty entity = new SampleProperty();

        SamplePropertyResponseDTO dto = dtoMapper.mapEntityToResDtoSampleProperty(entity);

        assertNotNull(dto);
    }

    // SampleTestDocument Tests
    @Test
    public void testMapDtoToEntitySampleTestDocument() {
        SampleTestDocumentRequestDTO dto = new SampleTestDocumentRequestDTO();
        dto.setLabSampleId(1L);

        SampleTestDocument entity = dtoMapper.mapDtoToEntitySampleTestDocument(dto);

        assertEquals(dto.getLabSampleId(), entity.getLabSample().getId());
    }

    @Test
    public void testMapEntityToResDtoSampleTestDocument() {
        SampleTestDocument entity = new SampleTestDocument();

        SampleTestDocumentResponseDTO dto = dtoMapper.mapEntityToResDtoSampleTestDocument(entity);

        assertNotNull(dto);
    }

    // LabTest Tests
    @Test
    public void testMapDtoToEntityLabTest() {
        LabTestRequestDTO dto = new LabTestRequestDTO();
        dto.setLabSampleId(1L);

        LabTest entity = dtoMapper.mapDtoToEntityLabTest(dto);

        assertEquals(dto.getLabSampleId(), entity.getLabSample().getId());
    }

    @Test
    public void testMapEntityToDtoLabTest() {
        LabTest entity = new LabTest();

        LabTestResponseDTO dto = dtoMapper.mapEntityToDtoLabTest(entity);

        assertNotNull(dto);
    }

    // Inspection Tests
    @Test
    public void testMapDtoToEntityInspection() {
        InspectionRequestDTO dto = new InspectionRequestDTO();

        Inspection entity = dtoMapper.mapDtoToEntityInspection(dto);

        assertNotNull(entity);
    }

    @Test
    public void testMapEntityToDtoInspection() {
        Inspection entity = new Inspection();

        InspectionResponseDTO dto = dtoMapper.mapEntityToDtoInspection(entity);

        assertNotNull(dto);
    }

    // Helper Method Tests
    @Test
    public void testConvertAddressToCompleteAddress() {
        Address address = new Address();
        address.setLaneOne("Lane 1");
        address.setLaneTwo("Lane 2");
        address.setLatitude(12.34);
        address.setLongitude(56.78);
        address.setPinCode("123456");

        Village village = new Village();
        village.setName("Test Village");

        District district = new District();
        district.setName("Test District");

        State state = new State();
        state.setName("Test State");

        Country country = new Country();
        country.setName("Test Country");

        state.setCountry(country);
        district.setState(state);
        village.setDistrict(district);
        address.setVillage(village);

        String completeAddress = dtoMapper.convertAddressToCompleteAddress(address);

        assertEquals("Lane 1, Lane 2, Test Village, Test District, Test State, Test Country, 123456, 12.34, 56.78", completeAddress);
    }





    @Test
    public void testSetLabManufacturerCategoryDtoToEntity_withNullInput() {
        Set<LabManufacturerCategoryMapping> result = dtoMapperimpl.setLabManufacturerCategoryDtoToEntity(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSetLabManufacturerCategoryDtoToEntity_withEmptySet() {
        Set<LabManufacturerCategoryMapping> result = dtoMapperimpl.setLabManufacturerCategoryDtoToEntity(new HashSet<>());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSetLabManufacturerCategoryDtoToEntity_withOneElement() {
        Set<LabManufacturerRequestDTO> input = new HashSet<>();
        LabManufacturerRequestDTO dto = new LabManufacturerRequestDTO(1L,1L,1L,1L);
        dto.setId(1L);
        dto.setCategoryId(2L);
        dto.setManufacturerId(3L);
        dto.setLabId(4L);
        input.add(dto);

        Set<LabManufacturerCategoryMapping> result = dtoMapperimpl.setLabManufacturerCategoryDtoToEntity(input);
        assertEquals(1, result.size());

        LabManufacturerCategoryMapping mapping = result.iterator().next();

        assertNotNull(mapping.getLab());
    }

    @Test
    public void testSetLabManufacturerCategoryDtoToEntity_withMultipleElements() {
        Set<LabManufacturerRequestDTO> input = new HashSet<>();
        for (long i = 1; i <= 3; i++) {
            LabManufacturerRequestDTO dto = new LabManufacturerRequestDTO(1L,1L,1L,1L);
            dto.setId(i);
            dto.setCategoryId(i * 2);
            dto.setManufacturerId(i * 3);
            dto.setLabId(i * 4);
            input.add(dto);
        }

        Set<LabManufacturerCategoryMapping> result = dtoMapperimpl.setLabManufacturerCategoryDtoToEntity(input);
        assertEquals(3, result.size());

        for (LabManufacturerCategoryMapping mapping : result) {
            assertNotNull(mapping.getLab());
        }
    }

    @Test
    public void testSetLabManufacturerCategoryDtoToEntity_withNullLabId() {
        Set<LabManufacturerRequestDTO> input = new HashSet<>();
        LabManufacturerRequestDTO dto = new LabManufacturerRequestDTO(1L,1L,1L,1L);
        dto.setId(1L);
        dto.setCategoryId(2L);
        dto.setManufacturerId(3L);
        dto.setLabId(null);
        input.add(dto);

        Set<LabManufacturerCategoryMapping> result = dtoMapperimpl.setLabManufacturerCategoryDtoToEntity(input);
        assertEquals(1, result.size());

        LabManufacturerCategoryMapping mapping = result.iterator().next();

        assertNull(mapping.getLab());
    }

    @Test
    public void testSetLabCategoryDtoToEntity_withNullInput() {
        Set<LabCategory> result = dtoMapperimpl.setLabCategoryDtoToEntity(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSetLabCategoryDtoToEntity_withEmptySet() {
        Set<LabCategory> result =dtoMapperimpl.setLabCategoryDtoToEntity(new HashSet<>());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSetLabCategoryDtoToEntity_withOneElement() {
        Set<LabCategoryRequestDto> input = new HashSet<>();
        LabCategoryRequestDto dto = new LabCategoryRequestDto(1L,1L,true,1L);
        dto.setId(1L);
        dto.setCategoryId(2L);
        dto.setLabId(3L);
        dto.setIsEnabled(true);
        input.add(dto);

        Set<LabCategory> result = dtoMapperimpl.setLabCategoryDtoToEntity(input);
        assertEquals(1, result.size());

        LabCategory category = result.iterator().next();
        assertNotNull(category.getLab());
        assertTrue(category.getIsEnabled());
    }

    @Test
    public void testSetLabCategoryDtoToEntity_withMultipleElements() {
        Set<LabCategoryRequestDto> input = new HashSet<>();
        for (long i = 1; i <= 3; i++) {
            LabCategoryRequestDto dto = new LabCategoryRequestDto(1L,1L,true,1L);
            dto.setId(i);
            dto.setCategoryId(i * 2);
            dto.setLabId(i * 3);
            dto.setIsEnabled(i % 2 == 0);
            input.add(dto);
        }

        Set<LabCategory> result = dtoMapperimpl.setLabCategoryDtoToEntity(input);
        assertEquals(3, result.size());

        for (LabCategory category : result) {
            assertNotNull(category.getLab());
            assertEquals(category.getId() % 2 == 0, category.getIsEnabled());
        }
    }

    @Test
    public void testSetLabCategoryDtoToEntity_withNullLabId() {
        Set<LabCategoryRequestDto> input = new HashSet<>();
        LabCategoryRequestDto dto = new LabCategoryRequestDto(1L,1L,true,1L);
        dto.setId(1L);
        dto.setCategoryId(2L);
        dto.setLabId(null);
        dto.setIsEnabled(true);
        input.add(dto);

        Set<LabCategory> result = dtoMapperimpl.setLabCategoryDtoToEntity(input);
        assertEquals(1, result.size());

        LabCategory category = result.iterator().next();

        assertNull(category.getLab());
        assertTrue(category.getIsEnabled());
    }

}
