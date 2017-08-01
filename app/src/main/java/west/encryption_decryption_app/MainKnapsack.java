package west.encryption_decryption_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainKnapsack extends Fragment {
    Button en, de;
    EditText val1, val2, val3, val4;
    TextView output;
    private static int[] seqNum;
    private static int m;
    private static int n;
    private static int[] publicKeyArray;
    private static int[] enMessage;
    private static String[] binaryStatements;

    public MainKnapsack() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.knapsack_main, container, false);
        en = (Button) view.findViewById(R.id.knap_encrypt);
        de = (Button) view.findViewById(R.id.knap_decrypt);
        val1 = (EditText) view.findViewById(R.id.knap_seq);
        val2 = (EditText) view.findViewById(R.id.knap_m);
        val3 = (EditText) view.findViewById(R.id.knap_n);
        val4 = (EditText) view.findViewById(R.id.knap_message);
        output = (TextView) view.findViewById(R.id.knap_output);
        en.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String numM = val2.getText().toString();
                String numN = val3.getText().toString();
                String text = val4.getText().toString();
                String seqStr = val1.getText().toString();
                m = Integer.parseInt(numM);
                n = Integer.parseInt(numN);
                seqNum = convertString(seqStr);
                publicKey();
                encryptMessage(text);


            }
        });
        de.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String numM = val2.getText().toString();
                String numN = val3.getText().toString();
                String text = val4.getText().toString();
                String seqStr = val1.getText().toString();
                m = Integer.parseInt(numM);
                n = Integer.parseInt(numN);
                enMessage = convertString(text);
                seqNum = convertString(seqStr);
                publicKey();
                decryptMessage();
            }
        });
        return view;
    }

    public static int[] convertString(String seqStr) {

        String[] parts = seqStr.split(" ");
        int[] numConvert = new int[parts.length];

        for (int x = 0; x < parts.length; x++) {
            numConvert[x] = Integer.parseInt(parts[x]);

        }
        return numConvert;
    }

    public static int calculateSum() {
        int sum = 0;
        for (int x = 0; x < seqNum.length; x++) {
            sum += seqNum[x];
        }
        return sum;
    }

    public static void checkM() {
        if (m == 0) {
            m = 20 + calculateSum();
        }
    }

    public static void publicKey() {
        publicKeyArray = new int[seqNum.length];
        for (int x = 0; x < seqNum.length; x++) {
            int value = seqNum[x];
            int formula = (value * n) % m;
            publicKeyArray[x] = formula;
        }
    }

    public void encryptMessage(String s) {
        byte[] bytes = s.getBytes();

        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }

        }
        int[] binaryArray = new int[binary.length()];
        for (int x = 0; x < binary.length(); x++) {
            Character a = binary.charAt(x);
            String b = a.toString();
            binaryArray[x] = Integer.parseInt(b);

        }
        int size = binary.length();
        size = size / 8;
        int loc = 0;
        enMessage = new int[size];

        for (int x = 0; x < binaryArray.length; x++) {
            int sum = 0;

            for (int y = 0; y < publicKeyArray.length; y++) {
                int bin = binaryArray[x];
                System.out.println("GET here");
                int key = publicKeyArray[y];
                sum = (bin * key) + sum;
                x++;
            }
            x = x - 1;
            enMessage[loc] = sum;
            loc++;
        }
        output.setText("Private key: " + Arrays.toString(seqNum) + "\nPublic Key: " + Arrays.toString(publicKeyArray) + "\nM value: " + m + "\nN value: " + n + "\nMessage: " + s + "\nEncrypted Message: " + Arrays.toString(enMessage));

    }

    public void decryptMessage() {
        String val1 = Integer.toString(n), val2 = Integer.toString(m);
        BigInteger nVal = new BigInteger(val1), mVal = new BigInteger(val2), invVal;
        invVal = nVal.modInverse(mVal);
        int inverse = invVal.intValue();
        binaryStatements = new String[enMessage.length];
        for (int x = 0; x < enMessage.length; x++) {
            int sum = enMessage[x];
            int[] binaryValues = {0, 0, 0, 0, 0, 0, 0, 0};
            int formula = (sum * inverse % m);
            for (int y = 7; y != -1; y--) {
                if (formula != 0) {

                    if (seqNum[y] <= formula) {

                        formula = formula - seqNum[y];
                        binaryValues[y] = 1;
                    }
                }
            }
            StringBuilder builder = new StringBuilder();
            for (int i : binaryValues) {
                builder.append(i);
            }
            String text = builder.toString();
            binaryStatements[x] = text;

        }
        StringBuilder textMessage = new StringBuilder();
        String[] s = binaryStatements;
        for (int p = 0; p < s.length; p++)
            textMessage.append((char) Integer.parseInt(s[p], 2));
        output.setText("Private key: " + Arrays.toString(seqNum) + "\nPublic Key: " + Arrays.toString(publicKeyArray) + "\nM value: " + m + "\nN value: " + n + "\nMessage: " + Arrays.toString(enMessage) + "\nDecrypted Message: " + textMessage);
    }


}
