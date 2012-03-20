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

package cz.mzk.editor.client.uihandlers;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.UiHandlers;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import cz.mzk.editor.client.view.window.ConnectExistingObjectWindow;
import cz.mzk.editor.shared.domain.DigitalObjectModel;

/**
 * @author Jiri Kremser
 * @version 11.3.2012
 */
public interface CreateObjectMenuUiHandlers
        extends UiHandlers {

    void revealItem(String uuid);

    Map<String, DigitalObjectModel> getModelFromLabel();

    Map<String, String> getLabelFromModel();

    void getModel(String valueAsString, ConnectExistingObjectWindow window);

    //Adds an ALTO file to a page
    void addAlto(ListGridRecord record);

    int newId();

    void loadStructure();

    HasHandlers getBus();

    void addPages(List<Record> pages, String parent);

}