package org.hillel.homework.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.Getter;
import lombok.Setter;
import org.hillel.homework.exception.InvalidJSONException;
import org.hillel.homework.model.Pet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShelterService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Setter
    @Getter
    private List<Pet> pets = new ArrayList<>();
    @Setter
    @Getter
    private File storageFile;
    public ShelterService(File storageFile) {
        this.storageFile = storageFile;
    }

    public void loadPets() throws IOException {
        if (storageFile == null) {
            throw new NullPointerException();
        }

        if (storageFile.exists()) {
            CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, Pet.class);
            try {
                pets = objectMapper.readValue(storageFile, type);
            } catch (JsonParseException e) {
                throw new InvalidJSONException(e);
            }
        } else {
            pets = new ArrayList<>();
        }
    }

    public void savePets() throws IOException {
        if (storageFile == null || pets == null) {
            throw new NullPointerException();
        }

        objectMapper.writeValue(storageFile, pets);
    }

    public void remove(int id) {
        if (id < 0 || id >= pets.size()) {
            throw new IndexOutOfBoundsException();
        }
        pets.remove(id);
    }

    public int addPet(String name, String breed, double age) {
        if (name == null || breed == null) {
            throw new NullPointerException();
        }
        pets.add(new Pet(name, breed, age));
        return pets.size() - 1;
    }
}
