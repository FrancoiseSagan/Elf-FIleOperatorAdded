package org.urbcomp.startdb.compress.elf.fileoperation;

import org.urbcomp.startdb.compress.elf.compressor.ElfCompressor;
import org.urbcomp.startdb.compress.elf.compressor.ICompressor;

import java.io.*;
import java.util.ArrayList;

import static org.urbcomp.startdb.compress.elf.fileoperation.OperationBetweenIntAndByte.intToTwoBytes;

public class CompressFileOperation {

    final String filePath;
    final String outputBinFilePath;

    public CompressFileOperation(String filePath, String outputBinFilePath){
        this.filePath = filePath;
        this.outputBinFilePath = outputBinFilePath;
    }

    public void readValuesFromCSV() throws IOException {
        FileReader fileReader;

        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        double[] vs;
        ArrayList<Byte> sizeList = new ArrayList<>();
        sizeList.add((byte)0x00);

        while ((vs = fileReader.nextBlock()) != null) {

            ICompressor compressor = new ElfCompressor();

            for (double v : vs) {
                compressor.addValue(v);
            }
            compressor.close();

            int sizeofcompressor = compressor.getSize()/8+12;

            byte[] result = compressor.getBytes();
            byte[] sizeOfBlock = intToTwoBytes(sizeofcompressor);

            sizeList.set(0,(byte)(sizeList.get(0)+1));
            sizeList.add(sizeOfBlock[0]);
            sizeList.add(sizeOfBlock[1]);

            writeBytesToFile(result, outputBinFilePath,sizeofcompressor);
        }
        addBlockSizeToFile(sizeList,outputBinFilePath);
    }

    private void writeBytesToFile(byte[] data, String outputBinFilePath,int size) {
        try (FileOutputStream fos = new FileOutputStream(outputBinFilePath, true)) {
            for (int i = 0; i < size; i++) {
                fos.write(data[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("when writing'" + outputBinFilePath + "',an error occurred:" + e.getMessage());
        }
    }

    private void addBlockSizeToFile(ArrayList<Byte> byteList,String outputBinFilePath) throws IOException {
        int spaceSize = byteList.size();

        RandomAccessFile file = new RandomAccessFile(new File(outputBinFilePath), "rw");

        byte[] existingData = new byte[(int) file.length()];
        file.read(existingData);

        file.setLength(file.length() + spaceSize);

        for (int i = existingData.length - 1; i >= 0; i--) {
            file.seek(i + spaceSize);
            file.writeByte(existingData[i]);
        }

        file.seek(0);
        for (int i = 0; i < spaceSize; i++) {
            file.writeByte(byteList.get(i));
        }

        file.close();
    }
}
