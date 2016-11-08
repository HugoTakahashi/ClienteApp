package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.telas.TelaDadosPessoais;
import br.com.diaristaslimpo.limpo.telas.Tela_Configuracao;
import br.com.diaristaslimpo.limpo.telas.Tela_Historico;
import br.com.diaristaslimpo.limpo.telas.Tela_MeusEnderecos;
import br.com.diaristaslimpo.limpo.util.MessageBox;

public class InicialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private ImageButton btSolicitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btSolicitar =(ImageButton) findViewById(R.id.bt_solicitar);
        btSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(InicialActivity.this,SelecionarEnderecoActivity.class);
                startActivity(it);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent it = new Intent(this, SelecionarEnderecoActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_gallery) {
            Intent it = new Intent(this, TelaDadosPessoais.class);
            startActivity(it);

        }else if (id == R.id.diarias_agendadas) {
            Intent it = new Intent(this, Tela_Historico.class);
            startActivity(it);

        } else if (id == R.id.nav_gallery2) {
            Intent it = new Intent(this, Tela_MeusEnderecos.class);
            startActivity(it);

        } else if (id == R.id.nav_slideshow) {
            Intent it = new Intent(this, Tela_Historico.class);
            startActivity(it);

        } else if (id == R.id.nav_manage) {
            Intent it = new Intent(this, Tela_Configuracao.class);
            startActivity(it);

        } else if (id == R.id.nav_send) {
            try {

                dataBase = new DataBase(this);
                conn = dataBase.getWritableDatabase();

                ScriptSQL scriptSQL = new ScriptSQL(conn);
                scriptSQL.logof();

                Intent it = new Intent(this, LoginActivity.class);
                startActivity(it);
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