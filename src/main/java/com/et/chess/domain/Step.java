package com.et.chess.domain;

public class Step {
    private Integer id;
    private Integer startRow;
    private Integer startCol;
    private Integer eid;
    private Integer endRow;
    private Integer endCol;
    private String user;
    private Integer stepScore;

    public Step(Integer id, Integer startRow, Integer startCol, String user) {
        this.id = id;
        this.startRow = startRow;
        this.startCol = startCol;
        this.user = user;
    }

    public Step() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getStartCol() {
        return startCol;
    }

    public void setStartCol(Integer startCol) {
        this.startCol = startCol;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }

    public Integer getEndCol() {
        return endCol;
    }

    public void setEndCol(Integer endCol) {
        this.endCol = endCol;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getStepScore() {
        return stepScore;
    }

    public void setStepScore(Integer stepScore) {
        this.stepScore = stepScore;
    }

    public Step(Integer id, Integer startRow, Integer startCol, Integer eid, Integer endRow, Integer endCol, String user, Integer stepScore) {
        this.id = id;
        this.startRow = startRow;
        this.startCol = startCol;
        this.eid = eid;
        this.endRow = endRow;
        this.endCol = endCol;
        this.user = user;
        this.stepScore = stepScore;
    }


    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", startRow=" + startRow +
                ", startCol=" + startCol +
                ", endRow=" + endRow +
                ", endCol=" + endCol +
                ", user='" + user + '\'' +
                ", stepScore=" + stepScore +
                '}';
    }

    public int compareTo(Step arg0) {
        return this.getStepScore().compareTo(arg0.getStepScore());
    }
}
