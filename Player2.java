import io.socket.emitter.Emitter;
import jsclub.codefest.bot.constant.GameConfig;
import jsclub.codefest.sdk.algorithm.AStarSearch;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.*;
import jsclub.codefest.sdk.util.GameUtil;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.*;

import java.util.*;


public class Player {
    final static String SERVER_URL = "https://codefest.jsclub.me/";
    final static String PLAYER_ID = "player1-xxx";
    final static String GAME_ID = "36b4fb7b-2e9a-4113-bdc7-c2e9310fc454";

    public static boolean check(List <Position> list, int row, int col){
        for (Position a : list){
            if (a.getRow() == row && a.getCol()==col) return false;
        }
        return true;
    }
    public static int check2(List <Position> list, int row, int col){
        for (int i =0 ;i < list.size();i++){
            if (list.get(i).getRow() == row && list.get(i).getCol()==col) return i;
        }
    return 0;
    }
    public static void dodge_bomb(int [][] map_coordinate,Hero player1, MapInfo map, Position currentPosition, int current_row, int current_col){
        // Ne bomb
        boolean done2=false;
        for (Position a : map.getBombList()) {
            if (a.getCol()== current_col && a.getRow() == current_row){
                done2 = false;

                // o ben phai
                if (map_coordinate[current_row][current_col + 1] == 0 && !done2 && check(map.getBombList(),current_row,current_col+1)) {
                    player1.move("2");
                    done2 = true;
                } else if (map_coordinate[current_row][current_col + 1] == 0 && !done2 && check(map.getBombList(),current_row,current_col+1)) {
                    boolean done3 = false;
                    // tren
                    if (map_coordinate[current_row - 1][current_col + 1] == 0 && !done3 && check(map.getBombList(),current_row-1,current_col+1)) {
                        player1.move("2");
                        player1.move("3");
                        done2 = true;
                        done3 = true;
                    }
                    // duoi
                    if (map_coordinate[current_row + 1][current_col + 1] == 0 && map_coordinate[current_row + 1][current_col + 1] == 7  && !done3 &&check(map.getBombList(),current_row+1,current_col+1)) {
                        player1.move("2");
                        player1.move("4");
                        done2 = true;
                        done3 = true;
                    }
                    // phai
                    if (map_coordinate[current_row][current_col + 2] == 0 && !done3 && check(map.getBombList(),current_row,current_col+2)) {
                        player1.move("2");
                        player1.move("2");
                        done2 = true;
                        done3 = true;
                    }

                }

                // o ben trai
                if (map_coordinate[current_row][current_col - 1] == 0 && !done2 && check(map.getBombList(),current_row,current_col-1)) {
                    player1.move("1");
                    done2 = true;
                } else if (map_coordinate[current_row][current_col - 1] == 0 && !done2 && check(map.getBombList(),current_row,current_col-1)) {
                    boolean done3 = false;
                    // tren
                    if (map_coordinate[current_row - 1][current_col - 1] == 0 && !done3 && check(map.getBombList(),current_row-1,current_col-1)) {
                        player1.move("1");
                        player1.move("3");
                        done2 = true;
                        done3 = true;
                    }
                    // duoi
                    if (map_coordinate[current_row + 1][current_col - 1] == 0 && !done3 && check(map.getBombList(),current_row+1,current_col-1)) {
                        player1.move("1");
                        player1.move("4");
                        done2 = true;
                        done3 = true;
                    }
                    // trai
                    if (map_coordinate[current_row][current_col - 2] == 0 && !done3 && check(map.getBombList(),current_row,current_col-2)) {
                        player1.move("1");
                        player1.move("1");
                        done2 = true;
                        done3 = true;
                    }

                }

                // o ben tren
                if (map_coordinate[current_row - 1][current_col] == 0 && !done2 && check(map.getBombList(),current_row-1,current_col)) {
                    player1.move("3");
                    done2 = true;
                } else if (map_coordinate[current_row - 1][current_col] == 0 && !done2 && check(map.getBombList(),current_row-1,current_col)) {
                    boolean done3 = false;
                    // tren
                    if (map_coordinate[current_row - 2][current_col] == 0 && !done3 && check(map.getBombList(),current_row-2,current_col)) {
                        player1.move("3");
                        player1.move("3");
                        done2 = true;
                        done3 = true;
                    }
                    // phai
                    if (map_coordinate[current_row - 1][current_col + 1] == 0 && !done3 && check(map.getBombList(),current_row-1,current_col+1)) {
                        player1.move("3");
                        player1.move("2");
                        done2 = true;
                        done3 = true;
                    }
                    //trai
                    if (map_coordinate[current_row - 1][current_col - 1] == 0 && !done3 && check(map.getBombList(),current_row-1,current_col-1)) {
                        player1.move("3");
                        player1.move("1");
                        done2 = true;
                        done3 = true;
                    }

                }

                // o ben duoi
                if (map_coordinate[current_row + 1][current_col] == 0 && !done2 && check(map.getBombList(),current_row+1,current_col)) {
                    player1.move("4");
                    done2 = true;
                } else if (map_coordinate[current_row + 1][current_col] == 0 && !done2 && check(map.getBombList(),current_row+1,current_col)) {
                    boolean done3 = false;
                    // duoi
                    if (map_coordinate[current_row + 2][current_col] == 0 && !done3 && check(map.getBombList(),current_row+2,current_col)) {
                        player1.move("4");
                        player1.move("4");
                        done2 = true;
                        done3 = true;
                    }
                    // phai
                    if (map_coordinate[current_row + 1][current_col + 1] == 0 && !done3 &&check(map.getBombList(),current_row+1,current_col+1)) {
                        player1.move("4");
                        player1.move("2");
                        done2 = true;
                        done3 = true;
                    }
                    //trai
                    if (map_coordinate[current_row + 1][current_col - 1] == 0 && !done3 && check(map.getBombList(),current_row+1,current_col-1)) {
                        player1.move("4");
                        player1.move("1");
                        done2 = true;
                        done3 = true;
                    }

                }
                current_col = map.getCurrentPosition(player1).getCol();
                current_row = map.getCurrentPosition(player1).getRow();
            }
        }

    }
    public static void dodge_virus(int [][] map_coordinate,Hero player1, MapInfo map, Position currentPosition, int current_row, int current_col){
        // Ne virus
        if (map.getVirus().size() == 0);
        else {
            jsclub.codefest.sdk.socket.data.Player player_info = map.getPlayerByKey("player1-xxx");
            if (player_info.pill > 0) ;
            else {
                ArrayList<Position> virus = new ArrayList<>();
                for (Viruses v : map.getVirus()) {
                    virus.add(new Position(v.position.getCol(), v.position.getRow()));
                }

                boolean done = false;
                // neu virus o ngay canh ben phai
                if (map.getVirus().get(check2(virus, current_row, current_col + 1)).direction == 1 && !check(virus, current_row, current_col + 1)) {
                    if (map_coordinate[current_row + 1][current_col] == 0 && check(virus, current_row + 1, current_col) && !done) {
                        player1.move("4");
                        done = true;
                    } else if (map_coordinate[current_row - 1][current_col] == 0 && check(virus, current_row - 1, current_col) && !done) {
                        player1.move("3");
                        done = true;
                    } else {
                        player1.move("1");
                        if (map_coordinate[current_row + 1][current_col] == 0 && check(virus, current_row + 1, current_col) && !done) {
                            player1.move("4");
                            done = true;
                        } else if (map_coordinate[current_row - 1][current_col] == 0 && check(virus, current_row - 1, current_col) && !done) {
                            player1.move("3");
                            done = true;
                        }

                    }
                }
                // neu virus o ngay canh ben trai
                if (map.getVirus().get(check2(virus, current_row, current_col - 1)).direction == 2 && !check(virus, current_row, current_col - 1)) {
                    if (map_coordinate[current_row + 1][current_col] == 0 && check(virus, current_row + 1, current_col) && !done) {
                        player1.move("4");
                        done = true;
                    } else if (map_coordinate[current_row - 1][current_col] == 0 && check(virus, current_row - 1, current_col) && !done) {
                        player1.move("3");
                        done = true;
                    } else {
                        player1.move("2");
                        if (map_coordinate[current_row + 1][current_col] == 0 && check(virus, current_row + 1, current_col) && !done) {
                            player1.move("4");
                            done = true;
                        } else if (map_coordinate[current_row - 1][current_col] == 0 && check(virus, current_row - 1, current_col) && !done) {
                            player1.move("3");
                            done = true;
                        }

                    }
                }
                // neu virus o o ngay canh ben duoi
                if (map.getVirus().get(check2(virus, current_row + 1, current_col)).direction == 3 && !check(virus, current_row + 1, current_col)) {
                    if (map_coordinate[current_row][current_col + 1] == 0 && check(virus, current_row, current_col + 1) && !done) {
                        player1.move("2");
                        done = true;
                    } else if (map_coordinate[current_row][current_col - 1] == 0 && check(virus, current_row, current_col - 1) && !done) {
                        player1.move("1");
                        done = true;
                    } else {
                        player1.move("3");
                        if (map_coordinate[current_row][current_col + 1] == 0 && check(virus, current_row, current_col + 1) && !done) {
                            player1.move("2");
                            done = true;
                        } else if (map_coordinate[current_row][current_col - 1] == 0 && check(virus, current_row, current_col - 1) && !done) {
                            player1.move("1");
                            done = true;
                        }
                    }

                }
                // neu virus o o ngay canh ben tren
                if (map.getVirus().get(check2(virus, current_row - 1, current_col)).direction == 4 && !check(virus, current_row - 1, current_col)) {
                    if (map_coordinate[current_row][current_col + 1] == 0 && check(virus, current_row, current_col + 1) && !done) {
                        player1.move("2");
                        done = true;
                    } else if (map_coordinate[current_row][current_col - 1] == 0 && check(virus, current_row, current_col - 1) && !done) {
                        player1.move("1");
                        done = true;
                    } else {
                        player1.move("4");
                        if (map_coordinate[current_row][current_col + 1] == 0 && check(virus, current_row, current_col + 1) && !done) {
                            player1.move("2");
                            done = true;
                        } else if (map_coordinate[current_row][current_col - 1] == 0 && check(virus, current_row, current_col - 1) && !done) {
                            player1.move("1");
                            done = true;
                        }

                    }
                }
            }
        }

    }
    public static void destroy_balk(int [][] map_coordinate,Hero player1, MapInfo map, Position currentPosition, int current_row, int current_col){
        // pha thung
        if ( !check(map.getBalk(),current_row-1,current_col) || !check(map.getBalk(),current_row+1,current_col) || !check(map.getBalk(),current_row,current_col+1) || !check(map.getBalk(),current_row,current_col-1) )
            player1.move("b"); //note: khi dang bi trong pham vi cua bomb va ben canh dang co thung thi lieu co nen dat bom de pha thung hay ko?
    }
    public static void run (int [][] map_coordinate,Hero player1, MapInfo map,ArrayList<Position> virus,ArrayList<Position> zombie ,List<Position> restrictPosition){
        String path = "";
        int current_col = map.getCurrentPosition(player1).getCol();
        Position currentPosition = map.getCurrentPosition(player1);
        int current_row= map.getCurrentPosition(player1).getRow();
        List<Position> blank = map.getBlank();
        for (Position i : map.getBombList()) System.out.println(i.getCol()+" "+i.getRow());
        System.out.println(map.getBombList().size());
        System.out.println(check(map.getBombList(),current_row,current_col));
        if (check(map.getBombList(),current_row,current_col) == false ){ //|| !check(virus,current_row, current_col) || !check(zombie,current_row,current_col)
            //burble sort to find the closet
            for (int i=0;i<blank.size();i++){
                for (int j =map.getBlank().size()-1;j>i;j--){
                    Position tmp = map.getBlank().get(i);
                    if ( AStarSearch.distanceBetweenTwoPoints(map.getCurrentPosition(player1),blank.get(j)) < AStarSearch.distanceBetweenTwoPoints(map.getCurrentPosition(player1),blank.get(i))) {
                        blank.set(i, blank.get(j));
                        blank.set(j, tmp);
                    }
                }
            }
            for (int i=2; i< blank.size();i++ ) {
                if (check(map.getBombList(), blank.get(i).getRow(), blank.get(i).getCol()) && !check(map.getBombList(), current_row, current_col)) {
                    path=(AStarSearch.aStarSearch(map_coordinate, restrictPosition, currentPosition, blank.get(i)));
                    blank.remove(i);
                    break;
                }
//                // ne virus and zombie neu pill=0
//                if (map.getPlayerByKey("player1-xxx").pill > 0);
//                else
//                {
//                    if (check(zombie, blank.get(i).getRow(), blank.get(i).getCol()) && !check(zombie, current_row, current_col)) {
//                        path = AStarSearch.aStarSearch(map_coordinate, restrictPosition, currentPosition, blank.get(i));
//                        player1.move(path);
//                        blank.remove(i);
//                        break;
//                    }
//                    if (check(virus, blank.get(i).getRow(), blank.get(i).getCol()) && !check(virus, current_row, current_col)) {
//                        path = AStarSearch.aStarSearch(map_coordinate, restrictPosition, currentPosition, blank.get(i));
//                        player1.move(path);
//                        blank.remove(i);
//                        break;
//                    }
//                }
            }
        }
        System.out.println("RUN path: " + path);
        player1.move(path);
        //return path;
    }
    public static void dodge_infected_human (int [][] map_coordinate,Hero player1, MapInfo map, Position currentPosition, int current_row, int current_col){
        if (map.getDhuman().size() == 0);
        else {
            jsclub.codefest.sdk.socket.data.Player player_info = map.getPlayerByKey("player1-xxx");
            if (player_info.pill>0);
            else {
            ArrayList<Position> dhuman_pos = new ArrayList<>();
            for (Human i: map.getDhuman()){
                dhuman_pos.add(i.position);
            }
            boolean done = false;
            // neu dhuman o ngay canh ben phai
            if (map.getDhuman().get(check2(dhuman_pos, current_row, current_col + 1)).direction == 1 && !check(dhuman_pos, current_row, current_col + 1)) {
                if (map_coordinate[current_row + 1][current_col] == 0 && check(dhuman_pos, current_row + 1, current_col) && !done) {
                    player1.move("4");
                    done = true;
                } else if (map_coordinate[current_row - 1][current_col] == 0 && check(dhuman_pos, current_row - 1, current_col) && !done) {
                    player1.move("3");
                    done = true;
                } else {
                    player1.move("1");
                    if (map_coordinate[current_row + 1][current_col] == 0 && check(dhuman_pos, current_row + 1, current_col) && !done) {
                        player1.move("4");
                        done = true;
                    } else if (map_coordinate[current_row - 1][current_col] == 0 && check(dhuman_pos, current_row - 1, current_col) && !done) {
                        player1.move("3");
                        done = true;
                    }

                }
            }
                // neu dhuman o ngay canh ben trai
                if (map.getDhuman().get(check2(dhuman_pos, current_row, current_col - 1)).direction == 2 && !check(dhuman_pos, current_row, current_col - 1)) {
                    if (map_coordinate[current_row + 1][current_col] == 0 && check(dhuman_pos, current_row + 1, current_col) && !done) {
                        player1.move("4");
                        done = true;
                    } else if (map_coordinate[current_row - 1][current_col] == 0 && check(dhuman_pos, current_row - 1, current_col) && !done) {
                        player1.move("3");
                        done = true;
                    } else {
                        player1.move("2");
                        if (map_coordinate[current_row + 1][current_col] == 0 && check(dhuman_pos, current_row + 1, current_col) && !done) {
                            player1.move("4");
                            done = true;
                        } else if (map_coordinate[current_row - 1][current_col] == 0 && check(dhuman_pos, current_row - 1, current_col) && !done) {
                            player1.move("3");
                            done = true;
                        }
                    }
                }
                // neu virus o o ngay canh ben duoi
                if (map.getVirus().get(check2(dhuman_pos, current_row + 1, current_col)).direction == 3 && !check(dhuman_pos, current_row + 1, current_col)) {
                    if (map_coordinate[current_row][current_col + 1] == 0 && check(dhuman_pos, current_row, current_col + 1) && !done) {
                        player1.move("2");
                        done = true;
                    } else if (map_coordinate[current_row][current_col - 1] == 0 && check(dhuman_pos, current_row, current_col - 1) && !done) {
                        player1.move("1");
                        done = true;
                    } else {
                        player1.move("3");
                        if (map_coordinate[current_row][current_col + 1] == 0 && check(dhuman_pos, current_row, current_col + 1) && !done) {
                            player1.move("2");
                            done = true;
                        } else if (map_coordinate[current_row][current_col - 1] == 0 && check(dhuman_pos, current_row, current_col - 1) && !done) {
                            player1.move("1");
                            done = true;
                        }
                    }
                }
                // neu virus o o ngay canh ben tren
                if (map.getVirus().get(check2(dhuman_pos, current_row + 1, current_col)).direction == 4 && !check(dhuman_pos, current_row + 1, current_col)) {
                    if (map_coordinate[current_row][current_col + 1] == 0 && check(dhuman_pos, current_row, current_col + 1) && !done) {
                        player1.move("2");
                        done = true;
                    } else if (map_coordinate[current_row][current_col - 1] == 0 && check(dhuman_pos, current_row, current_col - 1) && !done) {
                        player1.move("1");
                        done = true;
                    } else {
                        player1.move("4");
                        if (map_coordinate[current_row][current_col + 1] == 0 && check(dhuman_pos, current_row, current_col + 1) && !done) {
                            player1.move("2");
                            done = true;
                        } else if (map_coordinate[current_row][current_col - 1] == 0 && check(dhuman_pos, current_row, current_col - 1) && !done) {
                            player1.move("1");
                            done = true;
                        }
                    }
                }
    }}}
    public static String  go_to_pills(int [][] map_coordinate,Hero player1, MapInfo map,ArrayList<Position>pill,List<Position> restrictPosition) {
        Position currentPosition = map.getCurrentPosition(player1);
        int current_col = map.getCurrentPosition(player1).getCol();
        int current_row = map.getCurrentPosition(player1).getRow();
        String path="";
        if (pill.size() ==0);
        else {
            for (int i = 0; i < pill.size(); i++) {
                for (int j = pill.size() - 1; j > i; j--) {
                    Position tmp = pill.get(i);
                    if (AStarSearch.distanceBetweenTwoPoints(currentPosition, pill.get(j)) < AStarSearch.distanceBetweenTwoPoints(currentPosition, pill.get(i))) {
                        pill.set(i, pill.get(j));
                        pill.set(j, tmp);
                    }
                }
            }

//        for (Position i : pill ){
//            path = AStarSearch.aStarSearch(map_coordinate,restrictPosition,map.getCurrentPosition(player1),i);
////            for (int j=0;j<path.length()-1;j++){
////                System.out.println(path.substring(j,j+1));
////                player1.move(path.substring(j,j+1));
//////                dodge_virus(map_coordinate,player1,map,currentPosition,current_row,current_col);
//////                dodge_bomb(map_coordinate,player1,map,currentPosition,current_row,current_col);
//////                destroy_balk(map_coordinate,player1,map,currentPosition,current_row,current_col);
////
////            }
//
//           pill.remove(i);
//            break;
//        }
            path = AStarSearch.aStarSearch(map_coordinate, restrictPosition, map.getCurrentPosition(player1), pill.get(0));
            pill.remove(0);
        }
        System.out.println("PILL path: " + path);
        if (path.length() > 2) return path.substring(0, 1);
            else return path;
    }
    public static String go_to_balks (int [][] map_coordinate,Hero player1, MapInfo map,ArrayList<Position>balk,List<Position> restrictPosition){
        String path="";
        Position currentPosition = map.getCurrentPosition(player1);
        int current_col = map.getCurrentPosition(player1).getCol();
        int current_row = map.getCurrentPosition(player1).getRow();
        if (balk.size() ==0 );
        else {
            for (int i = 0; i < balk.size(); i++) {
                for (int j = balk.size() - 1; j > i; j--) {
                    Position tmp = balk.get(i);
                    if (AStarSearch.distanceBetweenTwoPoints(currentPosition, balk.get(j)) < AStarSearch.distanceBetweenTwoPoints(map.getCurrentPosition(player1), balk.get(i))) {
                        balk.set(i, balk.get(j));
                        balk.set(j, tmp);
                    }
                }
            }

            path = AStarSearch.aStarSearch(map_coordinate, restrictPosition, map.getCurrentPosition(player1), balk.get(0));
            balk.remove(0);

        }
        System.out.println("BALK path : " + path);
        if (path.length() > 2) return path.substring(0, 1);
        else return path;
    }
    public static void main(String[] args) {
            Hero player1 = new Hero(PLAYER_ID, GAME_ID);
            Emitter.Listener onTickTackListener = objects -> {

            GameInfo gameInfo = GameUtil.getGameInfo(objects);
            MapInfo map = gameInfo.getMapInfo();
            map.updateMapInfo();
            int[][] map_coordinate = map.mapMatrix;
            ArrayList<Position> pillPos = new ArrayList<Position>();
            boolean map_have_pill=false;
            List<Position> restrictPosition = new ArrayList();
            restrictPosition.addAll(map.quarantinePlace);
            restrictPosition.addAll(map.teleportGate);
            restrictPosition.addAll(map.bombs);
            ArrayList<Position> virus = new ArrayList<>();
            ArrayList<Position> zombie = new ArrayList<>();
            ArrayList<Position> balk = new ArrayList<>();
            String full_path = "";

            for (Viruses v : map.getVirus()) { virus.add(new Position(v.position.getCol(), v.position.getRow())); }
            restrictPosition.addAll(virus);
            for (Human v : map.getDhuman()) zombie.add(new Position(v.position.getCol(), v.position.getRow()));
            restrictPosition.addAll(zombie);
            for (Spoil i : map.getSpoils()) { if (i.spoil_type == 5) {  pillPos.add(new Position(i.getCol(),i.getRow())); map_have_pill=true; }}
            for (Position i: map.getBalk()) balk.add(i);
            restrictPosition.addAll(balk);
                run(map_coordinate,player1,map,virus,zombie,restrictPosition);
            if (map_have_pill) {
                    full_path=(go_to_pills(map_coordinate,player1,map,pillPos,restrictPosition));
                    System.out.println("FULL path : "+full_path);
                    player1.move(full_path);
                }
                else {
                    full_path.concat(go_to_balks(map_coordinate,player1,map,balk,restrictPosition));
                    System.out.println("FULL path : "+full_path);
                    player1.move(full_path);
                }
//                    for (Position i: pillPos) System.out.println("Row: "+i.getRow()+" "+ "Col: "+i.getCol());
//                    path = AStarSearch.getPathToAllTargets(map.mapMatrix, restrictPosition, currentPosition, pillPos);

//                    for (String str : path.values()) {
//                        player1.move(str);
//                        break;
//                    }

//                dodge_virus(map_coordinate,player1,map,currentPosition,current_row,current_col);
//                for (Position i: pillPos) System.out.println(i.getCol()+" "+i.getRow());
//                dodge_infected_human(map_coordinate,player1,map,currentPosition,current_row,current_col);
                    //destroy_balk(map_coordinate,player1,map,currentPosition,current_row,current_col);

                };
            player1.setOnTickTackListener(onTickTackListener);
            player1.connectToServer(SERVER_URL);
        }
        }



