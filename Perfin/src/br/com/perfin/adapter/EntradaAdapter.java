package br.com.perfin.adapter;

import java.util.List;

import br.com.perfin.R;
import br.com.perfin.model.Entrada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EntradaAdapter extends BaseAdapter{

	private Context context;
	private List<Entrada> lista;
			
	public EntradaAdapter(Context context, List<Entrada> lista) {
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
		Entrada entrada = lista.get(arg0);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.entry_list, null);
		
		TextView entryId = (TextView) view.findViewById(R.id.entry_Id);
		TextView entryDescription = (TextView) view.findViewById(R.id.entry_description);
		
		entryId.setText(String.valueOf(entrada.getId()));
		entryDescription.setText(entrada.getDescricao()+" R$ "+String.valueOf(entrada.getValor()));
		
		return view;
	}
}
