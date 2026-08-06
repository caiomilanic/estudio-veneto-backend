package com.estudioveneto.landing_api.content.dto;

public record ContentDTO(String sectionKey, String text) {
    public static ContentDTO from(com.estudioveneto.landing_api.content.entity.SiteContent entity) {
        return new ContentDTO(entity.getSectionKey(), entity.getText());
    }
}
