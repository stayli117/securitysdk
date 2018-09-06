package net.people.ocr;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.people.ocr.sec.LoginManager;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private TextView tv_ocr_results;

    boolean isBase = false;
    boolean isSave = false;
    int mCheckedId = 0;

    String value;
    private EditText mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tv_ocr_results = (TextView) findViewById(R.id.ocr_results);
        mValue = (EditText) findViewById(R.id.et_input);

        RadioGroup radioGroup = findViewById(R.id.rg_content);

        RadioButton aes = findViewById(R.id.rb_aes);
        aes.setChecked(true);
        mCheckedId = aes.getId();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mCheckedId = checkedId;
                security(mCheckedId, isBase, isBase);
            }
        });


        CheckBox checkBox = findViewById(R.id.cb_base);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isBase = isChecked;
                security(mCheckedId, isBase, isBase);
            }
        });
        CheckBox cb_save = findViewById(R.id.cb_save);
        cb_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSave = isChecked;
                security(mCheckedId, isBase, isSave);
            }
        });


    }

    private void security(int checkedId, boolean base, boolean save) {
        value = mValue.getText().toString().trim();
        Log.e(TAG, "security: base " + base + " save " + save);
        switch (checkedId) {
            case R.id.rb_aes:
                String aes = LoginManager.aes(base, save, value);
                tv_ocr_results.setText(aes);
                break;
            case R.id.rb_des:
                String des = LoginManager.des(base, save, value);
                tv_ocr_results.setText(des);
                break;
        }
    }


}
