package io.spring.pind.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Region {

    @Id
    @Column(name = "region_id")
    private Long id;

    private String regionDepth1;

    private String regionDepth2;

    private String regionDepth3;

}
