package pingpong_v1_ce160353;

import java.util.Random;

/**
 *
 * @author CE160353 PhamThanhNghiem
 */
public class Randomizer {

    private static Random rand = new Random();

    public static int random(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

}
