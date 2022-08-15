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
    final static String GAME_ID = "20c4f385-d538-4506-b27a-112578c2fbe8";

    public static boolean check(List <Position> list, int row, int col){
        for (Position a : list){
            if (a.getRow() == row && a.getCol()==col) return false;
        }
        return true;
    }
    public static void dodge_bomb(int [][] map_coordinate,Hero player1, MapInfo map, Position currentPosition, int current_row, int current_col){
        // Ne bomb
        boolean done1= false,done2=false;
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
    public static void destroy_balk(int [][] map_coordinate,Hero player1, MapInfo map, Position currentPosition, int current_row, int current_col){
        // pha thung
        if ( (check(map.getBalk(),current_row-1,current_col) || check(map.getBalk(),current_row+1,current_col) || check(map.getBalk(),current_row,current_col+1) || check(map.getBalk(),current_row,current_col-1)) && !check(map.getBombList(),current_row,current_col) )
            player1.move("b"); //note: khi dang bi trong pham vi cua bomb va ben canh dang co thung thi lieu co nen dat bom de pha thung hay ko?
    }
    public static void main(String[] args) {
            Hero player1 = new Hero(PLAYER_ID, GAME_ID);
            Emitter.Listener onTickTackListener = objects -> {
            GameInfo gameInfo = GameUtil.getGameInfo(objects);
            MapInfo map = gameInfo.getMapInfo();
            map.updateMapInfo();
            Position currentPosition = map.getCurrentPosition(player1);
            int current_col = map.getCurrentPosition(player1).getCol();
            int current_row = map.getCurrentPosition(player1).getRow();
            List<Viruses> virus = map.getVirus();
            int[][] map_coordinate = map.mapMatrix;
            Map<Position, String> path = new HashMap();
            ArrayList<Position> pillPos = new ArrayList<Position>();
            boolean map_have_pill=false;
            List<Position> restrictPosition = new ArrayList();
            restrictPosition.addAll(map.quarantinePlace);
            restrictPosition.addAll(map.teleportGate);
            Position tmp= new Position(0,0);
                for (Spoil i : map.getSpoils()) {
                    if (i.spoil_type == 5) {
                        pillPos.add(new Position(i.getCol(),i.getRow()));
                        map_have_pill=true;
                    }
                }



                if (map_have_pill) {
                    dodge_bomb(map_coordinate,player1,map,currentPosition,current_row,current_col);
                    //destroy_balk(map_coordinate,player1,map,currentPosition,current_row,current_col);
//                    for (int i=0;i<pillPos.size();i++){
//                        for (int j =pillPos.size()-1;j>i;j--){
//                            if ( (Math.abs(pillPos.get(i).getCol()-current_col) + Math.abs(pillPos.get(i).getRow()-current_row) ) > (Math.abs(pillPos.get(j).getCol()-current_col) + Math.abs(pillPos.get(j).getRow()-current_row) ))
//                                tmp = pillPos.get(i);
//                                pillPos.set(i,pillPos.get(j));
//                                pillPos.set(j,tmp);
//                        }
//                    }
                    for (Position i: pillPos) System.out.println("Row: "+i.getRow()+" "+ "Col: "+i.getCol());
                    path = AStarSearch.getPathToAllTargets(map.mapMatrix, restrictPosition, currentPosition, pillPos);

                    for (String str : path.values()) {
                        player1.move(str);
                        break;
                    }
                }
//                else {
//                    path = AStarSearch.getPathToAllTargets(map.mapMatrix, restrictPosition, currentPosition, pillPos);
//                    System.out.println("Path" + path);
//                    for (String str : path.values()) {
//                        player1.move(str);
//                        break;
//                    }
//                }




                };
            player1.setOnTickTackListener(onTickTackListener);
            player1.connectToServer(SERVER_URL);
        }
        }



