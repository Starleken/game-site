package org.leafall.mainservice.entity;

import org.leafall.mainservice.entity.aware.AuthorAware;
import org.leafall.mainservice.entity.aware.TimestampAware;
import org.leafall.mainservice.entity.listener.AuthorListener;
import org.leafall.mainservice.entity.listener.HistoryListener;
import org.leafall.mainservice.entity.listener.TimestampListener;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
@EntityListeners({AuthorListener.class, TimestampListener.class, HistoryListener.class})
public class CommentEntity implements AuthorAware, TimestampAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    private Long updatedAt;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
}
