package words.com.wordservice.db.entities.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountLearningHistoryEntity {
    private int count;
    private String type;
}
