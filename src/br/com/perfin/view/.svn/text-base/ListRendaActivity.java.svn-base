package br.com.perfin.view;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.perfin.R;
import br.com.perfin.adapter.EntradaJoinRendaAdapter;
import br.com.perfin.conexao.Conexao;
import br.com.perfin.model.EntradaJoinRenda;
import br.com.perfin.service.EntradaService;
import br.com.perfin.service.RendaService;

public class ListRendaActivity extends Activity{

	Conexao con;
	EntradaService entradaService;
	RendaService rendaService;
	List<EntradaJoinRenda> entradas;
	TextView textview;
	ListView lv;
	private Date data_ini;
	private Date data_fim;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        lv = new ListView(this);
        setContentView(lv);
        registerForContextMenu(lv);
        
        rendaService = new RendaService(conexao);
        entradaService = new EntradaService(conexao);
        
        data_ini = null;
        data_fim = null;
        atualizarlista();
    }

	public void atualizarlista(){
		entradas = entradaService.listar_renda(data_ini, data_fim);
        
        if(entradas != null && entradas.size() > 0){
        	lv.setAdapter(new EntradaJoinRendaAdapter(this, entradas));
        	setContentView(lv);
        }else{
        	textview = new TextView(this);
            textview.setText("Proventos, Renda...");
            setContentView(textview);
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    menu.removeItem(R.id.filtrar);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.adicionar:
	    	Intent i = new Intent(this, RendaActivity.class);
	    	startActivityForResult(i, 15);
	    	return true;
	    case R.id.listar_all_data:
	    	data_fim = null;
	    	data_ini = null;
	    	atualizarlista();
	    	return true;
	    case R.id.listar_data:
	    	filtrardata();
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.context_menu, menu);
	  
	  menu.removeItem(R.id.liquidar);
	  
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  switch (item.getItemId()) {
	  case R.id.editar:
		editar(info.position);
		return true;
	  case R.id.excluir:
		excluir(info.position);
	    return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
	
	public void editar(int position){
		Intent intent = new Intent(ListRendaActivity.this, RendaActivity.class);
		intent.putExtra("RendaID", entradas.get(position).getRenda().getId());
		startActivityForResult(intent, 15);
	}
	
	public void excluir(int poss){
		final int pos = poss;
		
		new AlertDialog.Builder(ListRendaActivity.this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Exclusão!")
        .setMessage("Você quer excluir este item? Entrada de renda no valor de R$"+entradas.get(pos).getEntrada().getValor())
        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            	rendaService.excluir(entradas.get(pos).getRenda().getId());    
            	entradaService.excluir(entradas.get(pos).getEntrada().getId());
            	atualizarlista();
            	Toast.makeText(ListRendaActivity.this, "Operação Realizada Com Sucesso!!!", Toast.LENGTH_SHORT).show();
            }

        })
        .setNegativeButton("Não", null)
        .show();
	}
	
	public void filtrardata(){
		final Calendar c = Calendar.getInstance();        
        final int y = c.get(Calendar.YEAR);        
        final int m = c.get(Calendar.MONTH);        
        final int d = c.get(Calendar.DAY_OF_MONTH);
        
		DatePickerDialog dp = new DatePickerDialog(ListRendaActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                 data_ini = new Date(year-1900, monthOfYear, dayOfMonth);
                 
                 DatePickerDialog dp2 = new DatePickerDialog(ListRendaActivity.this,new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                          data_fim = new Date(year-1900, monthOfYear, dayOfMonth);
                          atualizarlista();
                     }
                     
                   }, y, m, d);
                   dp2.setMessage("Escolha a Data Final:");
                   dp2.show();
                 
                 atualizarlista();
            }
            
            

          }, y, m, d);
		  dp.setMessage("Escolha a Data Inicial:");
          dp.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK){
			atualizarlista();
		}
		
	}
	
}
