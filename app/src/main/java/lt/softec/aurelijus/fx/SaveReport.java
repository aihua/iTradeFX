package lt.softec.aurelijus.fx;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.text.DecimalFormat;

public class SaveReport

{

    private String mSellAmount;
    private String mSellCurrency;
    private String mBuyAmount;
    private String mBuyCurrency;
    private Float mFeeCount;
    private Double mSellBalance;
    private Double mSellBalanceFee;
    private Float mBuyBalance;
    private String mFee;
    private Double mFeeDb;
    private static Double mFeePct = 0.007;

    public SaveReport(String sAmount, String sCurrency, String bCurrency) {
        mSellAmount = sAmount;
        mSellCurrency = sCurrency;
        mBuyCurrency = bCurrency;
    }

    public String Generate(Context context, String bAmount) {

        if (mSellCurrency == mBuyCurrency) {return "";}

        mBuyAmount = bAmount;
        DecimalFormat df = new DecimalFormat("#0.00");
        mSellAmount = String.valueOf(df.format(Double.parseDouble(mSellAmount)));
        mBuyAmount = String.valueOf(df.format(Double.parseDouble(mBuyAmount)));
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        mFeeCount = Float.parseFloat(sharedPref.getString("fee_count", "0.00"));

        if (mFeeCount + 1 <= 5) {
            mSellBalance = Double.parseDouble(sharedPref.getString(mSellCurrency, "0.00"));
            mSellBalance = mSellBalance - Double.parseDouble(mSellAmount);
            mSellBalanceFee = mSellBalance;
            mFee = "0.00";
            mFeeDb = 0.00;
        } else {
            mSellBalance = Double.parseDouble(sharedPref.getString(mSellCurrency, "0.00"));
            mFeeDb = Double.parseDouble(sharedPref.getString(mSellCurrency + "_fee", "0.00"));
            mSellBalance = mSellBalance - Double.parseDouble(mSellAmount);
            mSellBalanceFee = mSellBalance - (Double.parseDouble(mSellAmount) * mFeePct);
            mFee = String.valueOf(df.format(Double.parseDouble(mSellAmount) * mFeePct));
            mFeeDb = mFeeDb + Double.parseDouble(mSellAmount) * mFeePct;
        }


        if (mSellBalanceFee >= 0.00) {
            mBuyBalance = Float.parseFloat(sharedPref.getString(mBuyCurrency, "0.00"));
            mBuyBalance = mBuyBalance + Float.parseFloat(mBuyAmount);
            editor.putString(mSellCurrency, Double.toString(mSellBalanceFee));
            editor.putString(mBuyCurrency, Float.toString(mBuyBalance));
            editor.putString(mSellCurrency + "_fee", String.valueOf(mFeeDb));
            editor.putString("fee_count", Double.toString(mFeeCount + 1));
            editor.commit();

            return "Jūs konvertavote " + mSellAmount + " " + mSellCurrency + " į " + mBuyAmount + " " +
                    mBuyCurrency + ". Komisinis mokestis - " + mFee + " " + mSellCurrency + ".";

        }
        else{
            CharSequence text = mSellCurrency + " sąskaitoje nepakanka lėšų";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
            return String.valueOf(text);
        }
    }
}