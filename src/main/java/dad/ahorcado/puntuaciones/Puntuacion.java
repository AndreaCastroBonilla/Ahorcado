package dad.ahorcado.puntuaciones;

import java.io.Serializable;

public class Puntuacion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String playerName;
	private int points;

	public Puntuacion(String playerName, int points) {
		setPlayerName(playerName);
		setPoints(points);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Player name: " + playerName + " / Points: " + points;
	}

}
