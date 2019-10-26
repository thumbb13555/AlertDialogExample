package com.example.alertdialogexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btNormal,btDialog,btCustomDialog,btButtonCustomDialog;
    Boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btNormal            = findViewById(R.id.button_Normal);
        btDialog            = findViewById(R.id.button_Dialog);
        btCustomDialog      = findViewById(R.id.button_CustomDialog);
        btButtonCustomDialog= findViewById(R.id.button_CustomButtonDialog);

        btNormal.setOnClickListener((v ->{setNormalAlertDialog();}));
        btDialog.setOnClickListener((v ->{setDialog();}));
        btCustomDialog.setOnClickListener((v -> {setCustomDialog();}));
        /*抱歉我真的很懶...不熟悉lambda語法的朋友們照著下面的方法打就好*/
        btButtonCustomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonCustomDialog();
            }
        });
    }//onCreate
    private void setNormalAlertDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("這是標題");
        alertDialog.setMessage("文字在此");
        /*一樣，不熟的用這個打就OK了*/
        alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(),"確定",Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setNegativeButton("中立",(dialog, which) -> {
            setToast("中立");
        });
        alertDialog.setNeutralButton("取消",(dialog, which) -> {
            setToast("取消");
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void setDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("這是標題");
        alertDialog.setMessage("文字在此");
        alertDialog.setPositiveButton("確定",((dialog, which) -> {}));
        alertDialog.setNegativeButton("中立",((dialog, which) -> {}));
        alertDialog.setNeutralButton("取消",((dialog, which) -> {}));
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v -> {
            setToast("按了確定但是不給你消失");
        }));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener((v ->{
            setToast("中立");
            dialog.dismiss();
        }));
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener((v -> {
            setToast("取消");
            dialog.dismiss();
        }));

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


    }

    private void setCustomDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.set_custom_dialog_layout,null);
        alertDialog.setTitle("這是標題");
        alertDialog.setView(v);
        alertDialog.setPositiveButton("確定",(((dialog, which) -> {})));

        AlertDialog dialog = alertDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v1 -> {
            dialog.dismiss();
        }));


        TextView tvDisplay = v.findViewById(R.id.textView);
        Switch swPlay = v.findViewById(R.id.switch1);
        dialog.setCanceledOnTouchOutside(false);
        swPlay.setChecked(status?true:false);
        tvDisplay.setText(swPlay.isChecked()?"開":"關");
        /*此處註解程式已用lambda代替，請參考
        swPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });*/
        swPlay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            tvDisplay.setText(isChecked?"開":"關");
            status = (isChecked?true:false);
            /*對懶人寫法不熟的請參考註解
            if (isChecked == true){
                tvDisplay.setText("開");
            }else {
                tvDisplay.setText("關");
            }
            */
        });
    }

    private void setButtonCustomDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.set_custom_dialog_layout_with_button,null);
        alertDialog.setView(v);
        Button btOK = v.findViewById(R.id.button_ok);
        Button btC  = v.findViewById(R.id.buttonCancel);
        EditText editText = v.findViewById(R.id.ededed);
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        btOK.setOnClickListener((v1 -> {
            AlertDialog.Builder twoDialog = new AlertDialog.Builder(MainActivity.this);
            twoDialog.setTitle("這是疊上去的AlertDialog");
            twoDialog.setPositiveButton("瞭解",((dialog1, which) -> {}));
            twoDialog.show();
            }));
        btC.setOnClickListener((v1 -> {setToast(editText.getText().toString());dialog.dismiss();}));
    }


    //不要鳥他他只是打醬油設吐司的
    private void setToast(String input){
        Toast.makeText(getBaseContext(),input,Toast.LENGTH_SHORT).show();
    }

}
