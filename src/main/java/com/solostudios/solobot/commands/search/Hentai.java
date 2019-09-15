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

package com.solostudios.solobot.commands.search;

import com.solostudios.solobot.abstracts.AbstractCommand;
import com.solostudios.solobot.framework.utility.WebUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hentai extends AbstractCommand {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = "https://danbooru.donmai.us/posts.json?random=true&tags=rating%3Ae&limit=1&min_level=30";

    public Hentai() {
        super("hentai",
                "Search",
                "Retrieves a hentai image from danbooru. [NSFW]",
                "hentai",
                true);
    }

    @Override
    public void run(MessageReceivedEvent messageReceivedEvent, Message message, String[] args) throws IllegalArgumentException {
        if (!messageReceivedEvent.getTextChannel().isNSFW()) {
            message.getChannel().sendMessage("This channel is not NSFW. \nThis command can only be used in channels with an NSFW tag.").queue();
            return;
        }


        JSONArray hentaiJSON;
        int counter = 0;
        do {
            hentaiJSON = getHentai();
            counter++;
            logger.info(counter + "");
        } while (hentaiJSON.getJSONObject(0).getString("file_url") == null && counter > 10);

        if (counter == 10) {
            message.getChannel().sendMessage("Sorry, an error occurred while trying to get the image. \n" +
                    "Please try again or contact @solonovamax#3163 if this is a recurring problem.").queue();
        } else {
            message.getChannel().sendMessage(hentaiJSON.getJSONObject(0).getString("file_url")).queue();
        }

    }

    private JSONArray getHentai() {
        return WebUtils.readJSONArrayFromUrl(url);
    }
}