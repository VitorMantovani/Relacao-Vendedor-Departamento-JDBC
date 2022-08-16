package br.com.fiap.dao;

import br.com.fiap.dao.impl.SellerDaoJDBC;
import br.com.fiap.db.DB;

public class DaoFactory {
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }
}
