package com.shawnix.codepadx.entity;

import com.shawnix.codepadx.entity.enums.Visibility;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "codes")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault("Untitled")
    private String title;

    @Lob
    @Column(nullable = false)
    private String sourceCode;

    @Lob
    private String input;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'PUBLIC'")
    private Visibility visibility;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
