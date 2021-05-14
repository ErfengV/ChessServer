package com.et.chess.service;


import com.et.chess.dao.AiImpl;
import com.et.chess.domain.Step;
import com.et.chess.domain.Stone;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class ProtocolServerImpl implements ProtocolServer {
    BoardImpl boardService = new BoardImpl();
    AiImpl AI = new AiImpl();
    Stack<Step> stack = new Stack<Step>();
    Map<String, Integer> mapR = new HashMap<>();
    Map<Integer, String> mapB = new HashMap<>();

    public ProtocolServerImpl() {
        mapR.put("r_c_l", 1);
        mapR.put("r_m_l", 2);
        mapR.put("r_x_l", 3);
        mapR.put("r_s_l", 4);
        mapR.put("r_j", 5);
        mapR.put("r_s_r", 6);
        mapR.put("r_x_r", 7);
        mapR.put("r_m_r", 8);
        mapR.put("r_c_r", 9);
        mapR.put("r_p_l", 10);
        mapR.put("r_p_r", 11);
        mapR.put("r_z_1", 12);
        mapR.put("r_z_2", 13);
        mapR.put("r_z_3", 14);
        mapR.put("r_z_4", 15);
        mapR.put("r_z_5", 16);

        mapB.put(17, "b_c_l");
        mapB.put(25, "b_c_r");
        mapB.put(18, "b_m_l");
        mapB.put(24, "b_m_r");
        mapB.put(19, "b_x_l");
        mapB.put(23, "b_x_r");
        mapB.put(20, "b_s_l");
        mapB.put(22, "b_s_r");
        mapB.put(26, "b_p_l");
        mapB.put(27, "b_p_r");
        mapB.put(28, "b_z_1");
        mapB.put(29, "b_z_2");
        mapB.put(30, "b_z_3");
        mapB.put(31, "b_z_4");
        mapB.put(32, "b_z_5");
        mapB.put(21, "b_j");
    }

    public BoardImpl getBoardService() {
        return boardService;
    }

    @Override
    public String getToMoveSend(String command, int num, String user, String idx, int x, int y) {
        Integer tid = mapR.get(idx);
        Stone stone = boardService.getStone(tid);
        Step step = new Step(tid, stone.get_row(), stone.get_col(), boardService.getGameMapId(x, y), x, y, user, 0);
        getStep(step);
        AI.fakeMove(boardService, step);
        Step bestStep = AI.getBestStep(boardService, "black", 4);
        getStep(bestStep);
        AI.fakeMove(boardService, bestStep);
        String ans = mapB.get(bestStep.getId());
        return ans + "," + bestStep.getEndRow() + "," + bestStep.getEndCol();
    }

    @Override
    public boolean isAgree(String command, int num, String user) {
        Integer nums = new Random().nextInt(8);
        if (nums == 0) {
            return false;
        }
        if (stack.empty()) {
            return false;
        }
        backStep();
        return true;
    }

    @Override
    public void receiveLose(String command, int num, String user) {
        AI = null;
        boardService = null;
    }

    @Override
    public void receiveSucc(String command, int num, String user) {
        AI = null;
        boardService = null;
    }

    @Override
    public void getStep(Step step) {
        stack.push(step);
    }

    @Override
    public void backStep() {
        if (!stack.empty()) {
            AI.unFakeMove(boardService, stack.pop());
        }
        if (!stack.empty()) {
            AI.unFakeMove(boardService, stack.pop());
        }
    }

}
