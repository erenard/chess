package com.game.chess.model.piece;

import com.game.chess.model.Board;
import com.game.chess.model.Order;
import com.game.chess.model.PieceType;
import com.game.model.Color;


class Rook extends Piece {
	private static final long serialVersionUID = 1L;

	public Rook(Color player) {
		super(PieceType.ROOK, player);
	}

	@Override
	public void validateOrder(Order order, Board board) {
		int srcX = order.getSrcX();
		int srcY = order.getSrcY();
		int trgX = order.getTrgX();
		int trgY = order.getTrgY();

		if(srcX != trgX && srcY != trgY)
			order.setValid(false);
		
		if(Math.abs(srcX - trgX) != 0) { //Deplacement sur une ligne
			for(int x = Math.min(srcX, trgX) + 1; x < Math.max(srcX, trgX); x++) {
				Piece piece = board.getPiece(x, srcY);
				if(piece != null) {
					order.setValid(false);
					break;
				}
			}
		} else { //Deplacement sur une colonne
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
