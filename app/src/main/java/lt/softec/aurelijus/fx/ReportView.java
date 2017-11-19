package lt.softec.aurelijus.fx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ReportView extends Fragment {

    private TextView c1View;
    private TextView c2View;
    private TextView c3View;
    private TextView c1feeView;
    private TextView c2feeView;
    private TextView c3feeView;
    private TextView feesCountView;
    private ImageButton mButtonReset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View ReportView = inflater.inflate(R.layout.report_view, container, false);
        final SharedPreferences sharedPref = getActivity().getSharedPreferences(getActivity().getString(R.string.preference_file_key), getActivity().MODE_PRIVATE);
        final DecimalFormat df = new DecimalFormat("#0.00");
        final DecimalFormat df2 = new DecimalFormat("#");
        mButtonReset = ReportView.findViewById(R.id.buttonReset);

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //shake a button
                Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.button_shake);
                rotation.setRepeatCount(1);
                mButtonReset.startAnimation(rotation);
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                vibrator.vibrate(250);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("EUR", "1000.00");
                editor.putString("USD", "0.00");
                editor.putString("JPY", "0.00");
                editor.putString("EUR_fee", "0.00");
                editor.putString("USD_fee", "0.00");
                editor.putString("JPY_fee", "0.00");
                editor.putString("fee_count", "0");
                editor.commit();

                c1View = (TextView) ReportView.findViewById(R.id.c1Balance);
                c1View.setText("EUR balance: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("EUR", "0.00")))));
                c2View = (TextView) ReportView.findViewById(R.id.c2Balance);
                c2View.setText("USD balance: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("USD", "0.00")))));
                c3View = (TextView) ReportView.findViewById(R.id.c3Balance);
                c3View.setText("JPY balance: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("JPY", "0.00")))));

                c1feeView = (TextView) ReportView.findViewById(R.id.c1Fee);
                c1feeView.setText("EUR fees: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("EUR_fee", "0.00")))));
                c2feeView = (TextView) ReportView.findViewById(R.id.c2Fee);
                c2feeView.setText("USD fees: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("USD_fee", "0.00")))));
                c3feeView = (TextView) ReportView.findViewById(R.id.c3Fee);
                c3feeView.setText("JPY fees: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("JPY_fee", "0.00")))));

                feesCountView = (TextView) ReportView.findViewById(R.id.feesCount);
                feesCountView.setText("Fees count: " + String.valueOf(df2.format(Double.parseDouble(sharedPref.getString("fee_count", "0")))));

            }

        });


        c1View = (TextView) ReportView.findViewById(R.id.c1Balance);
        c1View.setText("EUR balance: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("EUR", "0.00")))));
        c2View = (TextView) ReportView.findViewById(R.id.c2Balance);
        c2View.setText("USD balance: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("USD", "0.00")))));
        c3View = (TextView) ReportView.findViewById(R.id.c3Balance);
        c3View.setText("JPY balance: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("JPY", "0.00")))));

        c1feeView = (TextView) ReportView.findViewById(R.id.c1Fee);
        c1feeView.setText("EUR fees: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("EUR_fee", "0.00")))));
        c2feeView = (TextView) ReportView.findViewById(R.id.c2Fee);
        c2feeView.setText("USD fees: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("USD_fee", "0.00")))));
        c3feeView = (TextView) ReportView.findViewById(R.id.c3Fee);
        c3feeView.setText("JPY fees: " + String.valueOf(df.format(Double.parseDouble(sharedPref.getString("JPY_fee", "0.00")))));

        feesCountView = (TextView) ReportView.findViewById(R.id.feesCount);
        feesCountView.setText("Fees count: " + String.valueOf(df2.format(Double.parseDouble(sharedPref.getString("fee_count", "0")))));


        return ReportView;
    }


}
