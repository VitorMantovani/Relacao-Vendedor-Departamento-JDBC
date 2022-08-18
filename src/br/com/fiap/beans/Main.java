package br.com.fiap.beans;

import br.com.fiap.dao.DaoFactory;
import br.com.fiap.dao.SellerDao;
import br.com.fiap.entities.Department;
import br.com.fiap.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

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

        System.out.println("\n==========Test 3: seller findAll===========");
        list = sellerDao.findAll();
        for (Seller seller1 : list) {
            System.out.println(seller1);
        }

        System.out.println("\n==========Test 4: seller insert===========");
        Seller newSeller = new Seller(null, "Vitor", "vitor@gmail.com", new Date(), 5000.00, department);

        sellerDao.insert(newSeller);

        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("\n==========Test 5: seller update==========");
        seller = sellerDao.findById(1);
        seller.setName("Bruce");
        sellerDao.update(seller);
        System.out.println("Update completed");

        System.out.println("\n==========Test 6: seller delete==========");
        System.out.println("Enter id for delete test: ");
        int id = scanner.nextInt();
        sellerDao.deleteById(id);
        System.out.println("Deleted completed!");

        scanner.close();


    }
}
