package br.com.perfin.view;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import br.com.perfin.model.Cartao;
import br.com.perfin.model.Categoria;
import br.com.perfin.model.Entrada;
import br.com.perfin.service.CartaoService;
import br.com.perfin.service.CategoriaService;
import br.com.perfin.service.EntradaService;
import br.com.perfin.util.Constantes;
import br.com.perfin.util.Util;

public class CartaoActivity extends Activity {
    
	private Date data;
	private Conexao con;
	private EditText ETdata;
	private EditText ETvalor;
	private EditText ETdescricao;
	private Spinner SPNSelectCategoria;
	private EditText ETparcela;
	private Button btAdiciona;
	private CartaoService cartaoService;
	private EntradaService entradaService;
	private CategoriaService categoriaService;
	private Cartao cartao = new Cartao();
	private Entrada entrada = new Entrada();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        con = new Conexao(this);
        SQLiteDatabase conexao = con.getConexao();
        
        setContentView(R.layout.entry_card);
        
        ETdata = (EditText) findViewById(R.id.date_input);
        ETvalor = (EditText) findViewById(R.id.value_input);
        ETdescricao = (EditText) findViewById(R.id.description_input);
        SPNSelectCategoria = (Spinner) findViewById(R.id.spinner_select_categoria);
        ETparcela = (EditText) findViewById(R.id.parcel_input);
        btAdiciona = (Button) findViewById(R.id.insert_entry_button);
        
        cartaoService = new CartaoService(conexao);
        entradaService = new EntradaService(conexao);
        categoriaService = new CategoriaService(conexao);
        
        Util.setTypeListToSpinner(SPNSelectCategoria, this, categoriaService.listar("", "Salário"));
        
        int id = getIntent().getIntExtra("CartaoID",0);
        if(id != 0){
        	carregar(id);
        }
        
        inicia();
    }
    
    public void carregar(int id){
    	cartao = cartaoService.buscarPorID(id);
    	entrada = cartao.getEntrada();
    	Date dt = entrada.getData();
		ETdata.setText(String.valueOf(dt.getDate())+"/"+String.valueOf(dt.getMonth()+1)+"/"+String.valueOf(dt.getYear()+1900));
		ETvalor.setText(entrada.getValor()*cartao.getParcela()+"");
		if(!entrada.getDescricao().equals(""))
			ETdescricao.setText(entrada.getDescricao());
		else ETdescricao.setText("");
		List<Categoria> cats = categoriaService.listar("", "Salário");
		int i = 0;
		for (i = 0; i < cats.size(); i++) {
			if(cats.get(i).getDescricao().equals(entrada.getCategoria().getDescricao())){
				SPNSelectCategoria.setSelection(i);
				break;
			}
		}
		ETparcela.setText(cartao.getParcela()+"");
		
		List<Cartao> cartoes = cartaoService.listar(0, null, Constantes.STATUS_PAID, entrada.getId());
		if(cartoes.size() > 0){
			ETdata.setClickable(false);
			ETdata.setEnabled(false);
			ETvalor.setEnabled(false);
			ETparcela.setEnabled(false);
		}
	  
		
    }
    
    public void inicia(){
    	ETdata.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                  
            final Calendar c = Calendar.getInstance();        
            int y = c.get(Calendar.YEAR);        
            int m = c.get(Calendar.MONTH);        
            int d = c.get(Calendar.DAY_OF_MONTH);
            
            DatePickerDialog dp = new DatePickerDialog(CartaoActivity.this,new DatePickerDialog.OnDateSetListener() {
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
            dp.setMessage("Escolha uma Data:");
            dp.setTitle("Escolha uma Data:");
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
				
	    		int parcela;
				if(ETparcela.getText().toString().equals("")){
					parcela = 1;
				}else{
					parcela = Integer.parseInt(ETparcela.getText().toString());
				}
	    		
				entrada.setData(data);
				entrada.setValor(Float.parseFloat(ETvalor.getText().toString())/parcela);
				entrada.setDescricao(ETdescricao.getText().toString());
				entrada.setCategoria(categoriaService.listar(SPNSelectCategoria.getSelectedItem().toString()).get(0));
				
				
				if(cartao.getParcela() == 0){
					//adição
					salvarcartao(parcela);
				}else{
					//edição
					//teste de alteração de data de entrada
					if(entrada.getData().compareTo(cartao.getEntrada().getData()) != 0){
						//houve alteração na data de entrada da divida
						List<Cartao> cartoes = cartaoService.listar(0, cartao.getEntrada().getData(), null, cartao.getEntrada().getId());
						if(cartoes.size() > 0){
							//existem parcelas agendadas
							if(cartoes.get(0).getDataFaturamento().after(entrada.getData())){
								//msg de erro, data alterada para depois de um faturamento
								return;
							}
						}
					}
					//teste de alteração de parcelas
					if(cartao.getParcela() != parcela){
						//houve alteração em parcelas
						List<Cartao> cartoes = cartaoService.listar(0, null, null, entrada.getId());
						for (int i = 0; i < cartoes.size(); i++) {
							cartaoService.excluir(cartoes.get(i).getId());
						}
						salvarcartao(parcela);
					}else{
						entrada = entradaService.salvar(entrada);
						cartao.setEntrada(entrada);
						cartaoService.salvar(cartao);
					}
					
				}
				
				Toast.makeText(getApplicationContext(), "Operação realizada com sucesso!", Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				CartaoActivity.this.finish();
			}else{
				Toast.makeText(getApplicationContext(), "Valor não pode ser vazio!", Toast.LENGTH_LONG).show();
			}
		}else{
			Toast.makeText(getApplicationContext(), "Data de entrada não pode ser uma data futura!", Toast.LENGTH_LONG).show();
		}
    }
    
    private void salvarcartao(int parcela){
    	entrada = entradaService.salvar(entrada);
    	for (int i = 0; i < parcela; i++) {
			cartao = new Cartao();
			cartao.setParcela(i+1);
			cartao.setStatus(Constantes.STATUS_NOTPAID);
			cartao.setEntrada(entrada);
			cartaoService.salvar(cartao);
		}
    }
    
}