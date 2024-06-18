package ru.abolsoft.core.service.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abolsoft.core.entity.ImageMetadata;
import ru.abolsoft.core.repository.ImageMetadataRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ImageMetadataService {

    @Autowired
    private ImageMetadataRepository imageMetadataRepository;

    @Transactional
    public List<ImageMetadata> getAllImages(String sortBy, Long size, LocalDate uploadDate) {
        return getAllImagesByAccount(null, sortBy, size, uploadDate);
    }

    @Transactional
    public List<ImageMetadata> getAllImagesByAccount(Long accountId, String sortBy, Long size, LocalDate uploadDate) {
        Specification<ImageMetadata> spec = Specification.where(null);

        if (accountId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("accountId"), accountId));
        }
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
    public boolean existImageByAccount(String imageName, Long currentAccountId) {
        Optional<ImageMetadata> optionalImageMetadata = imageMetadataRepository.findByName(imageName);
        if (optionalImageMetadata.isEmpty()) return false;
        if (!optionalImageMetadata.get().getAccountId().equals(currentAccountId)) return false;
        return true;
    }

    @Transactional
    public ImageMetadata save(ImageMetadata imageMetadata) {
        return imageMetadataRepository.save(imageMetadata);
    }
}
