package com.estudioveneto.landing_api.content;

import com.estudioveneto.landing_api.content.dto.ContentDTO;
import com.estudioveneto.landing_api.content.dto.HighlightDTO;
import com.estudioveneto.landing_api.content.dto.PhotoDTO;
import com.estudioveneto.landing_api.content.dto.SocialLinkDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final PhotoRepository photoRepository;
    private final SocialLinkRepository socialLinkRepository;
    private final HighlightRepository highlightRepository;

    public ContentService(ContentRepository contentRepository,
                          PhotoRepository photoRepository,
                          SocialLinkRepository socialLinkRepository,
                          HighlightRepository highlightRepository) {
        this.contentRepository = contentRepository;
        this.photoRepository = photoRepository;
        this.socialLinkRepository = socialLinkRepository;
        this.highlightRepository = highlightRepository;
    }

    public List<ContentDTO> getAllContent() {
        return contentRepository.findAll().stream()
                .map(ContentDTO::from)
                .toList();
    }

    public List<PhotoDTO> getAllPhotos() {
        return photoRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(PhotoDTO::from)
                .toList();
    }

    public SocialLinkDTO getSocialLinks() {
        return socialLinkRepository.findAll().stream()
                .findFirst()
                .map(SocialLinkDTO::from)
                .orElse(null);
    }

    public List<HighlightDTO> getHighlightsByCategory(String category) {
        return highlightRepository.findByCategoryOrderByDisplayOrderAsc(category).stream()
                .map(HighlightDTO::from)
                .toList();
    }
}