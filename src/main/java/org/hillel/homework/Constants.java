package org.hillel.homework;

public final class Constants {
    public static final int PORT = 8081;
    public static final boolean AUTO_FLUSH = true;
    public static final String SERVER_STARTED_MESSAGE = "Server started. Waiting for the client...";
    public static final String CLIENT_CONNECTED_MESSAGE = "Client connected.";
    public static final String GREETING = "Привіт!";
    public static final String DISPLAY_CLIENT_GREETING_TEMPLATE = "Client's greeting: %s\n";
    public static final String CONTAINS_RUSSIAN_LETTERS_REGEX = "(?s).*[ёъыЁЫЪ].*";
    public static final String PALIANYTSIA_MEANING_QUESTION = "Що таке паляниця?";
    public static final String PALIANYTSIA_MEANING = "хліб";
    public static final String DATETIME_MESSAGE_TEMPLATE = "Поточна дата та час: %s\n";
    public static final String SENT_DATETIME_MESSAGE = "Sent date and time to the client.";
    public static final String FAREWELL = "До побачення!";
    public static final String INCORRECT_PALYANYTSYA_MEANING_ANSWER = "Client gave incorrect answer. Disconnecting...";
    public static final String CLIENT_DISCONNECTED_MESSAGE = "Client disconnected.";

    private Constants() {
	throw new UnsupportedOperationException("Utility class is not instantiated");
    }
}
