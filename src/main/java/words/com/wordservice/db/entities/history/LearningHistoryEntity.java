package words.com.wordservice.db.entities.history;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import words.com.wordservice.domain.models.enums.LearningHistoryType;

import java.time.LocalDate;
import java.time.OffsetTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "learning_history")
public class LearningHistoryEntity {
    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    @Column(nullable = false, updatable = false)
    private String userId;
    @Column(nullable = false, updatable = false)
    private String wordId;
    private LocalDate date;
    private OffsetTime time;
    @Enumerated(EnumType.STRING)
    private LearningHistoryType type;
    private int grade;
}
