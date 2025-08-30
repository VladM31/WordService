package words.com.wordservice.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class LearningPlanEntity {
    @Id
    private String userId;
    private int wordsPerDay;
    @Enumerated(EnumType.STRING)
    private Language nativeLang;
    @Enumerated(EnumType.STRING)
    private Language learningLang;
    @Enumerated(EnumType.STRING)
    private CEFR cefr;
    @Column(updatable = false)
    @CreatedDate
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}