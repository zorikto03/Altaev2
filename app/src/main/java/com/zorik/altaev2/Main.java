package com.zorik.altaev2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class Main extends AppCompatActivity {
    EditText Answer;
    TextView textView , CountAttempt, CountAccept;

    Random rnd;
    String question, answer,finalcode;
    String First_Name, Last_Name, Number_Group;
    boolean implementation;
    int[] newCode;
    int Quest, attempt, CountOfQuestion;
    int CountOfAccept, CountQuestionAll=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Answer = findViewById(R.id.Answer);
        textView = findViewById(R.id.Textview);
        CountAccept = findViewById(R.id.CountAccept_id);
        CountAttempt = findViewById(R.id.CountAttempt_id);

        rnd = new Random();
        CountOfAccept=0;
        CountOfQuestion=0;
        MakeQuestion();

        First_Name = getIntent().getStringExtra("FName");
        Last_Name = getIntent().getStringExtra("LName");
        Number_Group = getIntent().getStringExtra("NGroup");
        implementation=false;
    }
    public void ButtonAnswer(View view) {
        answer = Answer.getText().toString();
        if(!implementation) {// если задачи еще не решены

            if (answer.equals(finalcode)) {
                CountOfQuestion++;
                CountOfAccept++;
                MakeQuestion();

            } else {
                attempt--;
                if (attempt == 0) {
                    CountOfQuestion++;
                    MakeQuestion();

                }
                PrintAttemptsCount();
            }
            if (CountOfQuestion == CountQuestionAll) {
                String implemet = "вы решили " + CountOfAccept + " задач(у / и) из " + CountQuestionAll;
                textView.setText(implemet);
                implementation = true; // признак решенных задач
                start_activity(); // переход на следующую активность
            }
        }
        else {

            start_activity();
        }
    }

    public void MakeQuestion(){
        Quest = rnd.nextInt(10000);
        question = "Преобразуйте число " + Quest + " в код Хэмминга\n\nПримечание: необходимо писать код со всеми нулями";
        attempt = 3;
        Accept_Question();
        PrintAttemptsCount();
        Hamming();

    }
    public void Accept_Question(){
        String str = CountOfAccept+" / " + CountOfQuestion;
        CountAccept.setText(str);
        textView.setText(question);
    }

    public void PrintAttemptsCount(){
        String att="Осталось попыток : " + attempt;
        CountAttempt.setText(att);
    }

    public void Hamming(){

        String bin_n = Integer.toBinaryString(Quest);
        int countbit = bin_n.length();
        int stepen=0;
        int[] a = new int[countbit];
        for(int i = 0; i < countbit; i++)
        {
            a[i] = Character.digit(bin_n.charAt(i), 10);
        }

        while(true)
        {
            if(countbit >= (Math.pow(2, stepen) - stepen)){
                stepen++;
            }
            else break;
        }

        int newcount = stepen + countbit;
        newCode  = new int[newcount];
        finalcode = "";
        newCode = generateCode(a);

        for(int j = 0; j < newCode.length; j++){
            finalcode = finalcode + newCode[j];

        }
    }

    static int[] generateCode(int a[]) {

        int b[];


        int i=0, parity_count=0 ,j=0, k=0;
        while(i < a.length) {

            if(Math.pow(2,parity_count) == i+parity_count + 1) {
                parity_count++;
            }
            else {
                i++;
            }
        }

        b = new int[a.length + parity_count];


        for(i=1 ; i <= b.length ; i++) {
            if(Math.pow(2, j) == i) {

                b[i-1] = 2;
                j++;
            }
            else {
                b[k+j] = a[k++];
            }
        }
        for(i=0 ; i < parity_count ; i++) {

            b[((int) Math.pow(2, i))-1] = getParity(b, i);
        }
        return b;
    }

    static int getParity(int b[], int power) {
        int parity = 0;
        for(int i=0 ; i < b.length ; i++) {
            if(b[i] != 2) {

                int k = i+1;
                String s = Integer.toBinaryString(k);

                int x = ((Integer.parseInt(s))/((int) Math.pow(10, power)))%10;
                if(x == 1) {
                    if(b[i] == 1) {
                        parity = (parity+1)%2;
                    }
                }
            }
        }
        return parity;
    }

    public void start_activity(){
        Intent intent = new Intent(Main.this, Final.class);

        intent.putExtra("FName", First_Name);
        intent.putExtra("LName", Last_Name);
        intent.putExtra("NGroup", Number_Group);
        intent.putExtra("CountOfAccept", CountOfAccept);
        intent.putExtra("CountQuestionAll", CountQuestionAll);
        startActivity(intent);
    }
}
