package br.com.perfin.view;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.perfin.R;
import br.com.perfin.conexao.Conexao;
import br.com.perfin.model.Categoria;
import br.com.perfin.model.Cheque;
import br.com.perfin.model.Entrada;
import br.com.perfin.service.CategoriaService;
import br.com.perfin.service.ChequeService;
import br.com.perfin.service.EntradaService;
import br.com.perfin.util.Constantes;
import br.com.perfin.util.Util;

public class ChequeActivity extends Activity{
	
	private Date data;
	private Date data_pg;
	private Conexao con;
	private EditText ETdata;
	private EditText ETvalor;
	private EditText ETdescricao;
	private Spinner SPNSelectCategoria;
	private EditText ETdata_pg;
	private Button btAdiciona;
	private ChequeService chequeService;
	private EntradaService entradaService;
	private CategoriaService categoriaService;
	private Cheque cheque = new Cheque();
	private Entrada entrada = new Entrada();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        setContentView(R.layout.entry_check);
        
        ETdata = (EditText) findViewById(R.id.date_input);
        ETvalor = (EditText) findViewById(R.id.value_input);
        ETdescricao = (EditText) findViewById(R.id.description_input);
        SPNSelectCategoria = (Spinner) findViewById(R.id.spinner_select_categoria);
        ETdata_pg = (EditText) findViewById(R.id.date_check_input);
        btAdiciona = (Button) findViewById(R.id.insert_entry_button);
        
        chequeService = new ChequeService(conexao);
        entradaService = new EntradaService(conexao);
        categoriaService = new CategoriaService(conexao);
        
        Util.setTypeListToSpinner(SPNSelectCategoria, this, categoriaService.listar("", "Salário"));
        
        int id = getIntent().getIntExtra("ChequeID",0);
        if(id != 0){
        	carregar(id);
        }
        
        inicia();
        
    }
    
    public void carregar(int id){
    	cheque = chequeService.buscarPorID(id);
    	
    	Date dt = cheque.getEntrada().getData();
		ETdata.setText(String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
		dt = cheque.getData_pg();
		if(dt != null)
			ETdata_pg.setText(String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
		ETvalor.setText(cheque.getEntrada().getValor()+"");
		if(!cheque.getEntrada().getDescricao().equals(""))
			ETdescricao.setText(cheque.getEntrada().getDescricao());
		else ETdescricao.setText("");
		List<Categoria> cats = categoriaService.listar("", "Salário");
		int i = 0;
		for (i = 0; i < cats.size(); i++) {
			if(cats.get(i).getDescricao().equals(cheque.getEntrada().getCategoria().getDescricao())){
				SPNSelectCategoria.setSelection(i);
				break;
			}
		}
		
		List<Cheque> cheques = chequeService.listar(null, Constantes.STATUS_PAID, entrada.getId());
		if(cheques.size() > 0){
			ETdata.setClickable(false);
			ETdata.setEnabled(false);
			ETdata_pg.setClickable(false);
			ETdata_pg.setEnabled(false);
			
		}
		
		data = cheque.getEntrada().getData();
		data_pg = cheque.getData_pg();
		
    }
    
    public void inicia(){
    	
    	ETdata.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                  
            final Calendar c = Calendar.getInstance();        
            int y = c.get(Calendar.YEAR);        
            int m = c.get(Calendar.MONTH);        
            int d = c.get(Calendar.DAY_OF_MONTH); 

            DatePickerDialog dp = new DatePickerDialog(ChequeActivity.this,new DatePickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                   String erg = "";
                   erg = String.valueOf(dayOfMonth);                      
                   erg += "/" + String.valueOf(monthOfYear+1);                    
                   erg += "/" + year;  
                   ETdata.setText(erg);
                   data = new Date(year-1900, monthOfYear, dayOfMonth);
              }

            }, y, m, d);

            dp.setTitle("Data");
            dp.show();
            
            } 
        });
    	
    	ETdata_pg.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                  
            final Calendar c = Calendar.getInstance();        
            int y = c.get(Calendar.YEAR);        
            int m = c.get(Calendar.MONTH);        
            int d = c.get(Calendar.DAY_OF_MONTH); 

            DatePickerDialog dp = new DatePickerDialog(ChequeActivity.this,new DatePickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                   String erg = "";
                   erg = String.valueOf(dayOfMonth);                      
                   erg += "." + String.valueOf(monthOfYear+1);                    
                   erg += "." + year;  
                   ETdata_pg.setText(erg);
                   data_pg = new Date(year-1900, monthOfYear, dayOfMonth);
              }

            }, y, m, d);

            dp.setTitle("Data");
            dp.show();
            
            } 
        });
    	
    	btAdiciona.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {	
				salvar();
			}
		});
    	
    }
    
    private void salvar(){
	    if(Util.VerificaDataEntrada(data)){
	    	
	    	if(!ETvalor.getText().toString().equals("")){
	    		if(!ETdata_pg.getText().toString().equals("")){
	    			if(data == null){
	    				data = new Date();
	    			}
	        		
	    			if(!(data.after(data_pg))){
	    				entrada.setData(data);
	        			entrada.setValor(Float.parseFloat(ETvalor.getText().toString()));
	        			entrada.setDescricao(ETdescricao.getText().toString());
	        			entrada.setCategoria(categoriaService.listar(SPNSelectCategoria.getSelectedItem().toString()).get(0));
	        			entrada = entradaService.salvar(entrada);
	            		
	            		if(data_pg == null){
	        				data_pg = new Date();
	        				cheque.setStatus(Constantes.STATUS_NOTPAID);
	        			}else{
	        				Date hoje = new Date();
	        	        	if(!hoje.before(data_pg))
	        	        		cheque.setStatus(Constantes.STATUS_PAID);
	        	        	else cheque.setStatus(Constantes.STATUS_NOTPAID);
	        			}
	                	
	                	cheque.setData_pg(data_pg);
	                	cheque.setEntrada(entrada);
	        			chequeService.salvar(cheque);
	        			
	        			
	        			Toast.makeText(getApplicationContext(), "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show();
	        			setResult(RESULT_OK);
	        			ChequeActivity.this.finish();
	    			}else{
	    				new AlertDialog.Builder(ChequeActivity.this)
	    		        .setIcon(android.R.drawable.ic_dialog_alert)
	    		        .setTitle("Atenção!")
	    		        .setMessage("A data escolhida para o pagamento não pode ser anterior à data de cadastro da divida.")
	    		        .setPositiveButton("OK", null)
	    		        .show();
	    			}
	    			
	    		}else{
	    			Toast.makeText(getApplicationContext(), "Data de compensação não pode ser vazio!", Toast.LENGTH_LONG).show();
	    		}
				
			}else{
				Toast.makeText(getApplicationContext(), "Valor não pode ser vazio!", Toast.LENGTH_LONG).show();
			}
	    }else{
	    	Toast.makeText(getApplicationContext(), "Data de entrada não pode ser uma data futura!", Toast.LENGTH_LONG).show();
	    }
    }
    
}
