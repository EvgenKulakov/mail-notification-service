package ru.abolsoft.core.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abolsoft.core.cloud.entity.ImageMetadata;
import ru.abolsoft.core.cloud.repository.ImageMetadataRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MetadataService {

    @Autowired
    private ImageMetadataRepository imageMetadataRepository;

    @Transactional
    public List<ImageMetadata> getImages(String sortBy, Long size, LocalDate uploadDate) {
        Specification<ImageMetadata> spec = Specification.where(null);

        if (size != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("size"), size));
        }
        if (uploadDate != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("uploadDate"), uploadDate));
        }

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy != null ? sortBy : "name");
        return imageMetadataRepository.findAll(spec, sort);
    }

    @Transactional
    public ImageMetadata save(ImageMetadata imageMetadata) {
        return imageMetadataRepository.save(imageMetadata);
    }
}
