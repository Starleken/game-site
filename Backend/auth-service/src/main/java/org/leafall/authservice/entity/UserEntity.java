package org.leafall.authservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.leafall.authservice.entity.aware.AuthorAware;
import org.leafall.authservice.entity.aware.TimestampAware;
import org.leafall.authservice.entity.listener.AuthorListener;
import org.leafall.authservice.entity.listener.HistoryListener;
import org.leafall.authservice.entity.listener.TimestampListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@EntityListeners({AuthorListener.class, TimestampListener.class, HistoryListener.class})
public class UserEntity implements AuthorAware, TimestampAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "username", nullable = false)
    private String username;

    @ToString.Exclude
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<TokenEntity> tokens = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserEntityRole role;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_at", nullable = true)
    private Long updatedAt;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
}
