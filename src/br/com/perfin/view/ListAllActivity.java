package br.com.perfin.view;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.perfin.R;
import br.com.perfin.adapter.EntradaALLAdapter;
import br.com.perfin.conexao.Conexao;
import br.com.perfin.model.Cartao;
import br.com.perfin.model.Cheque;
import br.com.perfin.model.EntradaJoinAll;
import br.com.perfin.service.CartaoService;
import br.com.perfin.service.ChequeService;
import br.com.perfin.service.EntradaService;
import br.com.perfin.util.Constantes;
import br.com.perfin.util.Util;

public class ListAllActivity extends Activity{

	private Conexao con;
	private EntradaService entradaService;
	private CartaoService cartaoService;
	private ChequeService chequeService;
	private List<EntradaJoinAll> entradas;
	private TextView textview;
	private ListView lv;
	private Date data_ini = null;
	private Date data_fim = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        lv = new ListView(this);
        setContentView(lv);
        
        cartaoService = new CartaoService(conexao);
        entradaService = new EntradaService(conexao);
        chequeService = new ChequeService(conexao);
        
        try {
			data_ini = Util.stringtodate(getIntent().getStringExtra("Data_I"));
		} catch (ParseException e) {
			data_ini = null;
		}
		
		try {
			data_fim = Util.stringtodate(getIntent().getStringExtra("Data_F"));
		} catch (ParseException e) {
			data_fim = null;
		}
        
		if(data_ini == null){
			data_ini = new Date();
			data_ini.setDate(01);
		}
		if(data_fim == null){
			data_fim = new Date();
		}
		
		Toast.makeText(getApplicationContext(), "Data Inicial: "+data_ini+"; Data Final: "+data_fim, Toast.LENGTH_LONG).show();
		
        atualizalista();
        inicia();
        
    }
	
	public void inicia(){
				
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				
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
				
				if(!entradas.get(position).getEntrada().getDescricao().equals(""))
					tvDesc.setText("Descrição: "+entradas.get(position).getEntrada().getDescricao());
				else tvDesc.setText("Descrição: Não Há.");
				tvValor.setText("Valor: R$ "+entradas.get(position).getEntrada().getValor()+"");
				tvCateg.setText("Tipo: "+entradas.get(position).getEntrada().getCategoria().getDescricao());
				
				
				if(entradas.get(position).getType().equals("Cartão")){
					Cartao cartao = cartaoService.buscarPorID(entradas.get(position).getId());
					dt = cartao.getDataFaturamento();
					if(dt == null){
						tvDataFat.setText("");
					}else{
						Date hoje = new Date();
						if(!hoje.before(dt))
							tvDataFat.setText("Pagamento efetuado em: "+String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
						else tvDataFat.setText("Pagamento agendado para: "+String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
					}
					
					tvValor.setText("Valor: R$ "+entradas.get(position).getEntrada().getValor()+" - Parcela: "+cartao.getParcela());
					
				}
				if(entradas.get(position).getType().equals("Cheque")){
					Cheque cheque = chequeService.buscarPorID(entradas.get(position).getId());
					dt = cheque.getData_pg();
					if(dt == null){
						tvDataFat.setText("");
					}else{
						tvDataFat.setText("Data de Compensação: "+String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
					}
				}
			
				menuDialog.show();
			}
		});
	}
	
	public void atualizalista(){
		entradas = entradaService.listar_all(Constantes.STATUS_PAID, Constantes.STATUS_PAID, data_ini, data_fim);
        
        if(entradas != null && entradas.size() > 0){
        	lv.setAdapter(new EntradaALLAdapter(this, entradas));
        	setContentView(lv);
        }else{
        	textview = new TextView(this);
            textview.setText("Nenhum Gasto ou Renda no Periodo especificado...");
            setContentView(textview);
        }
	}
	
}
