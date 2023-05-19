package com.example.webshop.tasks;

import com.example.webshop.csvReader.CsvReader;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.payments.Payment;
import com.example.webshop.model.costumerPurchases.CostumerPurchase;
import com.example.webshop.model.webshopPurchase.WebshopPurchase;

import java.io.IOException;
import java.util.*;

import static com.example.webshop.csvWriter.CsvWriter.writeToCsvFile;

public class Tasks {

    public static final String COSTUMER_PATH = "src/main/resources/customer.csv";

    public static final String PAYMENTS_PATH = "src/main/resources/payments.csv";


    public static void writeTasksToCsvFiles() {

        List<Customer> customers = null;
        List<Payment> payments = null;

        try {
            customers = CsvReader.readCustomerFromCsv(COSTUMER_PATH);
            payments = CsvReader.readPaymentsFromCsv(PAYMENTS_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var sumOfAllPurchases = Tasks.getSumOfAllPurchases(customers, payments);

        String[] costumerPurchaseColumns = new String[]
                { "name","address","sumOfPurchases"};

        writeToCsvFile("src/main/resources/report01.csv", sumOfAllPurchases,costumerPurchaseColumns,CostumerPurchase.class);

        writeToCsvFile("src/main/resources/top.csv", getHighestAndSecondHighestPurchases(sumOfAllPurchases),costumerPurchaseColumns,CostumerPurchase.class);

        String[] webShopPurchaseColumns = new String[]
                { "webshop","sumOfCardPayments","sumOfBankPayments"};

        writeToCsvFile("src/main/resources/report02.csv",getSumOfWebshopPurchases(payments),webShopPurchaseColumns, WebshopPurchase.class);
    }

    //feladat 3
    private static List<CostumerPurchase> getSumOfAllPurchases(List<Customer> customers, List<Payment> payments){
        List<CostumerPurchase> purchases =new ArrayList<>();

        for (Customer customer: customers){
            purchases.add(CostumerPurchase.builder().name(customer.getCustomerName()).address(customer.getCustomerAddress())
                    .sumOfPurchases(payments.stream().filter(payment -> payment.getCustomerId().equals(customer.getCustomerId())).mapToInt(payment -> payment.getAmount()).sum()).build());
        }

        return purchases;
    }

    //feladat 4
    private static List<CostumerPurchase> getHighestAndSecondHighestPurchases(List<CostumerPurchase> costumerPurchases) {

        List<CostumerPurchase> highestAndSecondHighestPurchases = new ArrayList<>();

        var highestPurchase = getHighestCostumerPurchase(costumerPurchases);

        costumerPurchases.removeIf(costumerPurchase -> costumerPurchase.getName().equals(highestPurchase.getName()));

        var secondHighestPurchase =getHighestCostumerPurchase(costumerPurchases);

        highestAndSecondHighestPurchases.add(highestPurchase);
        highestAndSecondHighestPurchases.add(secondHighestPurchase);
        return highestAndSecondHighestPurchases;
    }

    //feladat 5
    private static List<WebshopPurchase> getSumOfWebshopPurchases(List<Payment> payments){
        Set<String> webShops = new HashSet<>();

        for(Payment payment: payments) {
            webShops.add(payment.getWebShopId());
        }

        List<WebshopPurchase> webshopPurchases = new ArrayList<>();

        for(String webShop: webShops){
            webshopPurchases.add(WebshopPurchase.builder().webshop(webShop).sumOfCardPayments(payments.stream()
                    .filter(payment -> payment.getPaymentMethod()
                    .equals("card") && payment.getWebShopId().equals(webShop))
                    .mapToInt(payment -> payment.getAmount()).sum())
                    .sumOfBankPayments(payments.stream().filter(payment -> payment.getPaymentMethod()
                    .equals("transfer") && payment.getWebShopId().equals(webShop)).mapToInt(payment -> payment.getAmount()).sum()).build());

        }
        return webshopPurchases;
    }

    private static CostumerPurchase getHighestCostumerPurchase(List<CostumerPurchase> costumerPurchases){
        int highestPurchaseNumber = 0;
        CostumerPurchase highestPurchase = new CostumerPurchase();
        for (CostumerPurchase costumerPurchase : costumerPurchases) {
            if (costumerPurchase.getSumOfPurchases() > highestPurchaseNumber)
            {
                highestPurchaseNumber = costumerPurchase.getSumOfPurchases();
                highestPurchase.setName(costumerPurchase.getName());
                highestPurchase.setAddress(costumerPurchase.getAddress());
                highestPurchase.setSumOfPurchases(costumerPurchase.getSumOfPurchases());
            }
        }
        return highestPurchase;
        }

}
