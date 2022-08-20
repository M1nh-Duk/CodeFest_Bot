import io.socket.emitter.Emitter;
import jsclub.codefest.bot.constant.GameConfig;
import jsclub.codefest.sdk.algorithm.AStarSearch;
import jsclub.codefest.sdk.model.Hero;
import jsclub.codefest.sdk.socket.data.*;
import jsclub.codefest.sdk.util.GameUtil;
import java.util.*;



public class Player2 {
    final static String SERVER_URL = "https://codefest.jsclub.me/";
    final static String PLAYER_ID = "player1-xxx";
    final static String GAME_ID = "4f98c623-1db3-42b8-9b1a-230ce8f9ba62";


    public static String remove_last_char(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
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
    public static boolean dodge_virus(int [][] map_coordinate,Hero player1, MapInfo map, Position currentPosition, int current_row, int current_col, ArrayList<Position> blank, List<Position> restrictPosition){
        // Ne virus
        Boolean dodge = false;
        if (map.getVirus().size() == 0);
        else {
            String path = "";
            jsclub.codefest.sdk.socket.data.Player player_info = map.getPlayerByKey("player1-xxx");
            if (player_info.pill > 3) ;
            else {
                ArrayList<Position> virus = new ArrayList<>();
                for (Viruses v : map.getVirus()) {
                    virus.add(new Position(v.position.getCol(), v.position.getRow()));
                }
                for (int i=2; i< blank.size();i++ ) {
                    if (check(virus, blank.get(i).getRow(), blank.get(i).getCol()) && !check(virus, current_row, current_col)) {
                        path=(AStarSearch.aStarSearch(map_coordinate, restrictPosition, currentPosition, blank.get(i)));
                        if (path.compareTo("")==0) continue;
                        else { blank.remove(i);break; }
                    }}
                // neu virus o ngay canh ben phai
                if (map.getVirus().get(check2(virus, current_row, current_col + 2)).direction == 1 && !check(virus, current_row, current_col + 2)) {player1.move(path);dodge=true;}
                // neu virus o ngay canh ben trai
                if (map.getVirus().get(check2(virus, current_row, current_col - 2)).direction == 2 && !check(virus, current_row, current_col - 2)) {player1.move(path);dodge=true;}
                // neu virus o o ngay canh ben duoi
                if (map.getVirus().get(check2(virus, current_row + 2, current_col)).direction == 3 && !check(virus, current_row + 2, current_col)) {player1.move(path);dodge=true;}
                // neu virus o o ngay canh ben tren
                if (map.getVirus().get(check2(virus, current_row - 2, current_col)).direction == 4 && !check(virus, current_row - 2, current_col)) {player1.move(path);dodge=true;}
                System.out.println("VIRUS path : "+path);
            }
        }
        return dodge;
    }
    public static void destroy_balk(int [][] map_coordinate,Hero player1, MapInfo map){
        // pha thung
        Position currentPosition= map.getCurrentPosition(player1);
        int current_row=currentPosition.getRow();
        int current_col=currentPosition.getCol();
        if ( !check(map.getBalk(),current_row-1,current_col) && check(map.getBombList(),current_row-1,current_col)
                || !check(map.getBalk(),current_row+1,current_col) && check(map.getBombList(),current_row+1,current_col)
                || !check(map.getBalk(),current_row,current_col+1) && check(map.getBombList(),current_row,current_col+1)
                || !check(map.getBalk(),current_row,current_col-1)  && check(map.getBombList(),current_row-1,current_col-1) )
            player1.move("b"); //note: khi dang bi trong pham vi cua bomb va ben canh dang co thung thi lieu co nen dat bom de pha thung hay ko?
    }
    public static boolean check_3x3(MapInfo map, int current_row, int current_col){
        if (check(map.getBombList(),current_row,current_col+1)
                && check(map.getBombList(),current_row,current_col-1)
                && check(map.getBombList(),current_row-1,current_col)
                && check(map.getBombList(),current_row-1,current_col+1)
                &&  check(map.getBombList(),current_row-1,current_col-1)
                && check(map.getBombList(),current_row+1,current_col+1)
                && check(map.getBombList(),current_row+1,current_col)
                && check(map.getBombList(),current_row+1,current_col-1)
        )
            return true;
        return false;
    }
    public static boolean run (int [][] map_coordinate,Hero player1, MapInfo map,ArrayList<Position> virus,ArrayList<Position> zombie ,List<Position> restrictPosition,ArrayList<Position> blank){
        String path = "";
        Boolean dodge= false;
        Position currentPosition = map.getCurrentPosition(player1);
        int current_row= map.getCurrentPosition(player1).getRow();
        int current_col = map.getCurrentPosition(player1).getCol();
//        for (Position i : map.getBombList()) System.out.println(i.getCol()+" "+i.getRow());
//        System.out.println(map.getBombList().size());
//        System.out.println(check(map.getBombList(),current_row,current_col));
        if ( !check(map.getBombList(),current_row,current_col) ){
            for (int i=2; i< blank.size();i++ ) {
                if (check(map.getBombList(), blank.get(i).getRow(), blank.get(i).getCol()) && !check(map.getBombList(), current_row, current_col)) {
                    path=(AStarSearch.aStarSearch(map_coordinate, restrictPosition, currentPosition, blank.get(i)));
                    if (path.compareTo("")==0) continue;
                    else { blank.remove(i);break; }
                }
            }
            dodge=true;
        }
        System.out.println("RUN path: " + path);
        player1.move(path);
        if (!dodge) {
            if (dodge_virus(map_coordinate, player1, map, currentPosition, current_row, current_col, blank, restrictPosition)) {
                dodge = true;
                System.out.println("Dodge virus: " +dodge_virus(map_coordinate,player1,  map, currentPosition, current_row, current_col, blank, restrictPosition));
            }
        }
        if (!dodge) {
            if (dodge_zombie(map_coordinate,player1,  map, currentPosition, current_row, current_col, blank, restrictPosition))
            {
                dodge = true;
                System.out.println("Dodge zombie: " + dodge_zombie(map_coordinate, player1, map, currentPosition, current_row, current_col, blank, restrictPosition));
            }

        }
        return dodge;
    }
    public static Boolean dodge_zombie (int [][] map_coordinate,Hero player1, MapInfo map, Position currentPosition, int current_row, int current_col,ArrayList<Position> blank, List<Position> restrictPosition){
        Boolean dodge=false;
        if (map.getDhuman().size() == 0);
        else {
            String path ="";
            jsclub.codefest.sdk.socket.data.Player player_info = map.getPlayerByKey("player1-xxx");
            if (player_info.pill>1);
            else {
                ArrayList<Position> dhuman_pos = new ArrayList<>();
                for (Human i: map.getDhuman()){
                    dhuman_pos.add(i.position);
                }

                for (int i=2; i< blank.size();i++ ) {
                    if (check(dhuman_pos, blank.get(i).getRow(), blank.get(i).getCol()) && !check(dhuman_pos, current_row, current_col)) {
                        path=(AStarSearch.aStarSearch(map_coordinate, restrictPosition, currentPosition, blank.get(i)));
                        if (path.compareTo("")==0) continue;
                        else { blank.remove(i);break; }
                    }
                }
                // neu zom o ngay canh ben phai
                if (map.getDhuman().get(check2(dhuman_pos, current_row, current_col + 2)).direction == 1 && !check(dhuman_pos, current_row, current_col + 2))
                { player1.move(path);dodge=true; }
                // neu zom o ngay canh ben trai
                if (map.getDhuman().get(check2(dhuman_pos, current_row, current_col - 2)).direction == 2 && !check(dhuman_pos, current_row, current_col - 2))
                { player1.move(path);dodge=true; }
                // neu zom o o ngay canh ben duoi
                if (map.getDhuman().get(check2(dhuman_pos, current_row + 2, current_col)).direction == 3 && !check(dhuman_pos, current_row + 2, current_col))
                { player1.move(path);dodge=true; }
                // neu virus o o ngay canh ben tren
                if (map.getDhuman().get(check2(dhuman_pos, current_row + 2, current_col)).direction == 4 && !check(dhuman_pos, current_row + 2, current_col))
                { player1.move(path);dodge=true; }
                System.out.println("ZOMBIE path: "+path);
            }
        }
        return dodge;

    }
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
            for (int i=0;i< pill.size();i++) {
                path = AStarSearch.aStarSearch(map_coordinate, restrictPosition, map.getCurrentPosition(player1), pill.get(i));
                if (path.compareTo("")==0) continue;
                else {pill.remove(i);break;}
            }
        }
        System.out.println("PILL path: " + path);
        if (path.length() > 2) return path.substring(0, 1);
        else return path;
    }
    public static boolean go_to_humans(int [][] map_coordinate,Hero player1, MapInfo map,List<Position> restrictPosition,boolean dodge){
        String path ="";
        ArrayList<Position> human = new ArrayList<>();
        if (!dodge) {
            for (Human i : map.getHuman()) {
                if (!i.infected) human.add(i.position);
            }
            for (int i = 0; i < human.size(); i++) {
                path = AStarSearch.aStarSearch(map_coordinate, restrictPosition, map.getCurrentPosition(player1), human.get(i));
                if (path.compareTo("") == 0) continue;
                else {
                    human.remove(i);
                    break;
                }
            }
            if (path.compareTo("") == 0) return true;
            System.out.println("HUMAN path: " + path);
            if (path.length() > 2) player1.move(path.substring(0, 1));
            else player1.move(path);
            return false;
        }
        return true;
    }
    public static boolean go_to_zom(int [][] map_coordinate,Hero player1, MapInfo map,List<Position> restrictPosition,boolean dodge){
        String path ="";
        jsclub.codefest.sdk.socket.data.Player player_info = map.getPlayerByKey("player1-xxx");
        if (player_info.pill > 2 && !dodge) {
            ArrayList<Position> zombie = new ArrayList<>();
            for (Human i : map.getHuman()) {
                if (!i.infected) zombie.add(i.position);
            }
            for (int i = 0; i < zombie.size(); i++) {
                path = AStarSearch.aStarSearch(map_coordinate, restrictPosition, map.getCurrentPosition(player1), zombie.get(i));
                if (path.compareTo("") == 0) continue;
                else {
                    zombie.remove(i);
                    break;
                }
            }
            if (path.compareTo("") == 0) return true;
            System.out.println("GET ZOMBIE path: " + path);
            if (path.length() > 2) player1.move(path.substring(0, 1));
            else player1.move(path);
            return false;
        }
        return true;
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

            for (int i=0;i< balk.size();i++) {
                path = AStarSearch.aStarSearch(map_coordinate, restrictPosition, map.getCurrentPosition(player1), balk.get(i));
                if (path.compareTo("")==0) continue;
                else {balk.remove(i);break;}
            }

        }
        path= remove_last_char(path);
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
            Position currentPosition = map.getCurrentPosition(player1);
            int current_col = map.getCurrentPosition(player1).getCol();
            int current_row = map.getCurrentPosition(player1).getRow();
            int[][] map_coordinate = map.mapMatrix;
            String full_path = "";
            boolean dodge =false;
            ArrayList<Position> pillPos = new ArrayList<Position>();
            List<Position> restrictPosition = new ArrayList();
            restrictPosition.addAll(map.quarantinePlace);
            restrictPosition.addAll(map.teleportGate);
            restrictPosition.addAll(map.bombs);
            restrictPosition.addAll(map.walls);
            ArrayList<Position> virus = new ArrayList<>();
            ArrayList<Position> zombie = new ArrayList<>();
            ArrayList<Position> balk = new ArrayList<>();
            ArrayList<Position> blank = new ArrayList<>();

            for (Position i : map.getBlank()) blank.add(i);
            for (int i=0;i<blank.size();i++){
                for (int j =blank.size()-1;j>i;j--){
                    Position tmp = blank.get(i);
                    if ( AStarSearch.distanceBetweenTwoPoints(map.getCurrentPosition(player1),blank.get(j)) < AStarSearch.distanceBetweenTwoPoints(map.getCurrentPosition(player1),blank.get(i))) {
                        blank.set(i, blank.get(j));
                        blank.set(j, tmp);
                    }
                }
            }                    //burble sort to find the closet blanks
            for (Viruses v : map.getVirus()) { virus.add(new Position(v.position.getCol(), v.position.getRow())); }
            restrictPosition.addAll(virus);
            for (Human v : map.getDhuman()) zombie.add(new Position(v.position.getCol(), v.position.getRow()));
            restrictPosition.addAll(zombie);
            for (Spoil i : map.getSpoils()) { if (i.spoil_type == 5) {  pillPos.add(new Position(i.getCol(),i.getRow()));}}
            for (Position i: map.getBalk()) balk.add(i);
            restrictPosition.addAll(balk);


            //   BEGIN
            dodge=run(map_coordinate,player1,map,virus,zombie,restrictPosition,blank);
            System.out.println(dodge);
            if (check_3x3(map,current_row,current_col) && !dodge && go_to_humans(map_coordinate,player1,map,restrictPosition,dodge)&& go_to_zom(map_coordinate,player1,map,restrictPosition,dodge)) {
                full_path = go_to_pills(map_coordinate, player1, map, pillPos, restrictPosition);
                player1.move(full_path);
                System.out.println("FULL path : " + full_path);
                if (full_path.compareTo("") == 0) {
                    full_path = go_to_balks(map_coordinate, player1, map, balk, restrictPosition);
                    System.out.println("FULL path : " + full_path);
                    player1.move(full_path);
                    destroy_balk(map_coordinate, player1, map);
                    dodge=run(map_coordinate,player1,map,virus,zombie,restrictPosition,blank);
                    if (full_path.compareTo("") == 0) {
                        for (int i=2; i< blank.size();i++ ) {
                            full_path=(AStarSearch.aStarSearch(map_coordinate, restrictPosition, currentPosition, blank.get(i)));
                            if (full_path.compareTo("")==0) continue;
                            else { blank.remove(i);break; }
                        }
                        player1.move(full_path);
                    }
                }
            }
        };
        player1.setOnTickTackListener(onTickTackListener);
        player1.connectToServer(SERVER_URL);
    }
}



