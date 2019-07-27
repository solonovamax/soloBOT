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

package com.solostudios.solobot.commands.utility;

import com.solostudios.solobot.abstracts.AbstractCommand;
import com.solostudios.solobot.main.StatsHandler;
import com.solostudios.solobot.main.UserStats;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Level extends AbstractCommand {

    public Level() {
        super("level",
                "Statistics",
                "Gets the level of a given user.",
                "level \n" +
                        "level {user}",
                true,
                "l");
    }

    @Override
    public void run(MessageReceivedEvent event, Message message, String[] args) throws IllegalArgumentException {
        User author = event.getAuthor();


        UserStats stats;
        User user;
        if (args.length > 1) {
            try {
                user = message.getMentionedMembers().get(0).getUser();
            } catch (IndexOutOfBoundsException e) {
                throw new IllegalArgumentException();
            }
        } else {
            user = author;
        }

        stats = StatsHandler.getUserStats(user, message.getGuild());
        message.getChannel().sendMessage("User " + user.getAsMention() + " has " + stats.getXP() + " xp!").queue();

    }
}
