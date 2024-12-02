package org.path.fortification.service.impl;

import org.path.fortification.dto.responseDto.ListResponse;
import org.path.fortification.entity.Batch;
import org.path.fortification.entity.BatchDoc;
import org.path.fortification.entity.CategoryDoc;
import org.path.fortification.manager.BatchDocManager;
import org.path.fortification.mapper.BaseMapper;
import org.path.fortification.mapper.BatchDocMapper;
import org.path.fortification.dto.requestDto.BatchDocRequestDto;
import org.path.fortification.dto.responseDto.BatchDocResponseDto;
import org.path.fortification.service.BatchDocService;
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
