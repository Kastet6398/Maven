package org.hillel.homework.service;

import org.hillel.homework.model.Pet;
import org.hillel.homework.utils.TestUtils;
import org.hillel.homework.exception.InvalidJSONException;
import org.hillel.homework.utils.constants.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShelterServiceTest {
    private static final String FILE_NAME = "testPetsForService.json";
    private static ShelterService service;
    private final File writingFile = TestUtils.getResourceFile(FILE_NAME);

    @BeforeEach
    void loadPets() {
        service = new ShelterService(writingFile);
    }

    @Test
    void loadPets_fileNotExists_emptyList() {
        service.setStorageFile(TestUtils.getResourceFile((String) TestConstants.DOES_NOT_EXIST.getVal()));
        assertDoesNotThrow(() -> service.loadPets());
        assertEquals(List.of(), service.getPets());
    }

    @Test
    void loadPets_invalidJSON_InvalidJSONExceptionThrown() throws IOException {
        File resourceFile = TestUtils.getResourceFile((String) TestConstants.INVALID_JSON_FILE.getVal());
        Files.write(resourceFile.toPath(), ((String) TestConstants.INVALID_JSON.getVal()).getBytes());
        service.setStorageFile(resourceFile);
        assertThrows(InvalidJSONException.class, () -> service.loadPets());
    }

    @Test
    void loadPets_validJSONFile_petsLoaded() throws IOException {
        File resourceFile = TestUtils.getResourceFile((String) TestConstants.VALID_JSON_FILE.getVal());
        Files.write(resourceFile.toPath(), ((String) TestConstants.VALID_JSON.getVal()).getBytes());
        service.setStorageFile(resourceFile);
        assertDoesNotThrow(service::loadPets);
        assertEquals(TestConstants.VALID_JSON_DESERIALIZED.getVal(), service.getPets());
    }

    @Test
    void loadPets_nullFile_NullPointerExceptionThrown() {
        service.setStorageFile(null);
        assertThrows(NullPointerException.class, service::loadPets);
    }

    @Test
    @SuppressWarnings("unchecked")
    void savePets_validPetList_written() throws IOException {
        service.setStorageFile(writingFile);
        service.setPets(new ArrayList<>((List<Pet>) TestConstants.WRITTEN_JSON_DESERIALIZED.getVal()));
        assertDoesNotThrow(service::savePets);
        assertEquals(TestConstants.WRITTEN_JSON_SERIALIZED.getVal(), Files.readString(writingFile.toPath()));
    }

    @Test
    @SuppressWarnings("unchecked")
    void savePets_nullPetList_NullPointerExceptionThrown() {
        service.setPets(null);
        assertThrows(NullPointerException.class, service::savePets);
    }

    @Test
    @SuppressWarnings("unchecked")
    void savePets_nullPetFile_NullPointerExceptionThrown() {
        service.setStorageFile(null);
        assertThrows(NullPointerException.class, service::savePets);
    }

    @SuppressWarnings("unchecked")
    @Test
    void remove_validID_petRemoved() {
        service.setPets(new ArrayList<>((List<Pet>) TestConstants.WRITTEN_JSON_DESERIALIZED.getVal()));
        service.remove(0);
        assertEquals(List.of(new Pet("iRanOutOfIdeas", "myBreed", 3.9)), service.getPets());
    }

    @SuppressWarnings("unchecked")
    @Test
    void remove_invalidID_IndexOutOfBoundsExceptionThrown() {
        assertThrows(IndexOutOfBoundsException.class, () -> service.remove(3));
    }

    @Test
    void addPet_validPet_petAdded() {
        service.addPet("bobik", "labrador", 5.83);
        assertEquals(List.of(new Pet("bobik", "labrador", 5.83)), service.getPets());
    }

    @Test
    void addPet_nullName_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> service.addPet(null, "a", 4));
    }

    @Test
    void addPet_nullBreed_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> service.addPet("name", null, 4));
    }
}