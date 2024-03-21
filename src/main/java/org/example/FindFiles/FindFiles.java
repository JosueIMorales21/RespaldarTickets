package org.example.FindFiles;

import org.example.LogConfig.LogConfig;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FindFiles {

    private static final Logger logger = Logger.getLogger(LogConfig.class.getName());

    public static void result() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        String currentDate = dateFormat.format(new Date());
        String txtName = "resp_" + currentDate + ".txt";
        String output = ".\\" + txtName;
        String input = ".\\";
        List<String> data;
        data = findAndProcessTicketVoucherFiles(input);
        writeToFile(data, output);
        logger.log(Level.INFO, "Nombre del archivo final: {0}", txtName);
        logger.log(Level.INFO, "Aplicación ejecutada correctamente.");
    }

    private static List<String> readFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static List<String> findAndProcessTicketVoucherFiles(String directoryPath) throws IOException {
        List<String> ticketVouchers = new ArrayList<>();
        String ticket = "ticket";
        String voucher = "voucher";

        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().contains(ticket) && name.endsWith(".txt") || name.toLowerCase().contains(voucher) && name.endsWith(".txt"));
        List<String> tickets = new ArrayList<>();
        int ticketCounter = 0;
        List<String> vouchers = new ArrayList<>();
        int voucherCounter = 0;

        if (files != null) {
            for (File file : files) {
                if (file.getName().contains(ticket)) {
                    tickets.add(file.getName());
                    ticketCounter++;
                } else if (file.getName().contains(voucher)) {
                    vouchers.add(file.getName());
                    voucherCounter++;
                }
            }
            if (tickets.isEmpty()) {
                logger.log(Level.SEVERE, "Sin existencias en Tickets.");
                throw new IOException();
            } else {
                logger.log(Level.INFO, "Tickets encontrados: {0}\n{1}", new Object[]{ticketCounter, tickets});
            }
            if(!vouchers.isEmpty()) {
                logger.log(Level.INFO, "Vouchers encontrados: {0}\n{1}", new Object[]{voucherCounter, vouchers});
            } else {
                logger.log(Level.INFO, "Sin coincidencias para voucher.");
            }
        }

        if (files != null) {
            for (File file : files) {
                logger.log(Level.INFO, "Leyendo el archivo: {0}", file);
                if (file.isFile() && file.exists()) {
                    List<String> fileContents = readFile(file.getAbsolutePath());
                    for (String line : fileContents) {
                        ticketVouchers.add(line);
                    }
                    ticketVouchers.add("***************************************");
                }
                logger.log(Level.INFO, "Lectura realizada con éxito.");
            }
        }

        return ticketVouchers;
    }


    private static void writeToFile(List<String> content, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            logger.log(Level.INFO, "Escribiendo la información recopilada...");
            for (String line : content) {
                writer.write(line + "\n");
            }
            logger.log(Level.INFO, "Escritura realizada exitosamente.");
        }
    }

}
