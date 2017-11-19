package lt.softec.aurelijus.fx;


import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FeeModels {

    private Float mFeeCount;
    private Integer mCount;

    public FeeModels(Float feeCount) {

        mFeeCount = feeCount;

    }

    public Boolean ApplyFee() {

        mCount = Math.round(mFeeCount);

        if (mCount + 1 > 5 && ((mCount + 1) % 10) != 0) {
            return true;
        } else {
            return false;
        }

    }

}
