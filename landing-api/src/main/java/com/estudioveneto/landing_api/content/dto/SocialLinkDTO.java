package com.estudioveneto.landing_api.content.dto;

public record SocialLinkDTO(String instagramUrl, String whatsappNumber, String whatsappMessage) {
    public static SocialLinkDTO from(com.estudioveneto.landing_api.content.entity.SocialLink entity) {
        return new SocialLinkDTO(entity.getInstagramUrl(), entity.getWhatsappNumber(), entity.getWhatsappMessage());
    }
}
