package br.com.fiap.beans;

import br.com.fiap.dao.DaoFactory;
import br.com.fiap.dao.SellerDao;
import br.com.fiap.entities.Department;
import br.com.fiap.entities.Seller;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("==========Test 1: seller findById===========");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("\n==========Test 2: seller findByDepartmentId===========");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller seller1 : list) {
            System.out.println(seller1);
        }
    }
}
