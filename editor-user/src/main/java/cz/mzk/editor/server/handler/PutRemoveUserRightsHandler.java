/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Matous Jobanek (matous.jobanek@mzk.cz)
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
import cz.mzk.editor.client.util.Constants.EDITOR_RIGHTS;
import cz.mzk.editor.server.DAO.DatabaseException;
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.config.EditorConfiguration;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesAction;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesResult;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.activation.UnsupportedDataTypeException;
import javax.inject.Inject;
import java.util.List;

/**
 * @author Matous Jobanek
 * @version Nov 27, 2012
 */
@Service
public class PutRemoveUserRightsHandler
        implements ActionHandler<PutRemoveUserRightsAction, PutRemoveUserRightsResult> {

    @Inject
    private UserDAO userDAO;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(PutRemoveUserRightsHandler.class.getPackage()
            .toString());

    /** The configuration. */
    @Inject
    private EditorConfiguration configuration;

    /**
     * {@inheritDoc}
     */
    @Override
    public PutRemoveUserRightsResult execute(PutRemoveUserRightsAction action, ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: PutRemoveUserRightsAction");

        boolean successful = true;
        for (String right : action.getRightNames()) {
            try {

                successful &=
                        userDAO.addRemoveUserRightItem(right,
                                                       Long.parseLong(action.getUserId()),
                                                       action.isPut());

            } catch (NumberFormatException e) {
                LOGGER.warn(e);
                throw new ActionException(e);
            } catch (DatabaseException e) {
                LOGGER.warn(e);
                throw new ActionException(e);
            } catch (UnsupportedDataTypeException e) {
                LOGGER.warn(e);
                throw new ActionException(e);
            }
        }

        List<EDITOR_RIGHTS> rights = null;
        if (!action.isPut()) {
            GetUserRolesRightsIdentitiesHandler getRightsHandler =
                    new GetUserRolesRightsIdentitiesHandler(configuration, userDAO);
            GetUserRolesRightsIdentitiesAction getRightsAction =
                    new GetUserRolesRightsIdentitiesAction(action.getUserId(), null, false);
            GetUserRolesRightsIdentitiesResult rightsResult =
                    getRightsHandler.execute(getRightsAction, context);
            rights = rightsResult.getRights();
        }

        return new PutRemoveUserRightsResult(successful, rights);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<PutRemoveUserRightsAction> getActionType() {
        return PutRemoveUserRightsAction.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo(PutRemoveUserRightsAction action,
                     PutRemoveUserRightsResult result,
                     ExecutionContext context) throws ActionException {
        // TODO Auto-generated method stub
    }

}
