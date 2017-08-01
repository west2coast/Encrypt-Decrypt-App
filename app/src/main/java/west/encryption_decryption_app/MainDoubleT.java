package west.encryption_decryption_app;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MainDoubleT extends Fragment {
    EditText m, r1, c1;
    Button bt;

    public MainDoubleT() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.double_main, container, false);
        m = (EditText) view.findViewById(R.id.double_message);
        r1 = (EditText) view.findViewById(R.id.double_row1);
        c1 = (EditText) view.findViewById(R.id.double_col1);
        bt = (Button) view.findViewById(R.id.double_submit);

        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DoubleT doubleT = new DoubleT();
                String message = m.getText().toString();
                String row = r1.getText().toString();
                String col = c1.getText().toString();
                int row1 = Integer.parseInt(row);
                int col1 = Integer.parseInt(col);
                Bundle arg = new Bundle();
                arg.putInt("row",row1);
                arg.putInt("col", col1);
                arg.putString("message", message);

                doubleT.setArguments(arg);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_main,doubleT);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return view;
    }


}
