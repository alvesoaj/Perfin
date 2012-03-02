package br.com.perfin.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.perfin.R;
import br.com.perfin.util.Constantes;

public class PrincipalActivity extends Activity{
	
	private Button btlistar;
	private Button btrenda;
	private Button btgasto;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        
        btlistar = (Button) findViewById(R.id.read_entry_button);
        btrenda = (Button) findViewById(R.id.insert_entry_renda_button);
        btgasto = (Button) findViewById(R.id.insert_entry_button);
        
        iniciar();
    }
    
    public void iniciar(){
    	
    	btlistar.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {	
				Intent intent = new Intent(PrincipalActivity.this, EntradaActivity.class);
				startActivity (intent);
			}
		});
    	
    	btrenda.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {	
				Intent intent = new Intent(PrincipalActivity.this, RendaActivity.class);
				startActivity (intent);
		    }
		});
    	
    	btgasto.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				
				showDialog(Constantes.GASTO);
				
				/*Intent intent = new Intent(PrincipalActivity.this, SelectActivity.class);
				startActivityForResult(intent, Constantes.GASTO);
				*/
		    }
		});
    	
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if(resultCode == RESULT_OK){
    		
    		int i = data.getIntExtra("GASTO", 10);
        	Intent intent;
        	
        	switch (i) {
    			case Constantes.G_CARTAO:
    				intent = new Intent(PrincipalActivity.this, CartaoActivity.class);
    				startActivity(intent);
    				break;
    			case Constantes.G_CHEQUE:
    				intent = new Intent(PrincipalActivity.this, ChequeActivity.class);
    				startActivity(intent);
    				break;
    			case Constantes.G_DINHEIRO:
    				intent = new Intent(PrincipalActivity.this, DinheiroActivity.class);
    				startActivity(intent);
    				break;
    			default:
    				break;
    		}
    	}else{
    		Toast.makeText(this, "¬¬", Toast.LENGTH_SHORT).show();
    	}
    }
	*/
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		
		if(id == Constantes.GASTO){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Selecione o Meio de Pagamento:");
	        builder.setItems(Constantes.G_TYPE, new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int item) {
	            	Intent intent;
	            	switch (item) {
		    			case Constantes.G_CARTAO:
		    				intent = new Intent(PrincipalActivity.this, CartaoActivity.class);
		    				startActivity(intent);
		    				dialog.dismiss();
		    				break;
		    			case Constantes.G_CHEQUE:
		    				intent = new Intent(PrincipalActivity.this, ChequeActivity.class);
		    				startActivity(intent);
		    				dialog.dismiss();
		    				break;
		    			case Constantes.G_DINHEIRO:
		    				intent = new Intent(PrincipalActivity.this, DinheiroActivity.class);
		    				startActivity(intent);
		    				dialog.dismiss();
		    				break;
		    			default:
		    				break;
	    		}
	            }
	        });
	        final AlertDialog alert = builder.create();
	        alert.show();
		}
		
		return super.onCreateDialog(id);
	}
    
    
    
}
