import io.socket.emitter.Emitter;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.GameInfo;
import jsclub.codefest.sdk.util.GameUtil;

import static jsclub.codefest.bot.RandomPlayer.getRandomPath;

public class Player2 {
    final static String SERVER_URL = "https://codefest.jsclub.me/";
    final static String PLAYER_ID = "player2-xxx";
    final static String GAME_ID = "a5bc7bdd-cd65-40d4-a6cc-68c32f0bfa10";

    public static void main(String[] args) {

        Hero randomPlayer = new Hero(PLAYER_ID, GAME_ID);

        Emitter.Listener onTickTackListener = objects -> {
            GameInfo gameInfo = GameUtil.getGameInfo(objects);
            randomPlayer.move(getRandomPath(10));
        };
        randomPlayer.setOnTickTackListener(onTickTackListener);
        randomPlayer.connectToServer(SERVER_URL);
    }
}