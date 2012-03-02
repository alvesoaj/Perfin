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
import br.com.perfin.adapter.EntradaJoinCartaoAdapter;
import br.com.perfin.conexao.Conexao;
import br.com.perfin.model.Cartao;
import br.com.perfin.model.EntradaJoinCartao;
import br.com.perfin.service.CartaoService;
import br.com.perfin.service.EntradaService;
import br.com.perfin.util.Constantes;

public class ListCartaoActivity extends Activity{
	
	Conexao con;
	EntradaService entradaService;
	CartaoService cartaoService;
	List<EntradaJoinCartao> entradas;
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
        cartaoService = new CartaoService(conexao);
        
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
				TextView tvDataFat = (TextView) menuDialog.findViewById(R.id.tvData2);
				TextView tvDesc = (TextView) menuDialog.findViewById(R.id.tvDescricao);
				TextView tvValor = (TextView) menuDialog.findViewById(R.id.tvValor);
				TextView tvCateg = (TextView) menuDialog.findViewById(R.id.tvCategoria);
				
				Date dt = entradas.get(position).getEntrada().getData();
				tvData.setText("Data de cadastro: "+String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
				
				dt = entradas.get(position).getCartao().getDataFaturamento();
				if(dt == null){
					tvDataFat.setText("");
				}else{
					Date hoje = new Date();
					if(!hoje.before(dt))
						tvDataFat.setText("Pagamento efetuado em: "+String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
					else tvDataFat.setText("Pagamento agendado para: "+String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
				}
				
				if(!entradas.get(position).getEntrada().getDescricao().equals(""))
					tvDesc.setText("Descrição: "+entradas.get(position).getEntrada().getDescricao());
				else tvDesc.setText("Descrição: Não Há.");
				tvValor.setText("Valor: R$ "+entradas.get(position).getEntrada().getValor()+" - Parcela: "+entradas.get(position).getCartao().getParcela());
				tvCateg.setText("Tipo: "+entradas.get(position).getEntrada().getCategoria().getDescricao());
				
				menuDialog.show();
			}
		});
	}
	
	public void atualizarlista(){
		entradas = entradaService.listar_cartao(status, data_ini, data_fim);
        
        if(entradas != null && entradas.size() > 0){
        	lv.setAdapter(new EntradaJoinCartaoAdapter(this, entradas));
        	setContentView(lv);
        }else{
        	textview = new TextView(this);
            textview.setText("Gastos com Cartão...");
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
	    	Intent i = new Intent(this, CartaoActivity.class);
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
	  if(entradas.get(info.position).getCartao().getStatus().equals(Constantes.STATUS_PAID)){
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
		Intent intent = new Intent(ListCartaoActivity.this, CartaoActivity.class);
		List<Cartao> cartoes = cartaoService.listar(0, null, null, entradas.get(position).getEntrada().getId());
		intent.putExtra("CartaoID", cartoes.get(cartoes.size()-1).getId());
		startActivityForResult(intent, 15);
	}
	
	public void excluir(int position){
		final int pos = position;
		
		String msg = "";
		
		final List<Cartao> cartoes = cartaoService.listar(0, null, null, entradas.get(pos).getCartao().getEntrada().getId());
		if(cartoes.size()>1){
			msg = "Você está prestes a excluir todas as "+cartoes.get(cartoes.size()-1).getParcela()+" parcelas de R$"+
					entradas.get(pos).getEntrada().getValor()+" referentes à este gasto." +
					" Tem certeza sobre esta operação?";
		}else{
			msg = "Tem certeza que quer excluir este item? Gasto com Cartao no valor de R$"+entradas.get(pos).getEntrada().getValor();
		}
		
		new AlertDialog.Builder(ListCartaoActivity.this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Exclusão!")
        .setMessage(msg)
        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            	for (int i = 0; i < cartoes.size(); i++) {
					cartaoService.excluir(cartoes.get(i).getId());
				}
            	entradaService.excluir(entradas.get(pos).getEntrada().getId());
            	atualizarlista();
            	Toast.makeText(ListCartaoActivity.this, "Operação Realizada Com Sucesso!!!", Toast.LENGTH_SHORT).show();
            }

        })
        .setNegativeButton("Não", null)
        .show();
        
	}
	
	public void liquidar(int position){
		final int pos = position;
		final List<Cartao> cartoes = cartaoService.listar(0, entradas.get(pos).getEntrada().getData(), null, entradas.get(pos).getEntrada().getId());
		
		if(entradas.get(pos).getCartao().getParcela() > 1){
			if(cartoes.size() > 0){
				if(cartoes.get(cartoes.size()-1).getParcela() >= entradas.get(pos).getCartao().getParcela()-1){
					
					new AlertDialog.Builder(ListCartaoActivity.this)
			        .setIcon(android.R.drawable.ic_dialog_alert)
			        .setTitle("Liquidação!")
			        .setMessage("Você está prestes a informar a confirmação ou agendamento de pagamento do gasto com Cartao no valor " +
			        		"de R$"+entradas.get(pos).getEntrada().getValor()+", Parcela nº "+entradas.get(pos).getCartao().getParcela()+"!")
			        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			            	
			            	Date dt = entradas.get(pos).getCartao().getDataFaturamento();
			            	if(dt == null){
			            		dt = new Date();
			            	}
			            	int y = dt.getYear()+1900;
			        		int m = dt.getMonth();
			        		int d = dt.getDate();
			            	
			        		DatePickerDialog dp = new DatePickerDialog(ListCartaoActivity.this,new DatePickerDialog.OnDateSetListener() {
			                    @Override
			                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			                    	
			                    	Date dt_anterior = null;
									Date dt_posterior = null;
									if(cartoes.get(cartoes.size()-1).getParcela() == entradas.get(pos).getCartao().getParcela()-1){
										dt_anterior = cartoes.get(cartoes.size()-1).getDataFaturamento();
									}else{
										dt_anterior = cartoes.get(entradas.get(pos).getCartao().getParcela()-2).getDataFaturamento();
										if(!(cartoes.get(cartoes.size()-1).getParcela() == entradas.get(pos).getCartao().getParcela())){
											dt_posterior = cartoes.get(entradas.get(pos).getCartao().getParcela()).getDataFaturamento();
										}
										
									}
									
			                    	Date datafaturamento = new Date(year-1900, monthOfYear, dayOfMonth);
			                    	if(!(datafaturamento.before(entradas.get(pos).getEntrada().getData()))){
			                    		if(!(datafaturamento.before(dt_anterior))){
			                    			if(dt_posterior != null){
			                    				if((datafaturamento.after(dt_posterior))){
			                    					showDialog(Constantes.LIQUIDACAO_DATA_INVALIDA_3);
			                    					return;
			                    				}
			                    			}
			                    			Cartao cartao = entradas.get(pos).getCartao();
				                        	cartao.setDataFaturamento(datafaturamento);
				                        	
				                        	Date hoje = new Date();
				                        	if(!hoje.before(cartao.getDataFaturamento()))
				                        		cartao.setStatus(Constantes.STATUS_PAID);
				                        	
				                        	cartaoService.salvar(cartao);
				                        	atualizarlista();
				                        	Toast.makeText(ListCartaoActivity.this, "Operação Realizada Com Sucesso!!!", Toast.LENGTH_SHORT).show();
			                    		}else{
			                    			showDialog(Constantes.LIQUIDACAO_DATA_INVALIDA_2);
			                    		}
			                    	}else{
			                    		showDialog(Constantes.LIQUIDACAO_DATA_INVALIDA);                    		
			                    	}
			                    	
			                    }

			                  }, y, m, d);
			                  dp.setMessage("Escolha a Data de Pagamento da Fatura do Cartão:");
			                  dp.show();
			            	
			            }

			        })
			        .setNegativeButton("Carcelar", null)
			        .show();
				}else{
					showDialog(Constantes.LIQUIDACAO_PARCELA_PENDENTE);
				}
			}else{
				showDialog(Constantes.LIQUIDACAO_PARCELA_PENDENTE);
			}
		}else{
			new AlertDialog.Builder(ListCartaoActivity.this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Liquidação!")
	        .setMessage("Você está prestes a informar a confirmação ou agendamento de pagamento do gasto com Cartao no valor " +
	        		"de R$"+entradas.get(pos).getEntrada().getValor()+", Parcela nº "+entradas.get(pos).getCartao().getParcela()+"!")
	        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	
	            	Date dt = entradas.get(pos).getCartao().getDataFaturamento();
	            	if(dt == null){
	            		dt = new Date();
	            	}
	            	int y = dt.getYear()+1900;
	        		int m = dt.getMonth();
	        		int d = dt.getDate();
	            	
	            	
	            	DatePickerDialog dp = new DatePickerDialog(ListCartaoActivity.this,new DatePickerDialog.OnDateSetListener() {
	                    @Override
	                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	                    	Date dt_posterior = null;
	    					if(cartoes.size() > 1){
	    						dt_posterior = cartoes.get(entradas.get(pos).getCartao().getParcela()).getDataFaturamento();
	    					}
	    					
	    	            	Date datafaturamento = new Date(year-1900, monthOfYear, dayOfMonth);
	                    	if(!datafaturamento.before(entradas.get(pos).getEntrada().getData())){
	                    		
	                    		if(dt_posterior != null){
                    				if((datafaturamento.after(dt_posterior))){
                    					showDialog(Constantes.LIQUIDACAO_DATA_INVALIDA_3);
                    					return;
                    				}
                    			}
	                    		
	                    		Cartao cartao = entradas.get(pos).getCartao();
		                        cartao.setDataFaturamento(new Date(year-1900, monthOfYear, dayOfMonth));
		                        
		                        Date hoje = new Date();
		                        if(!hoje.before(cartao.getDataFaturamento()))
		                        	cartao.setStatus(Constantes.STATUS_PAID);
		                        
		                        cartaoService.salvar(cartao);
		                        atualizarlista();
		                        Toast.makeText(ListCartaoActivity.this, "Operação Realizada Com Sucesso!!!", Toast.LENGTH_SHORT).show();
	                    		
	                    	}else{
	                    		showDialog(Constantes.LIQUIDACAO_DATA_INVALIDA);                    		
	                    	}
	                    	
	                    }

	                  }, y, m, d);
	                  dp.setMessage("Escolha a Data de Pagamento da Fatura do Cartão:");
	                  dp.show();
	            	
	            }

	        })
	        .setNegativeButton("Carcelar", null)
	        .show();
		}
		
		
	}

	public void filtrardata(){
		final Calendar c = Calendar.getInstance();        
        final int y = c.get(Calendar.YEAR);        
        final int m = c.get(Calendar.MONTH);        
        final int d = c.get(Calendar.DAY_OF_MONTH);
        
		DatePickerDialog dp = new DatePickerDialog(ListCartaoActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                 data_ini = new Date(year-1900, monthOfYear, dayOfMonth);
                 
                 DatePickerDialog dp2 = new DatePickerDialog(ListCartaoActivity.this,new DatePickerDialog.OnDateSetListener() {
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

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
	    switch(id) {
	    case Constantes.LIQUIDACAO_DATA_INVALIDA:
	    	new AlertDialog.Builder(ListCartaoActivity.this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("ERRO!")
	        .setMessage("A operação não pôde ser concluída! A data escolhida para o pagamento não pode ser anterior à data de cadastro da divida.")
	        .setPositiveButton("OK", null)
	        .show();
	        break;
	    case Constantes.LIQUIDACAO_DATA_INVALIDA_2:
	    	new AlertDialog.Builder(ListCartaoActivity.this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("ERRO!")
	        .setMessage("A operação não pôde ser concluída! A data escolhida para o pagamento não pode ser anterior à data de pagamento da ultima parcela.")
	        .setPositiveButton("OK", null)
	        .show();
	    	break;
	    case Constantes.LIQUIDACAO_DATA_INVALIDA_3:
	    	new AlertDialog.Builder(ListCartaoActivity.this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("ERRO!")
	        .setMessage("A operação não pôde ser concluída! A data escolhida para o pagamento não pode ser posterior à data de pagamento da proxima parcela.")
	        .setPositiveButton("OK", null)
	        .show();
	    	break;
	    case Constantes.LIQUIDACAO_PARCELA_PENDENTE:
	    	new AlertDialog.Builder(ListCartaoActivity.this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("ERRO!")
	        .setMessage("A operação não pôde ser concluída! Existe(m) parcela(s) anterior(es) a esta que não foi(foram) paga(s)/agendada(s) ainda!")
	        .setPositiveButton("OK", null)
	        .show();
	    	break;
	    default:
	        dialog = null;
	    }
	    return dialog;
	}
	
}
