package com.erikriosetiawan.cubecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BalokActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextPanjang, editTextLebar, editTextTinggi;
    Button buttonCalculate;
    TextView textViewLuasResult, textViewVolumeResult;

    private static final String STATE_RESULT_LUAS_PERMUKAAN = "state_result_luas_permukaan";
    private static final String STATE_RESULT_VOLUME = "state_result_volume";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balok);

        getSupportActionBar().setTitle("Luas Permukaan & Volume Balok");

        editTextPanjang = findViewById(R.id.edit_text_panjang_balok);
        editTextLebar = findViewById(R.id.edit_text_lebar_balok);
        editTextTinggi = findViewById(R.id.edit_text_tinggi_balok);

        buttonCalculate = findViewById(R.id.button_calculate);
        buttonCalculate.setOnClickListener(this);

        textViewLuasResult = findViewById(R.id.text_view_luas_result);
        textViewVolumeResult = findViewById(R.id.text_view_volume_result);

        if (savedInstanceState != null) {
            String resultLuas = savedInstanceState.getString(STATE_RESULT_LUAS_PERMUKAAN);
            String resultVolume = savedInstanceState.getString(STATE_RESULT_VOLUME);

            textViewLuasResult.setText(resultLuas);
            textViewVolumeResult.setText(resultVolume);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_calculate) {
            String inputPanjang = editTextPanjang.getText().toString().trim();
            String inputLebar = editTextLebar.getText().toString().trim();
            String inputTinggi = editTextTinggi.getText().toString().trim();

            boolean isEmptyFields = false;
            boolean isInvalidDouble = false;

            if (TextUtils.isEmpty(inputPanjang)) {
                isEmptyFields = true;
                editTextPanjang.setError("Field ini tidak boleh kosong");
            }

            if (TextUtils.isEmpty(inputLebar)) {
                isEmptyFields = true;
                editTextLebar.setError("Field ini tidak boleh kosong");
            }

            if (TextUtils.isEmpty(inputTinggi)) {
                isEmptyFields = true;
                editTextTinggi.setError("Field ini tidak boleh kosong");
            }

            Double panjang = toDouble(inputPanjang);
            Double lebar = toDouble(inputLebar);
            Double tinggi = toDouble(inputTinggi);

            if (panjang == null) {
                isInvalidDouble = true;
                editTextPanjang.setError("Field ini harus berupa nomor yang valid");
            }

            if (lebar == null) {
                isInvalidDouble = true;
                editTextLebar.setError("Field ini harus berupa nomor yang valid");
            }

            if (tinggi == null) {
                isInvalidDouble = true;
                editTextTinggi.setError("Field ini harus berupa nomor yang valid");
            }

            if (!isEmptyFields && !isInvalidDouble) {
                Balok hitungBalok = new Balok();
                hitungBalok.setLength(panjang);
                hitungBalok.setWidth(lebar);
                hitungBalok.setHeight(tinggi);

                Double luas = hitungBalok.luas(hitungBalok.getLength(), hitungBalok.getWidth(), hitungBalok.getHeight());
                Double volume = hitungBalok.volume(hitungBalok.getLength(), hitungBalok.getWidth(), hitungBalok.getHeight());

                textViewLuasResult.setText(String.valueOf(luas));
                textViewVolumeResult.setText(String.valueOf(volume));
            }
        }
    }

    private Double toDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_RESULT_LUAS_PERMUKAAN, textViewLuasResult.getText().toString());
        outState.putString(STATE_RESULT_VOLUME, textViewVolumeResult.getText().toString());
    }
}
