package com.bondarenko.universityAssigment.lab9;

import com.bondarenko.universityAssigment.lab9.entities.*;
import com.bondarenko.universityAssigment.lab9.excelFormating.ExcelFileFormatter;
import com.bondarenko.universityAssigment.lab9.excelFormating.ExcelSheetFormatter;
import com.bondarenko.universityAssigment.lab9.fakeStoreAPI.PlatziFakeStoreAPI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ExcelDemo {
    private static PlatziFakeStoreAPI api = new PlatziFakeStoreAPI(100);
    private static ExcelFileFormatter excel = new ExcelFileFormatter();

    public static void main(String[] args) {
        String outputFilePath;
        if(args.length != 0){
            outputFilePath = Paths.get(args[0]).toAbsolutePath().toString();
        } else {
            System.out.println("Enter filename (don't forget .xlsx extension):");
            outputFilePath = Paths.get(new Scanner(System.in).nextLine()).toAbsolutePath().toString();
        }

        List<Product> products = api.getProducts();
        List<User> users = api.getUsers();
        List<Category> categories = api.getCategories();

        ExcelSheetFormatter<Product> productsSheet = excel.createSheet("Products");
        productsSheet.setHeaders(List.of("Id", "Title", "Description",  "Category", "Price"));
        productsSheet.setColumnMapper(prod ->
                List.of(prod.getId(), prod.getTitle(), prod.getDescription(), prod.getCategory().getName(), prod.getPrice()));
        productsSheet.setRows(products);

        ExcelSheetFormatter<User> usersSheet = excel.createSheet("Users");
        usersSheet.setHeaders(List.of("Id", "Name", "Role", "Email"));
        usersSheet.setColumnMapper(user -> List.of(user.getId(), user.getName(), user.getRole(), user.getEmail()));
        usersSheet.setRows(users);

        ExcelSheetFormatter<Category> categoriesSheet = excel.createSheet("Categories");
        categoriesSheet.setHeaders(List.of("Id", "Name"));
        categoriesSheet.setColumnMapper(category -> List.of(category.getId(), category.getName()));
        categoriesSheet.setRows(categories);

        try (FileOutputStream outputFile = new FileOutputStream(outputFilePath)){
            excel.writeToFile(outputFile);
            System.out.println("File " + outputFilePath + " created");
        } catch (FileNotFoundException e) {
            System.out.println("File " + outputFilePath + " not found");
        } catch (IOException e) {
            System.out.println("IO Exception while writing to file " + outputFilePath);
            System.out.println(e.getMessage());
        }
    }

}
