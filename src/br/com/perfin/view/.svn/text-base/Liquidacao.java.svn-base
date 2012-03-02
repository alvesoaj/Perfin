package br.com.perfin.view;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import br.com.perfin.R;

public class Liquidacao extends TabActivity{
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, LiquidarCartaoActivity.class);
	    spec = tabHost.newTabSpec("Cartao").setIndicator("Cartão",
	                      res.getDrawable(R.drawable.ic_tab_cartao))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    
	    intent = new Intent().setClass(this, LiquidarChequeActivity.class);
	    spec = tabHost.newTabSpec("Cheque").setIndicator("Cheque",
	                      res.getDrawable(R.drawable.ic_tab_cheque))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(0);
	}
}
