package org.hillel.homework;

import org.jetbrains.annotations.NotNull;

public class JewelsAndStones {
    public static long numJewelsInStones(@NotNull String jewels, @NotNull String stones) {
        long count = 0;
        for (char stone : stones.toCharArray()) {
            if (jewels.indexOf(stone) != -1) {
                count++;
            }
        }
        return count;
    }
}
