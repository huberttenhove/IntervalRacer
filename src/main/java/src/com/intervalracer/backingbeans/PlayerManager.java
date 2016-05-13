package com.intervalracer.backingbeans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.intervalracer.model.Player;

/**
 * Managed bean that manages the players session throughout the game cycle.
 * Responsible for storing the player data and redirecting the player when wrong
 * pages are accessed.
 */
@ManagedBean(name = "PlayerManager")
@SessionScoped
public class PlayerManager implements Serializable {

	private static final long serialVersionUID = -8749816521810346092L;

	private Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	/**
	 * Method checks if players are allowed to access a page. If not a redirect
	 * is called to the desired page.
	 */
	public void checkPageNavigation(String pageOrigin) {
		String redirectPage = "";
		if (player == null && !pageOrigin.equals("Join")) {
			redirectPage = "Join.xhtml";
		} else if (pageOrigin.equals("Join") && player != null) {
			redirectPage = "WaitRoom.xhtml";
		} else if (player != null && player.isStillActiveRacing() && !pageOrigin.equals("RaceView")) {
			redirectPage = "RaceView.xhtml";
		}

		if (!redirectPage.isEmpty()) {
			redirectPlayer(redirectPage);
		}

	}

	/**
	 * Redirects a user to the given page.
	 */
	private void redirectPlayer(String redirectPage) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + "/faces/" + redirectPage);
		} catch (IOException e) {
			System.out.println("ERROR: Unable to redirect user to page: " + redirectPage + ". Exception: " + e);
		}
	}
}
