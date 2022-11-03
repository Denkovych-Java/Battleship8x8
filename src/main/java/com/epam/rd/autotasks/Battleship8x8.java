package com.epam.rd.autotasks;

import static java.lang.Integer.parseInt;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

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
        return maps[cr1][cr2].equals("1");
    }

    public void state() {
        String map1 = Long.toBinaryString(ships);
        String[] temp1 = map1.split("", 64);
        String map2 = Long.toBinaryString(shots);
        String[] temp2 = new String[64];
        String[] temp3 = map2.split("", 64);
        int c = 0;
        for (int i = temp3.length-1; i >= 0; i--) {
           temp2[c++] = temp3[i];
        }
        for (int i = 0; i < 64; i++) {
            if(temp2[i].equals("")){temp2[i]="0";}
        }
        int co = 0;
        String[] temp4 = new String[64];
        for (int i = 63; i >= 0 ; i--) {
            temp4[co++] = temp2[i];
        }
        temp2=temp4;
        String[] tempMaps = new String[64];
        for (int i = 0; i < 64; i++) {
            if ((temp1[i].equals("1")) && (temp2[i].equals("1"))) tempMaps[i] = "☒";
            else if ((temp1[i].equals("1")) && (temp2[i].equals("0"))) tempMaps[i] = "☐";
            else if ((temp1[i].equals("0")) && (temp2[i].equals("1"))) tempMaps[i] = "×";
            else if ((temp1[i].equals("0")) && (temp2[i].equals("0"))) tempMaps[i] = ".";
        }
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
        battle.state();
    }
}