package br.com.perfin.adapter;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.perfin.R;
import br.com.perfin.model.EntradaJoinDinheiro;

public class EntradaJoinDinheiroAdapter extends BaseAdapter{

	private Context context;
	private List<EntradaJoinDinheiro> lista;
			
	public EntradaJoinDinheiroAdapter(Context context, List<EntradaJoinDinheiro> lista) {
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
		return lista.get(arg0).getDinheiro().getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		EntradaJoinDinheiro entrada = lista.get(arg0);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.list_ccdr, null);
		
		TextView tvValor = (TextView) view.findViewById(R.id.tvValor);
		TextView tvDesc = (TextView) view.findViewById(R.id.tvDescricao);
		TextView tvData = (TextView) view.findViewById(R.id.tvData);
		
		tvValor.setText("R$ "+String.valueOf(entrada.getEntrada().getValor()));
		if(!entrada.getEntrada().getDescricao().equals(""))
			tvDesc.setText("Descrição: "+entrada.getEntrada().getDescricao());
		else tvDesc.setText("Descrição: Não Há.");
		Date dt = entrada.getEntrada().getData();
		tvData.setText(String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
		
		return view;
	}
}
