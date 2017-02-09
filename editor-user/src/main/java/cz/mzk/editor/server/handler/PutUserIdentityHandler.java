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

package cz.mzk.editor.server.handler;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.UserProvider;
import cz.mzk.editor.shared.rpc.action.PutUserIdentityAction;
import cz.mzk.editor.shared.rpc.action.PutUserIdentityResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.activation.UnsupportedDataTypeException;
import javax.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
@Service
public class PutUserIdentityHandler
        implements ActionHandler<PutUserIdentityAction, PutUserIdentityResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(PutUserIdentityHandler.class.getPackage()
            .toString());

    /** The user dao. */
    @Inject
    private UserDAO userDAO;

    @Inject
    private UserProvider userProvider;

    /**
     * Instantiates a new put recently modified handler.
     * 
     * @param logger
     *        the logger
     * @param configuration
     *        the configuration
     */
    @Inject
    public PutUserIdentityHandler() {

    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public PutUserIdentityResult execute(final PutUserIdentityAction action, final ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: PutUserIdentityAction " + action.getIdentity());

        if (action.getIdentity() == null) throw new NullPointerException("getIdentity()");
        if (action.getIdentity().getUserId() == null || "".equals(action.getIdentity().getUserId()))
            throw new NullPointerException("getUserId()");
        LOGGER.debug("Processing action: PutUserIdentityAction identity:" + action.getIdentity());

        boolean succ = false;
        try {
            try {
                succ = userDAO.addRemoveUserIdentity(action.getIdentity(), true, userProvider.getUserId());
            } catch (UnsupportedDataTypeException e) {
                throw new ActionException(e);
            }
        } catch (NumberFormatException e) {
            throw new ActionException(e);
        } catch (DatabaseException e) {
            throw new ActionException(e);
        }
        return new PutUserIdentityResult(succ);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<PutUserIdentityAction> getActionType() {
        return PutUserIdentityAction.class;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#undo(com.
     * gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.shared.Result,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public void undo(PutUserIdentityAction action, PutUserIdentityResult result, ExecutionContext context)
            throws ActionException {
        // TODO undo method

    }
}