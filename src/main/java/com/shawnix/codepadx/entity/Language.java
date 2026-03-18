package com.shawnix.codepadx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "languages")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String code;
    @NotNull
    private String name;
    @NotNull
    private String fileExtension;
    @Column(columnDefinition = "TEXT")
    private String exampleCode;
}
