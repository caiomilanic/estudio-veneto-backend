package com.estudioveneto.landing_api.content.dto;

public record HighlightDTO(String category, String text) {
    public static HighlightDTO from(com.estudioveneto.landing_api.content.entity.Highlight entity) {
        return new HighlightDTO(entity.getCategory(), entity.getText());
    }
}