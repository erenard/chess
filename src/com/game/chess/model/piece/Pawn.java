package com.game.chess.model.piece;

import com.game.chess.model.Board;
import com.game.chess.model.Order;
import com.game.chess.model.PieceType;
import com.game.model.Color;

class Pawn extends Piece {
	private static final long serialVersionUID = 1L;

	public Pawn(Color player) {
		super(PieceType.PAWN, player);
	}

	@Override
	public void validateOrder(Order order, Board board) {
		Color playerColor = board.getCurrentTurnPlayerColor();
		int srcX = order.getSrcX();
		int srcY = order.getSrcY();
		int trgX = order.getTrgX();
		int trgY = order.getTrgY();
		Piece trgPiece = board.getPiece(trgX, trgY);

		if(trgX != srcX) { //Attaque
			if((playerColor.equals(Color.WHITE) && trgY - srcY != 1) //Avancement != 1
			|| (playerColor.equals(Color.BLACK) && trgY - srcY != -1))
				order.setValid(false);
			else {
				if(trgPiece == null) { //Absence d'ennemi
					if((getColor().equals(Color.WHITE) && srcY == 4) //Prise en passant
					|| (getColor().equals(Color.BLACK) && srcY == 3))
						board.validateEnPassant(order);
					else
						order.setValid(false);
				} else {
					//Attaque OK
				}
			}
		} else { //Deplacement
			if(playerColor.equals(Color.WHITE)) { //Double saut initial
				if(trgY != srcY + 1 && (trgY != srcY + 2 || hasMoved()))
					order.setValid(false);
			} else {
				if(trgY != srcY - 1 && (trgY != srcY - 2 || hasMoved()))
					order.setValid(false);
			}
			
			if(trgPiece != null) //Case trg occupé
				order.setValid(false);
			
			if(playerColor.equals(Color.WHITE) && trgY == 7 //Promotion
			|| playerColor.equals(Color.BLACK) && trgY == 0) {
				board.validatePromotion(order);
			}

		}
	}
	
}
