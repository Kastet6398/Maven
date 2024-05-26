package org.hillel.homework;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MaxNumberOfBalloons {
    private static final String WORD = "balloon";

    public static long maxNumberOfBalloons(@NotNull String text) {
	Map<Character, Long> charactersInWord = new HashMap<>();
	Map<Character, Long> charactersInText = new HashMap<>();

	for (char c : WORD.toCharArray()) {
	    charactersInWord.put(c, charactersInWord.getOrDefault(c, 0L) + 1);
	}

	for (char c : text.toCharArray()) {
	    charactersInText.put(c, charactersInText.getOrDefault(c, 0L) + 1);
	}

        long occurrencesOfWord = Long.MAX_VALUE;

        for (Map.Entry<Character, Long> entry : charactersInWord.entrySet()) {
	    if (charactersInText.containsKey(entry.getKey())) {
		long current = charactersInText.get(entry.getKey()) / entry.getValue();

		if (current < occurrencesOfWord) {
		    occurrencesOfWord = current;
		}
	    } else {
		return 0;
	    }
        }
        return occurrencesOfWord;
    }
}
