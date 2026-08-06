package com.estudioveneto.landing_api.content.dto;

public record PhotoDTO(Long id, String url, String caption, Integer displayOrder) {
    public static PhotoDTO from(com.estudioveneto.landing_api.content.entity.Photo entity) {
        return new PhotoDTO(entity.getId(), entity.getUrl(), entity.getCaption(), entity.getDisplayOrder());
    }
}
