package br.com.perfin.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAO {
	
	private SQLiteDatabase conexao;
			
	public DAO(SQLiteDatabase conexao) {
		super();
		this.conexao = conexao;
	}

	protected int ultimoID(String table){
		
		Cursor cursor = conexao.query(table, new String[]{"_id"}, null, null, null, null, "_id");
		
		if(!cursor.moveToLast())
			return 1;
		
		return cursor.getInt(0)+1;
	}
	

}
