package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String email;

    private String phone;

    @Column(columnDefinition = "nvarchar(max)")
    private String fullName;

    private Boolean isAdmin;

    private Boolean isActive;

    private String token;

    @OneToMany(mappedBy = "user")
    private List<Share> shares;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
