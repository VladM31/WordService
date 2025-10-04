package words.com.wordservice.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.WordType;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "words")
public class WordEntity {
    @Id
    private String id;
    private String original;
    @Enumerated(EnumType.STRING)
    private Language lang;
    private String translate;
    @Enumerated(EnumType.STRING)
    private Language translateLang;
    @Nullable
    private String category;
    @Nullable
    private String soundFileName;
    @Nullable
    private String imageFileName;
    @Enumerated(EnumType.STRING)
    private WordType type;
    @Enumerated(EnumType.STRING)
    private CEFR cefr;
    @Nullable
    @Column(length = 2000)
    private String description;
    @CreatedDate
    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
    private List<UserWordEntity> userWords;
}
