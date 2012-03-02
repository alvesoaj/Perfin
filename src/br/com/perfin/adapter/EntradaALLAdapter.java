package br.com.perfin.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.perfin.R;
import br.com.perfin.model.EntradaJoinAll;

public class EntradaALLAdapter extends BaseAdapter{

	private Context context;
	private List<EntradaJoinAll> lista;
			
	public EntradaALLAdapter(Context context, List<EntradaJoinAll> lista) {
		super();
		this.context = context;
		this.lista = lista;
	}

	@Override
	public int getCount() {		
		return lista.size();
	}

	@Override
	public Object getItem(int arg0) {		
		return lista.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return lista.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		EntradaJoinAll entrada = lista.get(arg0);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.list_all, null);
		
		TextView tvValor = (TextView) view.findViewById(R.id.tvValor);
		TextView tvDesc = (TextView) view.findViewById(R.id.tvDescricao);
		TextView tvTipo = (TextView) view.findViewById(R.id.tvTipo);
		TextView tvData = (TextView) view.findViewById(R.id.tvData);
		
		if(entrada.getType().equals("Renda")){
			tvValor.setTextColor(Color.GREEN);
			tvDesc.setTextColor(Color.GREEN);
			tvTipo.setTextColor(Color.GREEN);
			tvData.setTextColor(Color.GREEN);
		}else{
			tvValor.setTextColor(Color.RED);
			tvDesc.setTextColor(Color.RED);
			tvTipo.setTextColor(Color.RED);
			tvData.setTextColor(Color.RED);
		}
		
		tvValor.setText("R$ "+String.valueOf(entrada.getEntrada().getValor()));
		if(!entrada.getEntrada().getDescricao().equals(""))
			tvDesc.setText("Descrição: "+entrada.getEntrada().getDescricao());
		else tvDesc.setText("Descrição: Não Há.");
		tvTipo.setText(entrada.getType());
		Date dt = entrada.getData();
		tvData.setText(String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
		
		return view;
	}
}
