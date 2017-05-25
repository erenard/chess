package com.game.chess.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.game.chess.Chess;
import com.game.chess.model.Order;
import com.game.model.Color;
import com.game.model.User;

public class ConsoleInterface {

	private BufferedReader stdin = new BufferedReader(new InputStreamReader(
			System.in));

	public static void main(String[] args) {
		ConsoleInterface consoleInterface = new ConsoleInterface();
		String[] names = consoleInterface.askPlayerNames();
		User user1 = new User();
		user1.setColor(Color.WHITE);
		user1.setName(names[0]);
		Chess game = new Chess(user1);
		game.addUser(user1);
		User user2 = new User();
		user2.setColor(Color.BLACK);
		user2.setName(names[1]);
		game.addUser(user2);
		boolean continuePlay = true;
		do {

			consoleInterface.redrawScreen(game);
			boolean orderIsValid;
			Order order;
			do {
				order = consoleInterface.readOrder(game.getPlayer(game.getCurrentTurnPlayerColor()));
				game.validateOrder(order);
				if (game.needPromotion()) {
					String promotion = consoleInterface.askPromotion();
					game.setPromotion(promotion);
				}
				orderIsValid = order.isValid();
			} while (!orderIsValid);
			game.executeTurn();
		} while (continuePlay);
	}

	public String[] askPlayerNames() {
		String[] returned = new String[2];
		while (returned[0] == null) {
			try {
				System.out.println("Name of the white player");
				returned[0] = stdin.readLine();
			} catch (IOException e) {
			}
		}
		while (returned[1] == null) {
			try {
				System.out.println("Name of the black player");
				returned[1] = stdin.readLine();
			} catch (IOException e) {
			}
		}
		return returned;
	}

	private void clear() {
		for (int i = 0; i < 2; i++) {
			System.out.println("");
		}
	}

	public void redrawScreen(Chess game) {
		clear();
		System.out.println(game);
	}

	public Order readOrder(User player) {
		System.out.println(player.getName() + " order ?");
		Order order = null;
		while (order == null) {
			try {
				order = new Order(stdin.readLine());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return order;
	}

	public String askPromotion() {
		String input = "";
		do {
			try {
				System.out
						.println("Promotion : (Q:Queen, B:Bishop, H:Knight, R:Rook)");
				input = stdin.readLine();
			} catch (IOException e) {
			}
			input = input.toUpperCase();
		} while (!input.equals("Q") && !input.equals("B") && !input.equals("H")
				&& !input.equals("R"));
		return input;
	}
}
