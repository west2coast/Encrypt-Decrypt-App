package west.encryption_decryption_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.String;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainCaesar extends Fragment {

Button buttonEncrypt, buttonDecrypt;

    TextView output;
    EditText key1, message1;
    public MainCaesar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.caesar_main, container, false);
        // Inflate the layout for this fragment
        buttonEncrypt = (Button) view.findViewById(R.id.caesarEncrypt);
       key1 = (EditText) view.findViewById(R.id.key_caesar);
        message1 = (EditText) view.findViewById(R.id.message_caesar);
        output = (TextView) view.findViewById(R.id.output_caesar);
        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String a = key1.getText().toString();
                String m = message1.getText().toString();
                int k = Integer.parseInt(a);

                encryptMessage(k,m);
            }
        });
        buttonDecrypt = (Button) view.findViewById(R.id.caesarDecrypt);
        buttonDecrypt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String a = key1.getText().toString();
                String m = message1.getText().toString();
                int k = Integer.parseInt(a);
                decryptMessage(k,m);
            }
        });
        return view;
    }
    public void encryptMessage (int key, String message){
        char[] newMessage = new char[message.length()];
        message = message.toUpperCase();
        key = (key % 10 + 2);
        int letterNum = 0;
        for (int x = 0; x < message.length(); x++){
            char letter = message.charAt(x);
            letterNum = letter+key;
            if (letterNum > 'Z'){
                letterNum = letterNum -26;
            }
            letter = (char) letterNum;

            newMessage[x] = letter;

        }
        message = new String(newMessage);
        output.setText("Encrypted message: " + message + " with key " + key);

    }
    public void decryptMessage (int key, String message){
        char[] newMessage = new char[message.length()];
      message = message.toUpperCase();
        key = (key % 10 + 2);
        int letterNum = 0;
        for (int x = 0; x < message.length(); x++){
            char letter = message.charAt(x);
            letterNum = letter - key;
            if (letterNum < 'A'){
                letterNum = letterNum + 26;
            }
            letter = (char) letterNum;

            newMessage[x] = letter;

        }
        message = new String(newMessage);
        output.setText("Decrypted message: " + message + " with key " + key);
    }

}


