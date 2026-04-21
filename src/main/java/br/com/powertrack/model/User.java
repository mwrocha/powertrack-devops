package br.com.powertrack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document(collection = "users")
public class User {

    @Id
    private String id; // Mongo ObjectId

    @Indexed(unique = true)
    private String username;

    private String password;

    // Ex.: "ROLE_USER" / "ROLE_ADMIN"
    private String role;

    // opcional (ajuda no trabalho e auditoria)
    private OffsetDateTime createdAt;

    // Getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}