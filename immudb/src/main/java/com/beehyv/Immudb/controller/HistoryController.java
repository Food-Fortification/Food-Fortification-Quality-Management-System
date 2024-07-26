package com.beehyv.Immudb.controller;

import com.beehyv.Immudb.dto.HistoryRequestDto;
import com.beehyv.Immudb.entity.BatchEventEntity;
import com.beehyv.Immudb.service.ImmudbService;
import com.beehyv.parent.exceptions.CustomException;
import io.codenotary.immudb4j.sql.SQLException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Tag(name = "History Controller")
@RequestMapping("/batchHistory")
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
public class HistoryController {

    @Autowired
    ImmudbService immudbService;

    @GetMapping("{type}/entity/")
    public ResponseEntity<?> getHistory(@RequestParam List<Long> entityIds, @PathVariable String type) throws SQLException {
        try{
            Map<String,List<BatchEventEntity>> batchEventEntityListMap = immudbService.getHistoryForEntities(entityIds, type);
            return ResponseEntity.ok(batchEventEntityListMap);
        } catch (CustomException e){
            return ResponseEntity.noContent().build();
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Exception while fetching history of transactions for given entityIds " + entityIds);
        }
    }

    @PostMapping
    public ResponseEntity<?> getHistory(@RequestBody HistoryRequestDto dto){
        try {
            Map<String,List<BatchEventEntity>> historyMap = immudbService.getHistory(dto);
            return ResponseEntity.ok(historyMap);
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body("Exception while fetching history of transactions");
        }
    }
}
