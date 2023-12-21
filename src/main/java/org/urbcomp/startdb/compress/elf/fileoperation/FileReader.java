package org.urbcomp.startdb.compress.elf.fileoperation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {
    public static final int DEFAULT_BLOCK_SIZE = 1000;
    BufferedReader bufferedReader;
    private final int blockSize;

    public FileReader(String filePath, int blockSize) throws FileNotFoundException {
        java.io.FileReader fr = new java.io.FileReader(filePath);
        this.bufferedReader = new BufferedReader(fr);
        this.blockSize = blockSize;
    }

    public FileReader(String filePath) throws FileNotFoundException {
        this(filePath, DEFAULT_BLOCK_SIZE);
    }

    public double[] nextBlock() {
        double[] values = new double[DEFAULT_BLOCK_SIZE];
        String line;
        int counter = 0;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    values[counter++] = Double.parseDouble(line);
                    if (counter == blockSize) {
                        return values;
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
