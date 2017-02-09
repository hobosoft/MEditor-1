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
import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.UserProvider;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.shared.rpc.UserIdentity;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesAction;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesResult;
import cz.mzk.editor.shared.rpc.action.RemoveUserIdentityAction;
import cz.mzk.editor.shared.rpc.action.RemoveUserIdentityResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.activation.UnsupportedDataTypeException;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
@Service
public class RemoveUserIdentityHandler
        implements ActionHandler<RemoveUserIdentityAction, RemoveUserIdentityResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(RemoveUserIdentityHandler.class.getPackage()
            .toString());

    /** The recently modified dao. */
    @Inject
    private UserDAO userDAO;

    /** The configuration. */
    @Inject
    private EditorConfiguration configuration;

    @Inject
    private UserProvider userProvider;

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#execute(com
     * .gwtplatform.dispatch.shared.Action,
     * com.gwtplatform.dispatch.server.ExecutionContext)
     */
    @Override
    public RemoveUserIdentityResult execute(final RemoveUserIdentityAction action,
                                            final ExecutionContext context) throws ActionException {

        LOGGER.debug("Processing action: RemoveUserIdentityAction " + action.getUserIdentity());

        if (action.getUserIdentity() == null) throw new NullPointerException("getUserIdentity()");
        for (String identity : action.getUserIdentity().getIdentities()) {
            try {
                UserIdentity userIdentity = action.getUserIdentity();
                userIdentity.getIdentities().removeAll(userIdentity.getIdentities());
                userIdentity.getIdentities().add(identity);
                userDAO.addRemoveUserIdentity(action.getUserIdentity(), false, userProvider.getUserId());
            } catch (NumberFormatException e) {
                throw new ActionException(e);
            } catch (DatabaseException e) {
                throw new ActionException(e);
            } catch (UnsupportedDataTypeException e) {
                throw new ActionException(e);
            }
        }

        GetUserRolesRightsIdentitiesHandler getIdentitiesHandler =
                new GetUserRolesRightsIdentitiesHandler(configuration, userDAO);
        List<USER_IDENTITY_TYPES> type = new ArrayList<Constants.USER_IDENTITY_TYPES>(1);
        type.add(action.getUserIdentity().getType());
        GetUserRolesRightsIdentitiesAction getIdentitiesAction =
                new GetUserRolesRightsIdentitiesAction(action.getUserIdentity().getUserId().toString(),
                                                       type,
                                                       false);
        GetUserRolesRightsIdentitiesResult identitiesResult =
                getIdentitiesHandler.execute(getIdentitiesAction, context);

        return new RemoveUserIdentityResult(identitiesResult.getIdentities());
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<RemoveUserIdentityAction> getActionType() {
        return RemoveUserIdentityAction.class;
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
    public void undo(RemoveUserIdentityAction action,
                     RemoveUserIdentityResult result,
                     ExecutionContext context) throws ActionException {
        // TODO undo method

    }
}