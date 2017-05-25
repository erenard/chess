package com.game.chess;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import com.game.chess.model.Board;
import com.game.chess.model.Order;
import com.game.chess.model.Order.OrderType;
import com.game.chess.model.piece.Piece;
import com.game.model.Color;
import com.game.model.Game;
import com.game.model.User;


public class Chess extends Game {
	@Override
	public short getMaxPlayerNumber() {
		return 2;
	}

	@Override
	public Color[] getPossibleColors() {
		Color [] returned = new Color[2];
		returned[0] = Color.WHITE;
		returned[1] = Color.BLACK;
		return returned;
	}

	@Override
	public short getMinPlayerNumber() {
		return 2;
	}
	private Board board;
	private User whitePlayer;
	private User blackPlayer;
	private Order order;
	private SortedMap<Long, Order> history;
	
	public Chess(User user) {
		super(Type.CHESS, user);
		board = new Board();
		history = Collections.synchronizedSortedMap(new TreeMap<Long, Order>());
	}

	public User getPlayer(Color playerColor) {
		if(playerColor.equals(Color.WHITE)) return whitePlayer;
		else return blackPlayer;
	}

	public String toString() {
		return board.toString();
	}
	
	public String toXmlString() {
		return board.toString();
	}

	public void executeTurn() {
		board.executeOrder(order);
		board.executeTurn(order);
		history.put(getCycle(), order);
		order = null;
	}

	public void validateOrder(Order order) {
		this.order = order;
		board.validateOrder(order);
	}
	
	public boolean needPromotion() {
		return order.hasSubOrder() && order.getSubOrder().getType().equals(OrderType.PROMOTE);
	}

	public Color getCurrentTurnPlayerColor() {
		return board.getCurrentTurnPlayerColor();
	}

	public User getCurrentTurnPlayer() {
		if(board.getCurrentTurnPlayerColor().equals(Color.WHITE))
			return whitePlayer;
		else
			return blackPlayer;
	}

	public void setPromotion(String pieceType) {
		order.getSubOrder().setPromotionType(pieceType);
	}

	public Piece getPiece(int x, int y) {
		return board.getPiece(x, y);
	}

	public SortedMap<Long, Order> getHistoryTail(Long fromCycle) {
		return history.tailMap(fromCycle);
	}
	@Override
	protected boolean acceptPlayer(User user) {
		if(user.getColor() != null) {
			if(user.getColor().equals(Color.WHITE) && whitePlayer == null) {
				whitePlayer = user;
				return true;
			} else if(user.getColor().equals(Color.BLACK) && blackPlayer == null) {
				blackPlayer = user;
				return true;
			}
		} else {
			if(whitePlayer == null) {
				whitePlayer = user;
				return true;
			} else if(blackPlayer == null) {
				blackPlayer = user;
				return true;
			}
		}
		return false;
	}
}
