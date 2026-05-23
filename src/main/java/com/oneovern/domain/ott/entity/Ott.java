package com.oneovern.domain.ott.entity;

import com.oneovern.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ott")
public class Ott extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ott_name", nullable = false, length = 100)
    private String ottName;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Builder.Default
    @OneToMany(mappedBy = "ott", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OttPlan> ottPlans=new ArrayList<>();
}
