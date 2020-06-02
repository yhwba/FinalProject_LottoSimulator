package kr.co.yhw.finalproject_lottosimulator_20200602;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    long winMoney = 0L;

    int firstRankCount = 0;
    int secondRankCount = 0;
    int thirdRankCount = 0;
    int fourthRankCount = 0;
    int fifthRankCount = 0;
    int unRankCount = 0;


    List<TextView> myNumTxts = new ArrayList<>();

    boolean isAutoBuyRunning = false;
    Handler mHandler = new Handler();
    Runnable buyLottoRunnable = new Runnable() {
        @Override
        public void run() {
            if (useMoney < 1000000000) {
                makeLottoWinNumbers();
                checkWinRank();
                mHandler.post(buyLottoRunnable);
            } else {
                Toast.makeText(mContext, "로또 구매를 종료합니다.", Toast.LENGTH_SHORT).show();

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();

    }

    @Override
    public void setupEvents() {

//        자동구매를 누르면
        binding.buyAutoLottoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                지금 구매를 안돌리고 있다면
                if (!isAutoBuyRunning) {
//                    구매시작 코드를 할일로 등록시키가 =>mHandler가 실행
                    mHandler.post(buyLottoRunnable);
//                    구매가 돌아가고 있다고 명시
                    isAutoBuyRunning = true;
//                    버튼의 문구도 중단하기로 변경
                    binding.buyAutoLottoBtn.setText(getResources().getString(R.string.pause_buy_auto_lotto_btn));
                }
//                구매가 돌아가고있다면
                else {
//                    예정된 다음 구매 행동을 할일에서 제기
//                    더이상 할일이 없으니 , 정지된다
                    mHandler.removeCallbacks(buyLottoRunnable);
//                    지금 구매중이 아니라고 명시
                    isAutoBuyRunning = false;
//                    다시 누르면  제개하기로 변경
//                    res> strings에서 버튼 별 설정
                    binding.buyAutoLottoBtn.setText(getResources().getString(R.string.resume_buy_auto_lotto_btn));
                }
//                while (true){
//                    makeLottoWinNumbers();
//                    checkWinRank();
//                    if(useMoney > 100000000){
//                        break;
//                    }
//                }
            }
        });

//        한장구매 => 로또번호를 만들고/ 등수확인만 실행
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
//        당첨번호 텍스트뷰들을=> ArrayList에 담아둠
//        당첨번호 적어줄게 편리하게 짜려고
        winNumTxts.add(binding.winNumTxt01);
        winNumTxts.add(binding.winNumTxt02);
        winNumTxts.add(binding.winNumTxt03);
        winNumTxts.add(binding.winNumTxt04);
        winNumTxts.add(binding.winNumTxt05);
        winNumTxts.add(binding.winNumTxt06);

//        내 입력번호도 같은 처리
        myNumTxts.add(binding.myNumTxt01);
        myNumTxts.add(binding.myNumTxt02);
        myNumTxts.add(binding.myNumTxt03);
        myNumTxts.add(binding.myNumTxt04);
        myNumTxts.add(binding.myNumTxt05);
        myNumTxts.add(binding.myNumTxt06);

    }

    void checkWinRank() {
//        사용금액은 무조건 1000원 증가
        useMoney += 1000;

//        증가된 사용금액을 화면에 반영( 중복코드)
        binding.useMoneyTxt.setText(String.format("%,d원 ", useMoney));

//        맞춘갯수 저장 변수
        int correctCount = 0;

//        내 입력번호가 적힌 텍스트뷰들 (setValues 참고) 을 꺼내봄
        for (TextView myNumTxt : myNumTxts) {
            //   적혀있는 숫자myNumTxt(string)을 int로 바꿔야함 =>Integer.parseInt
            int myNum = Integer.parseInt(myNumTxt.getText().toString());

//            내 숫자를 들고 => 당첨번호를 돌면서 확인
            for (int winNum : winLottoNumArr) {

//                같은걸 찾았다면 , 맞춘갯수를 1 개 증가
                if (myNum == winNum) {
                    correctCount++;
                }
            }
        }
//        맞춘갯수에 따른 등수 판정 + 당첨 금액 누적
        if (correctCount == 6) {
            winMoney += 1300000000;
            firstRankCount++;
        } else if (correctCount == 5) {
//            5개를 맞췄을땐 , 보너스 번호 여부에 따라  2,3 등이 갈림
            boolean isBounsNumCorrect = false;


//            내 입력번호 텓스트뷰 목록을 돌면서 확인
            for (TextView myNumTxt : myNumTxts) {
//                 텍스트뷰에 적힌 내용을 int로 변경
                int myNum = Integer.parseInt(myNumTxt.getText().toString());

//               보너스 번호와 비교하여, 같은게 있다면 보너스맞췄다고 처리
//                한번보 이 분기에 못들어왔다 => 보너스 못맞춤
                if (myNum == bounsNum) {
                    isBounsNumCorrect = true;
                    break;
                }
            }
//            보너스 맞추면 2등 , 아니면 3등
            if (isBounsNumCorrect) {
                winMoney += 54000000;
                secondRankCount++;
            } else {
                winMoney += 1450000;
                thirdRankCount++;
            }
        } else if (correctCount == 4) {
            winMoney += 50000;
            fourthRankCount++;
        } else if (correctCount == 3) {
//            5등은 돈으로 가져가지 않고 로또 5장 추가구매 => 사용금액을 줄여준다.
            useMoney -= 5000;
            //            winMoney +=5000; //=> 당첨금액 증가
            fifthRankCount++;
        } else {
//            3개도 못맞췄다면 전부 낙첨으로 처리
            unRankCount++;
        }

//        사용금액과 당첨금액을 화면에 표시
        binding.winMoneyTxt.setText(String.format("%,d", winMoney));
        binding.useMoneyTxt.setText(String.format("%,d", useMoney));

//        각 등수별 (낙첨 포함) 횟수를 화면에 표시
        binding.firstRankTxt.setText(String.format("%d회", firstRankCount));
        binding.secondRankTxt.setText(String.format("%d회", secondRankCount));
        binding.thirdRankTxt.setText(String.format("%d회", thirdRankCount));
        binding.fourthRankTxt.setText(String.format("%d회", fourthRankCount));
        binding.fifthRankTxt.setText(String.format("%d회", fifthRankCount));
        binding.unRankTxt.setText(String.format("%d회", unRankCount));

    }


    void makeLottoWinNumbers() {
//        로또 넘버 초기화
        for (int i = 0; i < winLottoNumArr.length; i++) {
            winLottoNumArr[i] = 0;
        }
//        보너스 번호도 0으로 초기화
        bounsNum = 0;

//        여섯개의 당첨번호 뽑기위한 for
        for (int i = 0; i < winLottoNumArr.length; i++) {
//            조건에 맞는 (중복이 아닌) 숫자를 뽑을 때까지 무한반복
            while (true) {
//               1~45중 랜덤
                int randomNum = (int) (Math.random() * 45 + 1);
//                 중복검사 결과 저장 변수 => 일단 맞다고 했다가, 수틀리면 false
                boolean isDuplicatedOk = true;


//                당첨번호 중 같은게 있다면 false
//                한번도 같은게 없었다면, true 유지
                for (int num : winLottoNumArr) {
                    if (num == randomNum) {
                        isDuplicatedOk = false;
                        break;
                    }
                }
//              중복검사가 통과 되었다면
                if (isDuplicatedOk) {
//                    당첨번호로 등록
                    winLottoNumArr[i] = randomNum;
//                    무한반복 탈출=> 다음 당첨번호 뽑으러 감 for 문 다음 i
                    break;
                }

            }
        }
//        6개를 뽑는 for문이 다돌고 나면 => 순서가 뒤죽박죽
//        Array클래스의 static메쏘드를 활용해서 오름차순 정렬
        Arrays.sort(winLottoNumArr);
//         보너스 번호를 뽑는 무한반복 => 1개만 뽑는다 for문은 없고 바로 무한반복
        while (true) {

            int randomNum = (int) (Math.random() * 45 + 1);

//
            boolean isDuplicatedOk = true;

            for (int num : winLottoNumArr) {
                if (num == randomNum) {
                    isDuplicatedOk = false;
                    break;
                }
            }

            if (isDuplicatedOk) {
                bounsNum = randomNum;
                break;
            }
        }

//        당첨 번호들을 텍스트뷰에 표시
        for (int i = 0; i < winNumTxts.size(); i++) {
            int winNum = winLottoNumArr[i];
//            Log.d("당첨번호", winNum + "");
//            화면에 있는 당첨번호 텍스트뷰들을 ArrayList에 담아두고(setValues참고) 활용
            winNumTxts.get(i).setText(winNum + "");
        }

//        보너스번호도 화면에 표시
        binding.bounsNumTxt.setText(bounsNum + "");


    }
}
