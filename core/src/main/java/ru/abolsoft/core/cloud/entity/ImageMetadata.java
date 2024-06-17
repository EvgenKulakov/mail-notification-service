package ru.abolsoft.core.cloud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "image_metadata")
public class ImageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "upload_date", nullable = false)
    private LocalDate uploadDate;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "account_id", nullable = false)
    private Long accountId;
}
