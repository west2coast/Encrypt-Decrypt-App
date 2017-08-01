package west.encryption_decryption_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoubleT extends Fragment {
    int row, col;
    String message;
    Button btRow, btCol;
    EditText etRow, etRow2, etCol, etCol2;
    TextView output;
    private static char[][] array; // original
     char[][] array2;
    public DoubleT() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.double_user, container, false);
        Bundle args = getArguments();
        row = args.getInt("row");
        col = args.getInt("col");
        message = args.getString("message");
        fillArray();
        //print();
        btRow = (Button) view.findViewById(R.id.doubleU_rows);
        btCol = (Button) view.findViewById(R.id.doubleU_columns);
        etRow = (EditText) view.findViewById(R.id.doubleU_row1);
        etRow2 = (EditText) view.findViewById(R.id.doubleU_row2);
        etCol = (EditText) view.findViewById(R.id.doubleU_col1);
        etCol2 = (EditText) view.findViewById(R.id.doubleU_col2);
        output = (TextView) view.findViewById(R.id.doubleU_output);
        btRow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String etR = etRow.getText().toString();
                String etR1 = etRow2.getText().toString();
                int r1 = Integer.parseInt(etR);
                int r2 = Integer.parseInt(etR1);
                rowSwap(r1,r2);
//                print();
            }
        });
        btCol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String etC = etCol.getText().toString();
                String etC1 = etCol2.getText().toString();
                int c1 = Integer.parseInt(etC);
                int c2 = Integer.parseInt(etC1);
                columnSwap(c1,c2);
//                print(); print method don't work
            }
        });
        return view;
    }
    public void fillArray(){
        array = new char[row][col];
        array2 = new char[row][col];
        int m = 0;
        for (int x = 0; x < row; x++){
            for (int y = 0; y < col; y++){
                array[x][y] = message.charAt(m);
                m++;

            }
        }
       array2 = Arrays.copyOf(array, array.length);
        //System.arraycopy(array, 0, array2, 0, array.length);
    }
    public void rowSwap(int r, int r2){
        for(int x = 0; x < col; x++){
            array2[r2][x] = array[r][x];
            array2[r][x] = array[r2][x];
        }
        array = Arrays.copyOf(array2, array2.length);
       // System.arraycopy(array2, 0, array, 0, array2.length);
    }
    public void columnSwap(int c, int c2){
        for(int x = 0; x < row; x++){
            array2[x][c2] = array[x][c];
            array2[x][c] = array[x][c2];
        }
        array = Arrays.copyOf(array2, array2.length);
        //System.arraycopy(array2, 0, array, 0, array2.length);
    }
//    public void print(){
//char m;
//        for(int i = 0; i < array2.length; i++) {
//            m = array2[i];
//            output.setText();
//        }

    }


