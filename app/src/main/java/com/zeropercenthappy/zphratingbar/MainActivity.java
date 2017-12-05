package com.zeropercenthappy.zphratingbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zeropercenthappy.ratingbarlibrary.OnRatingClickCallback;
import com.zeropercenthappy.ratingbarlibrary.ZPHRatingBar;

public class MainActivity extends AppCompatActivity implements OnRatingClickCallback, TextView.OnEditorActionListener {

    private EditText etCount;
    private EditText etSelectedCount;
    private ZPHRatingBar rb;
    private TextView tvCount;
    private TextView tvCountHint;
    private TextView tvSelectedHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initParams();
    }

    private void initView() {
        etCount = findViewById(R.id.et_count);
        etSelectedCount = findViewById(R.id.et_selected_count);
        rb = findViewById(R.id.rb);
        tvCount = findViewById(R.id.tv_count);
        tvCountHint = findViewById(R.id.tv_count_hint);
        tvSelectedHint = findViewById(R.id.tv_selected_hint);
    }

    private void initParams() {
        tvCount.setText("total:" + rb.getItemCountNumber() + "\n"
                + "selected:" + rb.getItemSelectedNumber());
        rb.setOnRatingClickCallback(this);
        etCount.setText(String.valueOf(rb.getItemCountNumber()));
        etSelectedCount.setText(String.valueOf(rb.getItemSelectedNumber()));
        etCount.setOnEditorActionListener(this);
        etSelectedCount.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == etCount.getId() && actionId == EditorInfo.IME_ACTION_DONE) {
            String countStr = etCount.getText().toString().trim();
            int count = TextUtils.isEmpty(countStr) ? 0 : Integer.parseInt(countStr);
            rb.setItemCountNumber(count);
            tvCount.setText("total:" + rb.getItemCountNumber() + "\n"
                    + "selected:" + rb.getItemSelectedNumber());
            return true;
        } else if (v.getId() == etSelectedCount.getId() && actionId == EditorInfo.IME_ACTION_DONE) {
            String countStr = etSelectedCount.getText().toString().trim();
            int count = TextUtils.isEmpty(countStr) ? 0 : Integer.parseInt(countStr);
            rb.setItemSelectedNumber(count);
            tvCount.setText("total:" + rb.getItemCountNumber() + "\n"
                    + "selected:" + rb.getItemSelectedNumber());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onItemClick(int position) {
        tvCount.setText("click [" + position + "]\n"
                + "total:" + rb.getItemCountNumber() + "\n"
                + "selected:" + rb.getItemSelectedNumber());
    }
}
