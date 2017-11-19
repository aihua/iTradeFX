package lt.softec.aurelijus.fx;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class ConvertView extends Fragment {

    private RadioGroup mSellRadio;
    private RadioButton mSellCurrency;
    private RadioGroup mBuyRadio;
    private RadioButton mBuyCurrency;
    private ImageButton mButtonConvert;
    private EditText mAmount;
    private TextView mJsonAnswer;
    private ProgressBar mLoadingIndicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View ConvertView = inflater.inflate(R.layout.convert_view, container, false);

        // getting data from UI
        mButtonConvert = ConvertView.findViewById(R.id.buttonConvert);
        mSellRadio = (RadioGroup) ConvertView.findViewById(R.id.sellRadio);
        mBuyRadio = (RadioGroup) ConvertView.findViewById(R.id.buyRadio);
        mSellRadio.check(R.id.sellEur);
        mBuyRadio.check(R.id.buyUsd);
        mAmount = (EditText) ConvertView.findViewById(R.id.amount);
        mJsonAnswer = (TextView) ConvertView.findViewById(R.id.json);
        mLoadingIndicator = (ProgressBar) ConvertView.findViewById(R.id.progressBar);

        mButtonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check internet connectio
                if (AppStatus.getInstance(getActivity()).isOnline()) {
                    mJsonAnswer.setText("");
                    //rotate a button
                    Animation rotation = AnimationUtils.loadAnimation(getActivity(), R.anim.button_rotate);
                    rotation.setRepeatCount(1);
                    mButtonConvert.startAnimation(rotation);
                    //reading amount
                    if (mAmount.getText().toString().isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter sell amount!", Toast.LENGTH_SHORT).show();
                        mJsonAnswer.setText("Please enter sell amount!");
                        return;
                    } else {
                        float amountValue = Float.parseFloat(mAmount.getText().toString());
                    }
                    float amountValue = Float.parseFloat(mAmount.getText().toString());
                    //getting sell currency
                    int selectedId = mSellRadio.getCheckedRadioButtonId();
                    mSellCurrency = (RadioButton) ConvertView.findViewById(selectedId);
                    //getting buy currency
                    selectedId = mBuyRadio.getCheckedRadioButtonId();
                    mBuyCurrency = (RadioButton) ConvertView.findViewById(selectedId);

                    NetworkUtils network = new NetworkUtils(amountValue, mSellCurrency.getText().toString(), mBuyCurrency.getText().toString());
                    new QueryTask().execute(network.buildUrl());
                    //Toast.makeText(getActivity(), "Turime internetą!", Toast.LENGTH_SHORT).show();
                } else {
                    mJsonAnswer.setText("Please turn on internet ;-)");
                    Toast.makeText(getActivity(), "Please turn on internet ;-)", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

        });

        return ConvertView;

    }


    //asynchronous query method
    public class QueryTask extends AsyncTask<URL, Void, String> {

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String queryResults = null;
            try {
                NetworkUtils network = new NetworkUtils(Float.parseFloat(mAmount.getText().toString()), mSellCurrency.getText().toString(), mBuyCurrency.getText().toString());
                queryResults = network.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return queryResults;
        }

        @Override
        protected void onPostExecute(String queryResults) {
            if (queryResults != null && !queryResults.equals("")) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                try {
                    JsonParser parser = new JsonParser(queryResults);
                    SaveReport sr = new SaveReport(mAmount.getText().toString(), mSellCurrency.getText().toString(), mBuyCurrency.getText().toString());
                    mJsonAnswer.setText(sr.Generate(getActivity(), parser.buyAmount()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}