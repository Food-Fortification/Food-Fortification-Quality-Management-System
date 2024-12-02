package org.path.fortification.service.impl;

import org.path.fortification.dao.LotStateGeoDao;
import org.path.fortification.dto.iam.AddressResponseDto;
import org.path.fortification.dto.requestDto.DashboardRequestDto;
import org.path.fortification.dto.responseDto.*;
import org.path.fortification.entity.BatchStateGeo;
import org.path.fortification.entity.LotStateGeo;
import org.path.fortification.entity.State;
import org.path.fortification.entity.StateType;
import org.path.fortification.enums.GeoAggregationType;
import org.path.fortification.enums.GeoManufacturerQuantityResponseType;
import org.path.fortification.manager.*;
import org.path.fortification.helper.Constants;
import org.path.fortification.helper.IamServiceRestHelper;
import org.path.fortification.service.CategoryService;
import org.path.fortification.service.DashboardService;
import org.path.parent.keycloakSecurity.KeycloakInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private KeycloakInfo keycloakInfo;

    @Autowired
    BatchStateGeoManager batchStateGeoManager;
    @Autowired
    CategoryService categoryService;
    @Autowired
    LotStateGeoDao lotStateGeoDao;
    @Autowired
    BatchManager batchManager;
    @Autowired
    StateManager stateManager;
    @Autowired
    LotManager lotManager;
    ObjectMapper objectMapper;
    @Autowired
    CategoryManager categoryManager;

    @Override
    public List<StateGeoDto> getDashboardCountData(Integer year, DashboardRequestDto dto) {
        Long manufacturerId = Long.parseLong(keycloakInfo.getUserInfo().getOrDefault("manufacturerId", 0).toString());
        List<CategoryResponseDto> categoryResponseDto = categoryService.getAllCategoriesForManufacturer(true);
        List<Object[]> batchStateGeos = batchStateGeoManager.findByCategoryIdsAndManufacturerId(
                categoryResponseDto.stream().map(CategoryResponseDto::getId).collect(Collectors.toList()), manufacturerId,
                year == null ? getYearFromDate(new Date()) : year, dto);
        Set<CategoryResponseDto> lotCategories = categoryResponseDto.stream().flatMap(d -> d.getSourceCategories().stream())
                .collect(Collectors.toSet());
        List<Object[]> lotStateGeos = lotStateGeoDao.findByCategoryIdsAndManufacturerId(
                lotCategories.stream().filter(d -> !d.getIndependentBatch()).map(CategoryResponseDto::getId).collect(Collectors.toSet()), manufacturerId);
        List<StateGeoDto> results = new ArrayList<>();

        results.addAll(batchStateGeos.stream().map(d -> {
            StateGeoDto stateGeoDto = new StateGeoDto();
            stateGeoDto.setTotalQuantity((Double) d[1]);
            stateGeoDto.setInProductionQuantity((Double) d[2]);
            stateGeoDto.setProducedQuantity((Double) d[3]);
            stateGeoDto.setInTransitQuantity((Double) d[4]);
            stateGeoDto.setReceivedQuantity((Double) d[5]);
            stateGeoDto.setApprovedQuantity((Double) d[6]);
            stateGeoDto.setRejectedQuantity((Double) d[7]);
            stateGeoDto.setBatchSampleInTransitQuantity((Double) d[8]);
            stateGeoDto.setBatchSampleTestInProgressQuantity((Double) d[9]);
            stateGeoDto.setBatchTestedQuantity((Double) d[10]);
            stateGeoDto.setLotSampleInTransitQuantity((Double) d[11]);
            stateGeoDto.setLotSampleTestInProgressQuantity((Double) d[12]);
            stateGeoDto.setLotTestedQuantity((Double) d[13]);
            stateGeoDto.setLotRejected((Double) d[14]);
            stateGeoDto.setRejectedInTransitQuantity((Double) d[15]);
            stateGeoDto.setReceivedRejectedQuantity((Double) d[16]);
            Optional<CategoryResponseDto> categoryResponseDto1 = categoryResponseDto.stream().filter(d2 -> Objects.equals(d2.getId(), d[0])).findFirst();
            if (categoryResponseDto1.isPresent()) {
                stateGeoDto.setCategoryId(categoryResponseDto1.get().getId());
                stateGeoDto.setCategoryName(categoryResponseDto1.get().getName());
                stateGeoDto.setIsIndependentBatch(categoryResponseDto1.get().getIndependentBatch());
            }

            stateGeoDto.setType("batch");
            return stateGeoDto;
        }).toList());

        results.addAll(lotStateGeos.stream().map(d -> {
            StateGeoDto stateGeoDto = new StateGeoDto();
            stateGeoDto.setInTransitQuantity((Double) d[1]);
            stateGeoDto.setReceivedQuantity((Double) d[2]);
            stateGeoDto.setApprovedQuantity((Double) d[3]);
            stateGeoDto.setRejectedQuantity((Double) d[4]);
            stateGeoDto.setUsedQuantity((Double) d[5]);
            stateGeoDto.setSampleInTransitQuantity((Double) d[6]);
            stateGeoDto.setTestInProgressQuantity((Double) d[7]);
            stateGeoDto.setTestedQuantity((Double) d[8]);
            Optional<CategoryResponseDto> categoryResponseDto1 = lotCategories.stream().filter(d2 -> Objects.equals(d2.getId(), d[0])).findFirst();
            if (categoryResponseDto1.isPresent()) {
                stateGeoDto.setCategoryId(categoryResponseDto1.get().getId());
                stateGeoDto.setCategoryName(categoryResponseDto1.get().getName());
                stateGeoDto.setIsIndependentBatch(categoryResponseDto1.get().getIndependentBatch());
            }
            stateGeoDto.setType("lot");
            return stateGeoDto;
        }).toList());
        lotCategories.stream().filter(CategoryResponseDto::getIndependentBatch)
                .forEach(d -> {
                    StateGeoDto stateGeoDto = new StateGeoDto();
                    stateGeoDto.setCategoryName(d.getName());
                    stateGeoDto.setCategoryId(d.getId());
                    stateGeoDto.setIsIndependentBatch(true);
                    stateGeoDto.setType("lot");
                    Double[] resp = lotManager.getQuantitySum(manufacturerId, d.getId());
                    stateGeoDto.setTotalQuantity(resp[0]);
                    stateGeoDto.setRemainingQuantity(resp[1]);
                    results.add(stateGeoDto);
                });

        return results;


    }

    @Override
    public ListResponse<?> getManufacturersQuantities(GeoManufacturerQuantityResponseType responseType, Long categoryId,
                                                      GeoAggregationType type, String geoId, String search, Integer year,
                                                      Integer pageNumber, Integer pageSize, DashboardRequestDto dashboardRequestDto) {
        String filterBy = switch (type) {
            case district -> "districtGeoId";
            case state -> "stateGeoId";
            case country -> "countryGeoId";
        };
        String orderBy = switch (responseType) {
            case testing -> "lotTestedQuantity";
            case production -> "producedQuantity";
        };
        Map<Long, ManufacturerListDashboardResponseDto> manufacturers = IamServiceRestHelper.getManufacturersWithGeoFilter(categoryId, type, geoId, search, null, null, keycloakInfo.getAccessToken());
        Set<Long> mids = manufacturers.keySet();
        List<BatchStateGeo> results = batchStateGeoManager.findManufacturerQuantityByCategoryIdAndState(categoryId, filterBy, geoId,
                orderBy, mids, year == null ? getYearFromDate(new Date()) : year, pageNumber, pageSize, dashboardRequestDto);
        Long resultsCount = batchStateGeoManager.findManufacturerQuantityByCategoryIdAndStateCount(results.size(),
                categoryId, filterBy, geoId, year == null ? getYearFromDate(new Date()) : year, pageNumber, pageSize, dashboardRequestDto);
        return ListResponse.from(results, d -> {
            ManufacturerListDashboardResponseDto manufacturer = manufacturers.get(d.getGeoStateId().getManufacturerId());
            return switch (responseType) {

                case testing -> {
                    ManufacturerTestingQuantityResponseDto dto = new ManufacturerTestingQuantityResponseDto();
                    BeanUtils.copyProperties(d, dto);
                    if (manufacturer != null) BeanUtils.copyProperties(manufacturer, dto,
                            "approvedQuantity", "rejectedQuantity");
                    dto.setTestedQuantity(d.getBatchTestedQuantity());
                    dto.setUnderTestingQuantity(d.getBatchSampleTestInProgressQuantity() + d.getLotSampleTestInProgressQuantity());
                    yield dto;
                }
                case production -> {
                    ManufacturerProductionQuantityResponseDto dto = new ManufacturerProductionQuantityResponseDto();
                    BeanUtils.copyProperties(d, dto);
                    if (manufacturer != null) BeanUtils.copyProperties(manufacturer, dto,
                            "producedQuantity", "inProductionQuantity");
                    yield dto;
                }
            };
        }, resultsCount);
    }

    @Override
    public void recompileGeoData(StateType stateType) {
        boolean flag = true;
        int pageNumber = 1, pageSize = 30;
        AtomicReference<AddressResponseDto> address = new AtomicReference<>(null);
        AtomicReference<Long> currentManufacturerId = new AtomicReference<>(null);
        while (flag) {
            try {
                List<State> states = stateManager.findAll(null, null);
                List objects = switch (stateType) {
                    case BATCH -> batchManager
                            .findAllAggregateByManufacturerIdAndCategoryId(pageNumber, pageSize, states)
                            .stream().filter(d -> {
                                try {
                                    BatchStateGeo previousBatchStateGeo = batchStateGeoManager.findByGeoStateId(d.getGeoStateId());
                                    return this.checkIfQuantitiesAreEqual(previousBatchStateGeo, d);
                                } catch (Exception ignored) {
                                }
                                return true;
                            }).toList();
                    case LOT -> lotManager
                            .findAllAggregateByManufacturerIdAndCategoryId(pageNumber, pageSize, states.stream().filter(d -> d.getType().equals(StateType.LOT)).toList())
                            .stream()
                            .filter(d -> {
                                try {
                                    LotStateGeo previousLotStateGeo = lotStateGeoDao.findByCategoryIdAndManufacturerId(d.getGeoStateId());
                                    return this.checkIfQuantitiesAreEqual(previousLotStateGeo, d);
                                } catch (Exception ignored) {
                                }
                                return true;
                            }).toList();
                };
                objects.forEach(data -> {
                    if (data instanceof BatchStateGeo d) {
                        if (!Objects.equals(currentManufacturerId.get(), d.getGeoStateId().getManufacturerId())) {
                            currentManufacturerId.set(d.getGeoStateId().getManufacturerId());
                            address.set(IamServiceRestHelper.getManufacturerAddress(currentManufacturerId.get(), keycloakInfo.getAccessToken()));
                        }
                        if (address.get() != null && address.get().getVillage() != null && address.get().getVillage().getDistrict() != null) {
                            d.setDistrictGeoId(address.get().getVillage().getDistrict().getGeoId());
                            if (address.get().getVillage().getDistrict().getState() != null) {
                                d.setStateGeoId(address.get().getVillage().getDistrict().getState().getGeoId());
                                if (address.get().getVillage().getDistrict().getState().getCountry() != null) {
                                    d.setCountryGeoId(address.get().getVillage().getDistrict().getState().getCountry().getGeoId());
                                }
                            }
                        }
                        batchStateGeoManager.update(d);
                    } else if (data instanceof LotStateGeo d) {
                        if (!Objects.equals(currentManufacturerId.get(), d.getGeoStateId().getManufacturerId())) {
                            currentManufacturerId.set(d.getGeoStateId().getManufacturerId());
                            address.set(IamServiceRestHelper.getManufacturerAddress(currentManufacturerId.get(), keycloakInfo.getAccessToken()));
                        }
                        if (address.get() != null && address.get().getVillage() != null && address.get().getVillage().getDistrict() != null) {
                            d.setDistrictGeoId(address.get().getVillage().getDistrict().getGeoId());
                            if (address.get().getVillage().getDistrict().getState() != null) {
                                d.setStateGeoId(address.get().getVillage().getDistrict().getState().getGeoId());
                                if (address.get().getVillage().getDistrict().getState().getCountry() != null) {
                                    d.setCountryGeoId(address.get().getVillage().getDistrict().getState().getCountry().getGeoId());
                                }
                            }
                        }
                        lotStateGeoDao.update(d);
                    }
                });
                if (objects.size() < pageSize) flag = false;
                pageNumber++;
            } catch (Exception exception) {
                log.info("Recompile failure at page {}/{}: {}", pageNumber, pageSize, exception.getMessage());
                break;
            }
        }
    }

    private boolean checkIfQuantitiesAreEqual(Object obj1, Object obj2) {
        return Arrays.stream(obj1.getClass().getDeclaredFields()).filter(field -> field.getType().equals(Double.class))
                .anyMatch(field -> {
                    try {
                        field.setAccessible(true);
                        if (!Objects.equals(field.get(obj1), field.get(obj2))) {
                            return true;
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return false;
                });
    }

    private List<Long> getTestManufacturerIds() {
        String url = Constants.IAM_SERVICE_URL + "/manufacturer/test-manufacturers";
        List<Integer> testManufacturerIds = (List<Integer>) IamServiceRestHelper.fetchResponse(url, List.class, keycloakInfo.getAccessToken());
        if (testManufacturerIds == null) return new ArrayList<>();
        return testManufacturerIds.stream().mapToLong(Integer::longValue).boxed().toList();
    }

    public Integer getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}

