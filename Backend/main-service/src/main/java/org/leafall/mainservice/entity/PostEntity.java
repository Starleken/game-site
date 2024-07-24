package org.leafall.mainservice.entity;

import org.leafall.mainservice.entity.aware.AuthorAware;
import org.leafall.mainservice.entity.aware.TimestampAware;
import org.leafall.mainservice.entity.listener.AuthorListener;
import org.leafall.mainservice.entity.listener.HistoryListener;
import org.leafall.mainservice.entity.listener.TimestampListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
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
