package br.com.perfin.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import br.com.perfin.R;
import br.com.perfin.util.Constantes;
import br.com.perfin.util.Util;

public class SelectActivity extends Activity {
	
	private Spinner selType;
	private Button btSeleciona;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_type);
        
        selType = (Spinner) findViewById(R.id.spinner_select_type);
        btSeleciona = (Button) findViewById(R.id.select_type_button);
        
        iniciar();
        
    }
    
    private void iniciar(){
    	Util.setTypeListToSpinner(selType, this);
    	
    	btSeleciona.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {	
				
				int i = selType.getSelectedItemPosition();
				Intent intent = getIntent();
				
				switch (i) {
				case 0:
					intent.putExtra("GASTO", Constantes.G_CARTAO);
					break;
				case 1:
					intent.putExtra("GASTO", Constantes.G_CHEQUE);
					break;
				case 2:
					intent.putExtra("GASTO", Constantes.G_DINHEIRO);
					break;
				default:
					break;
				}
				
				setResult(RESULT_OK, intent);
				SelectActivity.this.finish();
				
			}
		});
    	
    	
    }
    
}