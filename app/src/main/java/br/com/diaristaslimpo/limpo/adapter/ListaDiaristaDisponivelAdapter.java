package br.com.diaristaslimpo.limpo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.DownloadImageTask;
import br.com.diaristaslimpo.limpo.to.DiaristaDisponivelTo;

/**
 * Created by Hugo on 23/11/2016.
 */

public class ListaDiaristaDisponivelAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<DiaristaDisponivelTo> items;

    public ListaDiaristaDisponivelAdapter(Context context, ArrayList<DiaristaDisponivelTo> items)
    {
        //Itens que preencheram o listview
        this.items = items;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    public int getCount()
    {
        return items.size();
    }

    public DiaristaDisponivelTo getItem(int position)
    {
        return items.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        DiaristaDisponivelTo item = items.get(position);
        view = mInflater.inflate(R.layout.listview_listadiaristas, null);

        ((TextView) view.findViewById(R.id.DiaNome)).setText(item.getNome());
        ((TextView) view.findViewById(R.id.DiaDistancia)).setText(item.getDistanciaEmKm());
        ((TextView) view.findViewById(R.id.DiaNota)).setText(item.getRankFormatado());
        ((TextView) view.findViewById(R.id.DiaNumeroAvaliacoes)).setText(item.getNumeroAvaliacoes());
        String url = "https://s3-sa-east-1.amazonaws.com/arquivo-e-imagens/diarista/"
                + item.getIdDiarista() + "/foto.jpg";
        new DownloadImageTask((ImageView) view.findViewById(R.id.DiaFoto)).execute(url);

        return view;
    }
}