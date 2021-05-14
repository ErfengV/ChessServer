package com.et.chess.service;


import com.et.chess.domain.Step;

public interface ProtocolServer {

    /**
     * 移动
     *
     * @param command 接收到的指令
     * @param num     房间号
     * @param user    是黑色方还是红色方  黑：“black”  红：“red”
     * @param idx     移动的棋子的索引号
     * @param x       移动的目标位置
     * @param y       移动的目标位置
     * @return 返回一个需要发送的移动协议   格式：move|num|user|idx,x,y
     */
    public String getToMoveSend(String command, int num, String user, String idx, int x, int y);

    /**
     * 悔棋
     *
     * @param command 接收到的指令
     * @param num     房间号
     * @param user    是黑色方还是红色方  黑：“black”  红：“red”
     * @return true：同意悔棋  false：不同意
     */
    public boolean isAgree(String command, int num, String user);

    /**
     * 认输
     *
     * @param command 接收到的指令
     * @param num     房间号
     * @param user    是黑色方还是红色方  黑：“black”  红：“red”
     */
    public void receiveLose(String command, int num, String user);

    /**
     * 游戏结束
     *
     * @param command
     * @param num
     * @param user
     */
    public void receiveSucc(String command, int num, String user);

    public void getStep(Step step);

    public void backStep();
}
