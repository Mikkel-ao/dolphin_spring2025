package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String note;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private String createdBy;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person person;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
