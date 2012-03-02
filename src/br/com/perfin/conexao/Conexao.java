package br.com.perfin.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Conexao {
	
	private OpenHelper open;
	private SQLiteDatabase db = null;	
	
	public Conexao(Context ctx) {							
		this.open = new OpenHelper(ctx);
	}
	
	public SQLiteDatabase getConexao(){
		if (this.db == null)
			this.db = this.open.getWritableDatabase();
		return this.db;
			
	}
	
	public void fecharConexao(){
		if(this.db != null && this.db.isOpen())
			this.db.close();
	}
	
	private static class OpenHelper extends SQLiteOpenHelper {

	      OpenHelper(Context context) {
	         super(context, "perfin", null, 1);
	      }

	      @Override
	      public void onCreate(SQLiteDatabase db) {
	    	 
	    	 Log.w("perfin", "Criando as tabelas no banco de dados");
	    	  
	         db.execSQL("" +
			  " CREATE TABLE [categoria] ( "+
			  " [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+ 
			  " [descricao] varchar(45) NOT NULL);");
	         
	         db.execSQL("" +
	   		  " INSERT INTO [categoria] ( "+
	   		  " [descricao] ) VALUES ( "+ 
	   		  " 'Salário');");
	         
	         db.execSQL("" +
	          " INSERT INTO [categoria] ( "+
		   	  " [descricao] ) VALUES ( "+ 
		   	  " 'Diversão');");
	         
	         db.execSQL("" +
		   	  " INSERT INTO [categoria] ( "+
		   	  " [descricao] ) VALUES ( "+ 
		   	  " 'Transporte');");
	         
	         db.execSQL("" +
		   	  " INSERT INTO [categoria] ( "+
		   	  " [descricao] ) VALUES ( "+ 
		   	  " 'Conta');");
	         
	         db.execSQL("" +
		   	  " INSERT INTO [categoria] ( "+
		   	  " [descricao] ) VALUES ( "+ 
		   	  " 'Compra');");
	         
	         db.execSQL("" +
		   	  " INSERT INTO [categoria] ( "+
		   	  " [descricao] ) VALUES ( "+ 
		   	  " 'Outros');");
	         
	         db.execSQL("" + 
			  " CREATE TABLE [entrada] ( "+
			  " [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+ 
			  " [data] DATETIME NOT NULL,  "+
			  " [valor] FLOAT NOT NULL,  "+
	          " [descricao] varchar(45) NOT NULL, "+
	          " [categoriaID] INTEGER NOT NULL CONSTRAINT [fk_entrada_categoria] REFERENCES [categoria]([_id]) ON DELETE CASCADE ON UPDATE CASCADE); ");
	         
	         db.execSQL("" + 
   			  " CREATE TABLE [dinheiro] ( "+	
   			  " [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
   			  " [entradaID] INTEGER NOT NULL CONSTRAINT [fk_dinheiro_entrada] REFERENCES [entrada]([_id]) ON DELETE CASCADE ON UPDATE CASCADE); ");
	         
	         db.execSQL("" + 
  			  " CREATE TABLE [cheque] ( "+	
  			  " [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
  			  " [data_pg] DATETIME NOT NULL,  "+
  			  " [status] varchar(45) NOT NULL, "+
	   	      " [entradaID] INTEGER NOT NULL CONSTRAINT [fk_cheque_entrada] REFERENCES [entrada]([_id]) ON DELETE CASCADE ON UPDATE CASCADE); ");
	         
	         db.execSQL("" + 
  			  " CREATE TABLE [cartao] ( "+	
  			  " [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
  			  " [parcelas] INTEGER NOT NULL, " +
  			  " [datafaturamento] DATETIME, "+
  			  " [status] varchar(45) NOT NULL, "+
  			  " [entradaID] INTEGER NOT NULL " +
  			  			"CONSTRAINT [fk_cartao_entrada] " +
  			  			"REFERENCES [entrada]([_id]) " +
  			  			"ON DELETE CASCADE " +
  			  			"ON UPDATE CASCADE); ");
	         
	         db.execSQL("" + 
  			  " CREATE TABLE [renda] ( "+	
  			  " [_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+
  			  " [entradaID] INTEGER NOT NULL CONSTRAINT [fk_renda_entrada] REFERENCES [entrada]([_id]) ON DELETE CASCADE ON UPDATE CASCADE); ");
	         
	      }

	      @Override
	      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	         Log.w("perfin", "Upgrading database, this will drop tables and recreate.");
	         //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	         onCreate(db);
	      }
	   }
	
}
