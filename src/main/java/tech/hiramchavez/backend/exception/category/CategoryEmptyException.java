package tech.hiramchavez.backend.exception.category;

public class CategoryEmptyException extends RuntimeException {
    public CategoryEmptyException() {
        super();
    }

    public CategoryEmptyException(String message) {
        super(message);
    }

    public CategoryEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryEmptyException(Throwable cause) {
        super(cause);
    }

    protected CategoryEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
