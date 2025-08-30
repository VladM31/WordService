package words.com.wordservice.db.actions;

public record UpdatePlayListGradeAction(
        String wordId,
        String userId,
        long playListGrade,
        long wordGrade
) {
}
