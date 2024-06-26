package com.leafall.accountsservice.entity;

import com.leafall.accountsservice.entity.aware.AuthorAware;
import com.leafall.accountsservice.entity.aware.TimestampAware;
import com.leafall.accountsservice.entity.listener.AuthorListener;
import com.leafall.accountsservice.entity.listener.TimestampListener;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "files")
@Data
@EntityListeners({AuthorListener.class, TimestampListener.class})
public class FileEntity implements AuthorAware, TimestampAware {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    private Long updatedAt;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
}
