package br.com.perfin.view;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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

public class LiquidarChequeActivity extends Activity{
	
	Conexao con;
	EntradaService entradaService;
	ChequeService chequeService;
	List<EntradaJoinCheque> entradas;
	TextView textview;
	ListView lv;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        lv = new ListView(this);
        setContentView(lv);
        registerForContextMenu(lv);
        
        entradaService = new EntradaService(conexao);
        chequeService = new ChequeService(conexao);
        
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
		entradas = entradaService.listar_cheque(Constantes.STATUS_NOTPAID, null, null);
        
        if(entradas != null && entradas.size() > 0){
        	lv.setAdapter(new EntradaJoinChequeAdapter(this, entradas));
        	setContentView(lv);
        }else{
        	textview = new TextView(this);
            textview.setText("Nenhum Cheque com Pendência de Pagamento...");
            setContentView(textview);
        }
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.context_menu, menu);
	  
	  menu.removeItem(R.id.editar);
	  menu.removeItem(R.id.excluir);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  switch (item.getItemId()) {
	  case R.id.liquidar:
	    liquidar(info.position);
	    return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
	
	public void liquidar(int position){
		final int pos = position;
		
		new AlertDialog.Builder(LiquidarChequeActivity.this)
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
            	
            	
            	DatePickerDialog dp = new DatePickerDialog(LiquidarChequeActivity.this,new DatePickerDialog.OnDateSetListener() {
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
                        	Toast.makeText(LiquidarChequeActivity.this, "Operação Realizada Com Sucesso!!!", Toast.LENGTH_SHORT).show();
                    	}else{
            				new AlertDialog.Builder(LiquidarChequeActivity.this)
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
	
}
