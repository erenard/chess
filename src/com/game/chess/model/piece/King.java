package com.game.chess.model.piece;

import com.game.chess.model.Board;
import com.game.chess.model.Order;
import com.game.chess.model.PieceType;
import com.game.model.Color;

class King extends Piece {
	private static final long serialVersionUID = 1L;

	public King(Color player) {
		super(PieceType.KING, player);
	}

	@Override
	public void validateOrder(Order order, Board board) {
		int srcX = order.getSrcX();
		int srcY = order.getSrcY();
		int trgX = order.getTrgX();
		int trgY = order.getTrgY();

		if(!hasMoved() && Math.abs(srcX - trgX) == 2 && Math.abs(srcY - trgY) == 0) {
			board.validateCastling(order);
		} else {
			if(Math.abs(srcX - trgX) > 1 || Math.abs(srcY - trgY) > 1)
				order.setValid(false);
		}
	}
}
