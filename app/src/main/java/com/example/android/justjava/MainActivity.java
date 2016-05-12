package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.cert.PolicyQualifierInfo;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    public static final String DEFAULT = "N/A";
    CheckBox whippedCream, chocolate;
    TextView priceTextView, quantityTextView, myText;
    Button myButton;
    EditText myInput;
    String name = DEFAULT;
    int quantity = 0;
    String priceMessage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whippedCream = (CheckBox) findViewById(R.id.cream_coffee);
        chocolate = (CheckBox) findViewById(R.id.cream_coffee2);
        priceTextView = (TextView) findViewById(R.id.price_text_view);
        quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        myButton = (Button) findViewById(R.id.order_button);
        myText = (TextView) findViewById(R.id.or);
        myInput = (EditText) findViewById(R.id.EnterName);


    }


    public void reviewOrder(View view) {

        boolean has_chocolate = chocolate.isChecked();
        boolean has_whippedCream = whippedCream.isChecked();
        //setText();
        Editable nameEditable = myInput.getText();
        name = nameEditable.toString();


        if (name.matches("")){
            Toast.makeText(MainActivity.this, "You forgot to enter your Name.",
                    Toast.LENGTH_SHORT).show();
        } else if (quantity == 0) {
            Toast.makeText(MainActivity.this, "You haven't ordered anything..",
                    Toast.LENGTH_SHORT).show();
        } else {

            priceMessage = "ORDER FOR " + name.toUpperCase() + "\n\n" + quantity + " CUPS OF COFFEE.\n" + "TOTAL BILL\t:\t₹ " + quantity * 10;
            //the result is stored in a boolean called has_whippedCream from the method isChecked()

            if (has_whippedCream == true) {

                priceMessage = "ORDER FOR " + name.toUpperCase() + "\n\n" + quantity + " CUPS OF COFFEE WITH WHIPPED CREAM.\n" + "TOTAL BILL\t:\t₹ " + quantity * 15;
            }
            if (has_chocolate == true) {
                priceMessage = "ORDER FOR " + name.toUpperCase() + "\n\n" + quantity + " CUPS OF COFFEE WITH CHOCOLATE.\n" + "TOTAL BILL\t:\t₹ " + quantity * 15;
                //displayMessage("TOTAL+"+quantity*15);
            }
            if (has_chocolate == true && has_whippedCream == true) {
                priceMessage = "ORDER FOR " + name.toUpperCase() + "\n\n" + quantity + " CUP(S) OF COFFEE WITH WHIPPED CREAM & CHOCOLATE.\n" + "TOTAL BILL\t:\t₹ " + quantity * 35;
            }

            priceMessage = priceMessage + "\n\n CLICK ON ORDER TO CONFIRM \n HAVE A NICE DAY!!";
            displayMessage(priceMessage);

        }

    }


    public void submitOrder(View view) {

        Toast.makeText(MainActivity.this, "Opening your Email App. Please Wait.",
                Toast.LENGTH_SHORT).show();

        String emailString = "NAME : " + name.toUpperCase() + "\nQUANTITY : " + quantity + "\nWHIPPED CREAM : " + whippedCream.isChecked() + "\nCHOCOLATE : " + chocolate.isChecked();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, emailString);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void submitOrderW(View view) {

        String emailString = "NAME : " + name.toUpperCase() + "\nQUANTITY : " + quantity + "\nWHIPPED CREAM : " + whippedCream.isChecked() + "\nCHOCOLATE : " + chocolate.isChecked();

        copyToClipboard(emailString);

        Uri uri = Uri.parse("smsto:" + "9108908807");
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("sms_body", "TExt");
        i.setPackage("com.whatsapp");
        startActivity(i);

    }

    public void copyToClipboard(String copyText) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(copyText);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData
                    .newPlainText("Your Order", copyText);
            clipboard.setPrimaryClip(clip);
        }
        Toast toast = Toast.makeText(getApplicationContext(),
                "Your Order is copied to Clipboard. Opening Whatsapp. Please Wait.", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        //displayAlert("Your OTP is copied");
    }
    //display(quantity);
    //displayMessage (priceMessage) ; adding in the order summary


    public void increment(View view) {
        if (quantity < 99) {
            quantity += 1;

            display(quantity);
        } else
            displayMessage("SORRY, WE WON'T BE ABLE TO DELIVER SUCH A LARGE ORDER");

    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity -= 1;
            display(quantity);

        } else
            myButton.setEnabled(false);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {

        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    public void displayMessage(String message) {

        priceTextView.setText(message);
    }

}
