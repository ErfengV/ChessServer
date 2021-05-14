package com.et.chess.domain;

public class Stone {
    public static String[] StoneType = {"Che", "Ma", "Xiang", "Shi", "Jiang", "Bin", "Pao"};
    public static Integer[] stoneScore = {300, 50, 10, 20, 100000, 10, 80};
    //棋子唯一标识
    private Integer _id;
    //棋子类型
    private Integer _type;
    //棋子所处的行
    private Integer _row;
    //棋子所处的列
    private Integer _col;
    //棋子是否被吃了
    private boolean _isDead;
    //棋子属于红色还是黑色
    private String user;

    public Stone(Integer _id, Integer _type, Integer _row, Integer _col, boolean _isDead, String user) {
        this._id = _id;
        this._type = _type;
        this._row = _row;
        this._col = _col;
        this._isDead = _isDead;
        this.user = user;
    }

    public Stone() {
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public Integer get_type() {
        return _type;
    }

    public void set_type(Integer _type) {
        this._type = _type;
    }

    public Integer get_row() {
        return _row;
    }

    public void set_row(Integer _row) {
        this._row = _row;
    }

    public Integer get_col() {
        return _col;
    }

    public void set_col(Integer _col) {
        this._col = _col;
    }

    public void set_isDead(boolean _isDead) {
        this._isDead = _isDead;
    }

    public boolean is_isDead() {
        return _isDead;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "_id=" + _id +
                ", _row=" + _row +
                ", _col=" + _col +
                ", _isDead=" + _isDead +
                ", user='" + user + '\'' +
                '}';
    }
}
