package ru.abolsoft.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abolsoft.core.entity.ImageMetadata;

@Repository
public interface ImageMetadataRepository
        extends JpaRepository<ImageMetadata, Long>, JpaSpecificationExecutor<ImageMetadata> {
}
