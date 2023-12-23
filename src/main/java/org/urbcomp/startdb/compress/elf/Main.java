package org.urbcomp.startdb.compress.elf;

import java.io.*;

import static org.urbcomp.startdb.compress.elf.fileoperation.CompressFileOperation.readValuesFromCSV;
import static org.urbcomp.startdb.compress.elf.fileoperation.DecompressFileOperation.writeDoubleToCSV;

public class Main {

    public static void main(String[] args) throws IOException {
        int flag = Integer.parseInt(args[0]);
        String csvFilePath;
        String binFilePath;

        if(flag == 0){
            csvFilePath = args[1];
            binFilePath = args[2];
            readValuesFromCSV(csvFilePath,binFilePath);
        }

        else{
            binFilePath = args[1];
            csvFilePath = args[2];
            writeDoubleToCSV(binFilePath,csvFilePath);
        }
    }
}
