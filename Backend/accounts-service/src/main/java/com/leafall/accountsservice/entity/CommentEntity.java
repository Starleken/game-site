package com.leafall.accountsservice.entity;

import com.leafall.accountsservice.entity.aware.AuthorAware;
import com.leafall.accountsservice.entity.aware.TimestampAware;
import com.leafall.accountsservice.entity.listener.AuthorListener;
import com.leafall.accountsservice.entity.listener.HistoryListener;
import com.leafall.accountsservice.entity.listener.TimestampListener;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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
