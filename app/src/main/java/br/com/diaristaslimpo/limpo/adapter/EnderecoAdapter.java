package br.com.diaristaslimpo.limpo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.to.Endereco;
import br.com.diaristaslimpo.limpo.R;

/**
 * Created by user on 09/05/2016.
 */
public class EnderecoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Endereco> itens;

    public EnderecoAdapter(Context context, ArrayList<Endereco> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        Endereco item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.listview_endereco, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.txt_endereco)).setText(item.getEndereco());
        ((TextView) view.findViewById(R.id.txt_cep)).setText(item.getCep());
        ((TextView) view.findViewById(R.id.txt_bairro)).setText(item.getBairro());
        ((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());

        return view;


    }
}
