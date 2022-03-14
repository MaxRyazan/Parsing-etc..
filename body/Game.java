package Ylab.Game_Lesson2.body;

import Ylab.Game_Lesson2.LauncherNew;
import Ylab.Game_Lesson2.logic.CheckGamersNames;
import Ylab.Game_Lesson2.logic.DrawCheck;
import Ylab.Game_Lesson2.logic.Logs;
import Ylab.Game_Lesson2.logic.ParsingXml;
import Ylab.Game_Lesson2.logic.Users;
import Ylab.Game_Lesson2.logic.WinCheck;
import Ylab.Game_Lesson2.logic.XmlReader.Move;
import Ylab.Game_Lesson2.logic.XmlReader.Player;
import Ylab.Game_Lesson2.logic.XmlReader.Root;
import java.io.FileNotFoundException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Game {

    public Player playerOne;

    public Player playerTwo;

    private final Logs logs;

    private final static Maps maps = new Maps();

    private final Users users;

    private final WinCheck winCheck;

    private final DrawCheck drawCheck;

    private final ParsingXml parsingXml;

    private final boolean isFromFile;

    public Game(final Users users,
                final WinCheck winCheck, final DrawCheck drawCheck,
                final Logs logs, final ParsingXml parsingXml, boolean isFromFile) {
        this.users = users;
        this.winCheck = winCheck;
        this.drawCheck = drawCheck;
        this.logs = logs;
        this.parsingXml = parsingXml;
        this.playerOne = new Player();
        this.playerTwo = new Player();
        this.isFromFile = isFromFile;
    }

    public void play()
            throws FileNotFoundException, ParserConfigurationException, TransformerException {
        if (!isFromFile) {
            playerOne.setName(CheckGamersNames.getPlayersName("Введите имя первого игрока:"));
            playerOne.setId("1");
            playerOne.setSymbol("X");

            playerTwo.setName(CheckGamersNames.getPlayersName("Введите имя второго игрока:"));
            playerTwo.setId("2");
            playerTwo.setSymbol("O");
        } else {
            playerOne = Root.players.get(0);
            playerTwo = Root.players.get(1);
        }

        maps.printMapOnce();

        System.out.println(playerOne.getName() + " make move using symbol '" + playerOne.getSymbol() + "'");
        System.out.println(playerTwo.getName() + " make move using symbol '" + playerTwo.getSymbol() + "'");
        System.out.println("Let's ROCK!!!");

        boolean gameEnded = false;

        while (!gameEnded) {
            gameEnded = makePlayerMove(playerOne);

            if (!gameEnded) {
                gameEnded = makePlayerMove(playerTwo);
            }
        }

        if(!isFromFile) {
            parsingXml.parse(playerOne, playerTwo);
        }
}
    private boolean makePlayerMove(Player player) {
        System.out.println(player.getName() + " '" + player.getSymbol() + "' :");

        Cell movedCell = users.printUserMove(player, isFromFile);

        LauncherNew.gamePlay.moves.add(new Move(
                Integer.parseInt(player.getId()),
                movedCell.getCellNumber(),
                player.getMoveCounter()
        ));

        Maps.refreshMap();

        if (winCheck.uisUerWin(player.getSymbol())) {
            player.setWinner(true);
            logs.loggingWin(player);
            System.out.println(player.getName() + " '" + player.getSymbol() + "' WIN!");
            System.out.println("GAME OVER");
            return true;
        } else if (drawCheck.check()) {
            logs.loggingIfDraw();
            System.out.println("Sorry.. nobody win.. its a DRAW!");
            System.out.println("GAME OVER");
            return true;
        }
        return false;
    }
}