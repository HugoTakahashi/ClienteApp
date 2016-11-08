package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.helper.LoginHelper;
import br.com.diaristaslimpo.limpo.to.LoginTo;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.task.LoginTask;
import br.com.diaristaslimpo.limpo.util.ValidationUtil;

public class LoginActivity extends AppCompatActivity {

    private LoginTo loginTo;
    private LoginHelper helper;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private int isLogon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new LoginHelper(this);

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            ScriptSQL scriptSQL = new ScriptSQL(conn);

            isLogon = scriptSQL.isLogin(this);

            if(isLogon==1){
                Intent it = new Intent(this, InicialActivity.class);
                startActivity(it);
                finish();
            }
        }catch (SQLException ex){
            MessageBox.show(this, "Erro", "Erro ao criar o banco: " + ex.getMessage());
        }

        findViewById(R.id.login_botao_logar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(helper.validarCamposObrigatorios()){

                    loginTo = helper.getLoginTo();

                    if(!ValidationUtil.isValidEmail(loginTo.getEmail())){
                        MessageBox.showAlert(LoginActivity.this, "E-mail inválido","Escreve o email certo ai");
                        return;
                    }

                    String json = helper.getLoginTo().toString();
                    new LoginTask(LoginActivity.this).execute(json);
                } else {

                }
            }
        });

        findViewById(R.id.login_botao_cadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, FormularioClienteActivity.class);
                startActivity(it);
            }
        });
    }
}