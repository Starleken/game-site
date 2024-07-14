package com.example.fileservice.entity;

import com.example.fileservice.entity.aware.AuthorAware;
import com.example.fileservice.entity.aware.TimestampAware;
import com.example.fileservice.entity.listener.AuthorListener;
import com.example.fileservice.entity.listener.HistoryListener;
import com.example.fileservice.entity.listener.TimestampListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
@EntityListeners({HistoryListener.class, AuthorListener.class, TimestampListener.class})
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
