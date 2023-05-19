package com.example.webshop.csvWriter;

import com.example.webshop.model.costumerPurchases.CostumerPurchase;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileWriter;
import java.util.List;


public class CsvWriter {


    public static void writeToCsvFile(String path, List<?> listToWrite,String[] columns, Class classToSetType) {
        try {
            FileWriter writer = new FileWriter(path);

            ColumnPositionMappingStrategy mappingStrategy= new ColumnPositionMappingStrategy();
            mappingStrategy.setType(classToSetType);

            mappingStrategy.setColumnMapping(columns);

            StatefulBeanToCsvBuilder<CostumerPurchase> builder= new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategy).build();
            beanWriter.write(listToWrite);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
