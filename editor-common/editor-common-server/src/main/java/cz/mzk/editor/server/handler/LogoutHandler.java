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
import cz.mzk.editor.server.DAO.UserDAO;
import cz.mzk.editor.server.UserProvider;
import cz.mzk.editor.server.config.EditorConfiguration.ServerConstants;
import cz.mzk.editor.shared.rpc.action.LogoutAction;
import cz.mzk.editor.shared.rpc.action.LogoutResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class PutRecentlyModifiedHandler.
 */
@Service
public class LogoutHandler
        implements ActionHandler<LogoutAction, LogoutResult> {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(LogoutHandler.class.getPackage().toString());
    private static final Logger ACCESS_LOGGER = Logger.getLogger(ServerConstants.ACCESS_LOG_ID);
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    @Inject
    private HttpServletRequest request;

    @Inject
    private UserDAO userDAO;

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
    public LogoutResult execute(final LogoutAction action, final ExecutionContext context)
            throws ActionException {

        LOGGER.debug("Processing action: LogoutAction");

        ACCESS_LOGGER.info("LOG OUT: [" + FORMATTER.format(new Date()) + "] User " + userProvider.getName()
                + " with IP " + request.getRemoteAddr());

        return new LogoutResult(request.getContextPath() + "/sso/logout");
    }

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.actionhandler.ActionHandler#getActionType
     * ()
     */
    @Override
    public Class<LogoutAction> getActionType() {
        return LogoutAction.class;
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
    public void undo(LogoutAction action, LogoutResult result, ExecutionContext context)
            throws ActionException {
        // TODO undo method

    }
}