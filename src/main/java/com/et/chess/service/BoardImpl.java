package com.et.chess.service;

import com.et.chess.domain.Board;
import com.et.chess.domain.Stone;




public class BoardImpl implements BoardService {
    private Board board;

    public BoardImpl() {
        this.board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    //判断row,col位置上是否有棋子
    @Override
    public boolean isExist(Integer row, Integer col) {
        if (row < 0 || row >= Board.get_n() || col < 0 || col >= Board.get_m()) {
            return false;
        }
        int stoneId = getGameMapId(row, col);
        if (stoneId != 0 && getStone(stoneId).is_isDead() == false) {
            return true;
        }
        return false;
    }


    //通过id获取棋子的类型
    @Override
    public String getStoneType(Integer id) {
        return Stone.StoneType[id];
    }

    //把row,col位置上的棋子杀了
    @Override
    public Integer killed(Integer row, Integer col) {
        int stoneId = getGameMapId(row, col);
        return killed(stoneId);
    }

    //通过id杀死棋子
    @Override
    public Integer killed(Integer id) {
        if (id >= 1 && id <= 32) {
            setGameMap(getStone(id).get_row(), getStone(id).get_col(), 0);
            getStone(id).set_isDead(true);
        }
        return id;
    }

    //通过id获取棋子的所有信息
    @Override
    public Stone getStone(Integer id) {
        if (id >= 1 && id <= 32) {
            return board.getStone(id);
        } else {
            return null;
        }
    }

    //通过(row,col)获取棋子的所有信息
    @Override
    public Stone getStone(Integer row, Integer col) {
        Stone stone = null;
        if (isExist(row, col)) {
            stone = getStone(getGameMapId(row, col));
        }
        return stone;
    }

    //设置gameMap[row][col]= id;
    @Override
    public void setGameMap(Integer row, Integer col, Integer id) {
        board.setGameMap(row, col, id);
    }

    //获得gameMap[row][col]的id
    @Override
    public Integer getGameMapId(Integer row, Integer col) {
        return board.getGameMapId(row, col);
    }

    //交换gameMap两个位置的id值
    @Override
    public void swap(Integer row1, Integer col1, Integer row2, Integer col2) {
        Integer t = getGameMapId(row1, col1);
        setGameMap(row1, col1, getGameMapId(row2, col2));
        setGameMap(row2, col2, t);
    }

    @Override
    public boolean isSameColor(Integer id1, Integer id2) {
        return getStone(id1).getUser().equals(getStone(id2).getUser());
    }

    @Override
    public boolean isSameColor(Integer row1, Integer col1, Integer row2, Integer col2) {
        boolean result = false;
        if (row1 < 0 || row1 >= Board.get_n() || col1 < 0 || col1 >= Board.get_m()) {
            return false;
        }
        if (row2 < 0 || row2 >= Board.get_n() || col2 < 0 || col2 >= Board.get_m()) {
            return false;
        }
        try {
            result = getStone(row1, col1).getUser().equals(getStone(row2, col2).getUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Integer getNumsTwoPoint(Integer row1, Integer col1, Integer row2, Integer col2) {
        Integer num = 0;
        if (row1.equals( row2)) {
            for (int i = Math.min(col1, col2) + 1; i < Math.max(col1, col2); i++) {
                if (getGameMapId(row1, i) != 0) {
                    num++;
                }
            }
        }
        if (col1.equals( col2)) {
            for (int i = Math.min(row1, row2) + 1; i < Math.max(row1, row2); i++) {
                if (getGameMapId(i, col1) != 0) {
                    num++;
                }
            }
        }
        return num;
    }

    //判断stone能否走到（row,col)
    @Override
    public boolean canMove(Stone stone, Integer row, Integer col) {
        boolean result = false;
        if (stone.get_type() == 0) {
            result = canMoveChe(stone.getUser(), stone.get_row(), stone.get_col(), row, col);
        } else if (stone.get_type() == 1) {
            result = canMoveMa(stone.getUser(), stone.get_row(), stone.get_col(), row, col);
        } else if (stone.get_type() == 2) {
            result = canMoveXiang(stone.getUser(), stone.get_row(), stone.get_col(), row, col);
        } else if (stone.get_type() == 3) {
            result = canMoveShi(stone.getUser(), stone.get_row(), stone.get_col(), row, col);
        } else if (stone.get_type() == 4) {
            result = canMoveJiang(stone.getUser(), stone.get_row(), stone.get_col(), row, col);
        } else if (stone.get_type() == 5) {
            result = canMoveBin(stone.getUser(), stone.get_row(), stone.get_col(), row, col);
        } else if (stone.get_type() == 6) {
            result = canMovePao(stone.getUser(), stone.get_row(), stone.get_col(), row, col);
        }
        return result;
    }

    //判断车能否走到（endRow,endCol)
    @Override
    public boolean canMoveChe(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol) {
        boolean result = false;
        if (startRow.equals( endRow) || startCol .equals( endCol) ) {
            if (startRow.equals( endRow) && startCol .equals( endCol)) {
                return false;
            }
            if (getNumsTwoPoint(startRow, startCol, endRow, endCol) == 0) {
                result = true;
            }
        }
        return result;
    }

    //判断马能否走到（endRow,endCol)
    @Override
    public boolean canMoveMa(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol) {
        if (isIndexOut(endRow, endCol)) {
            return false;
        }
        boolean result = false;
        Integer disRow = startRow - endRow;
        Integer disCol = startCol - endCol;
        if (Math.abs(disRow) == 2 && Math.abs(disCol) == 1) {
            if (disRow < 0) {
                if (!isExist(startRow + 1, startCol)) {
                    result = true;
                }
            } else {
                if (!isExist(startRow - 1, startCol)) {
                    result = true;
                }
            }
        }
        if (Math.abs(disRow) == 1 && Math.abs(disCol) == 2) {
            if (disCol < 0) {
                if (!isExist(startRow, startCol + 1)) {
                    result = true;
                }
            } else {
                if (!isExist(startRow, startCol - 1)) {
                    result = true;
                }
            }
        }
        return result;
    }

    //判断象能否走到（endRow,endCol)
    @Override
    public boolean canMoveXiang(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol) {
        if (isIndexOut(endRow, endCol)) return false;
        boolean result = false;
        Integer disRow = startRow - endRow;
        Integer disCol = startCol - endCol;
        Integer offset = 4;
        Integer f = 1;
        if (user.equals("black")) {
            offset = 5;
            f = -1;
        }
        if (f * endRow <= f * offset) {
            if (Math.abs(disRow) == 2 && Math.abs(disCol) == 2) {
                if (disRow > 0) {
                    if (disCol > 0 && !isExist(startRow - 1, startCol - 1)) {
                        result = true;
                    }
                    if (disCol < 0 && !isExist(startRow - 1, startCol + 1)) {
                        result = true;
                    }
                } else if (disRow < 0) {
                    if (disCol > 0 && !isExist(startRow + 1, startCol - 1)) {
                        result = true;
                    }
                    if (disCol < 0 && !isExist(startRow + 1, startCol + 1)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    //判断士否走到（endRow,endCol)
    @Override
    public boolean canMoveShi(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol) {
        if (isIndexOut(endRow, endCol)) return false;
        boolean result = false;
        Integer disRow = startRow - endRow;
        Integer disCol = startCol - endCol;
        Integer offset = 2;
        Integer f = 1;
        if (user.equals("black")) {
            offset = 7;
            f = -1;
        }
        if (f * endRow <= f * offset && endCol >= 3 && endCol <= 5) {
            if (Math.abs(disRow) == 1 && Math.abs(disCol) == 1) {
                result = true;
            }
        }
        return result;
    }

    //判断将否走到（endRow,endCol)
    @Override
    public boolean canMoveJiang(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol) {
        if (isIndexOut(endRow, endCol)) return false;
        boolean result = false;
        Integer disRow = startRow - endRow;
        Integer disCol = startCol - endCol;
        Integer offset = 2;
        Integer f = 1;
        if (user.equals("black")) {
            offset = 7;
            f = -1;
        }
        if (f * endRow <= f * offset && endCol >= 3 && endCol <= 5) {
            if (Math.abs(disRow) == 0 && Math.abs(disCol) == 1) {
                result = true;
            }
            if (Math.abs(disRow) == 1 && Math.abs(disCol) == 0) {
                result = true;
            }
        }
        offset = 16;
        if (user.equals("black")) {
            offset = -16;
        }
        try {
            Integer diJiangRow = board.get_s()[offset + getGameMapId(startRow, startCol)].get_row();
            Integer diJiangCol = board.get_s()[offset + getGameMapId(startRow, startCol)].get_col();
            if (startCol.equals(diJiangCol) && endRow.equals(diJiangRow) && endCol.equals(diJiangCol) && getNumsTwoPoint(startRow, startCol, endRow, endCol) == 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //判断炮能否走到（endRow,endCol)
    @Override
    public boolean canMovePao(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol) {
        boolean result = false;
        if (startRow.equals(endRow) || startCol.equals(endCol)) {
            if (startRow.equals(endRow) && startCol.equals(endCol)) {
                return false;
            }
            if (getNumsTwoPoint(startRow, startCol, endRow, endCol) == 0 && !isExist(endRow, endCol)) {
                result = true;
            } else if (getNumsTwoPoint(startRow, startCol, endRow, endCol) == 1 && isExist(endRow, endCol)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean canMoveBin(String user, Integer startRow, Integer startCol, Integer endRow, Integer endCol) {
        if (isIndexOut(endRow, endCol)) {
            return false;
        }
        boolean result = false;
        Integer disRow = startRow - endRow;
        Integer disCol = startCol - endCol;
        Integer offset = 4;
        Integer f = 1;
        if (user.equals("black")) {
            offset = 5;
            f = -1;
        }
        if (f * startRow <= f * offset) {
            if (disRow == -f && disCol == 0) {
                result = true;
            }
        } else {
            if (disRow == -f && disCol == 0) {
                result = true;
            }
            if (disRow == 0 && Math.abs(disCol) == 1) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean isGameEnd() {
        if (getStone(5).is_isDead() || getStone(21).is_isDead()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer getStoneScore(Integer id) {
        Integer up = 0;
        if (id == 5) {
            Integer offset = 4;
            Integer f = 1;
            if (getStone(id).getUser().equals("black")) {
                offset = 5;
                f = -1;
            }
            if (f * getStone(id).get_row() > offset) {
                up = 30;
            }
        }
        return Stone.stoneScore[getStone(id).get_type()] + up;
    }

    @Override
    public boolean isIndexOut(Integer row, Integer col) {
        if (row >= Board.get_n() || row < 0 || col >= Board.get_m() || col < 0) {
            return true;
        } else {
            return false;
        }
    }

}

