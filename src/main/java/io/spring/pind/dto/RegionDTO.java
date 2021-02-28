package io.spring.pind.dto;

import io.spring.pind.entity.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {

    private Long id;
    private String region1;
    private String region2;
    private String region3;

    public static RegionDTO entityToDto(Region region){
        RegionDTO regionDTO = RegionDTO.builder()
                .id(region.getId())
                .region1(region.getRegionDepth1())
                .region2(region.getRegionDepth2())
                .region3(region.getRegionDepth3())
                .build();
        return regionDTO;
    }
}
