package io.spring.pind.controller;

import io.spring.pind.dto.RegionDTO;
import io.spring.pind.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> getRegionList(RegionDTO regionDTO){
        List<RegionDTO> regionList = regionService.getRegionList(regionDTO);
        return new ResponseEntity<>(regionList, HttpStatus.OK);
    }

    @GetMapping("/{region_id}")
    public ResponseEntity<RegionDTO> getRegion(@PathVariable("region_id") Long regionId){
        RegionDTO regionDTO = regionService.getRegion(regionId);
        return new ResponseEntity<>(regionDTO, HttpStatus.OK);
    }
}
