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

package cz.mzk.editor.server.DAO;

import cz.mzk.editor.client.util.Constants;
import cz.mzk.editor.client.util.Constants.DEFAULT_SYSTEM_USERS;
import cz.mzk.editor.client.util.Constants.USER_IDENTITY_TYPES;
import cz.mzk.editor.server.config.EditorConfiguration;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDAO.
 * 
 * @author Jiri Kremser
 */
public abstract class AbstractDAO {

    /** The conn. */
    private Connection conn = null;

    /** The conf. */
    @Inject
    private EditorConfiguration conf;

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class);

    /** The Constant DRIVER. */
    private static final String DRIVER = "org.postgresql.Driver";

    /** Must be the same as in the META-INF/context.xml and WEB-INF/web.xml */
    private static final String JNDI_DB_POOL_ID = "jdbc/editor";

    /** The Constant FORMATTER with format: dd.MM.yyyy HH:mm:ss. */
    public static final SimpleDateFormat FORMATTER_TO_SECONDS = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    /** The Constant POOLABLE_YES. */
    private static final int POOLABLE_YES = 1;

    /** The Constant POOLABLE_NO. */
    private static final int POOLABLE_NO = 0;

    /** The poolable. */
    private static int poolable = -1;

    /** The context is correct. */
    private static boolean contextIsCorrect = false;

    /** The pool. */
    @Resource
    private DataSource pool;

    /**
     * Inits the connection.
     * 
     * @throws DatabaseException
     *         the database exception
     */
    private void initConnection() throws DatabaseException {
        if (poolable != POOLABLE_NO && pool == null) {
            if (conf != null && conf.isLocalhost()) {
                poolable = POOLABLE_NO;
                initConnection();
                return;
            }
            InitialContext cxt = null;
            try {
                cxt = new InitialContext();
            } catch (NamingException e) {
                poolable = POOLABLE_NO;
                LOGGER.warn("Unable to get initial context.", e);
            }
            if (cxt != null) {
                try {
                    pool = (DataSource) cxt.lookup("java:/comp/env/" + JNDI_DB_POOL_ID);
                } catch (NamingException e) {
                    poolable = POOLABLE_NO;
                    LOGGER.warn("Unable to get connection pool.", e);
                }
            }
        }
        if (poolable == POOLABLE_NO || pool == null) { // DI is working and servlet container manages the
            // connections
            poolable = POOLABLE_NO;
            initConnectionWithoutPool();
        } else {
            poolable = POOLABLE_YES;
            try {
                conn = pool.getConnection();
            } catch (SQLException ex) {
                LOGGER.error("Unable to get a connection from connection pool " + JNDI_DB_POOL_ID, ex);
                poolable = POOLABLE_NO;
                pool = null;
                initConnectionWithoutPool();
            }
        }
    }

    /**
     * Inits the connection without pool.
     * 
     * @throws DatabaseException
     *         the database exception
     */
    private void initConnectionWithoutPool() throws DatabaseException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            LOGGER.error("Could not find the driver " + DRIVER, ex);
        }
        String login = conf.getDBLogin();
        String password = conf.getDBPassword();
        String host = conf.getDBHost();
        String port = conf.getDBPort();
        String name = conf.getDBName();

        if (!contextIsCorrect && !conf.isLocalhost() && pool == null && login != null && password != null
                && port != null && name != null) {
            createCorrectContext(host, login, password, port, name);
        }

        if (password == null || password.length() < 3) {
            LOGGER.error("Unable to connect to database at 'jdbc:postgresql://" + host + ":" + port + "/"
                    + name + "' reason: no password set.");
            return;
        }
        try {
	    conn = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + name, login, password);
        } catch (SQLException ex) {
	    LOGGER.error("Unable to connect to database at 'jdbc:postgresql://" + host + ":" + port + "/" + name + "'",
		    ex);
            throw new DatabaseException("Unable to connect to database.");
        }
    }

    /**
     * Gets the connection.
     * 
     * @return the connection
     * @throws DatabaseException
     *         the database exception
     */
    protected Connection getConnection() throws DatabaseException {
        if (conn == null) {
            initConnection();
        } else {
            try {
                if (conn.isClosed()) {
                    LOGGER.warn("Connection to database is closed. Init new...");
                    initConnection();
                }
            } catch (SQLException e) {
                throw new DatabaseException("Unable to connect to database.");
            }
        }
        return conn;
    }

    /**
     * Close connection.
     */
    protected void closeConnection() {
        try {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException ex) {
            LOGGER.error("Connection was not closed", ex);
        }
        conn = null;
    }

    /**
     * 
     * TODO: this is so Tomcat specific!! Won't run on any other AS.
     * 
     * Creates the correct context.
     * 
     * @param login
     *        the login
     * @param password
     *        the password
     * @param port
     *        the port
     * @param name
     *        the name
     */
    private void createCorrectContext(String host, String login, String password, String port, String name) {
        String catalinaHome = System.getProperty("catalina.home");
        catalinaHome = "/usr/local/tomcat/";
        if (catalinaHome != null) {
            VelocityEngine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.init();
            VelocityContext context = new VelocityContext();
            context.put("login", login);
            context.put("host", host);
            context.put("password", password);
            context.put("port", port);
            context.put("name", name);
            Template t = ve.getTemplate("context.xml");
            File contextFile =
                    new File(catalinaHome + File.separator + "conf" + File.separator + "Catalina" + File.separator
                            + "localhost" + File.separator + "meditor.xml");
            FileWriter writer = null;
            try {
                writer = new FileWriter(contextFile);
                t.merge(context, writer);
                writer.flush();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }

        contextIsCorrect = true;
        poolable = POOLABLE_YES;
    }

    /**
     * Gets the users id.
     * 
     * @param identifier
     *        the identifier
     * @param type
     *        the type
     * @return the users id
     * @throws DatabaseException
     *         the database exception
     * @throws SQLException
     */
    protected long getUsersId(String identifier, USER_IDENTITY_TYPES type, boolean closeCon)
            throws DatabaseException, SQLException {
        PreparedStatement selectSt = null;
        long userId = -1;
        try {
            StringBuffer sql = new StringBuffer("SELECT editor_user_id FROM (");
            String tableIdentity = "";

            switch (type) {
                case OPEN_ID:
                    tableIdentity = Constants.TABLE_OPEN_ID_IDENTITY;
                    break;
                case SHIBBOLETH:
                    tableIdentity = Constants.TABLE_SHIBBOLETH_IDENTITY;
                    break;
                case LDAP:
                    tableIdentity = Constants.TABLE_LDAP_IDENTITY;
                    break;
                default:
                    return DEFAULT_SYSTEM_USERS.NON_EXISTENT.getUserId();
            }
            sql.append(tableIdentity).append(" INNER JOIN editor_user ON ").append(tableIdentity)
                    .append(".editor_user_id = editor_user.id)");
            sql.append(" WHERE state = 'true' AND identity = (?)");
            selectSt = getConnection().prepareStatement(sql.toString());
            selectSt.setString(1, identifier);

        } catch (SQLException e) {
            LOGGER.error("Could not process select statement: " + selectSt, e);
        }
        try {
            ResultSet rs = selectSt.executeQuery();
            while (rs.next()) {
                userId = rs.getLong("editor_user_id");
            }
        } catch (SQLException e) {
            LOGGER.error("Query: " + selectSt, e);
            if (closeCon) {
                e.printStackTrace();
            } else {
                throw new SQLException(e);
            }
        } finally {
            if (closeCon) closeConnection();
        }
        return userId;
    }

    /**
     * Format timestamp to seconds, the format: dd.MM.yyyy HH:mm:ss.
     * 
     * @param timestamp
     *        the timestamp
     * @return the string
     */
    protected String formatTimestampToSeconds(Timestamp timestamp) {
        return FORMATTER_TO_SECONDS.format(timestamp);
    }
}
