package io.spring.pind.service;

import io.spring.pind.dto.RegionDTO;
import io.spring.pind.entity.Region;
import io.spring.pind.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService{

    private final RegionRepository regionRepository;

    @Override
    public List<RegionDTO> getRegionList(RegionDTO regionDTO) {

        List<Region> result;
        if (regionDTO.getRegion1() == null){
            result = regionRepository.getDepthOneRegionList();
        }else if (regionDTO.getRegion2() == null){
            result = regionRepository.getDepthTwoRegionList(regionDTO.getRegion1());
        }else {
            result = regionRepository.getDepthThreeRegionList(regionDTO.getRegion1(), regionDTO.getRegion2());
        }

        List<RegionDTO> regionDTOList = new ArrayList<>();
        result.forEach(res -> {
            regionDTOList.add(RegionDTO.entityToDto(res));
        });

        return regionDTOList;
    }

    @Override
    public RegionDTO getRegion(Long regionId) {
        Region result = regionRepository.findById(regionId).get();
        return RegionDTO.entityToDto(result);
    }
}
