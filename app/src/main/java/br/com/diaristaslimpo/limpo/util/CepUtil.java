package br.com.diaristaslimpo.limpo.util;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import br.com.diaristaslimpo.limpo.task.BuscarEnderecoPorCepTask;

/**
 * Created by Hugo on 03/11/2016.
 */

public class CepUtil {
    public static TextWatcher buscarEnderecoPorCep(final EditText editText, final Activity activity) {
        return new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if(!editText.getText().toString().isEmpty()
                        && editText.getText().toString().length() == 9){
                    new BuscarEnderecoPorCepTask(activity)
                            .execute(editText.getText().toString());
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
        }

            public void afterTextChanged(Editable s) {
            }
        };
    }
}
