package org.hillel.homework.app;

import org.hillel.homework.exception.InvalidJSONException;
import org.hillel.homework.service.ShelterService;
import org.hillel.homework.utils.Validator;
import org.hillel.homework.model.Pet;
import org.hillel.homework.utils.constants.Constants;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ShelterApp {
    private final Scanner scanner = new Scanner(System.in);
    private final ShelterService service;

    public ShelterApp(ShelterService service) throws IOException {
        this.service = service;
    }

    public int menu() {
        display(Constants.MENU);

        int res;
        while (true) {
            display(Constants.MENU_OPERATION_PROMPT, false);
            String strRes = input();
            if (Validator.stringNotEmpty(strRes)) {
                if (Validator.stringNotSpaces(strRes)) {
                    if (Validator.isWholeNumber(strRes)) {
                        if (Validator.wholeNumberRange("1", "4", strRes)) {
                            res = Integer.parseInt(strRes);
                            break;
                        } else {
                            display(Constants.MENU_OPERATION_NOT_IN_RANGE);
                        }
                    } else {
                        display(Constants.NOT_WHOLE_NUMBER);
                    }
                }
            }
        }

        return res;
    }

    public void addPet() {
        String name;
        while (true) {
            display(Constants.PET_NAME_PROMPT, false);
            name = input();
            if (Validator.stringNotEmpty(name)) {
                if (Validator.stringNotSpaces(name)) {
                    if (name.equals(Constants.EXIT_TRIGGER.getVal())) {
                        return;
                    }
                    break;
                }
            }
        }

        String breed;
        while (true) {
            display(Constants.PET_BREED_PROMPT, false);
            breed = input();
            if (Validator.stringNotEmpty(breed)) {
                if (Validator.stringNotSpaces(breed)) {
                    if (breed.equals(Constants.EXIT_TRIGGER.getVal())) {
                        return;
                    }
                    break;
                }
            }
        }
        double age;
        while (true) {
            display(Constants.PET_AGE_PROMPT, false);
            String strRes = input();
            if (Validator.stringNotEmpty(strRes)) {
                if (Validator.stringNotSpaces(strRes)) {
                    if (strRes.equals(Constants.EXIT_TRIGGER.getVal())) {
                        return;
                    }
                    if (Validator.isFloatingPointNumber(strRes)) {
                        if (Validator.floatingPointRange("0.000000001", strRes)) {
                            age = Double.parseDouble(strRes);
                            break;
                        } else {
                            display(Constants.PET_AGE_NOT_POSITIVE);
                        }
                    } else {
                        display(Constants.NOT_FLOATING_POINT);
                    }
                }
            }
        }

        int id = service.addPet(name, breed, age);
        display(String.format((String) Constants.NEW_PET_ID.getVal(), id));
    }

    public void listPets() {
        List<Pet> pets = service.getPets();
        if (pets.isEmpty()) {
            display(Constants.NO_PETS);
        } else {
            for (int i = 0; i < pets.size(); i++) {
                display(String.format((String) Constants.PET_PATTERN.getVal(), i, pets.get(i).toString()));
            }
        }
    }

    public String input() {
        return scanner.nextLine();
    }

    public void display(Object o, boolean insertNewline) {
        System.out.print(o);
        if (insertNewline) {
            System.out.println();
        }
    }

    public void display(Object o) {
        display(o, true);
    }

    public void start() {
        try {
            service.loadPets();
            int operation;

            while ((operation = menu()) != 4) {
                switch (operation) {
                    case 1:
                        addPet();
                        break;
                    case 2:
                        listPets();
                        break;
                    case 3:
                        takePetFromShelter();
                        break;
                }
            }

            display(Constants.FAREWELL);
            try {
                service.savePets();
            } catch (IOException e) {
                display(Constants.ERROR_SAVING_PETS);
            }
        } catch (IOException | InvalidJSONException e) {
            display(Constants.ERROR_LOADING_PETS);
        }
    }

    public void takePetFromShelter() {
        int id;
        while (true) {
            display(Constants.PET_ID_PROMPT, false);
            String strRes = input();
            if (Validator.stringNotEmpty(strRes)) {
                if (Validator.stringNotSpaces(strRes)) {
                    if (strRes.equals(Constants.EXIT_TRIGGER.getVal())) {
                        return;
                    }
                    if (Validator.isWholeNumber(strRes)) {
                        if (Validator.wholeNumberRange("1", "4", strRes)) {
                            id = Integer.parseInt(strRes);
                            break;
                        } else {
                            display(Constants.PET_ID_NOT_IN_RANGE);
                        }
                    } else {
                        display(Constants.NOT_WHOLE_NUMBER);
                    }
                }
            }
        }

        service.remove(id);
    }
}
