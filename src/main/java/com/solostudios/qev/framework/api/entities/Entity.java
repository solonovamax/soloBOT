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

package com.solostudios.qev.framework.api.entities;

import com.solostudios.qev.framework.api.Client;
import com.solostudios.qev.framework.internal.utils.EntityUtil;


public interface Entity {
    /**
     * This is the numerical ID of the object with an identifier at the end for different types of objects.
     *
     * @return The ID of the object.
     */
    String getId();
    
    /**
     * Every entity MUST have a UNIQUE id that is a 64 bit integer (aka a long).
     * <p>
     * You can generate ids using {@link EntityUtil#generateUniqueID()}. This class will generate a unique id in a similar way to how the
     * discord and twitter snowflake-ids work.
     *
     * @return The numerical ID of the object. It will contain an ID resulting from the discord API, or 0.
     */
    long getIdLong();
    
    Client getClient();
}
