package br.com.diaristaslimpo.limpo.helper;

import android.widget.EditText;
import android.widget.RadioButton;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.FormularioClienteActivity;
import br.com.diaristaslimpo.limpo.to.FormularioClienteTo;
import br.com.diaristaslimpo.limpo.util.DateUtil;
import br.com.diaristaslimpo.limpo.util.MaskUtil;

import static android.R.attr.checked;
import static android.R.attr.supportsAssist;

/**
 * Created by Hugo on 01/11/2016.
 */

public class FormularioClienteHelper extends BaseHelper {
    private EditText nome, sobrenome, dataNascimento, cpf, email, senha, confirmacaoSenha, celular;
    private RadioButton masculino, feminino;
    private FormularioClienteTo to;

    public EditText getDataNascimento(){
        return this.dataNascimento;
    }

    public void setDataNascimento(int year, int month, int day){
        this.dataNascimento.setText(DateUtil.dateToString(year, month, day));
        to.setDataNascimento(DateUtil.dateToJson(year, month, day));
    }

    public FormularioClienteHelper(FormularioClienteActivity activity) {
        super.setContext(activity);

        nome = (EditText) activity.findViewById(R.id.formulario_cliente_nome);
        sobrenome = (EditText) activity.findViewById(R.id.formulario_cliente_sobrenome);
        dataNascimento = (EditText) activity.findViewById(R.id.formulario_cliente_data_nascimento);
        cpf = (EditText) activity.findViewById(R.id.formulario_cliente_cpf);
        email = (EditText) activity.findViewById(R.id.formulario_cliente_email);
        senha = (EditText) activity.findViewById(R.id.formulario_cliente_senha);
        confirmacaoSenha = (EditText) activity.findViewById(R.id.formulario_cliente_confirmar_senha);
        celular = (EditText) activity.findViewById(R.id.formulario_cliente_celular);
        masculino = (RadioButton) activity.findViewById(R.id.formulario_cliente_genero_masculino);
        feminino = (RadioButton) activity.findViewById(R.id.formulario_cliente_genero_feminino);

        to = new FormularioClienteTo();
        mask();
    }

    public FormularioClienteTo getFormularioClienteTo(){
        to.setNome(nome.getText().toString());
        to.setSobrenome(sobrenome.getText().toString());
        to.setCpf(cpf.getText().toString());
        to.setEmail(email.getText().toString());
        to.setSenha(senha.getText().toString());
        to.setConfirmacaoSenha(confirmacaoSenha.getText().toString());
        to.setCelular(celular.getText().toString());

        if(masculino.isChecked())
            to.setGenero("M");
        else
            to.setGenero("F");

        return to;
    }

    public boolean validarCamposObrigatorios(){
        super.setIsValid(true);

        validarPreenchimentoCampoObrigatorio(nome);
        validarPreenchimentoCampoObrigatorio(sobrenome);
        validarPreenchimentoCampoObrigatorio(dataNascimento);
        validarPreenchimentoCampoObrigatorio(cpf);
        validarPreenchimentoCampoObrigatorio(email);
        validarPreenchimentoCampoObrigatorio(senha);
        validarPreenchimentoCampoObrigatorio(confirmacaoSenha);
        validarPreenchimentoCampoObrigatorio(celular);

        return super.getIsValid();
    }

    private void mask(){
        cpf.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.CPF, cpf));
        celular.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.TEL, celular));
    }
}