package br.com.fiap.beans;

import br.com.fiap.dao.DaoFactory;
import br.com.fiap.dao.SellerDao;
import br.com.fiap.entities.Seller;

public class Main {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("==========Test 1: seller findById===========");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
    }
}
