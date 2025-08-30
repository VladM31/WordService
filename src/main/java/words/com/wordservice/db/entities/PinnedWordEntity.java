package words.com.wordservice.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pinned_word")
public class PinnedWordEntity {
    @EmbeddedId
    private PinnedWordId id;

    private Long learningGrade;

    private OffsetDateTime createdAt;

    private OffsetDateTime lastReadDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userWordId") // важно! связывает userWordId из ключа с этим relation
    @JoinColumn(name = "user_word_id", referencedColumnName = "id", nullable = false)
    private UserWordEntity word;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class PinnedWordId implements Serializable {
        private String playListId;
        private String userWordId;
    }
}
