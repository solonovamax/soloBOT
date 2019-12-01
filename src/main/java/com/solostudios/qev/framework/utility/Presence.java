/*
 *
 * Copyright 2016 2019 solonovamax <solonovamax@12oclockpoint.com>
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.solostudios.qev.framework.utility;

import com.solostudios.qev.Qev;
import net.dv8tion.jda.api.entities.Activity;


/**
 * Enum that holds a bunch of different custom statuses for discord presence.
 * <p>
 * Some items are duped so they show up more frequently.
 */
public enum Presence {
	helpI(Activity.ActivityType.DEFAULT, "Type !help for help."),
	helpII(Activity.ActivityType.DEFAULT, "Type !help for help."),
	helpIII(Activity.ActivityType.DEFAULT, "Type !help for help."),
	helpIV(Activity.ActivityType.DEFAULT, "Type !help for help."),
	writtenI(Activity.ActivityType.DEFAULT, "Written by solonovamax#3163"),
	writtenII(Activity.ActivityType.DEFAULT, "Written by solonovamax#3163"),
	infoI(Activity.ActivityType.DEFAULT, "Type !info."),
	infoII(Activity.ActivityType.DEFAULT, "Type !info."),
	versionI(Activity.ActivityType.DEFAULT, "Version " + Qev.VERSION),
	versionII(Activity.ActivityType.DEFAULT, "Version " + Qev.VERSION),
	versionIII(Activity.ActivityType.DEFAULT, "Version " + Qev.VERSION),
	versionIV(Activity.ActivityType.DEFAULT, "Version " + Qev.VERSION),
	anime(Activity.ActivityType.WATCHING, "Anime"),
	serverI(Activity.ActivityType.DEFAULT, Qev.SUPPORT_SERVER),
	serverII(Activity.ActivityType.DEFAULT, Qev.SUPPORT_SERVER),
	videoGames(Activity.ActivityType.DEFAULT, "Video Games"),
	technoblade(Activity.ActivityType.WATCHING, "Technoblade win minecraft mondays."),
	minecraft(Activity.ActivityType.DEFAULT, "Minecraft"),
	urmom(Activity.ActivityType.DEFAULT, "With your mom."),
	codeI(Activity.ActivityType.DEFAULT, "With some lines of code"),
	codeII(Activity.ActivityType.DEFAULT, "With some code");
	
	private final Activity.ActivityType gameType;
	private final String                action;
	
	
	Presence(Activity.ActivityType gameType, String action) {
		this.gameType = gameType;
		this.action = action;
	}
	
	/**
	 * Gets the game type for the enum
	 *
	 * @return Returns the game type of the enum
	 */
	public Activity.ActivityType getGameType() {
		return gameType;
	}
	
	/**
	 * Gets a string that contains what the text will be
	 *
	 * @return action
	 */
	public String getAction() {
		return action;
	}
	
}