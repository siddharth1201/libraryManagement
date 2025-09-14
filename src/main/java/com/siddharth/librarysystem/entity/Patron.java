package main.java.com.siddharth.librarysystem.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Patron {
    private String id;
    private String name;
    private String email;
    private final LocalDateTime createdAt; // Should be final, set once
    private LocalDateTime updatedAt;

    /**
     * Constructor for creating a new Patron.
     */
    public Patron(String name, String email) {
        this.id = UUID.randomUUID().toString(); // Assign a unique ID
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // --- Getters ---

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // --- Setters ---

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now(); // Update timestamp on change
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now(); // Update timestamp on change
    }
    
    // --- Overridden Methods ---

    @Override
    public String toString() {
        return "Patron{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patron patron = (Patron) o;
        // Patrons are unique by their ID
        return Objects.equals(id, patron.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}