package br.com.diaristaslimpo.limpo.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.util.DateUtil;
import br.com.diaristaslimpo.limpo.util.MessageBox;

public class DataServicoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText data, dataFake;
    private TextView valorDiaria;
    private Button bt;
    private ImageButton btcompleto,btmeia;
    private String end, servs, dataServico,idEndereco,outros;
    private int limpeza,passarroupa,lavarroupa;
    private Spinner sp2;
    private ArrayAdapter<String> adp2;
    private int inativo;
    private int ativo;
    private int completa,meia;
    private static DataServicoActivity activity;

    public static DataServicoActivity getInstance(){
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_data_servico);

        activity = this;

        ativo = Color.parseColor("#c60b2d");
        inativo = Color.parseColor("#DCDCDC");
        data = (EditText)findViewById(R.id.data);
        dataFake = (EditText)findViewById(R.id.dataFake);
        valorDiaria = (TextView) findViewById(R.id.valordadiaria);
        sp2 = (Spinner)findViewById(R.id.spinner2);

        adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        sp2.setAdapter(adp2);
        adp2.addAll(hora());

        bt = (Button)findViewById(R.id.btEscolheDiarista);
        bt.setOnClickListener(this);

        ExibeDataListener listener = new ExibeDataListener();
        data.setOnClickListener(listener);
        data.setOnFocusChangeListener(listener);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        end = bundle.getString("endereco");
        servs = bundle.getString("servicos");
        idEndereco = bundle.getString("idendereco");
        limpeza = bundle.getInt("limpeza");
        passarroupa = bundle.getInt("passarroupa");
        lavarroupa = bundle.getInt("lavarroupa");
        outros = bundle.getString("outros");

        btcompleto = (ImageButton) findViewById(R.id.btcompleta);

        btcompleto.setBackgroundColor(ativo);
        completa = 1;
        valorDiaria.setText("150,00");

        btcompleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (completa == 0) {
                    completa = 1;
                    meia = 0;
                    btcompleto.setBackgroundColor(ativo);
                    btmeia.setBackgroundColor(inativo);
                    valorDiaria.setText("150,00");
                } else {
                    completa = 0;
                    btcompleto.setBackgroundColor(inativo);
                }
            }
        });

        btmeia = (ImageButton) findViewById(R.id.btmeia);
        btmeia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (meia == 0) {
                    meia = 1;
                    completa = 0;
                    btmeia.setBackgroundColor(ativo);
                    btcompleto.setBackgroundColor(inativo);
                    valorDiaria.setText("100,00");
                } else {
                    meia = 0;
                    btmeia.setBackgroundColor(inativo);
                }
            }
        });

    }

    private void exibeData(){
        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(this,new SelecionaDataListener(),ano,mes,dia + 1);
        calendar.set(ano, mes, dia + 1);
        pickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        pickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(meia == 0 && completa == 0){
            MessageBox.show(DataServicoActivity.this,"Atenção","Selecione um tipo de diária");
            return;
        }

        Intent it = new Intent(this,ListaDiaristaActivity.class);
        dataServico = dataFake.getText().toString();

        it.putExtra("tipoDiaria",meia == 1 ? false : true);
        it.putExtra("endereco",end);
        it.putExtra("servicos",servs);
        it.putExtra("dataservico", dataServico);
        it.putExtra("data", data.getText().toString() + " - " + sp2.getSelectedItem().toString());
        it.putExtra("limpeza", limpeza);
        it.putExtra("passarroupa",passarroupa);
        it.putExtra("lavarroupa", lavarroupa);
        it.putExtra("idendereco",idEndereco);
        it.putExtra("outros",outros);
        it.putExtra("horainicio",sp2.getSelectedItem().toString());
        it.putExtra("diaria",sp2.getSelectedItem().toString());
        it.putExtra("valorDiaria", valorDiaria.getText().toString());

        startActivity(it);
    }

    private class ExibeDataListener implements View.OnClickListener,View.OnFocusChangeListener {
        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                exibeData();
            }
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String dt = DateUtil.dateToString(year, monthOfYear, dayOfMonth);
            String dtFake = DateUtil.dateToJson(year, monthOfYear, dayOfMonth);

            data.setText(dt);
            dataFake.setText(dtFake);
        }
    }

    private String[] hora(){
        String[] array = new String[8];
        array[0] = "07:00";
        array[1] = "08:00";
        array[2] = "09:00";
        array[3] = "10:00";
        array[4] = "11:00";
        array[5] = "12:00";
        array[6] = "13:00";
        array[7] = "14:00";
        return array;
    }
}
