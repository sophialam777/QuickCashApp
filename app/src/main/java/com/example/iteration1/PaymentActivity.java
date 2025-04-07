package com.example.iteration1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;


import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;


public class PaymentActivity extends AppCompatActivity {

    //UI Elements
    private TextView title;
    private Button payButton;
    private EditText payAmount;

    private static final String TAG = PaymentActivity.class.getName();

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private PayPalConfiguration payPalConfig;

    private String PayPal_ID = "AS6ec_hJy5CCNXMEIxaSIEp3DhZt8S-vlO3Bx6wgGsow4w4jLncsVwg-fh3xBQL_BleL3b5xaiSrKr8D" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_activity);

        title = findViewById(R.id.textView2);
        payButton = findViewById(R.id.button);
        payAmount = findViewById(R.id.editTextText);

        configPayPal();
        initActivityLauncher();
        setListner();
    }

    private void configPayPal(){
        payPalConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PayPal_ID);
    }

    private void setListner() {
        payButton.setOnClickListener(v -> processPayment());
    }


    private void processPayment() {
        String amount = payAmount.getText().toString();
        if (amount == null|| amount.trim().isEmpty()){
            payAmount.setError("Amount cannot be empty");
            return;
        }
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(
                amount), "CAD", "Job Payment", PayPalPayment.PAYMENT_INTENT_SALE);

        // Create Paypal Payment activity intent
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        // Adding paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        // Adding paypal payment to the intent
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        // Starting Activity Request launcher
        activityResultLauncher.launch(intent);
    }


    private void initActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        PaymentConfirmation confirmation = result.getData().getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            try {
                                // Get the payment details
                                String paymentDetails = confirmation.toJSONObject().toString(4);
                                Log.i(TAG, paymentDetails);
                                // Extract json response and display it in a text view.
                                JSONObject payObj = new JSONObject(paymentDetails);
                                String payID = payObj.getJSONObject("response").getString("id");
                                String state = payObj.getJSONObject("response").getString("state");
                                title.setText(String.format("Payment %s%n with payment id is %s" , state, payID));
                            } catch (JSONException e) {
                                Log.e("Error", "an Error occurred: ", e);
                            }
                        }
                    }
                });
    }

}
