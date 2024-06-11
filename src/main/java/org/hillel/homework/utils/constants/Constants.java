package org.hillel.homework.utils.constants;

import lombok.Getter;

@Getter
public enum Constants {
    PET_NAME_PROMPT("Enter pet name: "),
    PET_BREED_PROMPT("Enter pet breed: "),
    PET_AGE_PROMPT("Enter pet age: "),
    PET_ID_PROMPT("Enter pet ID: "),
    INVALID_PET_AGE("Invalid pet age"),
    INVALID_PET_ID("Invalid pet id"),
    MENU("""
Menu:
1. Add pet
2. Show all pets
3. Take pet from shelter
4. Exit"""),
    MENU_OPERATION_PROMPT("Enter operation number: "),
    ERROR_LOADING_PETS("Error loading pets"),
    NO_PETS("No pets in the shelter"),
    PET_PATTERN("%d. %s"),
    FAREWELL("Goodbye!"),
    PET_AGE_NOT_POSITIVE("Please provide a positive pet age"),
    PET_ID_NOT_IN_RANGE("Pet ID is out of limits"),
    ERROR("Unexpected error occurred"),
    NOT_WHOLE_NUMBER("Please enter a valid whole number"),
    MENU_OPERATION_NOT_IN_RANGE("Please enter a value from 1 to 4"),
    ERROR_SAVING_PETS("Error saving pets"),
    NOT_FLOATING_POINT("Please enter a valid floating point number"),
    EXIT_TRIGGER("#"),
    NEW_PET_ID("Pet id: %d");

    private final Object val;

    Constants(Object val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val.toString();
    }
}
