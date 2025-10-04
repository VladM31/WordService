package words.com.wordservice.api.responds;

public record ErrorDetailsRespond(
        String message,
        String exceptionName
) {
}
