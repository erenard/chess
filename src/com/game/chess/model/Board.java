package com.game.chess.model;

import java.util.ArrayList;
import java.util.List;

import com.game.chess.model.Order.OrderType;
import com.game.chess.model.piece.Piece;
import com.game.model.Color;

public class Board {

	private Piece [][] board;
	private Color playerColorCurrentTurn;
	private List<Order> history;
	
	private void clearBoard() {
		for (int i = 0; i < board.length; i++) {
			Piece [] line = board[i];
			for (int j = 0; j < line.length; j++) {
				line[j] = null;
			}
		}
	}
	
	public Board() {
		board = new Piece[8][8];
		history = new ArrayList<Order>();
		clearBoard();
		// Rook
		setPiece(0, 0, Piece.newInstance(PieceType.ROOK, Color.WHITE));
		setPiece(7, 0, Piece.newInstance(PieceType.ROOK, Color.WHITE));
		setPiece(0, 7, Piece.newInstance(PieceType.ROOK, Color.BLACK));
		setPiece(7, 7, Piece.newInstance(PieceType.ROOK, Color.BLACK));
		// Knight
		setPiece(1, 0, Piece.newInstance(PieceType.KNIGHT, Color.WHITE));
		setPiece(6, 0, Piece.newInstance(PieceType.KNIGHT, Color.WHITE));
		setPiece(1, 7, Piece.newInstance(PieceType.KNIGHT, Color.BLACK));
		setPiece(6, 7, Piece.newInstance(PieceType.KNIGHT, Color.BLACK));
		// Bishop
		setPiece(2, 0, Piece.newInstance(PieceType.BISHOP, Color.WHITE));
		setPiece(5, 0, Piece.newInstance(PieceType.BISHOP, Color.WHITE));
		setPiece(2, 7, Piece.newInstance(PieceType.BISHOP, Color.BLACK));
		setPiece(5, 7, Piece.newInstance(PieceType.BISHOP, Color.BLACK));
		// Queen
		setPiece(4, 0, Piece.newInstance(PieceType.QUEEN, Color.WHITE));
		setPiece(3, 7, Piece.newInstance(PieceType.QUEEN, Color.BLACK));
		// King
		setPiece(3, 0, Piece.newInstance(PieceType.KING, Color.WHITE));
		setPiece(4, 7, Piece.newInstance(PieceType.KING, Color.BLACK));
		// Pawn
		for (int i = 0; i < 8; i++) {
			setPiece(i, 1, Piece.newInstance(PieceType.PAWN, Color.WHITE));
			setPiece(i, 6, Piece.newInstance(PieceType.PAWN, Color.BLACK));
		}

		playerColorCurrentTurn = Color.WHITE;
	}

	public void setPiece(int x, int y, Piece piece) {
		board[y][x] = piece;
	}
	
	public Piece getPiece(int x, int y) {
		return board[y][x];
	}

	public Color getCurrentTurnPlayerColor() {
		return playerColorCurrentTurn;
	}

	@Override
	public String toString() {
		String externalLine	= "    A   B   C   D   E   F   G   H\n";
		String line			= "  +---+---+---+---+---+---+---+---+\n";
		String returned = "";
		returned += externalLine;
		returned += line;
		for(int y = 7; y > -1; y--) {
			returned += (y + 1) + " |";
			for(int x = 0; x < 8; x++) {
				Piece piece = getPiece(x, y);
				if(piece != null) {
					String pieceGraphic = piece.getPieceType().getShortName().toUpperCase();
					if(piece.getColor().equals(Color.BLACK)) pieceGraphic = pieceGraphic.toLowerCase();
						returned += " " + pieceGraphic;
				} else {
					returned += "  ";
				}
				returned += " |";
			}
			returned += " " + (y + 1) + "\n";
			returned += line;
		}
		returned += externalLine;
		return returned;
	}

	public void executeOrder(Order order) {
		int srcX = order.getSrcX();
		int srcY = order.getSrcY();
		int trgX = order.getTrgX();
		int trgY = order.getTrgY();
		OrderType orderType = order.getType();
		Piece piece = getPiece(srcX, srcY);
		setPiece(srcX, srcY, null);
		switch(orderType) {
		case MOVE:
			setPiece(trgX, trgY, piece);
			break;
		case REMOVE:
			setPiece(trgX, trgY, null);
			break;
		case PROMOTE:
			piece = Piece.newInstance(order.getPromotionType(), getCurrentTurnPlayerColor());
			setPiece(trgX, trgY, piece);
			break;
		}
		if(order.hasSubOrder()) {
			executeOrder(order.getSubOrder());
		}
	}
	
	public boolean executeTurn(Order order) {
		history.add(order);
		//TODO Implémenter les conditions de fin de jeu
		if (playerColorCurrentTurn.equals(Color.WHITE)) {
			playerColorCurrentTurn = Color.BLACK;
		} else {
			playerColorCurrentTurn = Color.WHITE;
		}
		return true;
	}
	
	public void validateOrder(Order order) {
		int srcX = order.getSrcX();
		int srcY = order.getSrcY();
		Piece piece = getPiece(srcX, srcY);
		if(piece == null || !piece.getColor().equals(getCurrentTurnPlayerColor())) {
			order.setValid(false);
		} else {
			Color playerColor = getCurrentTurnPlayerColor();
			order.setPieceType(piece.getPieceType());
			order.setPieceColor(playerColor);
			int trgX = order.getTrgX();
			int trgY = order.getTrgY();
			Piece trgPiece = getPiece(trgX, trgY);
			if(trgPiece != null && trgPiece.getColor().equals(playerColor))
				order.setValid(false);
			piece.validateOrder(order, this);
		}
	}
	
	public void validateCastling(Order order) {//Le roi ne doit pas etre en echec avant et après le roque
		int kingSrcX = order.getSrcX();
		int kingTrgX = order.getTrgX();
		int kingY = order.getSrcY();
		if(getPiece(kingSrcX, kingY).hasMoved()) {
			order.setValid(false);
		} else {
			int roqueSide = (int) Math.signum(kingTrgX - kingSrcX);;
			int rookY = kingY;
			int rookX;
			if(roqueSide > 0) rookX = 7;
			else rookX = 0;
			Piece rook = getPiece(rookX, rookY);
			if(rook == null || rook.hasMoved()) {
				order.setValid(false);
			} else {
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append((char)('a' + rookX));
				stringBuffer.append(rookY + 1);
				stringBuffer.append(" ");
				stringBuffer.append((char)('a' + kingTrgX - roqueSide));
				stringBuffer.append(rookY + 1);
				Order roqueOrder = new Order(stringBuffer.toString());
//				rook.validateOrder(roqueOrder, this);
				validateOrder(roqueOrder);
				if(roqueOrder.isValid()) {
					order.setSubOrder(roqueOrder);
				}
			}
		}
	}

	public void validateEnPassant(Order order) {
		Order lastOrder = history.get(history.size() - 1);
		if(lastOrder != null) {
			Piece lastOrderPiece = getPiece(lastOrder.getTrgX(), lastOrder.getTrgY());
			if(lastOrderPiece != null
				&& lastOrderPiece.getPieceType().equals(PieceType.PAWN)
				&& Math.abs(lastOrder.getSrcY() - lastOrder.getTrgY()) == 2) {
				Order removeOrder = new Order(lastOrder.getStringOrder());
				removeOrder.setType(OrderType.REMOVE);
				order.setSubOrder(removeOrder);
			} else {
				order.setValid(false);
			}
		} else {
			order.setValid(false);
		}
	}

	public void validatePromotion(Order order) {
		Order promotion = new Order(order.getStringOrder());
		promotion.setType(OrderType.PROMOTE);
		order.setSubOrder(promotion);
	}
}
