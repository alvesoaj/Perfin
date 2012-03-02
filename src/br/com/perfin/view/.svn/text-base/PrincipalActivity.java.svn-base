package br.com.perfin.view;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.perfin.R;
import br.com.perfin.conexao.Conexao;
import br.com.perfin.model.Cartao;
import br.com.perfin.model.Cheque;
import br.com.perfin.service.CartaoService;
import br.com.perfin.service.ChequeService;
import br.com.perfin.util.Constantes;

public class PrincipalActivity extends Activity{
	
	private Button btextrato;
	private Button btlistar;
	private Button btliquidacao;
	private Button btrenda;
	private Button btgasto;
	private Conexao con;
	CartaoService cartaoService;
	ChequeService chequeService;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        
        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        btextrato = (Button) findViewById(R.id.extrato_button);
        btlistar = (Button) findViewById(R.id.read_entry_button);
        btliquidacao = (Button) findViewById(R.id.liquidar_button);
        btrenda = (Button) findViewById(R.id.insert_entry_renda_button);
        btgasto = (Button) findViewById(R.id.insert_entry_button);
        
        cartaoService = new CartaoService(conexao);
        chequeService = new ChequeService(conexao);
        
        carregarAgendamentos();
        
        iniciar();
        
    }
    
    public void carregarAgendamentos(){
    	final List<Cartao> cartoes = cartaoService.listarNP(new Date(), Constantes.STATUS_NOTPAID);
    	List<Cheque> cheques = chequeService.listarNP(new Date(), Constantes.STATUS_NOTPAID);
    	
    	if(cheques.size() > 0){
    		for (int i = 0; i < cheques.size(); i++) {
				cheques.get(i).setStatus(Constantes.STATUS_PAID);
				chequeService.salvar(cheques.get(i));
			}
    	}
    	
    	if(cartoes.size() > 0){
    			//Perguntar!
    			final int i = 0;
    			
    			new AlertDialog.Builder(PrincipalActivity.this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("Confirmação!")
		        .setMessage("Você confirma o pagamento do gasto com Cartao no valor " +
		        		"de R$"+cartoes.get(i).getEntrada().getValor()+", Parcela nº "+cartoes.get(i).getParcela()+"! Agendado para o dia" +
		        				" "+cartoes.get(i).getDataFaturamento().getDate()+"/"+(cartoes.get(i).getDataFaturamento().getMonth()+1)+
		        				"/"+(cartoes.get(i).getDataFaturamento().getYear()+1900)+"?")
		        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	cartoes.get(i).setStatus(Constantes.STATUS_PAID);
						cartaoService.salvar(cartoes.get(i));
						carregarAgendamentos();
		            }	
		        })
		        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						List<Cartao> cartoes2 = cartaoService.listar(0, null, Constantes.STATUS_NOTPAID, cartoes.get(i).getEntrada().getId());
						if(cartoes2.size() > 0){
							for (int j = 0; j < cartoes2.size(); j++) {
								cartoes2.get(j).setDataFaturamento(null);
								cartaoService.salvar(cartoes2.get(j));
							}
							carregarAgendamentos();
						}
					}
				})
		        .show();
			}
    	
    }
    
    public void iniciar(){
    	
    	btextrato.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {	
				
				showDialog(Constantes.EXTRATO);
				
			}
		});
    	
    	btlistar.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {	
				Intent intent = new Intent(PrincipalActivity.this, ListTab.class);
				startActivity (intent);
			}
		});
    	
    	btliquidacao.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {	
				Intent intent = new Intent(PrincipalActivity.this, Liquidacao.class);
				startActivity (intent);
			}
		});
    	
    	btrenda.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {	
				Intent intent = new Intent(PrincipalActivity.this, RendaActivity.class);
				startActivity (intent);
		    }
		});
    	
    	btgasto.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				
				showDialog(Constantes.GASTO);
				
		    }
		});
    	
    }
    
    @Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		
    	switch (id) {
		case Constantes.GASTO:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Selecione o Meio de Pagamento:");
	        builder.setItems(Constantes.G_TYPE, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int item) {
	            	Intent intent;
	            	switch (item) {
		    			case Constantes.G_CARTAO:
		    				intent = new Intent(PrincipalActivity.this, CartaoActivity.class);
		    				startActivity(intent);
		    				dialog.dismiss();
		    				break;
		    			case Constantes.G_CHEQUE:
		    				intent = new Intent(PrincipalActivity.this, ChequeActivity.class);
		    				startActivity(intent);
		    				dialog.dismiss();
		    				break;
		    			case Constantes.G_DINHEIRO:
		    				intent = new Intent(PrincipalActivity.this, DinheiroActivity.class);
		    				startActivity(intent);
		    				dialog.dismiss();
		    				break;
		    			default:
		    				break;
	    		}
	            }
	        });
	        final AlertDialog alert = builder.create();
	        alert.show();
			break;
		case Constantes.EXTRATO:
			AlertDialog.Builder builde = new AlertDialog.Builder(this);
	        builde.setTitle("Selecione o Mês do Extrato:");
	        builde.setItems(Constantes.MESES, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int item) {
	            	
	            	GregorianCalendar calendar = new GregorianCalendar();
	            	calendar.set(Calendar.MONTH, item);
            		
	            	Date hoje = new Date();
	            	int mes = hoje.getMonth();
	            	
	            	Intent intent = new Intent(getApplicationContext(), ListAllActivity.class);
	            	
	            	if(item > mes){
	            		calendar.set(Calendar.YEAR, hoje.getYear()-1);
	            		
	            		intent.putExtra("Data_I", String.valueOf(01)+"/"+String.valueOf(item+1)+"/"+String.valueOf(hoje.getYear()+1900-1));
	            		intent.putExtra("Data_F", String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(item+1)+"/"+String.valueOf(hoje.getYear()+1900-1));
	            	}else{
	            		if(item < mes){
	            			calendar.set(Calendar.YEAR, hoje.getYear());
		            		
		            		intent.putExtra("Data_I", String.valueOf(01)+"/"+String.valueOf(item+1)+"/"+String.valueOf(hoje.getYear()+1900));
		            		intent.putExtra("Data_F", String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(item+1)+"/"+String.valueOf(hoje.getYear()+1900));
	            		}else{
	            			calendar.set(Calendar.YEAR, hoje.getYear());
		            		
		            		intent.putExtra("Data_I", String.valueOf(01)+"/"+String.valueOf(item+1)+"/"+String.valueOf(hoje.getYear()+1900));
		            		intent.putExtra("Data_F", String.valueOf(hoje.getDate())+"/"+String.valueOf(item+1)+"/"+String.valueOf(hoje.getYear()+1900));
	            		}
	            	}
	            	
	            	startActivity(intent);
	            	
	            }
	        });
	        final AlertDialog alrt = builde.create();
	        alrt.show();
			break;
		default:
			break;
		}
    	
		return super.onCreateDialog(id);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		con.getConexao().close();
	}
    
    
    
}
