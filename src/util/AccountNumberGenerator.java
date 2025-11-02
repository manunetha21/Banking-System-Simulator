package util;

import java.util.Random;

public class AccountNumberGenerator {
    public static String generate(String name) {
        String initials = name.substring(0, 2).toUpperCase();
        int random = new Random().nextInt(9000) + 1000;
        return initials + random;
    }
}
