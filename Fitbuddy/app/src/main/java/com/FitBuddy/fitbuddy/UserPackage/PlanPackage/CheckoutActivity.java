package com.FitBuddy.fitbuddy.UserPackage.PlanPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.FitBuddy.fitbuddy.R;
import com.FitBuddy.fitbuddy.UserPackage.PlanPackage.PaytmPackage.ServiceWrapper;
import com.FitBuddy.fitbuddy.UserPackage.PlanPackage.PaytmPackage.Token_Res;
import com.makeramen.roundedimageview.RoundedImageView;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    TextView fitness_pack, pack_display_price, pack_price, pack_session, pack_discount, pack_total;
    EditText promo_code;
    Button promo_btn;
    CircularProgressButton checkout_btn;
    RoundedImageView pack_image;
    private String TAG = this.getClass().getSimpleName();
    private String midString = "NCBcgC49229523932873", txnAmountString = "", orderIdString = "", txnTokenString = "";
    private Integer ActivityRequestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initialize();
        getData();
        paymentSetup();
    }

    private void paymentSetup() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min = 1000, max = 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        orderIdString = date + String.valueOf(randomNum);

    }

    private void getData() {
        PlanModel planModel = (PlanModel) getIntent().getSerializableExtra("PlanModel");
        if (planModel != null) {
            pack_display_price.setText(planModel.getPrice());
            fitness_pack.setText(planModel.getPackName());
            pack_price.setText(planModel.getPrice());
            pack_session.setText(planModel.getNo_of_sessions());
            pack_image.setImageResource(planModel.getImage());
            pack_total.setText(planModel.getPrice());
            String price = planModel.getPrice().substring(3);
            price = price.substring(0, price.indexOf("/"));
            txnAmountString = price;

        }
    }

    private void initialize() {
        pack_display_price = findViewById(R.id.checkout_pack_show_price);
        fitness_pack = findViewById(R.id.checkout_pack);
        pack_price = findViewById(R.id.checkout_pack_price);
        pack_session = findViewById(R.id.checkout_pack_sessions);
        pack_discount = findViewById(R.id.checkout_discount);
        pack_total = findViewById(R.id.checkout_total);
        promo_code = findViewById(R.id.checkout_promo_code);
        promo_btn = findViewById(R.id.checkout_promo_code_button);
        checkout_btn = findViewById(R.id.checkout_button);
        pack_image = findViewById(R.id.checkout_package_image);
        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout_btn.startAnimation();
                getToken();

            }
        });
    }

    private void getToken() {
        Log.e(TAG, " get token start");
//        progressBar.setVisibility(View.VISIBLE);
        ServiceWrapper serviceWrapper = new ServiceWrapper(null);
        Call<Token_Res> call = serviceWrapper.getTokenCall("12345", midString, orderIdString, txnAmountString);
        call.enqueue(new Callback<Token_Res>() {
            @Override
            public void onResponse(Call<Token_Res> call, Response<Token_Res> response) {
                Log.e(TAG, " respo " + response.isSuccessful());
//                progressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful() && response.body() != null) {
                        Log.e(TAG, response + "");
                        if (response.body().getBody().getTxnToken() != "") {
                            Log.e(TAG, " transaction token : " + response.body().getBody().getTxnToken());
                            startPaytmPayment(response.body().getBody().getTxnToken());
                        } else {
                            Log.e(TAG, " Token status false");
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, " error in Token Res " + e.toString());
                }
            }

            @Override
            public void onFailure(Call<Token_Res> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.e(TAG, " response error " + t.toString());
            }
        });
    }

    public void startPaytmPayment(String token) {

        txnTokenString = token;
        // for test mode use it
//        String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
        String host = "https://securegw.paytm.in/";
        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString
                + ", Amount: " + txnAmountString;
        //Log.e(TAG, "order details "+ orderDetails);

        String callBackUrl = host + "theia/paytmCallback?ORDER_ID=" + orderIdString;
        Log.e(TAG, " callback URL " + callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString, txnAmountString, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Log.e(TAG, "Response (onTransactionResponse) : " + bundle.toString());
            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
                Toast.makeText(CheckoutActivity.this, "Network not available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess " + s.toString());
                Toast.makeText(CheckoutActivity.this, "Some error please try again!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth " + s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error " + s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web " + s + "--" + s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
                Intent intent = new Intent(CheckoutActivity.this, PlanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel " + s);
            }
        });

        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, ActivityRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, " result code " + resultCode);
        // -1 means successful  // 0 means failed
        // one error is - nativeSdkForMerchantMessage : networkError
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            if (resultCode == -1) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                    }
                }
                Log.e(TAG, " data " + data.getStringExtra("nativeSdkForMerchantMessage"));
                Log.e(TAG, " data response - " + data.getStringExtra("response"));
/*
 data response - {"BANKNAME":"WALLET","BANKTXNID":"1395841115",
 "CHECKSUMHASH":"7jRCFIk6mrep+IhnmQrlrL43KSCSXrmM+VHP5pH0hekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
 "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAc0553138556","ORDERID":"100620202152",
 "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
 "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"20200610111212800110168328631290118"}
  */
//                Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
//                        + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Payment Success!!", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(CheckoutActivity.this, PlanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } else {
            Log.e(TAG, " payment failed");
        }
    }
}