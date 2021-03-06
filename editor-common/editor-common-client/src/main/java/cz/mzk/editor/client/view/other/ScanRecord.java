/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
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

package cz.mzk.editor.client.view.other;

import com.smartgwt.client.widgets.tile.TileRecord;

import cz.mzk.editor.client.util.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class PageRecord.
 */
public class ScanRecord
        extends TileRecord {

    /**
     * Instantiates a new page record.
     */
    public ScanRecord() {
    }

    /**
     * Instantiates a new page record.
     * 
     * @param name
     *        the name
     * @param issn
     *        the issn
     * @param picture
     *        the picture
     * @param path
     *        the path
     */
    public ScanRecord(String name, String model, String picture, String pageType) {
        setName(name);
        setModel(model);
        setPicture(picture);
        setPageType(pageType);
    }

    /**
     * Set the name.
     * 
     * @param name
     *        the name
     */
    public void setName(String name) {
        setAttribute(Constants.ATTR_NAME, name);
    }

    /**
     * Return the name.
     * 
     * @return the name
     */
    public String getName() {
        return getAttribute(Constants.ATTR_NAME);
    }

    /**
     * Set the model.
     * 
     * @param model
     *        the model
     */
    public void setModel(String model) {
        setAttribute(Constants.ATTR_MODEL, model);
    }

    /**
     * Return the model.
     * 
     * @return the model
     */
    public String getModel() {
        return getAttribute(Constants.ATTR_MODEL);
    }

    /**
     * Set the picture.
     * 
     * @param picture
     *        the picture
     */
    public void setPicture(String picture) {
        setAttribute(Constants.ATTR_PICTURE_OR_UUID, picture);
    }

    /**
     * Return the picture.
     * 
     * @return the picture
     */
    public String getPicture() {
        return getAttribute(Constants.ATTR_PICTURE_OR_UUID);
    }

    /**
     * Set the pageType.
     * 
     * @param pageType
     *        the pageType
     */
    public void setPageType(String pageType) {
        setAttribute(Constants.ATTR_TYPE, pageType);
    }

    /**
     * Return the pageType.
     * 
     * @return the pageType
     */
    public String getPageType() {
        return getAttribute(Constants.ATTR_TYPE);
    }

    /**
     * Deep copy.
     * 
     * @return the page record
     */
    //    public ScanRecord deepCopy() {
    //        return new ScanRecord(getName(), getModel(), getPicture(), getPath(), getPageType());
    //    }

    /**
     * {@inheritDoc}
     */

    @Override
    public String toString() {
        return "ScanRecord [getName()=" + getName() + ", getModel()=" + getModel() + ", getPicture()="
                + getPicture() + ", getPageType()=" + getPageType() + "]";
    }

}
