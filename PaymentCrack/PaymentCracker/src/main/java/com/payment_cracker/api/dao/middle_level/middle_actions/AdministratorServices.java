package com.payment_cracker.api.dao.middle_level.middle_actions;


import com.payment_cracker.api.dao.exceptions.DbException;
import com.payment_cracker.api.dao.middle_level.middle_entities.UserEntity;
import com.payment_cracker.api.dao.utils.SessionManager;


public class AdministratorServices extends UserServices {
    public AdministratorServices(SessionManager sessionManager) {
        super(sessionManager);
    }

    protected void setBan(Long id, boolean banStage) throws DbException {
        UserEntity userEntity = (new UserServices(super.getSessionManager())).getById(id);
        userEntity.setBan(banStage);
        super.update(id, userEntity);
    }

    protected void makeAdministrator(Long id, boolean isAdmin) throws DbException {
        UserEntity userEntity = (new UserServices(super.getSessionManager())).getById(id);
        userEntity.setAdministrator(isAdmin);
        super.update(id, userEntity);
    }
}
