package com.game.chess.model.piece;

import com.game.chess.model.Board;
import com.game.chess.model.Order;
import com.game.chess.model.PieceType;
import com.game.model.Color;

class Queen extends Piece {
	private static final long serialVersionUID = 1L;

	public Queen(Color player) {
		super(PieceType.QUEEN, player);
	}

	@Override
	public void validateOrder(Order order, Board board) {
		int srcX = order.getSrcX();
		int srcY = order.getSrcY();
		int trgX = order.getTrgX();
		int trgY = order.getTrgY();

		if(srcX != trgX && srcY != trgY && Math.abs(srcX - trgX) != Math.abs(srcY - trgY))
			order.setValid(false);

		if(Math.abs(srcX - trgX) != 0) {
			if(Math.abs(srcY - trgY) != 0) {
				int incX = (trgX - srcX) / Math.abs(srcX - trgX);
				int incY = (trgY - srcY) / Math.abs(srcY - trgY);
				
				for(int i = 1; i < Math.abs(srcX - trgX); i++) {
					Piece piece = board.getPiece(srcX + incX * i, srcY + incY * i);
					if(piece != null) {
						order.setValid(false);
						break;
					}
				}
			} else {
				for(int x = Math.min(srcX, trgX) + 1; x < Math.max(srcX, trgX); x++) {
					Piece piece = board.getPiece(x, srcY);
					if(piece != null) {
						order.setValid(false);
						break;
					}
				}
			}
		} else {
			for(int y = Math.min(srcY, trgY) + 1; y < Math.max(srcY, trgY); y++) {
				Piece piece = board.getPiece(srcX, y);
				if(piece != null) {
					order.setValid(false);
					break;
				}
			}
		}
	}
	
}
