package br.com.perfin.view;

import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import br.com.perfin.R;
import br.com.perfin.adapter.EntradaAdapter;
import br.com.perfin.conexao.Conexao;
import br.com.perfin.model.Entrada;
import br.com.perfin.service.EntradaService;

public class ListaActivity extends Activity{
	
	private List<Entrada> entradas;
	private Conexao con;
	private EntradaService entradaService;
	private ListView listEntradas;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.entry_list_body);
        
        listEntradas = (ListView) findViewById(R.id.list);
    
        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        entradaService = new EntradaService(conexao);
        entradas = entradaService.listar();        
    	listEntradas.setAdapter(new EntradaAdapter(this, entradas));
    }
}
