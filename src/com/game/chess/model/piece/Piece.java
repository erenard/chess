package com.game.chess.model.piece;

import java.io.Serializable;

import com.game.chess.model.Board;
import com.game.chess.model.Order;
import com.game.chess.model.PieceType;
import com.game.model.Color;


public abstract class Piece implements Serializable {
	private static final long serialVersionUID = 1L;
	private Color pieceColor;
	private PieceType pieceType;
//	private String pieceGraphic;
	private boolean moved = false;
	
	public Piece(PieceType pieceType, Color pieceColor) {
		this.pieceType = pieceType;
		this.pieceColor = pieceColor;
	}
	
	public abstract void validateOrder(Order order, Board board);

//	public String getPieceGraphic() {
//		return pieceGraphic;
//	}
//	
//	protected void setPieceGraphic(String string) {
//		pieceGraphic = string;
//	}
	
	public PieceType getPieceType() {
		return pieceType;
	}
	
	public Color getColor() {
		return pieceColor;
	}

	public boolean hasMoved() {
		return moved;
	}
	
	public void move() {
		moved = true;
	}
	
	public static Piece newInstance(PieceType pieceType, Color pieceColor) {
		switch(pieceType) {
		case KING: return new King(pieceColor);
		case QUEEN: return new Queen(pieceColor);
		case BISHOP: return new Bishop(pieceColor);
		case KNIGHT: return new Knight(pieceColor);
		case ROOK: return new Rook(pieceColor);
		case PAWN: return new Pawn(pieceColor);
		}
		return null;
	}
}
