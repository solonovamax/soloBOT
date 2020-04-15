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

package com.solostudios.qev.core.internal.entities;

import com.solostudios.qev.core.api.database.structure.raw.DataObject;
import com.solostudios.qev.core.api.entities.CachedEntityManager;

import java.util.Collection;


public class GuildManagerImpl extends CachedEntityManager<GuildImpl, GuildManagerImpl> {
    @Override
    public GuildImpl fromDataObject(DataObject object) {
        return new GuildImpl(this, object);
    }
    
    @Override
    public Collection<GuildImpl> getAllEntities() {
        return null;
    }
}