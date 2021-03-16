package io.spring.pind.dto;

import io.spring.pind.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {

    private Long id;
    private String name;
    private String path;
    private String uuid;

    public static ImageDTO entityToDto(Image image) {
        ImageDTO imageDTO = ImageDTO.builder()
                .id(image.getId())
                .name(image.getName())
                .path(image.getPath())
                .uuid(image.getUuid())
                .build();
        return imageDTO;
    }
}
