package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.task.BaixaAvaliacaoTask;
import br.com.diaristaslimpo.limpo.util.MessageBox;

public class InicialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private String idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();

        findViewById(R.id.bt_solicitar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, SelecionarEnderecoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.bt_agendadas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, ListaDiariasAgendadasActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.bt_historico).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this, ListaHistoricoActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        ScriptSQL scriptSQL = new ScriptSQL(conn);
        idCliente = String.valueOf(scriptSQL.retornaIdCliente());
        new BaixaAvaliacaoTask(InicialActivity.this).execute(String.valueOf(scriptSQL.retornaIdCliente()), idCliente);
    }

    @Override
    public void onBackPressed() {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, SelecionarEnderecoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, DadosPessoaisActivity.class);
            intent.putExtra("idCliente", idCliente);
            startActivity(intent);

        }else if (id == R.id.diarias_agendadas) {
            Intent intent = new Intent(this, ListaDiariasAgendadasActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery2) {
            Intent intent = new Intent(this, MeusEnderecosActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(InicialActivity.this, ListaHistoricoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, ConfiguracaoActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            try {

                dataBase = new DataBase(this);
                conn = dataBase.getWritableDatabase();

                ScriptSQL scriptSQL = new ScriptSQL(conn);
                scriptSQL.logof();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } catch (SQLException ex) {
                MessageBox.show(this, "Erro", "Erro ao criar o banco: " + ex.getMessage());
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}