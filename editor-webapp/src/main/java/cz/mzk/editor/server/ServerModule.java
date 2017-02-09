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

package cz.mzk.editor.server;

import com.gwtplatform.dispatch.rpc.server.spring.HandlerModule;
import com.gwtplatform.dispatch.rpc.server.spring.configuration.DefaultModule;
import cz.mzk.editor.server.handler.ChangeRightsHandler;
import cz.mzk.editor.server.handler.CheckAvailabilityHandler;
import cz.mzk.editor.server.handler.CheckRightsHandler;
import cz.mzk.editor.server.handler.ConvertToJPEG2000Handler;
import cz.mzk.editor.server.handler.DownloadDigitalObjectDetailHandler;
import cz.mzk.editor.server.handler.FindAltoOcrFilesBatchHandler;
import cz.mzk.editor.server.handler.FindAltoOcrFilesHandler;
import cz.mzk.editor.server.handler.FindMetadataHandler;
import cz.mzk.editor.server.handler.GetAllRolesHandler;
import cz.mzk.editor.server.handler.GetClientConfigHandler;
import cz.mzk.editor.server.handler.GetDOModelHandler;
import cz.mzk.editor.server.handler.GetDescriptionHandler;
import cz.mzk.editor.server.handler.GetDigitalObjectDetailHandler;
import cz.mzk.editor.server.handler.GetFullImgMetadataHandler;
import cz.mzk.editor.server.handler.GetIngestInfoHandler;
import cz.mzk.editor.server.handler.GetLockInformationHandler;
import cz.mzk.editor.server.handler.GetLoggedUserHandler;
import cz.mzk.editor.server.handler.GetObjectsToSortHandler;
import cz.mzk.editor.server.handler.GetOcrFromPdfHandler;
import cz.mzk.editor.server.handler.GetRecentlyModifiedHandler;
import cz.mzk.editor.server.handler.GetRelationshipsHandler;
import cz.mzk.editor.server.handler.GetUserRolesRightsIdentitiesHandler;
import cz.mzk.editor.server.handler.HasUserRightsHandler;
import cz.mzk.editor.server.handler.InitializeConversionHandler;
import cz.mzk.editor.server.handler.InsertNewDigitalObjectHandler;
import cz.mzk.editor.server.handler.LockDigitalObjectHandler;
import cz.mzk.editor.server.handler.LogoutHandler;
import cz.mzk.editor.server.handler.PutDescriptionHandler;
import cz.mzk.editor.server.handler.PutDigitalObjectDetailHandler;
import cz.mzk.editor.server.handler.PutRecentlyModifiedHandler;
import cz.mzk.editor.server.handler.PutRemoveRolesHandler;
import cz.mzk.editor.server.handler.PutRemoveUserRightsHandler;
import cz.mzk.editor.server.handler.PutRemoveUserRolesHandler;
import cz.mzk.editor.server.handler.PutUserIdentityHandler;
import cz.mzk.editor.server.handler.PutUserInfoHandler;
import cz.mzk.editor.server.handler.QuartzAddOcrHandler;
import cz.mzk.editor.server.handler.QuartzConvertImagesHandler;
import cz.mzk.editor.server.handler.QuartzScheduleJobsHandler;
import cz.mzk.editor.server.handler.RemoveDigitalObjectHandler;
import cz.mzk.editor.server.handler.RemoveUserHandler;
import cz.mzk.editor.server.handler.RemoveUserIdentityHandler;
import cz.mzk.editor.server.handler.ScanFolderHandler;
import cz.mzk.editor.server.handler.ScanInputQueueHandler;
import cz.mzk.editor.server.handler.StoreTreeStructureHandler;
import cz.mzk.editor.server.handler.StoredItemsHandler;
import cz.mzk.editor.server.handler.UnlockDigitalObjectHandler;
import cz.mzk.editor.shared.rpc.action.ChangeRightsAction;
import cz.mzk.editor.shared.rpc.action.CheckAvailabilityAction;
import cz.mzk.editor.shared.rpc.action.CheckRightsAction;
import cz.mzk.editor.shared.rpc.action.ConvertToJPEG2000Action;
import cz.mzk.editor.shared.rpc.action.DownloadDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.FindAltoOcrFilesAction;
import cz.mzk.editor.shared.rpc.action.FindAltoOcrFilesBatchAction;
import cz.mzk.editor.shared.rpc.action.FindMetadataAction;
import cz.mzk.editor.shared.rpc.action.GetAllRolesAction;
import cz.mzk.editor.shared.rpc.action.GetClientConfigAction;
import cz.mzk.editor.shared.rpc.action.GetDOModelAction;
import cz.mzk.editor.shared.rpc.action.GetDescriptionAction;
import cz.mzk.editor.shared.rpc.action.GetDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.GetFullImgMetadataAction;
import cz.mzk.editor.shared.rpc.action.GetIngestInfoAction;
import cz.mzk.editor.shared.rpc.action.GetLockInformationAction;
import cz.mzk.editor.shared.rpc.action.GetLoggedUserAction;
import cz.mzk.editor.shared.rpc.action.GetObjectsToSortAction;
import cz.mzk.editor.shared.rpc.action.GetOcrFromPdfAction;
import cz.mzk.editor.shared.rpc.action.GetRecentlyModifiedAction;
import cz.mzk.editor.shared.rpc.action.GetRelationshipsAction;
import cz.mzk.editor.shared.rpc.action.GetUserRolesRightsIdentitiesAction;
import cz.mzk.editor.shared.rpc.action.HasUserRightsAction;
import cz.mzk.editor.shared.rpc.action.InitializeConversionAction;
import cz.mzk.editor.shared.rpc.action.InsertNewDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.LockDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.LogoutAction;
import cz.mzk.editor.shared.rpc.action.PutDescriptionAction;
import cz.mzk.editor.shared.rpc.action.PutDigitalObjectDetailAction;
import cz.mzk.editor.shared.rpc.action.PutRecentlyModifiedAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveRolesAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRightsAction;
import cz.mzk.editor.shared.rpc.action.PutRemoveUserRolesAction;
import cz.mzk.editor.shared.rpc.action.PutUserIdentityAction;
import cz.mzk.editor.shared.rpc.action.PutUserInfoAction;
import cz.mzk.editor.shared.rpc.action.QuartzAddOcrAction;
import cz.mzk.editor.shared.rpc.action.QuartzConvertImagesAction;
import cz.mzk.editor.shared.rpc.action.QuartzScheduleJobsAction;
import cz.mzk.editor.shared.rpc.action.RemoveDigitalObjectAction;
import cz.mzk.editor.shared.rpc.action.RemoveUserAction;
import cz.mzk.editor.shared.rpc.action.RemoveUserIdentityAction;
import cz.mzk.editor.shared.rpc.action.ScanFolderAction;
import cz.mzk.editor.shared.rpc.action.ScanInputQueueAction;
import cz.mzk.editor.shared.rpc.action.StoreTreeStructureAction;
import cz.mzk.editor.shared.rpc.action.StoredItemsAction;
import cz.mzk.editor.shared.rpc.action.UnlockDigitalObjectAction;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// TODO: Auto-generated Javadoc
/**
 * Module which binds the handlers and configurations.
 */
@Configuration
@Import(DefaultModule.class)
public class ServerModule
        extends HandlerModule {

    /*
     * (non-Javadoc)
     * @see
     * com.gwtplatform.dispatch.server.guice.HandlerModule#configureHandlers()
     */
    @Override
    protected void configureHandlers() {
        bindHandler(ScanInputQueueAction.class, ScanInputQueueHandler.class);
        bindHandler(ScanFolderAction.class, ScanFolderHandler.class);
        bindHandler(GetClientConfigAction.class, GetClientConfigHandler.class);
        bindHandler(GetDigitalObjectDetailAction.class, GetDigitalObjectDetailHandler.class);
        bindHandler(PutDigitalObjectDetailAction.class, PutDigitalObjectDetailHandler.class);
        bindHandler(GetRecentlyModifiedAction.class, GetRecentlyModifiedHandler.class);
        bindHandler(PutRecentlyModifiedAction.class, PutRecentlyModifiedHandler.class);
        bindHandler(GetDescriptionAction.class, GetDescriptionHandler.class);
        bindHandler(PutDescriptionAction.class, PutDescriptionHandler.class);
        bindHandler(CheckAvailabilityAction.class, CheckAvailabilityHandler.class);
        bindHandler(FindMetadataAction.class, FindMetadataHandler.class);
        bindHandler(ConvertToJPEG2000Action.class, ConvertToJPEG2000Handler.class);
        bindHandler(LockDigitalObjectAction.class, LockDigitalObjectHandler.class);
        bindHandler(UnlockDigitalObjectAction.class, UnlockDigitalObjectHandler.class);
        bindHandler(DownloadDigitalObjectDetailAction.class, DownloadDigitalObjectDetailHandler.class);
        bindHandler(StoredItemsAction.class, StoredItemsHandler.class);
        bindHandler(GetDOModelAction.class, GetDOModelHandler.class);
        bindHandler(GetLockInformationAction.class, GetLockInformationHandler.class);
        bindHandler(InsertNewDigitalObjectAction.class, InsertNewDigitalObjectHandler.class);
        bindHandler(GetRelationshipsAction.class, GetRelationshipsHandler.class);
        bindHandler(GetObjectsToSortAction.class, GetObjectsToSortHandler.class);
        bindHandler(RemoveDigitalObjectAction.class, RemoveDigitalObjectHandler.class);
        bindHandler(GetIngestInfoAction.class, GetIngestInfoHandler.class);
        bindHandler(LogoutAction.class, LogoutHandler.class);
        bindHandler(FindAltoOcrFilesAction.class, FindAltoOcrFilesHandler.class);
        bindHandler(FindAltoOcrFilesBatchAction.class, FindAltoOcrFilesBatchHandler.class);
        bindHandler(StoreTreeStructureAction.class, StoreTreeStructureHandler.class);
        bindHandler(ChangeRightsAction.class, ChangeRightsHandler.class);
        bindHandler(GetFullImgMetadataAction.class, GetFullImgMetadataHandler.class);
        bindHandler(InitializeConversionAction.class, InitializeConversionHandler.class);
        bindHandler(GetOcrFromPdfAction.class, GetOcrFromPdfHandler.class);
        bindHandler(QuartzConvertImagesAction.class, QuartzConvertImagesHandler.class);
        bindHandler(QuartzAddOcrAction.class, QuartzAddOcrHandler.class);
        bindHandler(QuartzScheduleJobsAction.class, QuartzScheduleJobsHandler.class);
        //bind(EditorConfiguration.class).to(EditorConfigurationImpl.class).asEagerSingleton();

        bindHandler(RemoveUserAction.class, RemoveUserHandler.class);
        bindHandler(GetUserRolesRightsIdentitiesAction.class, GetUserRolesRightsIdentitiesHandler.class);
        bindHandler(PutUserIdentityAction.class, PutUserIdentityHandler.class);
        bindHandler(PutUserInfoAction.class, PutUserInfoHandler.class);
        bindHandler(RemoveUserIdentityAction.class, RemoveUserIdentityHandler.class);
        bindHandler(PutRemoveUserRolesAction.class, PutRemoveUserRolesHandler.class);
        bindHandler(GetAllRolesAction.class, GetAllRolesHandler.class);
        bindHandler(GetLoggedUserAction.class, GetLoggedUserHandler.class);
        bindHandler(CheckRightsAction.class, CheckRightsHandler.class);
        bindHandler(PutRemoveUserRightsAction.class, PutRemoveUserRightsHandler.class);
        bindHandler(PutRemoveRolesAction.class, PutRemoveRolesHandler.class);
        bindHandler(HasUserRightsAction.class, HasUserRightsHandler.class);


        // DAO
//        bind(InputQueueItemDAO.class).to(InputQueueItemDAOImpl.class);
//        bind(ImageResolverDAO.class).to(ImageResolverDAOImpl.class);
//        bind(RecentlyModifiedItemDAO.class).to(RecentlyModifiedItemDAOImpl.class);
//        bind(LockDAO.class).to(LockDAOImpl.class);
//        bind(StoredItemsDAO.class).to(StoredItemsDAOImpl.class);
//        bind(TreeStructureDAO.class).to(TreeStructureDAOImpl.class);
//        bind(DAOUtils.class).to(DAOUtilsImpl.class);
//        bind(DescriptionDAO.class).to(DescriptionDAOImpl.class);
//        bind(DigitalObjectDAO.class).to(DigitalObjectDAOImpl.class);
//        bind(LogInOutDAO.class).to(LogInOutDAOImpl.class);
//        bind(ConversionDAO.class).to(ConversionDAOImpl.class);
//        bind(UserDAO.class).to(UserDAOImpl.class);
//        bind(SecurityUserDAO.class).to(SecurityUserDAOImpl.class);
//        bind(UserDAO.class).to(UserDAOImpl.class);
//        bind(DAOUtils.class).to(DAOUtilsImpl.class);
//        bind(StoredAndLocksDAO.class).to(StoredAndLocksDAOImpl.class);
//        bind(OcrDao.class).to(OcrDaoImpl.class);
//        bind(UserDaoNew.class).to(UserDaoNewImpl.class);

        // Fedora
//        bind(FedoraAccess.class).annotatedWith(Names.named("rawFedoraAccess")).to(FedoraAccessImpl.class)
//                .in(Scopes.SINGLETON);
//        bind(FedoraAccess.class).annotatedWith(Names.named("securedFedoraAccess"))
//                .to(SecuredFedoraAccessImpl.class).in(Scopes.SINGLETON);
//        bind(NamespaceContext.class).to(FedoraNamespaceContext.class).in(Scopes.SINGLETON);
//
//        bind(FedoraDigitalObjectHandler.class).to(FedoraDigitalObjectHandlerImpl.class);
//        bind(StoredDigitalObjectHandler.class).to(StoredDigitalObjectHandlerImpl.class);
//        bind(Z3950Client.class).to(Z3950ClientImpl.class);
//        bind(OAIPMHClient.class).to(OAIPMHClientImpl.class);
//        bind(XServicesClient.class).to(XServicesClientImpl.class);
//
//        bind(IPaddressChecker.class).to(RequestIPaddressChecker.class);
//
//        // Quartz
//        bind(SchedulerFactory.class).to(StdSchedulerFactory.class).in(Scopes.SINGLETON);
//        bind(GuiceJobFactory.class).in(Scopes.SINGLETON);
//        bind(Quartz.class).in(Scopes.SINGLETON);
//
//        install(new FactoryModuleBuilder()
//                .implement(ScanFolder.class, ScanFolderImpl.class)
//                .build(ScanFolderImpl.ScanFolderFactory.class));
//
//
//        // static injection
//        requestStaticInjection(FedoraUtils.class);
//        requestStaticInjection(URLS.class);
//        requestStaticInjection(IngestUtils.class);
//        requestStaticInjection(ServerUtils.class);
//        requestStaticInjection(FOXMLBuilderMapping.class);
//        requestStaticInjection(SessionListener.class);
    }
}