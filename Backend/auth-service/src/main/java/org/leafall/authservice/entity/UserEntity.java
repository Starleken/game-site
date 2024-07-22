package org.leafall.authservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.leafall.authservice.entity.aware.AuthorAware;
import org.leafall.authservice.entity.aware.TimestampAware;
import org.leafall.authservice.entity.listener.AuthorListener;
import org.leafall.authservice.entity.listener.TimestampListener;

@Entity
@Table(name = "users")
@Data
@EntityListeners({AuthorListener.class, TimestampListener.class})
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
