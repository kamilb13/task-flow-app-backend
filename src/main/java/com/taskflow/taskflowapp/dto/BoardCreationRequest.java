package com.taskflow.taskflowapp.dto;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.User;

public class BoardCreationRequest {
    private Board board;
    private User boardCreator;

    public BoardCreationRequest(Board board, User boardCreator) {
        this.board = board;
        this.boardCreator = boardCreator;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getBoardCreator() {
        return boardCreator;
    }

    public void setBoardCreator(User boardCreator) {
        this.boardCreator = boardCreator;
    }

    @Override
    public String toString() {
        return "BoardCreationRequest{" +
                "board=" + board +
                ", boardCreator=" + boardCreator +
                '}';
    }
}
