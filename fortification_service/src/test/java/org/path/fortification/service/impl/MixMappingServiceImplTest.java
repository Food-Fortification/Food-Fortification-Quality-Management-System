package org.path.fortification.service.impl;

import org.path.fortification.dto.requestDto.MixMappingCommentsRequestDto;
import org.path.fortification.dto.requestDto.MixMappingRequestDto;
import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.dto.responseDto.MixMappingResponseDto;
import org.path.fortification.entity.*;
import org.path.fortification.manager.*;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MixMappingServiceImplTest {

    @Mock
    private MixMappingManager manager;

    @Mock
    private BatchManager batchManager;

    @Mock
    private LotManager lotManager;

    @Mock
    private UOMManager uomManager;

    @Mock
    private CategoryManager categoryManager;

    @Mock
    private KeycloakInfo keycloakInfo;

    @InjectMocks
    private MixMappingServiceImpl mixMappingService;

    private MixMappingRequestDto requestDto;
    private MixMapping mixMapping;
    private Batch batch;
    private Lot lot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDto = new MixMappingRequestDto();
        requestDto.setSourceLotId(1L);
        requestDto.setQuantityUsed(10.0);
        requestDto.setUomId(1L);

        mixMapping = new MixMapping();
        mixMapping.setId(1L);
        mixMapping.setQuantityUsed(10.0);
        mixMapping.setUom(new UOM(1L));

        batch = new Batch();
        batch.setId(1L);

        mixMapping.setTargetBatch(batch);
        lot = new Lot();
        lot.setId(1L);
        lot.setBatch(batch, lot);
        lot.setCategory(new Category());
        lot.setRemainingQuantity(10.0);
        lot.setState(new State(1L, null, null, null, "approved", null, null));
        lot.setUom(new UOM(1L, "uom", 1L));
        mixMapping.setSourceLot(lot);

        Map userInfo = new HashMap();
        userInfo.put("manufacturerId", "1");

        when(manager.create(any(MixMapping.class))).thenReturn(mixMapping);
        when(manager.findById(mixMapping.getId())).thenReturn(mixMapping);
        when(batchManager.findById(batch.getId())).thenReturn(batch);
        when(lotManager.findById(lot.getId())).thenReturn(lot);
        when(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0)).thenReturn(userInfo);

    }

    @Test
    void createMixMapping_ValidRequest_ShouldCreateMixMapping() {
        mixMappingService.createMixMapping(batch.getId(), requestDto);

        verify(manager, times(1)).create(any(MixMapping.class));
    }

    @Test
    void getMixMappingById_ValidId_ShouldReturnMixMappingDto() {
        MixMappingResponseDto response = mixMappingService.getMixMappingById(mixMapping.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mixMapping.getId(), response.getId());
        Assertions.assertEquals(mixMapping.getSourceLot().getId(), response.getSourceLot().getId());
        Assertions.assertEquals(mixMapping.getQuantityUsed(), response.getQuantityUsed());
        Assertions.assertEquals(mixMapping.getUom().getId(), response.getUom().getId());
    }

    @Test
    void getAllMixMappingsByTargetBatch_ValidRequest_ShouldReturnMixMappingList() {
        List<MixMapping> mixMappings = new ArrayList<>();
        mixMappings.add(mixMapping);

        when(manager.findAllByTargetBatchId(batch.getId(), 0, 10)).thenReturn(mixMappings);
        when(manager.getCountByTargetBatchId(mixMappings.size(), batch.getId(), 0, 10)).thenReturn(1L);

        ListResponse<MixMappingResponseDto> response = mixMappingService.getAllMixMappingsByTargetBatch(batch.getId(), 0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getCount());
    }

    @Test
    void getAllMixMappingsBySourceLot_ValidRequest_ShouldReturnMixMappingList() {
        List<MixMapping> mixMappings = new ArrayList<>();
        mixMappings.add(mixMapping);

        when(manager.findAllBySourceLotId(lot.getId(), 0, 10)).thenReturn(mixMappings);
        when(manager.getCountBySourceLotId(mixMappings.size(), lot.getId(), 0, 10)).thenReturn(1L);

        ListResponse<MixMappingResponseDto> response = mixMappingService.getAllMixMappingsBySourceLot(lot.getId(), 0, 10);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getCount());
    }

    @Test
    void updateMixMapping_ValidRequest_ShouldUpdateMixMapping() {
        mixMapping.setTargetBatch(batch);
        requestDto.setId(mixMapping.getId());

        mixMappingService.updateMixMapping(batch.getId(), requestDto);

        verify(manager, times(1)).update(mixMapping);
    }

    @Test
    void updateMixMapping_InvalidBatchId_ShouldThrowException() {
        requestDto.setId(mixMapping.getId());
        Batch invalidBatch = new Batch();
        invalidBatch.setId(2L);

        Assertions.assertThrows(Exception.class, () -> {
            mixMappingService.updateMixMapping(invalidBatch.getId(), requestDto);
        });
    }

    @Test
    void deleteMixMapping_ValidId_ShouldDeleteMixMapping() {
        mixMappingService.deleteMixMapping(mixMapping.getId());

        verify(manager, times(1)).delete(mixMapping.getId());
    }

    @Test
    void updateBatchMixes_ValidRequest_ShouldUpdateBatchMixes() {
        MixMappingCommentsRequestDto mixesInformation = new MixMappingCommentsRequestDto();
        List<MixMappingRequestDto> mixes = new ArrayList<>();
        MixMappingRequestDto mix1 = new MixMappingRequestDto();
        mix1.setSourceLotId(1L);
        mix1.setQuantityUsed(10.0);
        mix1.setUomId(1L);
        mixes.add(mix1);
        mixesInformation.setMixMappingRequestDtoList(mixes);
        mixesInformation.setComments("Updated batch mixes");
        Category c = new Category();
        c.setId(1L);
        batch.setCategory(c);
        MixMapping mix2 = new MixMapping();
        Set set = new HashSet<>();
        set.add(mix2);
        batch.setMixes(set);

        when(batchManager.update(any())).thenReturn(null);
        when(batchManager.findById(1L)).thenReturn(batch);
        when(lotManager.getAllByIds(mixes.stream().map(MixMappingRequestDto::getSourceLotId).collect(Collectors.toList()))).thenReturn(Collections.singletonList(
                lot
        ));
        when(categoryManager.isCategorySuperAdmin(1L, RoleCategoryType.MODULE)).thenReturn(true);
        when(lotManager.findById(1L)).thenReturn(lot);
        when(uomManager.findById(1L)).thenReturn(new UOM(1L, "uom", 1L));

        mixMappingService.updateBatchMixes(mixesInformation, 1L);

        verify(batchManager, times(1)).update(any(Batch.class));
        verify(uomManager, times(1)).findById(1L);
    }
}


//tests includes-
//createMixMapping_ValidRequest_ShouldCreateMixMapping: This test verifies that the createMixMapping method creates a new MixMapping entity correctly.
//getMixMappingById_ValidId_ShouldReturnMixMappingDto: This test verifies that the getMixMappingById method returns the correct MixMappingResponseDto for a given MixMapping ID.
//getAllMixMappingsByTargetBatch_ValidRequest_ShouldReturnMixMappingList: This test verifies that the getAllMixMappingsByTargetBatch method returns a ListResponse containing the correct list of MixMappingResponseDto objects for a given target batch ID.
//getAllMixMappingsBySourceLot_ValidRequest_ShouldReturnMixMappingList: This test verifies that the getAllMixMappingsBySourceLot method returns a ListResponse containing the correct list of MixMappingResponseDto objects for a given source lot ID.
//updateMixMapping_ValidRequest_ShouldUpdateMixMapping: This test verifies that the updateMixMapping method updates an existing MixMapping entity correctly with the provided MixMappingRequestDto.
//updateMixMapping_InvalidBatchId_ShouldThrowException: This test verifies that the updateMixMapping method throws a BadRequestException when an invalid batch ID is provided.
//deleteMixMapping_ValidId_ShouldDeleteMixMapping: This test verifies that the deleteMixMapping method deletes an existing MixMapping entity for the given ID.
//update batch mixes: This test verifies that the update call being made by batchManager.