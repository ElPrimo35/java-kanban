package responsers;

public class DeleteResponse {
    private final String deleteMessage;

    public DeleteResponse(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public String getErrorMessage() {
        return deleteMessage;
    }
}
