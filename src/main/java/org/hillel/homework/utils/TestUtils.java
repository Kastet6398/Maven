package org.hillel.homework.utils;

import java.io.File;
import java.net.URL;

public class TestUtils {
    public static File getResourceFile(String name) {
        URL resourceURL = Thread.currentThread().getContextClassLoader().getResource("");
        assert resourceURL != null;
        return new File(STR."\{resourceURL.getPath()}/\{name}");
    }
}
