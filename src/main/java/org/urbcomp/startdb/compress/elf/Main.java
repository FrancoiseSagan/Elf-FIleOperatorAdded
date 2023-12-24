package org.urbcomp.startdb.compress.elf;

import org.urbcomp.startdb.compress.elf.fileoperation.*;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int flag = Integer.parseInt(args[0]);
        String csvFilePath;
        String binFilePath;

        if(flag == 0){
            csvFilePath = args[1];
            binFilePath = args[2];
            CompressFileOperation fileCompressor = new CompressFileOperation(csvFilePath,binFilePath);
            fileCompressor.readValuesFromCSV();
        }

        else{
            binFilePath = args[1];
            csvFilePath = args[2];
            DecompressFileOperation fileDecompressor = new DecompressFileOperation(binFilePath,csvFilePath);
            fileDecompressor.writeDoubleToCSV();
        }
    }
}
