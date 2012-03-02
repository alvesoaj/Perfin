package br.com.perfin.view;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.perfin.R;
import br.com.perfin.adapter.EntradaJoinChequeAdapter;
import br.com.perfin.conexao.Conexao;
import br.com.perfin.model.Cheque;
import br.com.perfin.model.EntradaJoinCheque;
import br.com.perfin.service.ChequeService;
import br.com.perfin.service.EntradaService;
import br.com.perfin.util.Constantes;

public class ListChequeActivity extends Activity{
	
	Conexao con;
	EntradaService entradaService;
	ChequeService chequeService;
	List<EntradaJoinCheque> entradas;
	TextView textview;
	ListView lv;
	private String status;
	private Date data_ini = null;
	private Date data_fim = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        lv = new ListView(this);
        setContentView(lv);
        registerForContextMenu(lv);
        
        entradaService = new EntradaService(conexao);
        chequeService = new ChequeService(conexao);
        
        status = "";
        atualizarlista();
        inicia();
        
    }
	
	public void inicia(){
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				//detalhamento
				final Dialog menuDialog = new Dialog(v.getContext());
				
				menuDialog.setContentView(R.layout.details);
				menuDialog.setTitle("Detalhamento");
				
				TextView tvData = (TextView) menuDialog.findViewById(R.id.tvData);
				TextView tvData_pg = (TextView) menuDialog.findViewById(R.id.tvData2);
				TextView tvDesc = (TextView) menuDialog.findViewById(R.id.tvDescricao);
				TextView tvValor = (TextView) menuDialog.findViewById(R.id.tvValor);
				TextView tvCateg = (TextView) menuDialog.findViewById(R.id.tvCategoria);
				
				Date dt = entradas.get(position).getEntrada().getData();
				tvData.setText("Data de cadastro: "+String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
				
				dt = entradas.get(position).getCheque().getData_pg();
				if(dt == null){
					tvData_pg.setText("");
				}else{
					tvData_pg.setText("Data de Compensação: "+String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
				}
				
				if(!entradas.get(position).getEntrada().getDescricao().equals(""))
					tvDesc.setText("Descrição: "+entradas.get(position).getEntrada().getDescricao());
				else tvDesc.setText("Descrição: Não Há.");
				tvValor.setText("Valor: R$ "+entradas.get(position).getEntrada().getValor()+"");
				tvCateg.setText("Tipo: "+entradas.get(position).getEntrada().getCategoria().getDescricao());
				
				menuDialog.show();
			}
		});
	}
	
	public void atualizarlista(){
		entradas = entradaService.listar_cheque(status, data_ini, data_fim);
        
        if(entradas != null && entradas.size() > 0){
        	lv.setAdapter(new EntradaJoinChequeAdapter(this, entradas));
        	setContentView(lv);
        }else{
        	textview = new TextView(this);
            textview.setText("Gastos com Cheque...");
            setContentView(textview);
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.adicionar:
	    	Intent i = new Intent(this, ChequeActivity.class);
	    	startActivityForResult(i, 15);
	    	return true;
	    case R.id.listar_all:
		    status = "";
	    	atualizarlista();
	    	return true;
	    case R.id.listar_pg:
	    	status = Constantes.STATUS_PAID;
	    	atualizarlista();
		    return true;
	    case R.id.listar_npg:
	    	status = Constantes.STATUS_NOTPAID;
	    	atualizarlista();
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
	  
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	  if(entradas.get(info.position).getCheque().getStatus().equals(Constantes.STATUS_PAID)){
		  MenuItem m = (MenuItem) menu.findItem(R.id.liquidar);
		  m.setEnabled(false);
	  }
	  
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
	  case R.id.liquidar:
	    liquidar(info.position);
	    return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
	
	public void editar(int position){
		Intent intent = new Intent(ListChequeActivity.this, ChequeActivity.class);
		intent.putExtra("ChequeID", entradas.get(position).getCheque().getId());
		startActivityForResult(intent, 15);
	}
	
	public void excluir(int position){
		final int pos = position;
		
		new AlertDialog.Builder(ListChequeActivity.this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Exclusão!")
        .setMessage("Você tem certeza que quer excluir este item? Gasto com Cheque no valor de R$"+entradas.get(pos).getEntrada().getValor())
        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            	chequeService.excluir(entradas.get(pos).getCheque().getId());    
            	entradaService.excluir(entradas.get(pos).getEntrada().getId());
            	atualizarlista();
            	Toast.makeText(ListChequeActivity.this, "Operação Realizada Com Sucesso!!!", Toast.LENGTH_SHORT).show();
            }

        })
        .setNegativeButton("Não", null)
        .show();
	}
	
	public void liquidar(int position){
		final int pos = position;
		
		new AlertDialog.Builder(ListChequeActivity.this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Liquidação!")
        .setMessage("Você está prestes a informar a confirmação de gasto com Cheque no valor de R$"+entradas.get(pos).getEntrada().getValor()+"!")
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	//chequeService.excluir(entradas.get(pos).getCheque().getId());    
            	//entradaService.excluir(entradas.get(pos).getEntrada().getId());
            	
            	Date dt = entradas.get(pos).getCheque().getData_pg();
            	if(dt == null){
            		dt = new Date();
            	}
            	int y = dt.getYear()+1900;
        		int m = dt.getMonth();
        		int d = dt.getDate();
            	
            	
            	DatePickerDialog dp = new DatePickerDialog(ListChequeActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    	
                    	Date data_pg = new Date(year-1900, monthOfYear, dayOfMonth);
                    	if(!(data_pg.before(entradas.get(pos).getEntrada().getData()))){
                    		Cheque cheque = entradas.get(pos).getCheque();
                        	cheque.setData_pg(data_pg);
                        	
                        	Date hoje = new Date();
                        	if(!hoje.before(cheque.getData_pg()))
                        		cheque.setStatus(Constantes.STATUS_PAID);
                        	
                        	chequeService.salvar(cheque);
                        	atualizarlista();
                        	Toast.makeText(ListChequeActivity.this, "Operação Realizada Com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    	}else{
            				new AlertDialog.Builder(ListChequeActivity.this)
            		        .setIcon(android.R.drawable.ic_dialog_alert)
            		        .setTitle("Atenção!")
            		        .setMessage("A data escolhida para o pagamento não pode ser anterior à data de cadastro da divida.")
            		        .setPositiveButton("OK", null)
            		        .show();
            			}
                    	
                    }

                  }, y, m, d);
                  dp.setMessage("Escolha a Data de Compensação do Cheque:");
                  dp.show();
            	
            }

        })
        .setNegativeButton("Carcelar", null)
        .show();
	}
	
	public void filtrardata(){
		final Calendar c = Calendar.getInstance();        
        final int y = c.get(Calendar.YEAR);        
        final int m = c.get(Calendar.MONTH);        
        final int d = c.get(Calendar.DAY_OF_MONTH);
        
		DatePickerDialog dp = new DatePickerDialog(ListChequeActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                 data_ini = new Date(year-1900, monthOfYear, dayOfMonth);
                 
                 DatePickerDialog dp2 = new DatePickerDialog(ListChequeActivity.this,new DatePickerDialog.OnDateSetListener() {
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
