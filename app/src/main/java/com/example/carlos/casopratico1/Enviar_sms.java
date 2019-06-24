package com.example.carlos.casopratico1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Enviar_sms extends AppCompatActivity {
    Button button;
  EditText mensagem,numero;
    public static  final  int RequestPermissionCode=1;

    @Override

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_sms_layout);
         numero=findViewById(R.id.id_numeros_tel);
         button=findViewById(R.id.id_enviar_sms);
         mensagem=findViewById(R.id.id_mensagem_text);
        Intent i=getIntent();
        Bundle extras=i.getExtras();
        if(extras!=null){
            final String numeropassado=extras.getString("numero");
            numero.setText(numeropassado);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckPermission()) {
                        String texto = mensagem.getText().toString();
                        if (!texto.isEmpty()) {
                           SmsManager sms =SmsManager.getDefault();
                            sms.sendTextMessage(numeropassado, null, texto, null, null);

                            finish();
                        }
                    }else{
                        requestPermission();
                    }
                }
            });

        }
    }
    public boolean CheckPermission(){

        int Result=ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);


        return Result ==PackageManager.PERMISSION_GRANTED ;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void requestPermission(){

        ActivityCompat.requestPermissions(this,new String[] { Manifest.permission.SEND_SMS},RequestPermissionCode);
    }
}
