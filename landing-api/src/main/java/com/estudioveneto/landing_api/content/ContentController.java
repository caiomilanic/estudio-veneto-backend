package com.estudioveneto.landing_api.content;

import com.estudioveneto.landing_api.content.dto.ContentDTO;
import com.estudioveneto.landing_api.content.dto.HighlightDTO;
import com.estudioveneto.landing_api.content.dto.PhotoDTO;
import com.estudioveneto.landing_api.content.dto.SocialLinkDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/content")
    public List<ContentDTO> getContent() {
        return contentService.getAllContent();
    }

    @GetMapping("/photos")
    public List<PhotoDTO> getPhotos() {
        return contentService.getAllPhotos();
    }

    @GetMapping("/social-links")
    public SocialLinkDTO getSocialLinks() {
        return contentService.getSocialLinks();
    }

    @GetMapping("/highlights")
    public List<HighlightDTO> getHighlights(@RequestParam String category) {
        return contentService.getHighlightsByCategory(category);
    }
}