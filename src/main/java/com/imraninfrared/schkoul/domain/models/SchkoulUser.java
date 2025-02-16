package com.imraninfrared.schkoul.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class SchkoulUser {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;
}
