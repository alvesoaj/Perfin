package br.com.perfin.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.model.Dinheiro;

public class DinheiroDAO extends DAO {
			
	private SQLiteDatabase conexao;
	private String table = "Dinheiro";
	private String[] columns = {"_id","entrada_id"};
	
	private EntradaDAO entradaDAO;
	
	public DinheiroDAO(SQLiteDatabase conexao) {
		super(conexao);
		this.conexao = conexao;
		this.entradaDAO = new EntradaDAO(conexao);
	}

	public Dinheiro salvar(Dinheiro dinheiro){
		
		if(dinheiro.getId() > 0)
			conexao.update(table, dinheiro.toBD(), "_id = ?", new String[]{String.valueOf(dinheiro.getId())});
		else{
			dinheiro.setId(super.ultimoID(table));
			conexao.insert(table, null, dinheiro.toBD());
		}			
		
		return dinheiro;
	}
	
	public void excluir(int dinheiroID){
		conexao.delete(table, "_id = ?", new String[]{String.valueOf(dinheiroID)});
	}
	
	public Dinheiro buscarPorID(int dinheiroID){
		
		Cursor c = conexao.query(table, columns, "_id = ?", new String[]{String.valueOf(dinheiroID)}, null, null, null);

		if(c.getCount() <= 0) return null;
		
		c.moveToFirst();
		
		int idxId = c.getColumnIndex(columns[0]);
		int idxEntradaID = c.getColumnIndex(columns[2]);
		
		Dinheiro dinheiro = new Dinheiro(c.getInt(idxId), null);
		dinheiro.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
		
		c.close();
		
		return dinheiro;
	}
	
	public List<Dinheiro> listar(int entrada_id){
		
		String where = getWhere(entrada_id);
		String[] whereArgs = getWhereArgs(entrada_id);
											
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, null);
		List<Dinheiro> dinheiros = new ArrayList<Dinheiro>();
		
		if(c.moveToFirst()){
		
			int idxId = c.getColumnIndex(columns[0]);
			int idxEntradaID = c.getColumnIndex(columns[2]);
			
			do {
										
				Dinheiro dinheiro = new Dinheiro(c.getInt(idxId), null);
				dinheiro.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
				dinheiros.add(dinheiro);
				
			} while (c.moveToNext());
			
		}					
		c.close();
		return dinheiros;
	}
	
	private String getWhere(int entrada_id){
		String where = " 1 = 1 ";
		
		if(entrada_id > 0){
			where += " AND entradaID = ? ";
		}
		
		return where;
		
	}
	
	private String[] getWhereArgs(int entrada_id){
		
		List<String> args = new ArrayList<String>();
		
		if(entrada_id > 0){
			args.add(String.valueOf(entrada_id));
		}
		
		String[] whereArgs = new String[args.size()];
		
		for (int i = 0; i < args.size(); i++) {
			whereArgs[i] = args.get(i);			
		}
		
		return whereArgs;
		
	}
}
