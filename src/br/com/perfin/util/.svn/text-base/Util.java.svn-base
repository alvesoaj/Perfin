package br.com.perfin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import br.com.perfin.R;
import br.com.perfin.model.Categoria;

public class Util {
	public static void setTypeListToSpinner(Spinner selType, Context context){
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selType.setAdapter(adapter);
    }
	
	public static void setTypeListToSpinner(Spinner selType, Context context, List<Categoria> categorias){
		List<CharSequence> list = new ArrayList<CharSequence>();
		
		for (int i = 0; i < categorias.size(); i++) {
			list.add(categorias.get(i).getDescricao());
		}
		
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selType.setAdapter(adapter);
	}
	
	public static Date stringtodate(String data) throws ParseException{
		if (data == null || data.equals(""))
			return null;
		
		Date date = null;
		
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = (java.util.Date)formatter.parse(data);
		} catch (ParseException e) {
			throw e;
		  }
		return date;
	}
	
	public static Boolean VerificaDataEntrada(Date dataEntrada){
		if(dataEntrada != null){
			Date hoje = new Date();
			if(dataEntrada.after(hoje)){
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
		
		
	}
	
}
