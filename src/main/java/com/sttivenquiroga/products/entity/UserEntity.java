package com.sttivenquiroga.products.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "users")
public class UserEntity {
    private final String role_id = "role_id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String firstname;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String lastname;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = CascadeType.PERSIST)
    @Column(nullable = false)
    private Set<Role> roles;
}
