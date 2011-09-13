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

package cz.fi.muni.xkremser.editor.client.view;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.UiHandlers;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverEvent;
import com.smartgwt.client.widgets.form.fields.events.ItemHoverHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

import cz.fi.muni.xkremser.editor.client.LangConstants;
import cz.fi.muni.xkremser.editor.client.gwtrpcds.RecentlyTreeGwtRPCDS;
import cz.fi.muni.xkremser.editor.client.presenter.CreateObjectMenuPresenter;
import cz.fi.muni.xkremser.editor.client.view.tree.SideNavInputTree;
import cz.fi.muni.xkremser.editor.client.view.tree.SideNavRecentlyGrid;

import cz.fi.muni.xkremser.editor.shared.rpc.RecentlyModifiedItem;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateObjectMenuView.
 */
public class CreateObjectMenuView
        extends ViewWithUiHandlers<CreateObjectMenuView.MyUiHandlers>
        implements CreateObjectMenuPresenter.MyView {

    /** The Constant SECTION_RELATED_ID. */
    private static final String SECTION_RELATED_ID = "related";

    private static final String SECTION_INPUT_ID = "input";

    private final LangConstants lang;

    /**
     * The Interface MyUiHandlers.
     */
    public interface MyUiHandlers
            extends UiHandlers {

        void onRefresh();

        void refreshRecentlyModified();

        void onAddSubelement(final RecentlyModifiedItem item, final List<? extends List<String>> related);

        void revealItem(String uuid);

    }

    /** The input tree. */
    private SideNavInputTree inputTree;

    /** The side nav grid. */
    private final SideNavRecentlyGrid sideNavGrid;

    /** The section stack. */
    private final SectionStack sectionStack;

    /** The section recently modified. */
    private final SectionStackSection sectionRecentlyModified;

    /** The refresh button. */
    private ImgButton refreshButton;

    /** The layout. */
    private final VLayout layout;

    private final SelectItem selectItem = new SelectItem();

    /**
     * Instantiates a new digital object menu view.
     */
    @Inject
    public CreateObjectMenuView(LangConstants lang) {
        this.lang = lang;
        layout = new VLayout();

        layout.setHeight100();
        layout.setWidth100();
        layout.setOverflow(Overflow.AUTO);

        sideNavGrid = new SideNavRecentlyGrid();

        final DynamicForm form = new DynamicForm();
        form.setHeight(1);
        form.setWidth(60);
        form.setNumCols(1);

        selectItem.setWidth(60);
        selectItem.setShowTitle(false);
        selectItem.setValueMap(lang.me(), lang.all());
        selectItem.setDefaultValue(lang.me());
        selectItem.setHoverOpacity(75);
        selectItem.setHoverStyle("interactImageHover");
        selectItem.addItemHoverHandler(new ItemHoverHandler() {

            @Override
            public void onItemHover(ItemHoverEvent event) {
                selectItem.setPrompt(CreateObjectMenuView.this.lang.showModifiedHint()
                        + selectItem.getValue());

            }
        });
        selectItem.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                getUiHandlers().refreshRecentlyModified();
            }
        });

        form.setFields(selectItem);
        form.setTitle("by:");

        sectionRecentlyModified = new SectionStackSection();
        sectionRecentlyModified.setTitle(lang.recentlyModified());
        sectionRecentlyModified.setResizeable(true);
        sectionRecentlyModified.setItems(sideNavGrid);
        sectionRecentlyModified.setControls(form);
        sectionRecentlyModified.setExpanded(true);

        sectionStack = new SectionStack();
        sectionStack.addSection(sectionRecentlyModified);
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setAnimateSections(true);
        sectionStack.setWidth100();
        sectionStack.setHeight100();
        sectionStack.setOverflow(Overflow.HIDDEN);
        layout.addMember(sectionStack);
    }

    /**
     * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
     * 
     * @return the widget
     */
    @Override
    public Widget asWidget() {
        return layout;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#setDS(com.gwtplatform.dispatch.client.DispatchAsync)
     */
    @Override
    public void setDS(DispatchAsync dispatcher, EventBus bus) {
        this.sideNavGrid.setDataSource(new RecentlyTreeGwtRPCDS(dispatcher, lang, bus));
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#expandNode(java.lang.String)
     */
    @Override
    public void expandNode(String id) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getRefreshWidget()
     */
    @Override
    public HasClickHandlers getRefreshWidget() {
        return refreshButton;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getInputTree()
     */
    @Override
    public SideNavInputTree getInputTree() {
        return inputTree;
    }

    /*
     * (non-Javadoc)
     * @see
     * cz.fi.muni.xkremser.editor.client.presenter.DigitalObjectMenuPresenter.
     * MyView#getRecentlyModifiedTree()
     */
    @Override
    public ListGrid getSubelementsGrid() {
        return sideNavGrid;
    }

    @Override
    public SectionStack getSectionStack() {
        return sectionStack;
    }

    @Override
    public SelectItem getSelectItem() {
        return selectItem;

    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void setInputTree(SideNavInputTree tree) {
        String isInputSection = sectionStack.getSection(0).getAttribute(SECTION_INPUT_ID);
        if (isInputSection != null && "yes".equals(isInputSection)) {
            return;
        }
        inputTree = tree;
        SectionStackSection section1 = new SectionStackSection();
        section1.setTitle(lang.inputQueue());
        section1.setItems(inputTree);
        refreshButton = new ImgButton();
        refreshButton.setSrc("[SKIN]headerIcons/refresh.png");
        refreshButton.setSize(16);
        refreshButton.setShowRollOver(true);
        refreshButton.setCanHover(true);
        refreshButton.setShowDownIcon(false);
        refreshButton.setShowDown(false);
        refreshButton.setHoverOpacity(75);
        refreshButton.setHoverStyle("interactImageHover");
        refreshButton.setHoverOpacity(75);
        refreshButton.addHoverHandler(new HoverHandler() {

            @Override
            public void onHover(HoverEvent event) {
                refreshButton.setPrompt("Rescan directory structure.");
            }
        });

        section1.setControls(refreshButton);
        section1.setResizeable(true);
        section1.setExpanded(true);
        sectionStack.addSection(section1, 0);
        section1.setAttribute(SECTION_INPUT_ID, "yes");
    }
}