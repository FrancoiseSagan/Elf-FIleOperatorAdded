package org.urbcomp.startdb.compress.elf.fileoperation;

import org.urbcomp.startdb.compress.elf.decompressor.ElfDecompressor;
import org.urbcomp.startdb.compress.elf.decompressor.IDecompressor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.urbcomp.startdb.compress.elf.fileoperation.OperationBetweenIntAndByte.oneBytesToInt;
import static org.urbcomp.startdb.compress.elf.fileoperation.OperationBetweenIntAndByte.twoBytesToInt;

public class DecompressFileOperation {

    public static void writeDoubleToCSV(String binFilePath, String filePath) throws IOException {

        List<byte[]> data = readBytesFromFile(binFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (int i = 0; i < data.size(); i++) {

                IDecompressor decompressor = new ElfDecompressor(data.get(i));
                List<Double> blockValues = decompressor.decompress();

                for (double element : blockValues) {
                    writer.write(String.valueOf(element));
                    writer.newLine();
                }
            }
        }
    }

    public static List<byte[]> readBytesFromFile(String outputBinFilePath) throws IOException {
        File file = new File(outputBinFilePath); // 创建一个File对象，指定bin文件的路径
        FileInputStream inStream = new FileInputStream(file); // 创建一个FileInputStream对象，关联bin文件

        byte[] length = new byte[1];
        inStream.read(length);

        int intlength = oneBytesToInt(length[0]);
        byte[] sizeOfBlock = new byte[intlength*2];
        inStream.read(sizeOfBlock);

        List<Integer> sizeOfBlockToInt = new ArrayList<>();

        for(int i =0;i<intlength;i++){
            byte[] tempArr = new byte[2];
            tempArr[0] = sizeOfBlock[2*i];
            tempArr[1] = sizeOfBlock[2*i+1];
            sizeOfBlockToInt.add(twoBytesToInt(tempArr));
        }

        List<byte[]> byteTodec = new ArrayList<>();
        for(int i = 0;i < intlength;i++){
            byte[] byteOfBlock = new byte[sizeOfBlockToInt.get(i)];
            inStream.read(byteOfBlock);
            byteTodec.add(byteOfBlock);
        }
        inStream.close();

        return byteTodec;
    }
}
