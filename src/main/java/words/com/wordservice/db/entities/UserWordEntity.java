package words.com.wordservice.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_words")
@Entity
public class UserWordEntity {
    @Id
    private String id;
    @Column(nullable = false, updatable = false)
    private String userId;
    private long learningGrade;

    @Nullable
    private String customSoundFileName;
    @Nullable
    private String customImageFileName;

    @CreatedDate
    @Column(updatable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime lastReadDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "word_id", referencedColumnName = "id", nullable = false)
    private WordEntity word;
}
