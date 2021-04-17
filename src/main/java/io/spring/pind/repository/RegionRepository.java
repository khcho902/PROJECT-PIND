package io.spring.pind.repository;

import io.spring.pind.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query("SELECT r FROM Region r" +
            " WHERE r.regionDepth2 IS NULL " +
            " AND r.regionDepth3 IS NULL")
    List<Region> getDepthOneRegionList();

    @Query("SELECT r FROM Region r" +
            " WHERE r.regionDepth1 = :regionDepth1" +
            " AND r.regionDepth2 IS NOT NULL" +
            " AND r.regionDepth3 IS NULL")
    List<Region> getDepthTwoRegionList(@Param("regionDepth1") String regionDepth1);

    @Query("SELECT r FROM Region r" +
            " WHERE r.regionDepth1 = :regionDepth1" +
            " AND r.regionDepth2 = :regionDepth2" +
            " AND r.regionDepth3 IS NOT NULL")
    List<Region> getDepthThreeRegionList(@Param("regionDepth1") String regionDepth1, @Param("regionDepth2") String regionDepth2);

}
