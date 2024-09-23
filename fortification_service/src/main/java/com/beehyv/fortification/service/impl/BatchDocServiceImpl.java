package com.beehyv.fortification.service.impl;

import com.beehyv.fortification.dto.responseDto.ListResponse;
import com.beehyv.fortification.entity.Batch;
import com.beehyv.fortification.entity.BatchDoc;
import com.beehyv.fortification.entity.CategoryDoc;
import com.beehyv.fortification.manager.BatchDocManager;
import com.beehyv.fortification.mapper.BaseMapper;
import com.beehyv.fortification.mapper.BatchDocMapper;
import com.beehyv.fortification.dto.requestDto.BatchDocRequestDto;
import com.beehyv.fortification.dto.responseDto.BatchDocResponseDto;
import com.beehyv.fortification.service.BatchDocService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class BatchDocServiceImpl implements BatchDocService {

    private final BaseMapper<BatchDocResponseDto, BatchDocRequestDto, BatchDoc> mapper = BaseMapper.getForClass(BatchDocMapper.class) ;
    private BatchDocManager manager;
    @Override
    public void createBatchDoc(BatchDocRequestDto dto) {
        BatchDoc entity = mapper.toEntity(dto);
        manager.create(entity);
    }

    @Override
    public BatchDocResponseDto getBatchDocById(Long id) {
        BatchDoc entity = manager.findById(id);
        return mapper.toDto(entity);
    }

    @Override
    public ListResponse<BatchDocResponseDto> getAllBatchDocsByBatchId(Long batchId, Integer pageNumber, Integer pageSize) {
        List<BatchDoc> entities = manager.findAllByBatchId(batchId, pageNumber, pageSize);
        Long count = manager.getCount(entities.size(), pageNumber, pageSize);
        return ListResponse.from(entities, mapper::toDto, count);
    }

    @Override
    public void updateBatchDoc(BatchDocRequestDto dto) {
        BatchDoc existingBatchDoc = manager.findById(dto.getId());
        Batch batch = new Batch();
        batch.setId(dto.getBatchId());
        CategoryDoc categoryDoc = new CategoryDoc();
        categoryDoc.setId(dto.getCategoryDocId());

        existingBatchDoc.setBatch(batch);
        existingBatchDoc.setCategoryDoc(categoryDoc);
        existingBatchDoc.setName(dto.getName());
        existingBatchDoc.setPath(dto.getPath());
        existingBatchDoc.setExpiry(dto.getExpiry());
        manager.update(existingBatchDoc);
    }

    @Override
    public void deleteBatchDoc(Long id) {
        manager.delete(id);
    }
}
