package com.filmweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
@Table(name = "users")
public class User extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

//    @Column(unique = true)
    private String email;

//    @Column(unique = true)
    private String phone;

    @Column(name = "full_name",columnDefinition = "nvarchar(50)")
    private String fullName;

    @Column(name = "is_admin")
    private Boolean isAdmin = Boolean.FALSE;

    @Column(name = "is_active")
    private Boolean isActive = Boolean.FALSE;

    private String image;

    @OneToMany(mappedBy = "user")
    private List<Share> shares;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
