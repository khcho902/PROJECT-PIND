package io.spring.pind.service;

import io.spring.pind.dto.RegionDTO;

import java.util.List;

public interface RegionService {

    List<RegionDTO> getRegionList(RegionDTO regionDTO);

    RegionDTO getRegion(Long regionId);

}
