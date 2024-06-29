package com.leafall.accountsservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "files")
@Data
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;
}
