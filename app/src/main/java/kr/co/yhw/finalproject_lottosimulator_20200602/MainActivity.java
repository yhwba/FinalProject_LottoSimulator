package kr.co.yhw.finalproject_lottosimulator_20200602;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.co.yhw.finalproject_lottosimulator_20200602.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    int[] winLottoNumArr = new int[6];  //배열의 3번칸에 적힌값. 0 => 배열의 모든 칸 기본값은 0
    int bounsNum = 0;

    List<TextView> winNumTxts = new ArrayList<>();
    long useMoney = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.buyOneLottoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeLottoWinNumbers();
                checkWinRank();
            }
        });

    }

    @Override
    public void setValues() {
        winNumTxts.add((binding.winNumTxt01));
        winNumTxts.add((binding.winNumTxt02));
        winNumTxts.add((binding.winNumTxt03));
        winNumTxts.add((binding.winNumTxt04));
        winNumTxts.add((binding.winNumTxt05));
        winNumTxts.add((binding.winNumTxt06));
    }

    void checkWinRank(){
        useMoney +=1000;

        binding.useMoneyTxt.setText(String.format("%,d원 ", useMoney));

    }
    void makeLottoWinNumbers() {
//        로또 넘버 초기화
        for (int i = 0; i < winLottoNumArr.length; i++) {
            winLottoNumArr[i] = 0;
        }
        bounsNum =0;

//        여섯개의 당첨번호 뽑기
        for (int i = 0; i < winLottoNumArr.length; i++) {
            while (true) {

                int randomNum = (int) (Math.random() * 45 + 1);

                boolean isDuplicatedOk = true;

                for (int num : winLottoNumArr) {
                    if (num == randomNum) {
                        isDuplicatedOk = false;
                        break;
                    }
                }

                if (isDuplicatedOk) {
                    winLottoNumArr[i] = randomNum;
                    break;
                }

            }
        }
        Arrays.sort(winLottoNumArr);

        while (true){

            int randomNum =(int)(Math.random()*45 +1);

            boolean isDuplicatedOk = true;

            for (int num : winLottoNumArr){
                if (num == randomNum){
                    isDuplicatedOk=false;
                    break;
                }
            }

            if(isDuplicatedOk){
                bounsNum = randomNum;
                break;
            }
        }

        for (int i = 0; i < winNumTxts.size(); i++) {
            int winNum = winLottoNumArr[i];
//            Log.d("당첨번호", winNum + "");
            winNumTxts.get(i).setText(winNum+"");
        }

        binding.bounsNumTxt.setText(bounsNum+"");


    }
}
