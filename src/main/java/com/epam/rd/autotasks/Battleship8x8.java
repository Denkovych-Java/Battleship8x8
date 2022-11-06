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

    private String[][] changeToSquare(String arr) {
        String[] binaryMap = arr.split("", 64);
        String[][] temp = new String[8][8];
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp[i][j] = binaryMap[counter++];
            }
        }
        return temp;
    }

    private String[] changeToNormal(String[][] arr) {
        String[] temp = new String[64];
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                temp[counter++] = arr[i][j];
            }
        }
        return temp;
    }

    private String[][] changeToBinary(long value) {
        String maps = Long.toBinaryString(value);
        String[] str2 = maps.split("");
        String[] temp = new String[64];
        System.arraycopy(str2, 0, temp, temp.length - str2.length, str2.length);
        for (int i = 0; i < 64; i++) {
            if (temp[i] == null) temp[i] = "0";
        }
        String ar = StringUtils.join(temp, "");
        return changeToSquare(ar);

    }

    private void addLong(String[][] arr, int x, int y) {
        String[][] temp1;
        if (shots == 0L) {
            temp1 = arr;
        } else {
            temp1 = changeToBinary(shots);
        }
        temp1[x][y] = "1";
        String[] temp = changeToNormal(temp1);
        shots = Long.parseUnsignedLong(StringUtils.join(temp, ""), 2);
    }

    private int checkCoordinateY(String str) {
        String[] sp = str.split("", 2);
        if (Objects.equals(sp[0], "A")) return 0;
        if (Objects.equals(sp[0], "B")) return 1;
        if (Objects.equals(sp[0], "C")) return 2;
        if (Objects.equals(sp[0], "D")) return 3;
        if (Objects.equals(sp[0], "E")) return 4;
        if (Objects.equals(sp[0], "F")) return 5;
        if (Objects.equals(sp[0], "G")) return 6;
        else return 7;

    }

    public boolean shoot(String shot) {
        String[] sp = shot.split("", 2);
        int x = parseInt(sp[1]) - 1;
        int y = checkCoordinateY(shot);
        String[][] before = changeToBinary(ships);
        String shooting = "0000000000000000000000000000000000000000000000000000000000000000";
        String[][] after = changeToSquare(shooting);
        after[x][y] = "1";
        addLong(after, x, y);
        return Objects.equals(after[x][y], before[x][y]);
    }

    public String state() {
        String[][] mapsArr = changeToBinary(ships);
        String[][] shotsArr = changeToBinary(shots);
        String[][] temp = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (mapsArr[i][j].equals("1") && shotsArr[i][j].equals("1")) temp[i][j] = "☒";
                else if (mapsArr[i][j].equals("1") && shotsArr[i][j].equals("0")) temp[i][j] = "☐";
                else if (mapsArr[i][j].equals("0") && shotsArr[i][j].equals("1")) temp[i][j] = "×";
                else if (mapsArr[i][j].equals("0") && shotsArr[i][j].equals("0")) temp[i][j] = ".";
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                res.append(temp[i][j]);
            }
            res.append("\n");
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        long map = 0b11110000_00000111_00000000_00110000_00000010_01000000_00000000_00000000L;
        Battleship8x8 battle = new Battleship8x8(map);

        System.out.println(battle.shoot("A1"));
        System.out.println(battle.shoot("B2"));
        System.out.println(battle.shoot("C3"));
        System.out.println(battle.shoot("D4"));
        System.out.println(battle.shoot("E5"));
        System.out.println(battle.shoot("F6"));
        System.out.println(battle.shoot("G7"));


        System.out.println(battle.state());
    }
}