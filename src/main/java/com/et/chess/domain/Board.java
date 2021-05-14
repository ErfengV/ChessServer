package com.et.chess.domain;

public class Board {
    //棋盘行数
    static private Integer _n = 10;
    //棋盘列数
    static private Integer _m = 9;
    //棋子
    private Stone[] _s = new Stone[33];
    //棋盘 为零表示没有棋子 1-32表示那个位置的棋子ID
    private Integer[][] GameMap = new Integer[10][9];

    public Stone[] get_s() {
        return _s;
    }

    public Integer[][] getGameMap() {
        return GameMap;
    }

    static public Integer get_n() {
        return _n;
    }

    static public Integer get_m() {
        return _m;
    }

    public Board() {
        for (int i = 0; i < _n; i++) {
            for (int j = 0; j < _m; j++) {
                this.GameMap[i][j] = 0;
            }
        }
        //棋子ID
        int StartStone = 0;

        for (int i = 0; i < 5; i++) {
            this.GameMap[0][i] = ++StartStone;
            _s[StartStone] = new Stone(StartStone, i, 0, i, false, "red");
        }
        for (int i = 5, j = 3; i < _m; i++, j--) {
            this.GameMap[0][i] = ++StartStone;
            _s[StartStone] = new Stone(StartStone, j, 0, i, false, "red");
        }
        this.GameMap[2][1] = ++StartStone;
        _s[StartStone] = new Stone(StartStone, 6, 2, 1, false, "red");
        this.GameMap[2][7] = ++StartStone;
        _s[StartStone] = new Stone(StartStone, 6, 2, 7, false, "red");
        for (int i = 0; i < _m; i++) {
            if (i % 2 == 0) {
                this.GameMap[3][i] = ++StartStone;
                _s[StartStone] = new Stone(StartStone, 5, 3, i, false, "red");
            }
        }
        for (int i = 0; i < 5; i++) {
            this.GameMap[9][i] = ++StartStone;
            _s[StartStone] = new Stone(StartStone, i, 9, i, false, "black");
        }
        for (int i = 5, j = 3; i < _m; i++, j--) {
            this.GameMap[9][i] = ++StartStone;
            _s[StartStone] = new Stone(StartStone, j, 9, i, false, "black");
        }
        this.GameMap[7][1] = ++StartStone;
        _s[StartStone] = new Stone(StartStone, 6, 7, 1, false, "black");
        this.GameMap[7][7] = ++StartStone;
        _s[StartStone] = new Stone(StartStone, 6, 7, 7, false, "black");
        for (int i = 0; i < _m; i++) {
            if (i % 2 == 0) {
                this.GameMap[6][i] = ++StartStone;
                _s[StartStone] = new Stone(StartStone, 5, 6, i, false, "black");
            }
        }
    }

    //获得棋子的种类
    public String getStoneType(int id) {
        return Stone.StoneType[_s[id].get_type()];
    }

    //获取gameMap[row][col]的值
    public Integer getGameMapId(Integer row, Integer col) {
        return GameMap[row][col];
    }

    //
    public void setGameMap(Integer row, Integer col, Integer id) {
        GameMap[row][col] = id;
    }

    //设置gameMap[row][col]的值
    public Stone getStone(Integer id) {
        return _s[id];
    }

    //显示棋盘
    public void displayGameMap() {
        System.out.print("    ");
        for (int i = 0; i < _m; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();
        System.out.print("    ");
        for (int i = 0; i < _m; i++) {
            System.out.print("====");
        }
        System.out.println();
        for (int i = _n - 1; i >= 0; i--) {
            System.out.print(i + "  |");
            for (int j = 0; j < _m; j++) {
                if (GameMap[i][j] != 0) {
                    System.out.print(GameMap[i][j]);
                    if (GameMap[i][j] < 10) {
                        System.out.print(getStoneType(GameMap[i][j]).charAt(0) + "  ");
                    } else {
                        System.out.print(getStoneType(GameMap[i][j]).charAt(0) + " ");
                    }
                } else System.out.print("    ");
            }
            System.out.println();
        }
    }
}

