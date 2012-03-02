package br.com.perfin.view;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import br.com.perfin.R;


public class EntradaActivity extends TabActivity{
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.entry_list_menu);
        
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        
        TabSpec cashTabSpec = tabHost.newTabSpec("tab1");
        TabSpec checkTabSpec = tabHost.newTabSpec("tab2");
        TabSpec cardTabSpec = tabHost.newTabSpec("tab3");
        
        Intent intentCash = new Intent(EntradaActivity.this, ListaActivity.class);
        intentCash.putExtra("type", "cash");
        Intent intentCheck = new Intent(EntradaActivity.this, ListaActivity.class);
        intentCheck.putExtra("type", "check");
        Intent intentCard = new Intent(EntradaActivity.this, ListaActivity.class);
        intentCard.putExtra("type", "card");
        
        cashTabSpec.setIndicator("Dinheiro").setContent(intentCash);
        checkTabSpec.setIndicator("Cheque").setContent(intentCheck);
        cardTabSpec.setIndicator("Cartao").setContent(intentCard);

        tabHost.addTab(cashTabSpec);
        tabHost.addTab(checkTabSpec);
        tabHost.addTab(cardTabSpec);
    }
}
