/*
 * Copyright (c) 2020 solonovamax <solonovamax@12oclockpoint.com>
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
 */

package com.solostudios.qev.core.command.builtins;

import com.solostudios.qev.core.command.handler.abstracts.AbstractCommand;
import com.solostudios.qev.core.command.handler.old.ArgumentContainer;
import com.solostudios.qev.core.exceptions.IllegalInputException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;


public class PingCommand extends AbstractCommand {
    public PingCommand() {
        super("ping");
        this.setAliases("p");
        this.setCategory("Utility");
        this.setDescription("Used to ping the bot to test if it is working.\n" +
                            "Can also be used to see response times of the bot.");
    }
    
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    public void run(MessageReceivedEvent event, ArgumentContainer args) throws IllegalInputException {
        //Send ping message
        event.getChannel().sendMessage(new EmbedBuilder().setAuthor("Ping time to discord API: " + ((float) Math.round(
                event.getJDA().getRestPing().complete() / 100000)) / 10 + " milliseconds.\n" +
                                                                    "Discord API heartbeat: " + ((float) Math.round(
                event.getJDA().getGatewayPing() / 100000)) / 10)
                                                         .setColor(Color.GREEN)
                                                         .build()).queue();
    }
}
