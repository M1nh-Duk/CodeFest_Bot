import io.socket.emitter.Emitter;
import jsclub.codefest.bot.constant.GameConfig;
import jsclub.codefest.sdk.algorithm.AStarSearch;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.*;
import jsclub.codefest.sdk.util.GameUtil;
import java.util.*;

import java.util.*;


public class Player {
    final static String SERVER_URL = "https://codefest.jsclub.me/";
    final static String PLAYER_ID = "player1-xxx";
    final static String GAME_ID = "533f6c64-96a6-407e-b598-a49fdf59fe66";
    public static void main(String[] args) {

        Hero player1 = new Hero(PLAYER_ID, GAME_ID);
        Emitter.Listener onTickTackListener = objects -> {
            GameInfo gameInfo = GameUtil.getGameInfo(objects);
            MapInfo map = gameInfo.getMapInfo();
            map.updateMapInfo();
            Position currentPosition = map.getCurrentPosition(player1);
            Position enemyPos = map.getEnemyPosition(player1);
            int current_col = map.getCurrentPosition(player1).getCol();
            int current_row = map.getCurrentPosition(player1).getRow();
            List<Viruses> virus = map.getVirus();
            int[][] map_coordinate = map.mapMatrix;
            ArrayList<Position> pillPos = new ArrayList<Position>();
            boolean done1= false,done2=false;
            List<Position> restrictPosition = new ArrayList();

            // Ne bomb
            for (Position a : map.getBombList()) {
                do{
                    current_col = map.getCurrentPosition(player1).getCol();
                    current_row = map.getCurrentPosition(player1).getRow();
                    done2=false;
                    if (map_coordinate[current_row][current_col + 1] == 0 && !done2) {player1.move("2");done2=true;}
                    if (map_coordinate[current_row][current_col - 1] == 0 && !done2) {player1.move("1");done2=true;}
                    if (map_coordinate[current_row - 1][current_col] == 0 && !done2) {player1.move("3");done2=true;}
                    if (map_coordinate[current_row + 1][current_col] == 0 && !done2) {player1.move("4");done2=true;}
                }
                while (a.getRow() == current_row && a.getCol() == current_col);
            }
//            if (map_coordinate[current_row][current_col + 1] == 2 || map_coordinate[current_row][current_col - 1] == 2 || map_coordinate[current_row-1][current_col] == 2||map_coordinate[current_row+1][current_col] == 2)
//                    player1.move("b");
//            else {
//                    done1=false;
//                    if (map_coordinate[current_row][current_col + 1] == 0 && done1==false) {player1.move("2");done1=true;}
//                    if (map_coordinate[current_row][current_col - 1] == 0 && done1==false) {player1.move("1");done1=true;}
//                    if (map_coordinate[current_row - 1][current_col] == 0 && done1==false) {player1.move("3");done1=true;}
//                    if (map_coordinate[current_row + 1][current_col] == 0 && done1==false) {player1.move("4");done1=true;}
//
//            }

//            for (Spoil i : map.getSpoils()) {
//                    if (i.spoil_type == 5) {
//                        pillPos.add(new Position(i.getCol(),i.getRow()));
//                       // map_have_pill=true;
//                    }
//                }
//            for (Position i: pillPos){
//                System.out.println(i.getCol()+" "+i.getRow());
//            }
//            Collections.sort((List<T>) pillPos);
//
//            restrictPosition.addAll(map.quarantinePlace); // them o ko muon di vao
//                restrictPosition.addAll(map.bombs);
//
//                Map<Position,String> path = new HashMap();
//                path = AStarSearch.getPathToAllTargets(map.mapMatrix, restrictPosition, currentPosition, pillPos);
//                System.out.println("Path" + path);
//                for (String str : path.values()) {player1.move(str);break;}
                };
            player1.setOnTickTackListener(onTickTackListener);
            player1.connectToServer(SERVER_URL);
        }
        }



