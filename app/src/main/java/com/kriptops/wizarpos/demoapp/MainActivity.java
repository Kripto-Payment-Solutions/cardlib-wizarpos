package com.kriptops.wizarpos.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kriptops.wizarpos.cardlib.Defaults;
import com.kriptops.wizarpos.cardlib.Pinpad;
import com.kriptops.wizarpos.cardlib.Pos;
import com.kriptops.wizarpos.cardlib.Printer;
import com.kriptops.wizarpos.cardlib.TransactionData;
import com.kriptops.wizarpos.cardlib.Util;
import com.kriptops.wizarpos.cardlib.android.PosActivity;
import com.kriptops.wizarpos.cardlib.android.PosApp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity implements PosActivity {

    private EditText masterKey;
    private EditText pinKey;
    private EditText dataKey;
    private EditText plainText;
    private EditText encriptedText;
    private TextView log;

    @Override
    public Pos getPos() {
        return ((PosApp) this.getApplication()).getPos();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.masterKey = this.findViewById(R.id.txt_llave_master);
        //Setear con la configuracion de la MK de pruebas asignada
        this.masterKey.setText("");
        this.pinKey = this.findViewById(R.id.txt_llave_pin);
        this.dataKey = this.findViewById(R.id.txt_llave_datos);
        this.plainText = this.findViewById(R.id.txt_texto_plano);
        this.encriptedText = this.findViewById(R.id.txt_texto_cifrado_hex);
        this.log = this.findViewById(R.id.txt_log);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void clearLog(View log) {
        this.log.setText("");
    }

    public void btn_generar_llaves(View btn) {
        Log.d(Defaults.LOG_TAG, "Generar llaves");
        byte[] data = new byte[16];
        Random r = new Random();

        r.nextBytes(data);
        pinKey.setText(Util.toHexString(data));
        Log.d(Defaults.LOG_TAG, "llave de pin " + pinKey.getText());

        r.nextBytes(data);
        dataKey.setText(Util.toHexString(data));
        Log.d(Defaults.LOG_TAG, "llave de datos " + dataKey.getText());
    }

    public void btn_inyectar_llaves(View btn) {
        Log.d(Defaults.LOG_TAG, "Inyectar llaves");
        getPos().withPinpad(this::updateKeys);
    }

    public void updateKeys(Pinpad pinpad) {
        String masterKey = this.masterKey.getText().toString();
        boolean actualizadas = pinpad.updateKeys(
                protectKey(masterKey, pinKey.getText().toString()),
                protectKey(masterKey, dataKey.getText().toString())
        );

        this.runOnUiThread(() -> {
            if (actualizadas) {
                Toast.makeText(this, "Llaves actualizadas", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No se puede actualizar llaves", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void btn_encriptar(View btn) {
        Log.d(Defaults.LOG_TAG, "Cifrar");
        //este primer paso es necesario porque yo tengo data ascii y no hex string
        getPos().withPinpad(this::encrypt);
    }

    public void encrypt(Pinpad pinpad) {
        String plainText = this.plainText.getText().toString();
        String plainHex = Util.toHexString(plainText.getBytes(), true);
        Log.d(Defaults.LOG_TAG, "Encriptando: " + plainHex);
        String encrypted = getPos().getPinpad().encryptHex(plainHex);
        this.encriptedText.setText(encrypted);
    }

    public void btn_imprimir_ticket(View btn) {
        Log.d(Defaults.LOG_TAG, "Imprimir Ticket");
        getPos().withPrinter(this::print);
    }

    public void print(Printer printer) {
        for (Printer.FontSize size : Printer.FontSize.values())
            for (Printer.Align align : Printer.Align.values()) {
                printer.println(size.name() + " " + align.name(), size, align);
            }
        printer.feedLine();
        printer.feedLine();
        printer.feedLine();
        printer.feedLine();
    }

    public void btn_do_trade(View view) {
        Log.d(Defaults.LOG_TAG, "Imprimir Ticket");
        getPos().configTerminal( // este metodo se puede llamar una sola vez
                "PK000001", // identidad del comercio
                "PRUEBA KRIPTO", // nombre del comercio
                "00000001", // identidad del terminal dentro del comercio (no es el serial number)
                "000000100000", // floor limit contactless
                "000000100000", // transaction limit contactless
                "000000001000" // cvm limit (desde que monto pasan de ser quick a full)
        );
        getPos().setPinpadCustomUI(true); // cambia la pantalla de fondo cuando se solicita el uso del pinpad
        getPos().setOnPinRequested(this::onPinRequested);
        getPos().setDigitsListener(this::onPinDigit);
        getPos().setOnPinCaptured(this::onPinCaptured);

        getPos().setOnError(this::onError);
        getPos().setGoOnline(this::online);
        getPos().beginTransaction( // ete metodo se llama en cada transaccion
                "210531", // fecha en formato
                "140900",
                "00000001",
                "10000"
        );
    }

    private void online(TransactionData data) {
        Log.d(Defaults.LOG_TAG, "online message " + data);
        this.runOnUiThread(() -> {
            this.log.setText("Online Message " + data);
        });
    }

    private void onError(String source, String code) {
        Log.d(Defaults.LOG_TAG, "Controlar el error de lectura de datos");
        this.runOnUiThread(() -> {
            this.log.setText("Error " + source + " " + code);
        });
    }

    private void onPinRequested() {
        // hacer con las graficas lo que se quiera luego enlazar el pin
        // el emv thread esta fuera del main looper hay que llamar prepare para acceder a los contextos graficos o entrar al main looper
        this.runOnUiThread(() -> {
            this.log.setText("requiriendo el pin");
        });
        getPos().callPin();
    }

    private void onPinCaptured() {
        // hacer con las graficas lo que se quiera luego enlazar el pin
        // el emv thread esta fuera del main looper hay que llamar prepare para acceder a los contextos graficos o entrar al main looper
        this.runOnUiThread(() -> {
            this.log.setText("pin leido seguir el flujo");
        });
        getPos().continueAfterPin();
    }

    private void onPinDigit(Integer pinDigits) {
        Log.d(Defaults.LOG_TAG, "cantidad de digitos del pin " + pinDigits);
    }

    //TOOL para cifrar en 3DESede ECB NoPadding
    public byte[] protectKey(byte[] suppliedKey, byte[] data) {
        byte[] keyMaterial = new byte[24];
        System.arraycopy(suppliedKey, 0, keyMaterial, 0, 16);
        System.arraycopy(suppliedKey, 0, keyMaterial, 16, 8);
        try {
            SecretKeySpec key = new SecretKeySpec(keyMaterial, "DESede");
            Cipher cip = Cipher.getInstance("DESede/ECB/NoPadding");
            cip.init(Cipher.ENCRYPT_MODE, key);
            return cip.doFinal(data);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String protectKey(String suppliedKey, String data) {
        return Util.toHexString(
                protectKey(
                        Util.toByteArray(suppliedKey),
                        Util.toByteArray(data)
                ));
    }

}