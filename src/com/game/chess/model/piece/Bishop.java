package com.game.chess.model.piece;

import com.game.chess.model.Board;
import com.game.chess.model.Order;
import com.game.chess.model.PieceType;
import com.game.model.Color;

class Bishop extends Piece {
	private static final long serialVersionUID = 1L;

	public Bishop(Color playerColor) {
		super(PieceType.BISHOP, playerColor);
	}

	@Override
	public void validateOrder(Order order, Board board) {
		int srcX = order.getSrcX();
		int srcY = order.getSrcY();
		int trgX = order.getTrgX();
		int trgY = order.getTrgY();

		if(Math.abs(srcX - trgX) != Math.abs(srcY - trgY))
			order.setValid(false);

		int incX = (trgX - srcX) / Math.abs(srcX - trgX);
		int incY = (trgY - srcY) / Math.abs(srcY - trgY);
		
		for(int i = 1; i < Math.abs(srcX - trgX); i++) {
			Piece piece = board.getPiece(srcX + incX * i, srcY + incY * i);
			if(piece != null) {
				order.setValid(false);
				break;
			}
		}
	}
	
}
