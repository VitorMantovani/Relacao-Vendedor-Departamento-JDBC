package br.com.fiap.dao;

import br.com.fiap.dao.impl.DepartmentDaoJDBC;
import br.com.fiap.dao.impl.SellerDaoJDBC;
import br.com.fiap.db.DB;

public class DaoFactory {
    /*
    / Método que retorna o tipo da interface SellerDao, mas internamente
    vai instanciar uma implementação. A implementação não vai ser exposta,
    deixa somente a Interface. Desse modo ao instanciar um SellerDao no
    programa principal apenas chamo este método
     */
    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    /*
    / Método que retorna o tipo da interface DepartmentDao, mas internamente
    vai instanciar uma implementação. A implementação não vai ser exposta,
    deixa somente a Interface. Desse modo ao instanciar um DepartmentDao no
    programa principal apenas chamo este método
    */
    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
