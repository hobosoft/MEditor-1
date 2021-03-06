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

package cz.mzk.editor.server.newObject;

import cz.mzk.editor.shared.domain.FedoraRelationship;

/**
 * @author Jiri Kremser
 * @version 31.10.2011
 */
public class RelsExtRelation {

    private String targetUuid;
    private FedoraRelationship relationName;
    private String targetName;

    public RelsExtRelation(String targetUuid, FedoraRelationship relationName, String targetName) {
        this.targetUuid = targetUuid;
        this.relationName = relationName;
        this.targetName = targetName;
    }

    public String getTargetUuid() {
        return targetUuid;
    }

    public void setTargetUuid(String targetUuid) {
        this.targetUuid = targetUuid;
    }

    public FedoraRelationship getRelationName() {
        return relationName;
    }

    public void setRelationName(FedoraRelationship relationName) {
        this.relationName = relationName;
    }

    /**
     * @return the targetName
     */

    public String getTargetName() {
        return targetName;
    }

    /**
     * @param targetName
     *        the targetName to set
     */

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

}