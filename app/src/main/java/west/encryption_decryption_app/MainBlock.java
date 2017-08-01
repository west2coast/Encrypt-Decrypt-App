package west.encryption_decryption_app;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class MainBlock extends Fragment {

    Button buttonEn;
    EditText mes, keys;
    TextView outputter;

    public MainBlock() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.block_main, container, false);
        buttonEn = (Button) view.findViewById(R.id.blockEncrypt);
        mes = (EditText) view.findViewById(R.id.doubleU_row2);
        keys = (EditText) view.findViewById(R.id.doubleU_col1);
        outputter = (TextView) view.findViewById(R.id.output_BlockDecrypt);
        buttonEn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String encrypted = "";
                fileLogic backEnd = new fileLogic();
                String message = mes.getText().toString();
                String keyString = keys.getText().toString();
                String[] parts = keyString.split(" ");
                String sKey1 = parts [0];
                String sKey2 = parts [1];
                String sKey3 = parts [2];
                String sKey4 = parts [3];
                String sMainKey = parts [4];
                try {
                    encrypted = backEnd.encrypt(message, sKey1, sKey2, sKey3, sKey4, sMainKey);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                outputter.setText(encrypted);
            }
        });

        return view;
    }

}
class fileLogic {
    String curLine = "";
    public fileLogic (){

    }
    String key1;
    String key2;
    String key3;
    String key4;
    String mKey;
    String directory;
    public String encrypt(String dir, String k1, String k2, String k3, String k4, String mk) throws FileNotFoundException {


        String encryptedKey = "";
        directory = dir;
        key1 = k1;
        key2 = k2;
        key3 = k3;
        key4 = k4;
        mKey = mk;

        String[] parts = directory.split(" ");
        List<String> characters = new ArrayList<>(Arrays.asList(parts));


        //Check if file size is not multiple of 100 and pad it
        long length = characters.size();
        if (length % 100 != 0){
            length = ((characters.size() + 99) / 100 ) * 100; //Round up to nearest hundred
        }
        long amountToPad = length - characters.size();

        //pad file with spaces
        for (int loop = 0; loop < amountToPad; loop++){
            characters.add("_");
        }
        String block = characters.toString();

        //remove "[" and "]" from notepad file
        block = block.replaceAll("\\[", "").replaceAll("\\]","");

        Block bigBlock = new Block (block.split(", "));
        //System.out.println(characters.size());

        //Split apart characters into blocks of 100 and save to file "a1.txt"
        bigBlock.sliceAndSave();

        //Save Sliced Bits into 10x10 Arrays and save them as their own Object, could not get it to work with arraylists within the slice function for Block class
        File inputFile1 = new File("saveData.txt");
        Scanner reader1 = new Scanner(new FileReader(inputFile1));

        String hundredBitBlock = "";
        ArrayList<String []> hundredBitBlocksString = new ArrayList<>();

        ArrayList<HundredBitBlock> hundredBitBlocks = new ArrayList<>();//<----Important
        String [] SingleLineHBB = new String [100];

        while (reader1.hasNext()){
            curLine = reader1.nextLine();
            if (curLine.equals( "----------")){
                SingleLineHBB = hundredBitBlock.split("");
                hundredBitBlocks.add(new HundredBitBlock(SingleLineHBB));
                SingleLineHBB = new String[10];
                hundredBitBlock = "";
            }
            else{
                hundredBitBlock = hundredBitBlock + curLine;
            }

        }


        for (int loop = 0; loop < hundredBitBlocks.size(); loop++){
            hundredBitBlocks.get(loop).slice();
        }


        String key = "";

        //Convert all string keys to int and error trap keys
        //Key 1
        String [] sKeyArr1 = key1.split(" ");
        int [] keyArr1 = new int [sKeyArr1.length];

        for (int loop = 0; loop < sKeyArr1.length; loop++){
            keyArr1[loop] = Integer.parseInt(sKeyArr1[loop]);

            if (keyArr1[loop] > 9){
                System.out.println("5x5 Square has 9 diagonals");
                System.exit(0);
            }
            else{
                if (keyArr1[loop] > 5){ //take into account diagonals > 5
                    keyArr1[loop] = 10 - keyArr1[loop];
                }
            }
        }

        //Key 2
        String [] sKeyArr2 = key2.split(" ");
        int [] keyArr2 = new int [sKeyArr2.length];
        for (int loop = 0; loop < sKeyArr2.length; loop++){
            keyArr2[loop] = Integer.parseInt(sKeyArr2[loop]);

            if (keyArr2[loop] > 9){
                System.out.println("5x5 Square has 9 diagonals");
                System.exit(0);
            }
            else{
                if (keyArr2[loop] > 5){ //take into account diagonals > 5
                    keyArr2[loop] = 10 - keyArr2[loop];
                }
            }
        }


        //Key 3
        String [] sKeyArr3 = key3.split(" ");
        int [] keyArr3 = new int [sKeyArr3.length];
        for (int loop = 0; loop < sKeyArr3.length; loop++){
            keyArr3[loop] = Integer.parseInt(sKeyArr3[loop]);

            if (keyArr3[loop] > 9){
                System.out.println("5x5 Square has 9 diagonals");
                System.exit(0);
            }
            else{
                if (keyArr3[loop] > 5){ //take into account diagonals > 5
                    keyArr3[loop] = 10 - keyArr3[loop];
                }
            }
        }


        //Key 4
        String [] sKeyArr4 = key4.split(" ");
        int [] keyArr4 = new int [sKeyArr4.length];
        for (int loop = 0; loop < sKeyArr4.length; loop++){
            keyArr4[loop] = Integer.parseInt(sKeyArr4[loop]);

            if (keyArr4[loop] > 9){
                System.out.println("5x5 Square has 9 diagonals");
                System.exit(0);
            }
            else{
                if (keyArr4[loop] > 5){ //take into account diagonals > 5
                    keyArr4[loop] = 10 - keyArr4[loop];
                }
            }
        }



        //Main Key
        String [] sMainKeyArr = mKey.split(" ");
        int [] mainKeyArr = new int [sMainKeyArr.length];

        for (int loop = 0; loop < sMainKeyArr.length; loop++){
            mainKeyArr[loop] = Integer.parseInt(sMainKeyArr[loop]);
            if (mainKeyArr[loop] > 19){
                System.out.println("10x10 Square has 19 diagonals");
                System.exit(0);
            }
            else{
                if (mainKeyArr[loop] > 5){ //take into account diagonals > 10
                    mainKeyArr[loop] = 20 - mainKeyArr[loop];
                }
            }
        }


        //Encrypt all the 25bit blocks and update them
        for (int loop = 0; loop < hundredBitBlocks.size(); loop++){ //loop to control what 100bit block we are on
            for (int loop1 = 0; loop < keyArr1.length; loop++){ //control what part of key is being encrypted
                hundredBitBlocks.get(loop).getQ1().encrypt(keyArr1[loop1]);
            }
            hundredBitBlocks.get(loop).updateQ1();
        }

        for (int loop = 0; loop < hundredBitBlocks.size(); loop++){ //loop to control what 100bit block we are on
            for (int loop1 = 0; loop < keyArr2.length; loop++){ //control what part of key is being encrypted
                hundredBitBlocks.get(loop).getQ2().encrypt(keyArr2[loop1]);
            }
            hundredBitBlocks.get(loop).updateQ2();
        }

        for (int loop = 0; loop < hundredBitBlocks.size(); loop++){ //loop to control what 100bit block we are on
            for (int loop1 = 0; loop < keyArr3.length; loop++){ //control what part of key is being encrypted
                hundredBitBlocks.get(loop).getQ3().encrypt(keyArr3[loop1]);
            }
            hundredBitBlocks.get(loop).updateQ3();
        }

        for (int loop = 0; loop < hundredBitBlocks.size(); loop++){ //loop to control what 100bit block we are on
            for (int loop1 = 0; loop < keyArr4.length; loop++){ //control what part of key is being encrypted
                hundredBitBlocks.get(loop).getQ4().encrypt(keyArr4[loop1]);
            }
            hundredBitBlocks.get(loop).updateQ4();
        }

        //Encrypt all the 100bit blocks
        for (int loop = 0; loop < hundredBitBlocks.size(); loop++){ //loop to control what 100bit block we are on
            for (int loop1 = 0; loop < mainKeyArr.length; loop++){ //control what part of key is being encrypted
                hundredBitBlocks.get(loop).encrypt(mainKeyArr[loop1]);
            }
        }

        //Save to a file
        String output = "";
        for (int loop = 0; loop < hundredBitBlocks.size(); loop++){//control what block I'm on

            for(int loop1 = 0; loop1 < hundredBitBlocks.get(loop).String().length; loop1++){
                for (int loop2 = 0; loop2 < hundredBitBlocks.get(loop).String()[0].length; loop2++){
                    output = output + hundredBitBlocks.get(loop).String()[loop1][loop2];
                }
            }
        }
        System.out.println("The Encrypted Message Is:" + output);
        encryptedKey = output;


    return encryptedKey;
}

}
class Block{
    String[][]BigBlock;

    public Block(String [] array){
        String[][]doubleArrBlock = new String [10][array.length/10];
        //Convert 1D array into 2D array
        int count = 0; //keep track of position in 1D array
        for (int loop = 0; loop < 10; loop++){
            for (int loop1 = 0; loop1 < array.length/10; loop1++){
                doubleArrBlock[loop][loop1] = array [count];
                count++;
            }
        }
        BigBlock = doubleArrBlock;
    }

    //Slices doubleArrBlock into multiple HundredBitBlocks
    public void sliceAndSave () throws FileNotFoundException {
        int num = 0;
        int count = 0;
        String [][] hundredBitBlock = new String [10][10];

        PrintStream print = new PrintStream(new FileOutputStream("saveData.txt"));
        PrintStream old = System.out;
        System.setOut(print);

        while (num < BigBlock[0].length){
            for (int loop = 0; loop < BigBlock.length; loop++){
                for (int loop1 = num; loop1 < num+10; loop1++){
                    hundredBitBlock[loop][count] = BigBlock[loop][loop1];
                    System.out.print(hundredBitBlock[loop][count]);
                    count++;
                    if (count > 9){
                        count = 0;
                        System.out.println();
                    }
                }
            }


            num = num + 10;
            System.out.println("----------"); //locate 10x10 blocks, refer to line 80 why I did this
        }
        System.setOut(old);
        print.close();
    }

    public String getBlockAsString(){
        String block = "";
        for (int loop = 0; loop < BigBlock.length; loop++){
            for (int loop1 = 0; loop1 < BigBlock[0].length; loop1++){
                block = block + " " + BigBlock[loop][loop1];
            }
            block = block + "\n";
        }
        return block;
    }

}

class HundredBitBlock{
    ArrayList<QuarterBitBlock>qBitBlocks = new ArrayList<>();
    String hundredBitBlock[][];
    HundredBitBlock(String [] array){
        String[][]doubleArrBlock = new String [10][10];
        int count = 0;
        for (int loop = 0; loop < 10; loop++){
            for (int loop1 = 0; loop1 < 10; loop1++){
                doubleArrBlock[loop][loop1] = array [count];
                count++;
            }
        }
        hundredBitBlock = doubleArrBlock;
    }

    public String [][] String(){
        return hundredBitBlock;
    }


    public void slice(){
        String slicedText = "";
        for (int loop = 0; loop < hundredBitBlock.length; loop++){
            for (int loop1 = 0; loop1 < hundredBitBlock.length; loop1++){
                slicedText = slicedText + hundredBitBlock [loop] [loop1];
                if (loop1 == hundredBitBlock.length/2 - 1){
                    slicedText = slicedText + "   ";
                }
            }
            if (loop == 4){
                slicedText = slicedText + "\n";
            }
            slicedText = slicedText + "\n";
        }

        String [][] q1 = new String [5][5];
        String [][] q2 = new String [5][5];
        String [][] q3 = new String [5][5];
        String [][] q4 = new String [5][5];

        for (int loop = 0; loop < 5; loop++){
            for (int loop1 = 0; loop1 < 5; loop1++){
                q1[loop][loop1] = hundredBitBlock[loop][loop1];
            }
        }

        int rowCount = 0;
        int colCount = 0;
        for (int loop = 0; loop < 5; loop++){
            for (int loop1 = 5; loop1 < 10; loop1++){
                if (colCount > 4){
                    colCount = 0;
                }
                q2[rowCount][colCount] = hundredBitBlock[loop][loop1];
                colCount++;
            }
            rowCount++;
        }

        rowCount = 0;
        colCount = 0;
        for (int loop = 5; loop < 10; loop++){
            for (int loop1 = 0; loop1 < 5; loop1++){
                if (colCount > 4){
                    colCount = 0;
                }
                q3[rowCount][colCount] = hundredBitBlock[loop][loop1];
                colCount++;
            }
            rowCount++;
        }

        rowCount = 0;
        colCount = 0;
        for (int loop = 5; loop < 10; loop++){
            for (int loop1 = 5; loop1 < 10; loop1++){
                if (colCount > 4){
                    colCount = 0;
                }
                q4[rowCount][colCount] = hundredBitBlock[loop][loop1];
                colCount++;
            }
            rowCount++;
        }

        qBitBlocks.add(new QuarterBitBlock(q1));
        qBitBlocks.add(new QuarterBitBlock(q2));
        qBitBlocks.add(new QuarterBitBlock(q3));
        qBitBlocks.add(new QuarterBitBlock(q4));
    }

    public QuarterBitBlock getQ1(){
        return qBitBlocks.get(0);
    }

    public void updateQ1(){
        QuarterBitBlock qb1 = qBitBlocks.get(0);
        String [][] q1 = qb1.String();
        for (int loop = 0; loop < 5; loop++){
            for (int loop1 = 0; loop1 < 5; loop1++){
                hundredBitBlock[loop][loop1] = q1[loop][loop1];
            }
        }
    }

    public QuarterBitBlock getQ2(){
        return qBitBlocks.get(1);
    }

    public void updateQ2(){
        int rowCount = 0;
        int colCount = 0;
        QuarterBitBlock qb2 = qBitBlocks.get(1);
        String [][] q2 = qb2.String();
        for (int loop = 0; loop < 5; loop++){
            for (int loop1 = 5; loop1 < 10; loop1++){
                if (colCount > 4){
                    colCount = 0;
                }
                hundredBitBlock[loop][loop1] = q2[rowCount][colCount];
                colCount++;
            }
            rowCount++;
        }
    }

    public QuarterBitBlock getQ3(){
        return qBitBlocks.get(2);
    }

    public void updateQ3(){
        int rowCount = 0;
        int colCount = 0;
        QuarterBitBlock qb3 = qBitBlocks.get(2);
        String [][] q3 = qb3.String();
        for (int loop = 5; loop < 10; loop++){
            for (int loop1 = 0; loop1 < 5; loop1++){
                if (colCount > 4){
                    colCount = 0;
                }
                hundredBitBlock[loop][loop1] = q3[rowCount][colCount];
                colCount++;
            }
            rowCount++;
        }
    }

    public QuarterBitBlock getQ4(){
        return qBitBlocks.get(3);
    }

    public void updateQ4(){
        int rowCount = 0;
        int colCount = 0;
        QuarterBitBlock qb4 = qBitBlocks.get(3);
        String [][] q4 = qb4.String();
        for (int loop = 5; loop < 10; loop++){
            for (int loop1 = 5; loop1 < 10; loop1++){
                if (colCount > 4){
                    colCount = 0;
                }
                hundredBitBlock[loop][loop1] = q4[rowCount][colCount];
                colCount++;
            }
            rowCount++;
        }
    }

    public void encrypt(int inputKey){
        int key = inputKey;
        //Find displacement b/w diagonals
        int displacement = hundredBitBlock[0].length - key;


        //all the numbers in the same diagonal have the same sum.
        int leftRows = 0;
        int leftCols = key - 1;
        String storage = "";
        while (leftCols >= 0){
            storage = hundredBitBlock[leftRows][leftCols];
            hundredBitBlock[leftRows][leftCols] = hundredBitBlock[leftRows+displacement][leftCols+displacement];
            hundredBitBlock[leftRows+displacement][leftCols+displacement] = storage;
            leftRows++;
            leftCols--;
        }
    }
}

class QuarterBitBlock{
    String [][] quarterBitBlock;
    QuarterBitBlock(String [][] array){
        quarterBitBlock = array;
    }

    public String [][] String(){
        return quarterBitBlock;
    }

    public void encrypt(int key){
        //Find displacement b/w diagonals
        int displacement = quarterBitBlock[0].length - key;


        //all the numbers in the same diagonal have the same sum.
        int leftRows = 0;
        int leftCols = key - 1;

        String storage = "";
        while (leftCols >= 0){
            storage = quarterBitBlock[leftRows][leftCols];
            quarterBitBlock[leftRows][leftCols] = quarterBitBlock[leftRows+displacement][leftCols+displacement];
            quarterBitBlock[leftRows+displacement][leftCols+displacement] = storage;
            leftRows++;
            leftCols--;
        }
    }
}