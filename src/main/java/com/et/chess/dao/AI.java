package com.et.chess.dao;


import com.et.chess.domain.Step;
import com.et.chess.service.BoardService;

import java.util.List;

public interface AI {

    /**
     * 获取当前局面的最优走法
     *
     * @param board 当前的局面
     * @param user  当前走棋的那一方
     * @param deep  搜索的最大深度
     * @return 最优一步, 由step对象存储
     */
    Step getBestStep(BoardService board, String user, Integer deep);

    /**
     * 获取当前局面的所有走法
     *
     * @param board 当前的局面
     * @param user  当前走棋的那一方
     * @return steplist
     */
    List<Step> getAllStep(BoardService board, String user);

    /**
     * 假装走这一步
     *
     * @param board 当前局面
     * @param step  要走的那一步
     * @return 走到那一步杀死的棋子id
     */
    void fakeMove(BoardService board, Step step);

    /**
     * 撤销走的这一步
     *
     * @param board 当前局面
     * @param step  要撤销的那一步
     */
    void unFakeMove(BoardService board, Step step);

    /**
     * 计算当前局面的得分
     *
     * @param board 当前的局面
     * @return 获得的分数
     */
    Integer calcScore(BoardService board);

    /**
     * 获取当前局面的最优得分
     *
     * @param board 当前局面
     * @param user  当前是哪一方
     * @param deep  当前深度
     * @param alpha 红色方的最优值
     * @param beta  黑色方的最优值
     * @return 最优得分
     */
    Integer getMaxMinScore(BoardService board, String user, Integer deep, Integer alpha, Integer beta);

}
