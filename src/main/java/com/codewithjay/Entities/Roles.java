package com.codewithjay.Entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Roles {

    @Id
    private String roleId;

    private String roleName;

    @ManyToMany()
    @JsonBackReference
    private Set<User> users = new HashSet<>();

}
