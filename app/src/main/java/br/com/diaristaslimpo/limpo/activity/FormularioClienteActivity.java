package br.com.diaristaslimpo.limpo.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Calendar;
import java.util.Date;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.helper.FormularioClienteHelper;
import br.com.diaristaslimpo.limpo.to.FormularioClienteTo;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.util.ValidationUtil;
import br.com.diaristaslimpo.limpo.task.CadastraClienteTask;

public class FormularioClienteActivity extends AppCompatActivity {
    private FormularioClienteTo formularioClienteTo;
    private FormularioClienteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cliente);

        helper = new FormularioClienteHelper(this);

        ExibeDataListener listener = new ExibeDataListener();
        helper.getDataNascimento().setOnClickListener(listener);
        helper.getDataNascimento().setOnFocusChangeListener(listener);
    }

    private void exibeData(){
        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, new SelecionaDataListener(), ano - 18, mes, dia);
        calendar.set(ano - 18, mes, dia);
        pickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        pickerDialog.show();
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
            helper.setDataNascimento(year, monthOfYear, dayOfMonth);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salva_cadastro1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_cadastro:
                if(helper.validarCamposObrigatorios()){
                    formularioClienteTo = helper.getFormularioClienteTo();

                    if (!(formularioClienteTo.getSenha().equals(formularioClienteTo.getConfirmacaoSenha()))){
                        MessageBox.showAlert(this,"Atenção","Campos de senha não estão iguais");
                        break;
                    }

                    if(!ValidationUtil.isValidCPF(formularioClienteTo.getCpf())){
                        MessageBox.showAlert(this,"Atenção","CPF inválido");
                        break;
                    }

                    if(!ValidationUtil.isValidEmail(formularioClienteTo.getEmail())){
                        MessageBox.showAlert(this,"Atenção","E-mail inválido");
                        break;
                    }

                    String json = formularioClienteTo.toString();
                    new CadastraClienteTask(this).execute(json);
                }
        }

        return super.onOptionsItemSelected(item);
    }
}