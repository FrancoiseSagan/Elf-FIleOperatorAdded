package org.urbcomp.startdb.compress.elf;

import java.io.*;

import static org.urbcomp.startdb.compress.elf.fileoperation.DecompressFileOperation.writeDoubleToCSV;

public class Main {

    public static void main(String[] args) throws IOException {
        String csvFilePath = "C:\\Users\\25379\\Downloads\\Stocks-UK.csv";
        String outputBinFilePath = "fuck.bin";
        String outputCSVFilePath = "null.csv";
        writeDoubleToCSV(outputBinFilePath,outputCSVFilePath);
    }
}
