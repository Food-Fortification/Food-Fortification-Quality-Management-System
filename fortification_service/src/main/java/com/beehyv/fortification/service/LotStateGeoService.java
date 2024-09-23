package com.beehyv.fortification.service;

import com.beehyv.fortification.dto.requestDto.SearchListRequest;
import com.beehyv.fortification.dto.responseDto.LotStateGeoResponseDto;

import java.util.List;

public interface LotStateGeoService {
    List<LotStateGeoResponseDto> get(SearchListRequest searchListRequest);
    void recompile();
}
