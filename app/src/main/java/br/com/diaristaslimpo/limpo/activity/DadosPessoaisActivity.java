package br.com.diaristaslimpo.limpo.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.diaristaslimpo.limpo.to.Cliente;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.DateUtil;
import br.com.diaristaslimpo.limpo.util.GeraJson;
import br.com.diaristaslimpo.limpo.task.AlteraCadastroTask;
import br.com.diaristaslimpo.limpo.util.MaskUtil;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.util.ValidationUtil;

public class DadosPessoaisActivity extends AppCompatActivity {
    private EditText edtNome,edtSobrenome,edtCPF,edtDataNasc,edtEmail,edtCelular;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private int id;
    private String idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dados_pessoais);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtSobrenome = (EditText)findViewById(R.id.edtSobrenome);
        edtCPF = (EditText)findViewById(R.id.edtCPF);
        edtDataNasc = (EditText)findViewById(R.id.edtDateNasc);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtCelular = (EditText)findViewById(R.id.edtCelular);

        edtCPF.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.CPF, edtCPF));
        edtCelular.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.TEL, edtCelular));

        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            idCliente = bundle.getString("idCliente");

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            ScriptSQL sql = new ScriptSQL(conn);
            ArrayList<Cliente> obj;
            obj = sql.selectCliente(idCliente);
            id = obj.get(0).getIdCliente();
            edtNome.setText(obj.get(0).getNome());
            edtSobrenome.setText(obj.get(0).getSobrenome());
            edtCPF.setText(obj.get(0).getCpf());
            edtDataNasc.setText(obj.get(0).getDataNascimento());
            edtEmail.setText((obj.get(0).getEmail()));
            edtCelular.setText(String.valueOf(obj.get(0).getCelular()));
        }catch (Exception e){
        }

        DadosPessoaisActivity.ExibeDataListener listener = new DadosPessoaisActivity.ExibeDataListener();
        edtDataNasc.setOnClickListener(listener);
        edtDataNasc.setOnFocusChangeListener(listener);
    }

    private void exibeData(){
        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DadosPessoaisActivity.SelecionaDataListener(), ano - 18, mes, dia);
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
            edtDataNasc.setText(DateUtil.dateToString(year, monthOfYear, dayOfMonth));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salva_altera_dados, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_alteracao_dados:
                if(preenchimentoValido()) {
                    AlertDialog dialog = new AlertDialog.Builder(this).create();
                    dialog.setTitle("Atenção");
                    dialog.setMessage("Confirma alteração de dados?");

                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String[] data = edtDataNasc.getText().toString().split("/");
                            String dataFormatada = data[2] + "-" + data[1] + "-" + data[0];

                            String json = new GeraJson().jsonAlteraCadastro(
                                    id,
                                    edtNome.getText().toString(),
                                    edtSobrenome.getText().toString(),
                                    dataFormatada,
                                    MaskUtil.unmask(edtCPF.getText().toString()),
                                    edtEmail.getText().toString(),
                                    MaskUtil.unmask(edtCelular.getText().toString()));
                            new AlteraCadastroTask(DadosPessoaisActivity.this).execute(json);
                        }
                    });
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean preenchimentoValido(){
        campoObrigatorioPreenchido(edtNome);
        campoObrigatorioPreenchido(edtSobrenome);
        campoObrigatorioPreenchido(edtCPF);
        campoObrigatorioPreenchido(edtDataNasc);
        campoObrigatorioPreenchido(edtEmail);
        campoObrigatorioPreenchido(edtCelular);

        boolean cpfIsValid = true, emailIsValid = true;
        if(!ValidationUtil.isValidCPF(edtCPF.getText().toString())){
            edtCPF.setError("CPF inválido");
            cpfIsValid = false;
        }

        if(!ValidationUtil.isValidEmail(edtEmail.getText().toString())){
            edtEmail.setError("E-mail inválido");
            emailIsValid = false;
        }

        return !edtNome.getText().toString().matches("") &&
        !edtSobrenome.getText().toString().matches("") &&
        !edtCPF.getText().toString().matches("") &&
        !edtDataNasc.getText().toString().matches("") &&
        !edtEmail.getText().toString().matches("") &&
        !edtCelular.getText().toString().matches("") &&
        cpfIsValid && emailIsValid;
    }

    public void campoObrigatorioPreenchido(EditText campo){
        if(campo.getText().toString().matches(""))
            campo.setError(this.getResources().getString(R.string.campo_obrigatorio));
    }

}