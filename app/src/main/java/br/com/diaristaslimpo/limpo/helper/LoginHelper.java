package br.com.diaristaslimpo.limpo.helper;

import android.widget.EditText;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.LoginActivity;
import br.com.diaristaslimpo.limpo.to.LoginTo;

/**
 * Created by Hugo on 29/10/2016.
 */

public class LoginHelper extends BaseHelper{
    private final EditText email, senha;
    private LoginTo loginTo;

    public LoginHelper(LoginActivity activity) {
        super.setContext(activity);

        email = (EditText) activity.findViewById(R.id.login_email);
        senha = (EditText) activity.findViewById(R.id.login_senha);

        loginTo = new LoginTo(email.getText().toString() , senha.getText().toString());
    }

    public LoginTo getLoginTo(){
        loginTo.setEmail(email.getText().toString());
        loginTo.setSenha(senha.getText().toString());

        return loginTo;
    }

    public boolean validarCamposObrigatorios(){
        validarPreenchimentoCampoObrigatorio(email);
        validarPreenchimentoCampoObrigatorio(senha);

        return super.getIsValid();
    }
}
