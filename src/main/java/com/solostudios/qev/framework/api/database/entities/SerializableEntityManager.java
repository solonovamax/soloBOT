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

package com.solostudios.qev.framework.api.database.entities;

import com.solostudios.qev.framework.api.Client;
import com.solostudios.qev.framework.api.database.DataObject;
import com.solostudios.qev.framework.api.database.GenericDatabase;
import com.solostudios.qev.framework.api.database.Table;

import java.util.Collection;
import java.util.function.Predicate;


public interface SerializableEntityManager<E extends SerializableEntity<M, E>, M extends SerializableEntityManager<E, M>> {
    void save(E e);
    
    GenericDatabase getDatabase();
    
    Table getEntityTable();
    
    Client getClient();
    
    E createNew(long id);
    
    /**
     * Constructs an {@link SerializableEntity} using a {@link DataObject}.
     *
     * @param object
     *         The {@link DataObject} that is used to construct a new {@link SerializableEntity}.
     *
     * @return The new {@link SerializableEntity} that you constructed.
     */
    E fromDataObject(DataObject object);
    
    boolean usesCaching();
    
    boolean isConcurrent();
    
    E getEntityById(long id);
    
    E getEntityByFilter(Predicate<E> filter);
    
    Collection<E> getEntitiesByFilter(Predicate<E> filter);
    
    Collection<E> getAllEntities();
    
    void saveAll();
    
    void shutdown();
    
}
