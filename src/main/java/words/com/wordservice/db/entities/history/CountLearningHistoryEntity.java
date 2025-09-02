package words.com.wordservice.db.entities.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import words.com.wordservice.domain.models.enums.LearningHistoryType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountLearningHistoryEntity {
    private int count;
    private LearningHistoryType type;
}
