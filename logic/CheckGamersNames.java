package Ylab.Game_Lesson2.logic;

import java.util.Scanner;

public class CheckGamersNames {


    public static String getPlayersName(String messageText) {
        int tries = 0;

        while (true) {
            System.out.println(messageText);
            String nameVariable = new Scanner(System.in).nextLine();

            if (tries > 3) {
                throw new RuntimeException("Ошибка. Лимит попыток исчерпан.");
            } else if (!checkName(nameVariable)) {
                System.out.println("Некорректный ввод данных. Имя должно содержать хотя бы одну букву или цифру.");
                tries++;
            } else {
                return nameVariable;
            }
        }
    }

    private static boolean checkName(String name) {
        return !name.isBlank();
    }

}