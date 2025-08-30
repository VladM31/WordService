package words.com.wordservice.db.entities.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsLearningHistoryEntity {
    private int count;
    private long grades;
    private String type;
    private LocalDate date;
}
