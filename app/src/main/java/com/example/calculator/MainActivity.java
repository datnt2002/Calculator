package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    TextView resultText, expressionText;
    MaterialButton buttonC, buttonOpenBracket, buttonCloseBracket;
    MaterialButton buttonDivide, buttonMultiple, buttonMinus, buttonPlus, buttonEqual;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    MaterialButton buttonDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = findViewById(R.id.result_text);
        expressionText = findViewById(R.id.expression_text);

        assignIdForButton(buttonC, R.id.button_c);
        assignIdForButton(buttonOpenBracket, R.id.button_open_bracket);
        assignIdForButton(buttonCloseBracket, R.id.button_close_bracket);
        assignIdForButton(buttonDivide, R.id.button_divide);
        assignIdForButton(buttonMultiple, R.id.button_multiple);
        assignIdForButton(buttonMinus, R.id.button_minus);
        assignIdForButton(buttonPlus, R.id.button_plus);
        assignIdForButton(buttonEqual, R.id.button_equal);
        assignIdForButton(button0, R.id.button_0);
        assignIdForButton(button1, R.id.button_1);
        assignIdForButton(button2, R.id.button_2);
        assignIdForButton(button3, R.id.button_3);
        assignIdForButton(button4, R.id.button_4);
        assignIdForButton(button5, R.id.button_5);
        assignIdForButton(button6, R.id.button_6);
        assignIdForButton(button7, R.id.button_7);
        assignIdForButton(button8, R.id.button_8);
        assignIdForButton(button9, R.id.button_9);
        assignIdForButton(buttonDot, R.id.button_dot);
    }

    void assignIdForButton(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = expressionText.getText().toString();

        if(buttonText.equals("C")){
            expressionText.setText("");
            resultText.setText("0");
            return;
        }

        if (buttonText.equals("=")){
            String finalResult = getResult(dataToCalculate);
            if(!finalResult.equals("Error")){
                resultText.setText(finalResult);
            }
            return;
        }else{
            dataToCalculate = dataToCalculate + buttonText;
        }
        expressionText.setText(dataToCalculate);
    }


    String getResult (String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult =  context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            double parsedResult = Double.parseDouble(finalResult);
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            finalResult = decimalFormat.format(parsedResult);
//            if (finalResult.endsWith(".00")){
//              finalResult = finalResult.replace(".00", "");
//            }
            return finalResult;
        }catch(Exception e){
            return "Error";
        }
    }
}