package com.eduardo.workshopjavafxjdbc.model.dao;


import com.eduardo.workshopjavafxjdbc.db.DB;
import com.eduardo.workshopjavafxjdbc.model.dao.impl.DepartmentDaoJDBC;
import com.eduardo.workshopjavafxjdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
