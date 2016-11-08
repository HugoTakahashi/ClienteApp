package br.com.diaristaslimpo.limpo.adapter;

/**
 * Created by user on 23/08/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.model.ListaDiarista;
import br.com.diaristaslimpo.limpo.R;

public class ListaDiaristaAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private ArrayList<ListaDiarista> itens;

    public ListaDiaristaAdapter(Context context, ArrayList<ListaDiarista> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount()
    {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public ListaDiarista getItem(int position)
    {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        //Pega o item de acordo com a posção.
        ListaDiarista item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.listview_listadiaristas, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.DiaNome)).setText(item.getNome());
        ((TextView) view.findViewById(R.id.DiaDistancia)).setText( String.valueOf(item.getDistacia() / 1000) + " Km");
        ((RatingBar) view.findViewById(R.id.ratingBar)).setProgress(item.getRate());

        return view;
    }
}
