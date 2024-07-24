package org.leafall.mainservice.entity;

import org.leafall.mainservice.entity.aware.AuthorAware;
import org.leafall.mainservice.entity.aware.TimestampAware;
import org.leafall.mainservice.entity.listener.AuthorListener;
import org.leafall.mainservice.entity.listener.HistoryListener;
import org.leafall.mainservice.entity.listener.TimestampListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.util.*;

@Entity
@Table(name = "games")
@Data
@EntityListeners({AuthorListener.class, TimestampListener.class, HistoryListener.class})
public class GameEntity implements AuthorAware, TimestampAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "header_image")
    private UUID headerImage;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ElementCollection(targetClass = UUID.class)
    @CollectionTable(name = "game_images", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "image_id")
    private List<UUID> images = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "game_genres", joinColumns = @JoinColumn(name = "game_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres = new ArrayList<>();

    @Formula("(select v.avg from game_view v where v.id = id)")
    private Float rating;

    @Column(name = "description", nullable = false)
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    private Long updatedAt;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
}
