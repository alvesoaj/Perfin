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
import android.widget.Toast;
import br.com.perfin.R;
import br.com.perfin.conexao.Conexao;
import br.com.perfin.model.Entrada;
import br.com.perfin.model.Renda;
import br.com.perfin.service.CategoriaService;
import br.com.perfin.service.EntradaService;
import br.com.perfin.service.RendaService;
import br.com.perfin.util.Util;

public class RendaActivity extends Activity{
	
	private Date data;
	private Conexao con;
	private EditText ETdata;
	private EditText ETvalor;
	private EditText ETdescricao;
	private Button btAdiciona;
	private RendaService rendaService;
	private EntradaService entradaService;
	private CategoriaService categoriaService;
	Renda renda = new Renda();
	Entrada entrada = new Entrada();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        setContentView(R.layout.entry_renda);
        
        ETdata = (EditText) findViewById(R.id.date_input);
        ETvalor = (EditText) findViewById(R.id.value_input);
        ETdescricao = (EditText) findViewById(R.id.description_input);
        btAdiciona = (Button) findViewById(R.id.insert_entry_button);
        
        rendaService = new RendaService(conexao);
        entradaService = new EntradaService(conexao);
        categoriaService = new CategoriaService(conexao);
        
        int id = getIntent().getIntExtra("RendaID",0);
        if(id != 0){
        	carregar(id);
        }
        
        inicia();
    }
    
    public void carregar(int id){
    	renda = rendaService.buscarPorID(id);
    	
    	Date dt = renda.getEntrada().getData();
		ETdata.setText(String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
		ETvalor.setText(renda.getEntrada().getValor()+"");
		if(!renda.getEntrada().getDescricao().equals(""))
			ETdescricao.setText(renda.getEntrada().getDescricao());
		else ETdescricao.setText("");
		
    }
    
    private void inicia(){
    	ETdata.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                  
            final Calendar c = Calendar.getInstance();        
            int y = c.get(Calendar.YEAR);        
            int m = c.get(Calendar.MONTH);        
            int d = c.get(Calendar.DAY_OF_MONTH); 

            DatePickerDialog dp = new DatePickerDialog(RendaActivity.this,new DatePickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                   String erg = "";
                   erg = String.valueOf(dayOfMonth);                      
                   erg += "/" + String.valueOf(monthOfYear+1);                    
                   erg += "/" + year;
                   ETdata.setText(erg);
                   data = new Date(year-1900, monthOfYear, dayOfMonth);
                   //
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
				if(data == null){
					data = new Date();
				}
				
				entrada.setData(data);
				entrada.setValor(Float.parseFloat(ETvalor.getText().toString()));
				entrada.setDescricao(ETdescricao.getText().toString());
				entrada.setCategoria(categoriaService.listar("Salário").get(0));
				entrada = entradaService.salvar(entrada);
	    		
				renda.setEntrada(entrada);
				rendaService.salvar(renda);
				
				Toast.makeText(getApplicationContext(), "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				RendaActivity.this.finish();
				
			}else{
				Toast.makeText(getApplicationContext(), "Valor não pode ser vazio!", Toast.LENGTH_SHORT).show();
			}
    	}else{
    		Toast.makeText(getApplicationContext(), "Data de entrada não pode ser uma data futura!", Toast.LENGTH_LONG).show();
    	}
    }
    
}
