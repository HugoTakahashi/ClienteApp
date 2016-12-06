package br.com.diaristaslimpo.limpo.helper;

import android.content.Context;
import android.widget.TextView;

import br.com.diaristaslimpo.limpo.R;

/**
 * Created by Hugo on 01/11/2016.
 */

public class BaseHelper {

    private boolean isValid = true;
    private Context context;

    public boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        isValid = valid;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void validarPreenchimentoCampoObrigatorio(TextView campo){
        if(campo.getText().toString().equals("")){
            campo.setError(context.getResources().getString(R.string.campo_obrigatorio));
            setIsValid(false);
        }
    }
}