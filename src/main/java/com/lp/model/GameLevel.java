
package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_level")
public class GameLevel implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private long gameLevelId;
	private String level;
	
	public GameLevel() {
	}

	public GameLevel(long gameLevelId) {
		this.gameLevelId = gameLevelId;
	}

	public GameLevel(long gameLevelId,String level) {
		this.gameLevelId = gameLevelId;
		this.level = level;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "game_level_id", unique = true, nullable = false)
	public long getGameLevelId() {
		return this.gameLevelId;
	}

	public void setGameLevelId(long gameLevelId) {
		this.gameLevelId = gameLevelId;
	}

	@Column(name = "level", length = 1000)
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
