package com.eduardo.workshopjavafxjdbc.model.services;

import com.eduardo.workshopjavafxjdbc.model.dao.DaoFactory;
import com.eduardo.workshopjavafxjdbc.model.dao.SellerDao;
import com.eduardo.workshopjavafxjdbc.model.entities.Seller;

import java.util.List;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Seller obj) {
        if(obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void delete(Seller obj) {
        dao.deleteById(obj.getId());
    }

}
