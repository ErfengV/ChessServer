package com.et.chess.dao;


import com.et.chess.domain.Board;
import com.et.chess.domain.Step;
import com.et.chess.domain.Stone;
import com.et.chess.service.BoardService;

import java.util.*;

public class AiImpl implements AI {

    //获取当前局面user方的最优走法，一共进行deep步
    @Override
    public Step getBestStep(BoardService board, String User, Integer deep) {
        List<Step> allStep = getAllStep(board, User);
        if (User.equals("black")) {
            User = "red";
        } else {
            User = "black";
        }
        if (allStep.size() <= 20) deep = 5;
        if (allStep.size() <= 10) deep = 7;
        for (Step step : allStep) {
            fakeMove(board, step);
            if (board.isGameEnd()) return step;
            Integer tScore = getMaxMinScore(board, User, deep - 1, 0x3f3f3f3f, 0xcfcfcfcf);
            step.setStepScore(tScore);
            unFakeMove(board, step);
        }
        allStep.sort(new Comparator<Step>() {
            @Override
            public int compare(Step o1, Step o2) {
                return o2.getStepScore() - o1.getStepScore();
            }
        });
        Integer tid = 0;
        for (int i = 1; i < Math.min(allStep.size(), 15); i++) {
            if (!allStep.get(i).getStepScore().equals(allStep.get(0).getStepScore())) {
                tid = new Random().nextInt(200) % i;
                break;
            }
        }
        return allStep.get(tid);
    }

    //当前局面,尝试走这一步
    @Override
    public void fakeMove(BoardService board, Step step) {
        board.killed(step.getEndRow(), step.getEndCol());
        board.swap(step.getStartRow(), step.getStartCol(), step.getEndRow(), step.getEndCol());
        board.getStone(step.getId()).set_row(step.getEndRow());
        board.getStone(step.getId()).set_col(step.getEndCol());
    }

    //当前局面撤销这一步
    @Override
    public void unFakeMove(BoardService board, Step step) {
        Integer killId = step.getEid();
        board.getStone(step.getId()).set_row(step.getStartRow());
        board.getStone(step.getId()).set_col(step.getStartCol());
        board.setGameMap(step.getStartRow(), step.getStartCol(), board.getStone(step.getId()).get_id());
        board.setGameMap(step.getEndRow(), step.getEndCol(), killId);
        if (killId != 0) {
            board.getStone(killId).set_isDead(false);
            board.getStone(killId).set_row(step.getEndRow());
            board.getStone(killId).set_col(step.getEndCol());

        }
    }

    //计算当前局面得分
    @Override
    public Integer calcScore(BoardService board) {
        Integer blackTotalScore = 0;
        Integer redTotalScore = 0;
        for (int i = 1; i <= 32; i++) {
            //棋子如果死亡则不计分
            if (board.getStone(i).is_isDead()) continue;
            if (board.getStone(i).getUser().equals("black")) {
                blackTotalScore += board.getStoneScore(i);
            } else {
                redTotalScore += board.getStoneScore(i);
            }
        }
        return blackTotalScore - redTotalScore;
    }

    //最大值-最小值搜索
    @Override
    public Integer getMaxMinScore(BoardService board, String user, Integer deep, Integer alpha, Integer beta) {
        if (deep == 0 || board.isGameEnd()) {
            return calcScore(board);
        }
        Integer score;
        List<Step> stepList = getAllStep(board, user);
        for (Step step : stepList) {
            fakeMove(board, step);
            if (user.equals("black")) {
                score = getMaxMinScore(board, "red", deep - 1, alpha, beta);
                unFakeMove(board, step);
                if (score >= alpha) return alpha;
                if (score > beta) {
                    beta = score;
                }
            } else {
                score = getMaxMinScore(board, "black", deep - 1, alpha, beta);
                unFakeMove(board, step);
                if (score <= beta) return beta;
                if (score < alpha) {
                    alpha = score;
                }
            }

        }
        if (user.equals("red")) return alpha;
        else return beta;
    }

    @Override
    public List<Step> getAllStep(BoardService board, String user) {
        Integer[] direction = {2, -2, 1, -1};
        Integer score = 0, offset = 0;
        Integer row, col, eRow, eCol;
        List<Step> stepsList = new ArrayList<>();
        if (user.equals("black")) {
            offset = 16;
        }
        for (int i = 1 + offset; i <= 16 + offset; i++) {
            Stone stone = board.getStone(i);
            if (!stone.is_isDead()) {
                row = stone.get_row();
                col = stone.get_col();
                if (stone.get_type() == 0) {//车的所有走法
                    for (int j = 0; j < Board.get_n(); j++) {
                        score = 0xcfcfcfcf;
                        if (board.isExist(j, col) && board.isSameColor(row, col, j, col)) continue;
                        if (board.canMoveChe(user, row, col, j, col)) {
                            if (board.isExist(j, col)) {
                                score = board.getStoneScore(board.getGameMapId(j, col));
                            }
                            stepsList.add(new Step(i, row, col, board.getGameMapId(j, col), j, col, user, score));
                        }
                    }
                    for (int j = 0; j < Board.get_m(); j++) {
                        score = 0xcfcfcfcf;
                        if (board.isExist(row, j) && board.isSameColor(row, col, row, j)) continue;
                        if (board.canMoveChe(user, row, col, row, j)) {
                            if (board.isExist(row, j)) {
                                score = board.getStoneScore(board.getGameMapId(row, j));
                            }
                            stepsList.add(new Step(i, row, col, board.getGameMapId(row, j), row, j, user, score));
                        }
                    }
                } else if (stone.get_type() == 1) {//马的所有走法
                    for (int j = 0; j < 2; j++) {
                        for (int k = 2; k < 4; k++) {
                            score = 0xcfcfcfcf;
                            eRow = row + direction[j];
                            eCol = col + direction[k];
                            if (board.isExist(eRow, eCol) && board.isSameColor(row, col, eRow, eCol)) continue;
                            if (board.canMoveMa(user, row, col, eRow, eCol)) {
                                if (board.isExist(eRow, eCol)) {
                                    score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                                }
                                stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                            }
                        }
                    }
                    for (int j = 0; j < 2; j++) {
                        for (int k = 2; k < 4; k++) {
                            score = 0xcfcfcfcf;
                            eRow = row + direction[k];
                            eCol = col + direction[j];
                            if (board.isExist(eRow, eCol) && board.isSameColor(row, col, eRow, eCol)) continue;
                            if (board.canMoveMa(user, row, col, eRow, eCol)) {
                                if (board.isExist(eRow, eCol)) {
                                    score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                                }
                                stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                            }
                        }
                    }
                } else if (stone.get_type() == 2) {//象的所有走法
                    for (int j = 0; j < 2; j++) {
                        for (int k = 0; k < 2; k++) {
                            score = 0xcfcfcfcf;
                            eRow = row + direction[j];
                            eCol = col + direction[k];
                            if (board.isExist(eRow, eCol) && board.isSameColor(row, col, eRow, eCol)) continue;
                            if (board.canMoveXiang(user, row, col, eRow, eCol)) {
                                if (board.isExist(eRow, eCol)) {
                                    score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                                }
                                stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                            }
                        }
                    }
                } else if (stone.get_type() == 3) {//士的所有走法
                    for (int j = 2; j < 4; j++) {
                        for (int k = 2; k < 4; k++) {
                            score = 0xcfcfcfcf;
                            eRow = row + direction[j];
                            eCol = col + direction[k];
                            if (board.isExist(eRow, eCol) && board.isSameColor(row, col, eRow, eCol)) continue;
                            if (board.canMoveShi(user, row, col, eRow, eCol)) {
                                if (board.isExist(eRow, eCol)) {
                                    score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                                }
                                stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                            }
                        }
                    }
                } else if (stone.get_type() == 4) {//将的所有走法
                    for (int j = 2; j < 4; j++) {
                        score = 0xcfcfcfcf;
                        eRow = row;
                        eCol = col + direction[j];
                        if (board.isExist(eRow, eCol) && board.isSameColor(row, col, eRow, eCol)) continue;
                        if (board.canMoveJiang(user, row, col, eRow, eCol)) {
                            if (board.isExist(eRow, eCol)) {
                                score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                            }
                            stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                        }
                    }
                    for (int j = 2; j < 4; j++) {
                        score = 0xcfcfcfcf;
                        eRow = row + direction[j];
                        eCol = col;
                        if (board.isExist(eRow, eCol) && board.isSameColor(row, col, eRow, eCol)) continue;
                        if (board.canMoveJiang(user, row, col, eRow, eCol)) {
                            if (board.isExist(eRow, eCol)) {
                                score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                            }
                            stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                        }
                    }
                    score = 0xcfcfcfcf;
                    Integer f = 16;
                    if (user.equals("black")) {
                        f = -16;
                    }
                    eRow = board.getStone(i + f).get_row();
                    eCol = board.getStone(i + f).get_col();
                    if (board.canMoveJiang(user, row, col, eRow, eCol)) {
                        score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                        stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                    }
                } else if (stone.get_type() == 5) {//兵的所有走法
                    for (int j = 2; j < 4; j++) {
                        eRow = row;
                        eCol = col + direction[j];
                        score = 0xcfcfcfcf;
                        if (board.isExist(eRow, eCol) && board.isSameColor(row, col, eRow, eCol)) continue;
                        if (board.canMoveBin(user, row, col, eRow, eCol)) {
                            if (board.isExist(eRow, eCol)) {
                                score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                            }
                            stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                        }
                    }
                    for (int j = 2; j < 4; j++) {
                        eRow = row + direction[j];
                        eCol = col;
                        score = 0xcfcfcfcf;
                        if (board.isExist(eRow, eCol) && board.isSameColor(row, col, eRow, eCol)) continue;
                        if (board.canMoveBin(user, row, col, eRow, eCol)) {
                            if (board.isExist(eRow, eCol)) {
                                score = board.getStoneScore(board.getGameMapId(eRow, eCol));
                            }
                            stepsList.add(new Step(i, row, col, board.getGameMapId(eRow, eCol), eRow, eCol, user, score));
                        }
                    }
                } else if (stone.get_type() == 6) {//炮的所有走法
                    for (int j = 0; j < Board.get_n(); j++) {
                        score = 0xcfcfcfcf;
                        if (board.isExist(j, col) && board.isSameColor(row, col, j, col)) continue;
                        if (board.canMovePao(user, row, col, j, col)) {
                            if (board.isExist(j, col)) {
                                score = board.getStoneScore(board.getGameMapId(j, col));
                            }
                            stepsList.add(new Step(i, row, col, board.getGameMapId(j, col), j, col, user, score));
                        }
                    }
                    for (int j = 0; j < Board.get_m(); j++) {
                        score = 0xcfcfcfcf;
                        if (board.isExist(row, j) && board.isSameColor(row, col, row, j)) continue;
                        if (board.canMovePao(user, row, col, row, j)) {
                            if (board.isExist(row, j)) {
                                score = board.getStoneScore(board.getGameMapId(row, j));
                            }
                            stepsList.add(new Step(i, row, col, board.getGameMapId(row, j), row, j, user, score));
                        }
                    }
                }
            }
        }
        stepsList.sort(new Comparator<Step>() {
            @Override
            public int compare(Step o1, Step o2) {
                return o2.getStepScore() - o1.getStepScore();
            }
        });
        return stepsList;
    }

}
