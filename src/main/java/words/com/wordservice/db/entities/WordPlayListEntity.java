package words.com.wordservice.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;
import words.com.wordservice.db.converters.CefrTreeSetConverter;
import words.com.wordservice.db.converters.StringTreeSetConverter;
import words.com.wordservice.domain.models.enums.CEFR;
import words.com.wordservice.domain.models.enums.Language;
import words.com.wordservice.domain.models.enums.PlayListVisibility;

import java.time.OffsetDateTime;
import java.util.TreeSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "word_playlists")
public class WordPlayListEntity {
    @Id
    private String id;
    @Column(nullable = false, updatable = false)
    private String userId;
    private String name;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;


    @Convert(converter = StringTreeSetConverter.class)
    @Column(columnDefinition = "TEXT", updatable = false)
    private TreeSet<String> tags;
    @Convert(converter = CefrTreeSetConverter.class)
    @Column(columnDefinition = "TEXT", updatable = false)
    private TreeSet<CEFR> cefrs;

    @Nullable
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Language language;
    @Nullable
    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Language translateLanguage;
    @Column(nullable = false, updatable = false, columnDefinition = "varchar(255) DEFAULT 'PRIVATE'")
    @Enumerated(EnumType.STRING)
    private PlayListVisibility visibility;

    @Nullable
    @Column(updatable = false)
    private String baseId;
    @Nullable
    @Column(updatable = false)
    private String associationId;
}
