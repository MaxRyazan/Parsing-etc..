package Ylab.Game_Lesson2.logic;

import Ylab.Game_Lesson2.body.Cell;
import Ylab.Game_Lesson2.body.Maps;
import Ylab.Game_Lesson2.logic.XmlReader.Move;
import Ylab.Game_Lesson2.logic.XmlReader.Player;
import Ylab.Game_Lesson2.logic.XmlReader.Root;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Users {

    char[][] tempMap = {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
    };

    public Cell makeMove() {
        int tries = 0;
        String userMove = "";
        int userMoveInt;

        while (true) {
            if (tries > 3) {
                throw new RuntimeException("Исчерпан лимит попыток ввода координат.");
            }

            userMove = new Scanner(System.in).nextLine();
            if (isNotBlank(userMove) && userMove.trim().length() == 1) {
                try {
                    userMoveInt = Integer.parseInt(userMove);

                    if (userMoveInt <= 0 || userMoveInt > 9) {
                        System.out.println("Ошибка. Введенное число должно быть в диапазоне между 1 и 9 включительно.");
                    }
                    Cell cell = convert(userMoveInt);
                    if (!Maps.isEmpty(cell)) {
                        System.out.println("Ошибка. Ячейка уже занята.");
                        tries++;
                    } else {

                        return cell;
                    }
                } catch (NumberFormatException nfe) {
                    System.out.println("Ошибка. Введенный символ должен быть числом.");
                    tries++;
                }
            } else {
                System.out.println("Incorrect data, please try again!");
                tries++;
            }
        }
    }

    public Cell convert(int cellNumber) {
        if (cellNumber >= 1 && cellNumber <= 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tempMap[i][j] == (Integer.toString(cellNumber)).charAt(0)) {
                        return new Cell(i, j, cellNumber);
                    }
                }
            }
        }
        return null;
    }

    public Cell printUserMove(Player player, boolean isFromFile) {
        final Cell cell;

        player.increaseMoveCount();

        if (!isFromFile) {
            cell = makeMove();
        } else {
            Move playersMove = Root.getMoveByIdAndName(Integer.parseInt(player.getId()), player.getMoveCounter());
            cell = convert(playersMove.getCoordinate());
        }

        Maps.setSymbol(cell, player.getSymbol().charAt(0));
        logMove(player, cell);

        return cell;
    }

    private void logMove(Player player, Cell cell) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("GameLogs.txt", true))) {
            bw.write(player.getName()
                    + " ('" + player.getSymbol() + "') make "
                    + player.getMoveCounter()
                    + " move to " + cell.getRow() + "-" + cell.getCol() + "\n");
        } catch (IOException ex) {
            System.out.println("File not found");
        }
    }

    private boolean isNotBlank(String name) {
        return !name.isBlank();
    }
}