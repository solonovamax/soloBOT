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

package com.solostudios.solobot.framework.commands;

import com.solostudios.solobot.framework.commands.errors.IllegalArgumentException;
import com.solostudios.solobot.framework.commands.errors.IllegalInputException;
import com.solostudios.solobot.framework.utility.MessageUtils;
import com.solostudios.solobot.soloBOT;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.entities.MemberImpl;
import net.dv8tion.jda.internal.entities.RoleImpl;
import net.dv8tion.jda.internal.entities.UserImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommand {

    private static Logger logger = LoggerFactory.getLogger(AbstractCommand.class);

    private String name;
    private String[] aliases = new String[]{""};
    private String category = "";
    private JSONArray arguments = new JSONArray();
    private String usage;
    private String description = "";
    private boolean enabled = true;
    private Permission[] userPermissions = new Permission[]{};
    private Permission[] clientPermissions = new Permission[]{Permission.MESSAGE_WRITE};
    private boolean ownerOnly;
    private String example = "";
    private boolean nsfw = false;
    private ArgumentContainer defaultArgs = new ArgumentContainer();


    protected AbstractCommand(String name) {
        this.name = name;
        this.usage = name;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final String getName() {
        return name;
    }

    public final String[] getAliases() {
        return aliases;
    }

    public final String getCategory() {
        return category;
    }

    public final String getUsage() {
        return usage;
    }

    public final String getDescription() {
        return description;
    }

    public Permission[] getUserPermissions() {
        return userPermissions;
    }

    public Permission[] getClientPermissions() {
        return clientPermissions;
    }

    public JSONArray getArguments() {
        return arguments;
    }

    public static ArgumentContainer parseArgs(MessageReceivedEvent event, AbstractCommand command, String[] args) throws IllegalArgumentException {


        JSONArray arguments = command.getArguments();
        ArgumentContainer temp = new ArgumentContainer(command.getDefaultArgs());

        for (int i = 0, j = 1; j < arguments.length() || i < arguments.length(); i++) {

            JSONObject obj = arguments.getJSONObject(i);
            String key = obj.getString("key");
            boolean skippable = (obj.has("optional") && obj.getBoolean("optional"));
            boolean defaultable = obj.has("default");


            Class clazz = (Class) obj.get("type");

            logger.info(clazz.getName());

            Object put;

            try {
                if (clazz.equals(RoleImpl.class)) {
                    if ((put = MessageUtils.getRoleFromString(args[j++], event.getGuild())) == null) {
                        if (skippable) {
                            temp.setNull(key);
                            continue;
                        }
                        if (defaultable) {
                            continue;
                        }
                        throw new java.lang.IllegalArgumentException(obj.getString("error"));
                    }
                    temp.put(key, put);
                    continue;
                }

                if (clazz.equals(UserImpl.class)) {
                    if ((put = MessageUtils.getUserFromString(args[j++], event.getGuild())) == null) {
                        if (skippable) {
                            temp.setNull(key);
                            continue;
                        }
                        if (defaultable) {
                            continue;
                        }
                        throw new java.lang.IllegalArgumentException(obj.getString("error"));
                    }
                    temp.put(key, put);
                    continue;
                }

                if (clazz.equals(MemberImpl.class)) {
                    if ((put = MessageUtils.getMemberFromString(args[j++], event.getGuild())) == null) {
                        if (skippable) {
                            temp.setNull(key);
                            continue;
                        }
                        if (defaultable) {
                            continue;
                        }
                        throw new java.lang.IllegalArgumentException(obj.getString("error"));
                    }
                    temp.put(key, put);
                    continue;
                }

                if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
                    temp.put(key, parseBoolean(args[j++]));
                    continue;
                }

                if (clazz.equals(String.class)) {
                    StringBuilder sb = new StringBuilder();
                    for (; j < args.length; j++) {
                        sb.append(args[j]).append(" ");
                    }
                    if (sb.toString().strip().equals("")) {
                        if (skippable) {
                            temp.setNull(key);
                            continue;
                        }
                        if (defaultable) {
                            continue;
                        }
                        throw new java.lang.IllegalArgumentException(obj.getString("error"));
                    }
                    temp.put(key, sb.toString().trim());
                    continue;
                }

                try {
                    if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
                        temp.put(key, Integer.parseInt(args[j++]));
                        continue;
                    }
                    if (clazz.equals(double.class) || clazz.equals(Double.class)) {
                        temp.put(key, Double.parseDouble(args[j++]));
                        continue;
                    }
                } catch (NumberFormatException e) {
                    if (skippable) {
                        temp.setNull(key);
                        continue;
                    }
                    if (defaultable) {
                        continue;
                    }
                    throw new java.lang.IllegalArgumentException(obj.getString("error"));
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                if (skippable) {
                    temp.setNull(key);
                    continue;
                }
                if (defaultable) {
                    continue;
                }
                throw new java.lang.IllegalArgumentException(obj.getString("error"));
            }
        }


        if (command.fitsArguments(temp)) {
            return temp;
        } else {
            throw new IllegalArgumentException("Arguments did not conform after compile");
        }

    }

    public String getExample() {
        return example;
    }

    public boolean isOwnerOnly() {
        return ownerOnly;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    private static boolean parseBoolean(String s) {
        if (s.equals("t") || s.equals("true") || s.equals("yes"))
            return true;
        if (s.equals("f") || s.equals("false") || s.equals("no"))
            return false;
        throw new java.lang.IllegalArgumentException();
    }

    public ArgumentContainer getDefaultArgs() {
        return new ArgumentContainer(defaultArgs);
    }

    /* layout of the JSONArray
    [{
        "key": String,
        "type": Clazz, <- Must be integer, double, boolean, role, member or string
        "optional": boolean,
        "default": Object <- Must be of class type
        "error": "Invalid input" <- What it says when there is an error
        "prompt": "Please input an argument" <- What is says when the user need to input something.
     },
     {
        "key": String,
        "type": Clazz, <- Must be integer, double, boolean, role, member or string
        "optional": boolean,
        "default": Object <- Must be of class type
        "error": "Invalid input" <- What it says when there is an error
        "prompt": "Please input an argument" <- What is says when the user need to input something.
     }]
     */

    public void prerun(MessageReceivedEvent event, ArgumentContainer args) {

        for (Permission perm : this.getUserPermissions()) {
            if (event.getMember() != null && !event.getMember().hasPermission(perm)) {
                event.getChannel().sendMessage("You require the " + perm.getName() + " permission to use this command.\n" +
                        "Please contact the server owner if you think you should have this permission, or contact @solonovamax#3163 if you think this is an error.").queue();
                return;
            }
        }

        for (Permission perm : this.getClientPermissions()) {
            event.getGuild().getSelfMember();
            if (!event.getGuild().getSelfMember().hasPermission(perm)) {
                event.getChannel().sendMessage("I require the " + perm.getName() + " permission for this command to work. Please ask the server owner to give me this permission.").queue();
                return;
            }
        }

        if (isNsfw() && !event.getTextChannel().isNSFW()) {
            event.getChannel().sendMessage("This channel is not marked as an NSFW channel. You can only use this command in an NSFW channel.").queue();
            return;
        }

        if (isOwnerOnly() && !event.getAuthor().getId().equals(soloBOT.BOT_OWNER)) {
            event.getChannel().sendMessage("This command can only be used by the owner.").queue();
            return;
        }

        run(event, args);
    }

    protected void withEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected void withAliases(String... aliases) {
        this.aliases = aliases;
    }

    protected void withCategory(String category) {
        this.category = category;
    }

    /**
     * @param usage
     * @return instance of calling object.
     */
    protected void withUsage(String usage) {
        this.usage = usage;
    }

    public abstract void run(MessageReceivedEvent event, ArgumentContainer arguments) throws IllegalInputException;

    /**
     * @param description
     * @return instance of calling object
     */
    protected void withDescription(String description) {
        this.description = description;
    }


    protected void withExample(String example) {
        this.example = example;
    }

    protected void withNSFW(boolean nsfw) {
        this.nsfw = nsfw;
    }


    /**
     * @param userPermissions
     * @return instance of calling object
     */
    protected void withUserPermissions(Permission... userPermissions) {
        this.userPermissions = userPermissions;
    }

    protected void withArguments(JSONArray arguments) {
        if (verifyArguments(arguments)) {
            this.arguments = arguments;
        } else {
            throw new java.lang.IllegalArgumentException();
        }
    }

    /**
     * @param ownerOnly
     * @return instance of calling object
     */
    protected void withOwnerOnly(boolean ownerOnly) {
        this.ownerOnly = ownerOnly;
    }

    /**
     * @param clientPermissions
     * @return instance of calling object
     */
    protected void withClientPermissions(Permission... clientPermissions) {
        Permission[] tempPermissions = new Permission[clientPermissions.length + 1];
        tempPermissions[0] = Permission.MESSAGE_WRITE;
        System.arraycopy(clientPermissions, 0, tempPermissions, 1, clientPermissions.length);
        this.clientPermissions = tempPermissions;
    }

    private boolean verifyArguments(JSONArray args) {
        if (args.length() < 1) {
            return false;
        }


        for (int i = 0; i < args.length(); i++) {
            JSONObject arg;
            try {
                arg = args.getJSONObject(i);
            } catch (JSONException e) {
                return false;
            }
            if (!arg.has("key") || !arg.has("type")) {
                return false;
            }

            Object c = arg.get("type");
            if (!(c == String.class || c == int.class || c == double.class || c == Role.class || c == Member.class || c == boolean.class || c.equals("BannedUser"))) {
                return false;
            }

            if ((c == int.class)) {
                args.put(i, arg.put("type", Integer.class));
            }
            if ((c == Role.class)) {
                args.put(i, arg.put("type", RoleImpl.class));
            }
            if ((c == Member.class)) {
                args.put(i, arg.put("type", MemberImpl.class));
            }
            if ((c == User.class)) {
                args.put(i, arg.put("type", UserImpl.class));
            }
            if (c == double.class) {
                args.put(i, arg.put("type", Double.class));
            }
            if (c == boolean.class) {
                args.put(i, arg.put("type", Boolean.class));
            }

            if (arg.has("default")) {
                if (arg.get("default").getClass() != c) {
                    return false;
                }
                defaultArgs.put(arg.getString("key"), arg.get("default"));
            }

            if (arg.has("optional")) {
                try {
                    arg.getBoolean("optional");
                } catch (JSONException e) {
                    return false;
                }
            }

            if (!arg.has("prompt")) {
                arg.put("prompt", "Please input the " + arg.getString("key"));
            }
        }
        return true;
    }

    public boolean fitsArguments(ArgumentContainer args) {

        logger.info("must conform to \n" + arguments.toString(11));

        for (int i = 0; i < arguments.length(); i++) {

            JSONObject obj = arguments.getJSONObject(i);

            /*
            try {
                if (obj.has("optional") && obj.getBoolean("optional")) {
                    logger.info("skipping optional arg");
                    continue;
                }
            } catch (JSONException e) {
            }
             */

            if (!(args.contains(obj.getString("key")))) {
                return false;
            }

            if (!args.isNull(obj.getString("key")) && args.get(obj.getString("key")).getClass() != obj.get("type")) {
                return false;
            }
        }

        return true;
    }

    public String nextArgPrompt(ArgumentContainer args) {
        for (int i = 0; i < arguments.length(); i++) {
            JSONObject obj = arguments.getJSONObject(i);

            if (args.contains(obj.getString("key"))) {
                continue;
            }
            try {
                if (obj.has("default")) {
                    return obj.getString("prompt") + "\nThis argument has a default. Please say \"null\" to use the default.";
                }
                if (obj.has("optional") && obj.getBoolean("optional")) {
                    return obj.getString("prompt") + "\nSay skip if you want to skip this optional argument.";
                }
            } catch (JSONException e) {
            }

            return obj.getString("prompt");
        }

        return "error";
    }

    public void putNextArg(MessageReceivedEvent event, ArgumentContainer args) throws java.lang.IllegalArgumentException {
        for (int i = 0; i < arguments.length(); i++) {

            JSONObject obj = arguments.getJSONObject(i);

            if (args.contains(obj.getString("key"))) {
                continue;
            }
            String key = obj.getString("key");

            if (event.getMessage().getContentRaw().equals("null") && obj.has("default")) {
                args.put(key, obj.get("default"));
                return;
            }

            logger.info(event.getMessage().getContentRaw());


            if (event.getMessage().getContentRaw().equals("skip") && obj.has("optional") && obj.getBoolean("optional")) {
                args.setNull(key);
                return;
            }

            Class clazz = (Class) obj.get("type");

            logger.info(clazz.getName());

            try {
                Object put;
                if (clazz.equals(RoleImpl.class)) {
                    if ((put = MessageUtils.getRoleFromMessage(event, "")) == null)
                        throw new java.lang.IllegalArgumentException();
                    args.put(key, put);
                    return;
                }
                if (clazz.equals(UserImpl.class)) {
                    if ((put = MessageUtils.getUserFromMessage(event, "")) == null)
                        throw new java.lang.IllegalArgumentException();
                    args.put(key, put);
                    return;
                }
                if (clazz.equals(MemberImpl.class)) {
                    if ((put = MessageUtils.getMemberFromMessage(event, "")) == null)
                        throw new java.lang.IllegalArgumentException();
                    args.put(key, put);
                    return;
                }
                if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
                    args.put(key, Integer.parseInt(event.getMessage().getContentRaw()));
                    return;
                }
                if (clazz.equals(double.class) || clazz.equals(Double.class)) {
                    args.put(key, Double.parseDouble(event.getMessage().getContentRaw()));
                    return;
                }
                if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
                    args.put(key, parseBoolean(event.getMessage().getContentRaw()));
                    return;
                }
                if (clazz.equals(String.class)) {
                    args.put(key, event.getMessage().getContentDisplay());
                    return;
                }
            } catch (java.lang.IllegalArgumentException e) {
            }
            throw new java.lang.IllegalArgumentException((!obj.has("error") ? "Invalid input" : obj.getString("error")));
        }
        throw new java.lang.IllegalArgumentException("No more arguments");
    }

}
