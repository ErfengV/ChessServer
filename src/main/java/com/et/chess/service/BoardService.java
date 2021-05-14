package com.et.chess.service;


import com.et.chess.domain.Stone;

public interface BoardService {

    /**
     * 判断该坐标是否有棋子
     *
     * @param row 行坐标
     * @param col 列坐标
     * @return 有棋子返回true
     */
    public boolean isExist(Integer row, Integer col);

    /**
     * 通过坐标杀死棋子
     *
     * @param row 棋子行坐标
     * @param col 棋子列坐标
     * @return 杀死的棋子id, 如果没有棋子则返回0
     */
    public Integer killed(Integer row, Integer col);

    /**
     * 通过id杀死棋子
     *
     * @param id 想要杀死的棋子id
     * @return 杀死的棋子id
     */
    public Integer killed(Integer id);

    /**
     * 通过id获取棋子的类型
     *
     * @param id 棋子的id
     * @return 棋子的类型
     */
    public String getStoneType(Integer id);

    /**
     * 通过id获取棋子的所有信息
     *
     * @param id 棋子的id
     * @return 棋子的所有信息
     */
    public Stone getStone(Integer id);

    /**
     * 通过坐标获取棋子的所有信息
     *
     * @param row 棋子的行坐标
     * @param col 棋子的列坐标
     * @return 棋子的所有信息
     */
    public Stone getStone(Integer row, Integer col);

    /**
     * 设置gameMap[row][col]上的棋子id
     *
     * @param row 棋子的行坐标
     * @param col 棋子的列坐标
     * @param id  想要设置的坐标
     */
    public void setGameMap(Integer row, Integer col, Integer id);

    /**
     * 获取gameMap[row][col]的棋子id
     *
     * @param row 棋子的行坐标
     * @param col 棋子的列坐标
     * @return gameMap[row][col]的棋子id
     */
    public Integer getGameMapId(Integer row, Integer col);

    /**
     * 交换gameMap[row1][co1],gameMap[row2][col2]的值
     *
     * @param row1 第一个棋子的行坐标
     * @param col1 第一个棋子的列坐标
     * @param row2 第二个棋子的行坐标
     * @param col2 第二个棋子的列坐标
     */
    public void swap(Integer row1, Integer col1, Integer row2, Integer col2);

    /**
     * 判断两个棋子是不是同一方
     *
     * @param id1 第一个棋子的id
     * @param id2 第二个棋子的id
     * @return 是同一方返回true
     */
    public boolean isSameColor(Integer id1, Integer id2);

    /**
     * 判断两个棋子是不是同一方
     *
     * @param row1 第一个棋子的行坐标
     * @param col1 第一个棋子的列坐标
     * @param row2 第二个棋子的行坐标
     * @param col2 第二个棋子的列坐标
     * @return 是同一方返回true
     */
    public boolean isSameColor(Integer row1, Integer col1, Integer row2, Integer col2);

    /**
     * 判断同一行或同一列的两个位置之间有多少个棋子，不包括这两个位置
     *
     * @param row1 第一个棋子的行坐标
     * @param col1 第一个棋子的列坐标
     * @param row2 第二个棋子的行坐标
     * @param col2 第二个棋子的列坐标
     * @return 棋子的个数
     */
    public Integer getNumsTwoPoint(Integer row1, Integer col1, Integer row2, Integer col2);

    /**
     * 判断该棋子是否能移动到gameMap[row][col]
     *
     * @param stone 棋子的所有信息
     * @param row   移动位置的行坐标
     * @param col   移动位置的列坐标
     * @return 能移动返回true
     */
    public boolean canMove(Stone stone, Integer row, Integer col);

    /**
     * 判断车能不能走到gameMap[row][col]
     *
     * @param user     棋子是红色方还是黑色 黑：“black”  红：“red”
     * @param startRow 初始行坐标
     * @param startCol 初始列坐标
     * @param endRow   目的行坐标
     * @param endCol   目的列坐标
     * @return 能走到返回true
     */
    public boolean canMoveChe(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol);

    /**
     * 判断马能不能走到gameMap[row][col]
     *
     * @param user     棋子是红色方还是黑色 黑：“black”  红：“red”
     * @param startRow 初始行坐标
     * @param startCol 初始列坐标
     * @param endRow   目的行坐标
     * @param endCol   目的列坐标
     * @return 能走到返回true
     */
    public boolean canMoveMa(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol);

    /**
     * 判断象能不能走到gameMap[row][col]
     *
     * @param user     棋子是红色方还是黑色 黑：“black”  红：“red”
     * @param startRow 初始行坐标
     * @param startCol 初始列坐标
     * @param endRow   目的行坐标
     * @param endCol   目的列坐标
     * @return 能走到返回true
     */
    public boolean canMoveXiang(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol);

    /**
     * 判断士能不能走到gameMap[row][col]
     *
     * @param user     棋子是红色方还是黑色 黑：“black”  红：“red”
     * @param startRow 初始行坐标
     * @param startCol 初始列坐标
     * @param endRow   目的行坐标
     * @param endCol   目的列坐标
     * @return 能走到返回true
     */
    public boolean canMoveShi(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol);

    /**
     * 判断将能不能走到gameMap[row][col]
     *
     * @param user     棋子是红色方还是黑色 黑：“black”  红：“red”
     * @param startRow 初始行坐标
     * @param startCol 初始列坐标
     * @param endRow   目的行坐标
     * @param endCol   目的列坐标
     * @return 能走到返回true
     */
    public boolean canMoveJiang(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol);

    /**
     * 判断炮能不能走到gameMap[row][col]
     *
     * @param user     棋子是红色方还是黑色 黑：“black”  红：“red”
     * @param startRow 初始行坐标
     * @param startCol 初始列坐标
     * @param endRow   目的行坐标
     * @param endCol   目的列坐标
     * @return 能走到返回true
     */
    public boolean canMovePao(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol);

    /**
     * 判断兵能不能走到gameMap[row][col]
     *
     * @param user     棋子是红色方还是黑色 黑：“black”  红：“red”
     * @param startRow 初始行坐标
     * @param startCol 初始列坐标
     * @param endRow   目的行坐标
     * @param endCol   目的列坐标
     * @return 能走到返回true
     */
    public boolean canMoveBin(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol);

    /**
     * 判断棋局是否结束
     *
     * @return 结束返回true
     */
    public boolean isGameEnd();

    /**
     * 通过id获取棋子的得分
     *
     * @param id 棋子的id
     * @return 返回棋子的分数
     */
    public Integer getStoneScore(Integer id);

    /**
     * 判断坐标是否越界
     *
     * @param row 行坐标
     * @param col 列坐标
     * @return 越界返回true
     */
    public boolean isIndexOut(Integer row, Integer col);
}

