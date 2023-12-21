package org.urbcomp.startdb.compress.elf.fileoperation;

import org.urbcomp.startdb.compress.elf.compressor.ElfCompressor;
import org.urbcomp.startdb.compress.elf.compressor.ICompressor;

import java.io.*;
import java.util.ArrayList;

import static org.urbcomp.startdb.compress.elf.fileoperation.OperationBetweenIntAndByte.intToTwoBytes;

public class CompressFileOperation {

    private static int AmountOfByte = 0;

    public static void readValuesFromCSV(String filePath, String outputBinFilePath) throws IOException {
        FileReader fileReader;

        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        double[] vs;
        int numOfBlock = 0;
        ArrayList<Byte> sizeList = new ArrayList<>();
        sizeList.add((byte)0x00);

        while ((vs = fileReader.nextBlock()) != null) {
            numOfBlock++;
            System.out.println("第"+numOfBlock+"个块");

            ICompressor compressor = new ElfCompressor();

            for (double v : vs) {
                compressor.addValue(v);
            }
            compressor.close();

            int sizeofcompressor = compressor.getSize()/8+6;

            byte[] result = compressor.getBytes();
            byte[] sizeOfBlock = intToTwoBytes(sizeofcompressor);

            sizeList.set(0,(byte)(sizeList.get(0)+1));
            sizeList.add(sizeOfBlock[0]);
            sizeList.add(sizeOfBlock[1]);

            writeBytesToFile(result, outputBinFilePath,sizeofcompressor);
        }
        addBlockSizeToFile(sizeList,outputBinFilePath);
    }

    private static void writeBytesToFile(byte[] data, String outputBinFilePath,int size) {
        try (FileOutputStream fos = new FileOutputStream(outputBinFilePath, true)) {
            for (int i = 0; i < size; i++) {
                fos.write(data[i]);
                if(i == size-1){
                    System.out.println("写入" + (i+1) + "字节");
                    AmountOfByte += i+1;
                }
            }

            System.out.println("共有" + AmountOfByte + "字节");
            System.out.println("文件写入成功。");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("写入文件'" + outputBinFilePath + "'时出错：" + e.getMessage());
        }
    }

    private static void addBlockSizeToFile(ArrayList<Byte> byteList,String outputBinFilePath) throws IOException {
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
