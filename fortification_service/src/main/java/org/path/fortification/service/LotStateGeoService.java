package org.path.fortification.service;

import org.path.fortification.dto.requestDto.SearchListRequest;
import org.path.fortification.dto.responseDto.LotStateGeoResponseDto;

import java.util.List;

public interface LotStateGeoService {
    List<LotStateGeoResponseDto> get(SearchListRequest searchListRequest);
    void recompile();
}
