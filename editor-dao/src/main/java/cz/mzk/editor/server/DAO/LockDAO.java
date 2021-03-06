/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.mzk.editor.server.DAO;

/**
 * @author Jiri Kremser
 * @version $Id$
 */

public interface LockDAO {

    /**
     * Tries to lock a digital object with the given
     * <code>uuid<code>, returns whether locking was successful
     * 
     * @param uuid
     *        the uuid of digital object
     * @param description
     *        the lock-description
     * @param insert
     * @param userId
     * @return whether locking was successful
     * @throws DatabaseException
     */
    boolean lockDigitalObject(String uuid, String description, boolean insert, Long userId) throws DatabaseException;

    /**
     * Gets the id of a owner of the lock
     * 
     * @param uuid
     *        the uuid of digital object
     * @return id the id of owner of lock, <code>id == 0<code> when there is no
     *         lock (no owner), <code> id &lt 0
     *         <code> when there has occurred any other problem and the DB has
     *         not been updated
     * @throws DatabaseException
     */
    long getLockOwnersID(String uuid) throws DatabaseException;

    /**
     * Returns the description of a digital object with the uuid
     * 
     * @param uuid
     *        the uuid of the digital object which has the needed description
     * @return the description
     * @throws DatabaseException
     */
    String getDescription(String uuid) throws DatabaseException;

    /**
     * Returns the parsed time to expiration of the lock
     * 
     * @param uuid
     *        the uuid of the digital object which is locked
     * @return the parsed time to expiration of the lock
     * @throws DatabaseException
     */
    String[] getTimeToExpirationLock(String uuid) throws DatabaseException;

}
