package br.com.diaristaslimpo.limpo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.to.DiariaTo;

/**
 * Created by user on 09/05/2016.
 */
public class HistoricoDiariasAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<DiariaTo> itens;

    public HistoricoDiariasAdapter(Context context, ArrayList<DiariaTo> itens){
        this.itens = itens;
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
        DiariaTo item = itens.get(position);
        view = mInflater.inflate(R.layout.listview_historico_diaria, null);
        ((TextView) view.findViewById(R.id.listview_historico_diaria_nome)).setText(item.getNome());
        ((TextView) view.findViewById(R.id.listview_historico_diaria_valor)).setText(item.getValor());
        ((TextView) view.findViewById(R.id.listview_historico_diaria_identificacao_casa)).setText(item.getIdentificacaoEndereco());
        ((TextView) view.findViewById(R.id.listview_historico_diaria_status)).setText(item.getStatusDiaria());
        ((TextView) view.findViewById(R.id.listview_historico_diaria_data)).setText(item.getData());
        ((ImageView) view.findViewById(R.id.listview_historico_diaria_imagem)).setImageResource(R.drawable.historico_limpeza);

        return view;
    }
}
