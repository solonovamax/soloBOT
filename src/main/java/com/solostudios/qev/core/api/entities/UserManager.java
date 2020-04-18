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

package com.solostudios.qev.core.api.entities;

import com.solostudios.qev.core.api.database.Database;
import com.solostudios.qev.core.api.database.structure.raw.DataObject;


public class UserManager extends InMemoryManager<User, UserManager> {
    public UserManager(Database database, long guildId) {
        super(database);
    }
    
    @Override
    protected void save(User user) {
    
    }
    
    @Override
    protected void createNew(long id) {
    
    }
    
    @Override
    public User fromDataObject(DataObject object) {
        return null;
    }
    
    @Override
    public boolean isConcurrent() {
        return false;
    }
}
