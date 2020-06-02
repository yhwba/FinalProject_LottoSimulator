package kr.co.yhw.finalproject_lottosimulator_20200602;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import kr.co.yhw.finalproject_lottosimulator_20200602.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

    }
}
