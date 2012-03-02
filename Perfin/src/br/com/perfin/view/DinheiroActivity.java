package br.com.perfin.view;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
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
import br.com.perfin.model.Dinheiro;
import br.com.perfin.model.Entrada;
import br.com.perfin.service.CategoriaService;
import br.com.perfin.service.DinheiroService;
import br.com.perfin.service.EntradaService;
import br.com.perfin.util.Constantes;
import br.com.perfin.util.Util;

public class DinheiroActivity extends Activity{
	
	private Date data;
	private Conexao con;
	private EditText ETdata;
	private EditText ETvalor;
	private EditText ETdescricao;
	private Spinner SPNSelectCategoria;
	private Button btAdiciona;
	private DinheiroService dinheiroService;
	private EntradaService entradaService;
	private CategoriaService categoriaService;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        setContentView(R.layout.entry_cash);
        
        ETdata = (EditText) findViewById(R.id.date_input);
        ETvalor = (EditText) findViewById(R.id.value_input);
        ETdescricao = (EditText) findViewById(R.id.description_input);
        SPNSelectCategoria = (Spinner) findViewById(R.id.spinner_select_categoria);
        btAdiciona = (Button) findViewById(R.id.insert_entry_button);
        
        dinheiroService = new DinheiroService(conexao);
        entradaService = new EntradaService(conexao);
        categoriaService = new CategoriaService(conexao);
        
        inicia();
    }
    
    private void inicia(){
    	Util.setTypeListToSpinner(SPNSelectCategoria, this, categoriaService.listar("", "Salário"));
    	
    	ETdata.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                  
            final Calendar c = Calendar.getInstance();        
            int y = c.get(Calendar.YEAR);        
            int m = c.get(Calendar.MONTH);        
            int d = c.get(Calendar.DAY_OF_MONTH); 

            DatePickerDialog dp = new DatePickerDialog(DinheiroActivity.this,new DatePickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                   String erg = "";
                   erg = String.valueOf(dayOfMonth);                      
                   erg += "." + String.valueOf(monthOfYear+1);                    
                   erg += "." + year;  
                   ETdata.setText(erg);
                   data = new Date(year-1900, monthOfYear, dayOfMonth);
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
    	if(!ETvalor.getText().toString().equals("")){
			
    		if(data == null){
				data = new Date();
			}
			
			Entrada entrada = new Entrada(data, Float.parseFloat(ETvalor.getText().toString()), ETdescricao.getText().toString(), Constantes.STATUS_PAID, categoriaService.listar(SPNSelectCategoria.getSelectedItem().toString()).get(0));
			entrada = entradaService.salvar(entrada);
			
			Dinheiro dinheiro = new Dinheiro(entrada);
			dinheiroService.salvar(dinheiro);
			
			Toast.makeText(getApplicationContext(), "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show();
			DinheiroActivity.this.finish();
			
		}else{
			Toast.makeText(getApplicationContext(), "Valor não pode ser vazio!", Toast.LENGTH_SHORT).show();
		}
    }
    
}
