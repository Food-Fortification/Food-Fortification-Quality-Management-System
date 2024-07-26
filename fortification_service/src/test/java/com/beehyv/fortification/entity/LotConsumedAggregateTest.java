package com.beehyv.fortification.entity;

import com.beehyv.fortification.enums.LotConsumedType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LotConsumedAggregateTest {

    @Test
    void testAllFields() {
        // Arrange
        LotConsumedAggregate lotConsumedAggregate = new LotConsumedAggregate();
        Long id = 1L;
        Long categoryId = 2L;
        String sourceDistrictGeoId = "sourceDistrictGeoId";
        String sourceStateGeoId = "sourceStateGeoId";
        String targetDistrictGeoId = "targetDistrictGeoId";
        String targetStateGeoId = "targetStateGeoId";
        Double quantity = 10d;
        LotConsumedType lotConsumedType = LotConsumedType.DIRECT;

        // Act
        lotConsumedAggregate.setId(id);
        lotConsumedAggregate.setCategoryId(categoryId);
        lotConsumedAggregate.setSourceDistrictGeoId(sourceDistrictGeoId);
        lotConsumedAggregate.setSourceStateGeoId(sourceStateGeoId);
        lotConsumedAggregate.setTargetDistrictGeoId(targetDistrictGeoId);
        lotConsumedAggregate.setTargetStateGeoId(targetStateGeoId);
        lotConsumedAggregate.setQuantity(quantity);
        lotConsumedAggregate.setLotConsumedType(lotConsumedType);

        // Assert
        assertEquals(id, lotConsumedAggregate.getId());
        assertEquals(categoryId, lotConsumedAggregate.getCategoryId());
        assertEquals(sourceDistrictGeoId, lotConsumedAggregate.getSourceDistrictGeoId());
        assertEquals(sourceStateGeoId, lotConsumedAggregate.getSourceStateGeoId());
        assertEquals(targetDistrictGeoId, lotConsumedAggregate.getTargetDistrictGeoId());
        assertEquals(targetStateGeoId, lotConsumedAggregate.getTargetStateGeoId());
        assertEquals(quantity, lotConsumedAggregate.getQuantity());
        assertEquals(lotConsumedType, lotConsumedAggregate.getLotConsumedType());
    }
}