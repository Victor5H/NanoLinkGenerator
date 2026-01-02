package com.harshit.NanoLinkGenerator.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "url_mapping")
public class UrlMapping {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "long_url", nullable = false)
    private String longUrl;
    @Column(name = "short_url",nullable = false,unique = true)
    private String shortUrl;
}
