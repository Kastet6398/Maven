package org.hillel.homework.utils.constants;

import lombok.Getter;
import org.hillel.homework.model.Pet;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public enum TestConstants {
    INVALID_JSON_FILE("invalidJSON.json"),
    INVALID_JSON("invalid JSON"),
    VALID_JSON_FILE("validJSON.json"),
    VALID_JSON("[{\"name\":\"name\",\"breed\":\"breed\",\"age\":9}]"),
    VALID_JSON_DESERIALIZED(new ArrayList<>(Arrays.asList(new Pet("name", "breed", 9)))),
    DOES_NOT_EXIST("doesNotExist.json"),
    WRITTEN_JSON_DESERIALIZED(new ArrayList<>(Arrays.asList(new Pet("bobik", "labrador", 5.83), new Pet("iRanOutOfIdeas", "myBreed", 3.9)))),
    WRITTEN_JSON_SERIALIZED("[{\"name\":\"bobik\",\"breed\":\"labrador\",\"age\":5.83},{\"name\":\"iRanOutOfIdeas\",\"breed\":\"myBreed\",\"age\":3.9}]");

    private final Object val;

    TestConstants(Object val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val.toString();
    }
}
