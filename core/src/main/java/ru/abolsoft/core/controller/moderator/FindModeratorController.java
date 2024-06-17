package ru.abolsoft.core.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.abolsoft.core.entity.ImageMetadata;
import ru.abolsoft.core.service.MetadataService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/moderator")
public class FindModeratorController {

    @Autowired
    private MetadataService metadataService;

    @GetMapping("/images")
    public List<ImageMetadata> getImages(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Long size,
            @RequestParam(required = false) LocalDate uploadDate) {
        return metadataService.getAllImages(sortBy, size, uploadDate);
    }
}
