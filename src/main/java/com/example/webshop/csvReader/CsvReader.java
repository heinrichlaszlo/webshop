package com.example.webshop.csvReader;



import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.payments.Payment;

import lombok.extern.slf4j.Slf4j;


import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


@Slf4j
public class CsvReader {


    public static List<Payment> readPaymentsFromCsv(String fileName) throws IOException {
        DateFormat formatter=new SimpleDateFormat("yyyy.MM.dd");

        Logger logger = Logger.getLogger("src/main/resources/application.log");
        FileHandler fh;

        List<Payment> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {

            fh = new FileHandler("src/main/resources/application.log");
            logger.addHandler(fh);
            SimpleFormatter logFormatter = new SimpleFormatter();
            fh.setFormatter(logFormatter);


            String line;
            while ((line = br.readLine()) != null) {

                String[] items = line.split(";");

                    Payment payment = new Payment();
                    payment.setWebShopId(items[0]);
                    payment.setCustomerId(items[1]);
                    payment.setPaymentMethod((items[2]));
                    payment.setAmount(Integer.valueOf(items[3]));
                    payment.setBankAccountNumber(items[4]);
                    payment.setCardNumber(items[5]);

                    if (isValidCvsData(payment) == false || isValidDateFormat(items[6],formatter) == false ){

                    logger.info("invalid payment data:  " + payment);
                }
                else {
                    payment.setPaymentDate(formatter.parse(items[6]));
                    result.add(payment);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return result;
    }

    public static List<Customer> readCustomerFromCsv(String fileName) throws IOException {
        List<Customer> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String[] items = line.split(";");
                Customer customer = new Customer();
                customer.setWebShopId(items[0]);
                customer.setCustomerId(items[1]);
                customer.setCustomerName(items[2]);
                customer.setCustomerAddress(items[3]);
                result.add(customer);
            }
        } finally {
            br.close();
        }
        return result;
    }

    private static boolean isValidCvsData(Payment payment){
        return (payment.getPaymentMethod().equals("card") && payment.getBankAccountNumber().isEmpty()) || (payment.getPaymentMethod().equals("transfer") && payment.getCardNumber().isEmpty());
    }

    private static boolean isValidDateFormat(String date,DateFormat formatter) {

        formatter.setLenient(false);
        try {
            formatter.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
