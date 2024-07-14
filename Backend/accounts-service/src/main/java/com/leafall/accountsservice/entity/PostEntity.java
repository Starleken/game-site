package com.leafall.accountsservice.entity;

import com.leafall.accountsservice.entity.aware.AuthorAware;
import com.leafall.accountsservice.entity.aware.TimestampAware;
import com.leafall.accountsservice.entity.listener.AuthorListener;
import com.leafall.accountsservice.entity.listener.HistoryListener;
import com.leafall.accountsservice.entity.listener.TimestampListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Data
@EntityListeners({AuthorListener.class, TimestampListener.class, HistoryListener.class})
public class PostEntity implements AuthorAware, TimestampAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "header", nullable = false)
    private String header;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "image_id", nullable = true)
    private UUID imageId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    private Long updatedAt;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
}
