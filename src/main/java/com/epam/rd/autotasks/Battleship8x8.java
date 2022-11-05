package com.epam.rd.autotasks;

import org.apache.maven.shared.utils.StringUtils;

import java.util.Objects;

import static java.lang.Integer.parseInt;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }
    private String[][] changeToSquare(String arr){
        String[] binaryMap = arr.split("",64);
        String[][] temp = new String[8][8];
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp[i][j] = binaryMap[counter++];
            }
        }
        return temp;
    }
    private String[] changeToNormal(String[][] arr){
        String[] temp = new String[64];
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp[counter++]=arr[i][j];
            }
        }
        return temp;
    }
    private String[][] changeToBinary(long value){
        String maps = Long.toBinaryString(value);
        return changeToSquare(maps);
    }
    private void addLong(String[][] arr,int x,int y){
        String[][] temp1;
        if (shots == 0L)  {
            temp1 = arr;
        }
       else {
            temp1 = changeToBinary(shots);
        }
        temp1[x][y] = "1";
        shots = Long.parseUnsignedLong(StringUtils.join(changeToNormal(temp1), ""), 2);
    }

    public boolean shoot(String shot) {

        String[] sp = shot.split("", 2);
        int x = parseInt(sp[1]) - 1;
        int y = Coordinate.valueOf(sp[0]).getCoordinate();
        String[][] before = changeToBinary(ships);
        String shooting = "0000000000000000000000000000000000000000000000000000000000000000";
        String[][] after = changeToSquare(shooting);
        after[x][y] = "1";
        addLong(after,x,y);
        return Objects.equals(after[x][y], before[x][y]);
    }
    public String state() {
      String[][] mapsArr = changeToBinary(ships);
      String[][] shotsArr = changeToBinary(shots);
      String[][] temp = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (mapsArr[i][j].equals("1")&&shotsArr[i][j].equals("1")) temp[i][j] = "☒";
                else if (mapsArr[i][j].equals("1")&&shotsArr[i][j].equals("0")) temp[i][j] = "☐";
                else if (mapsArr[i][j].equals("0")&&shotsArr[i][j].equals("1")) temp[i][j] = "×";
                else if (mapsArr[i][j].equals("0")&&shotsArr[i][j].equals("0")) temp[i][j] = ".";
            }
        }
        return StringUtils.join((temp),"");
    }
    public enum Coordinate {
        A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7);
        private final int coordinate;

        Coordinate(int coordinate) {
            this.coordinate = coordinate;
        }

        public int getCoordinate() {
            return coordinate;
        }
    }

    public static void main(String[] args) {
        long map = 0b11110000_00000111_00000000_00110000_00000010_01000000_00000000_00000000L;
        Battleship8x8 battle = new Battleship8x8(map);
        System.out.println(battle.shoot("A1"));
        System.out.println(battle.shoot("B1"));
        System.out.println(battle.state());
    }
}

    /*
    public boolean shoot(String shot) {
        String map = Long.toBinaryString(ships);
        String[] temp = map.split("", 64);
        String[][] maps = new String[8][8];
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                maps[i][j] = temp[counter++];
            }
        }
        String[] sp = shot.split("", 2);
        int cr1 = parseInt(sp[1]) - 1;
        int cr2 = Coordinate.valueOf(sp[0]).getCoordinate();
        StringBuilder binaryMap = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i == cr1) && (j == cr2)) binaryMap.append("1");
                else binaryMap.append("0");
            }
        }
        shots = Long.parseUnsignedLong(binaryMap.toString(), 2);
        mapa();
        return maps[cr1][cr2].equals("1");
    }
    public String[] invert(String[] arr){
        for (int i = 0; i < arr.length / 2 ; i++) {
            Object temp = arr[i];
            arr[i] = arr[arr.length-1-i];
            arr[arr.length-1-i] = (String) temp;
        }
        return arr;
    }
    public String[] mapa(){
        String map1 = Long.toBinaryString(ships);
        String[] temp1 = map1.split("", 64);
        String map2 = Long.toBinaryString(shots);
        String[] temp2 = new String[64];
        String[] temp3 = map2.split("");
        String[] temp4 = invert(temp3);
        for (int i = 0; i < temp4.length; i++) {
            temp2[i] = temp4[i];
        }
        for (int i = 0; i < 64; i++) {
            if(temp2[i] == null) temp2[i] = "0";
        }
        String[] good = invert(temp2);
        String[] tempMaps = new String[64];
        for (int i = 0; i < 64; i++) {
            if ((temp1[i].equals("1")) && (good[i].equals("1"))) tempMaps[i] = "☒";
            else if ((temp1[i].equals("1")) && (good[i].equals("0"))) tempMaps[i] = "☐";
            else if ((temp1[i].equals("0")) && (good[i].equals("1"))) tempMaps[i] = "×";
            else if ((temp1[i].equals("0")) && (good[i].equals("0"))) tempMaps[i] = ".";
        }
        return tempMaps;
    }

    public void state() {
        String[] tempMaps = mapa();
        int count = 0;
        String[][] map = new String[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                map[i][j] = tempMaps[count++];
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public enum Coordinate {
        A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7);
        private final int coordinate;

        Coordinate(int coordinate) {
            this.coordinate = coordinate;
        }

        public int getCoordinate() {
            return coordinate;
        }
    }

    public static void main(String[] args) {
        long map = 0b11110000_00000111_00000000_00110000_00000010_01000000_00000000_00000000L;
        Battleship8x8 battle = new Battleship8x8(map);
        battle.shoot("B1");
        battle.shoot("A1");
        battle.state();
    }
} */