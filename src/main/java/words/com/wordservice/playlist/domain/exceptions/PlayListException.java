package words.com.wordservice.playlist.domain.exceptions;

import words.com.wordservice.shared.exceptions.AppException;

public class PlayListException extends AppException {
    public PlayListException(String message) {
        super(message);
    }
}
