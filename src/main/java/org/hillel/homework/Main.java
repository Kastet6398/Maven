package org.hillel.homework;

import org.hillel.homework.app.ShelterApp;
import org.hillel.homework.service.ShelterService;
import org.hillel.homework.utils.constants.Constants;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ShelterService shelterService = new ShelterService(new File("pets.json"));
            ShelterApp app = new ShelterApp(shelterService);
            app.start();
        } catch (IOException e) {
            System.out.println(Constants.ERROR.getVal());
        }
    }
}
