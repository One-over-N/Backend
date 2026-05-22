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

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "imate_url")
    private String imateUrl;

    @OneToMany(mappedBy = "ott")
    private List<OttPlan> ottPlans=new ArrayList<>();
}
