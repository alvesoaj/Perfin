package br.com.perfin.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.model.Renda;

public class RendaDAO extends DAO {
			
	private SQLiteDatabase conexao;
	private String table = "Renda";
	private String[] columns = {"_id","entrada_id"};
	
	private EntradaDAO entradaDAO;
	
	public RendaDAO(SQLiteDatabase conexao) {
		super(conexao);
		this.conexao = conexao;
		this.entradaDAO = new EntradaDAO(conexao);
	}

	public Renda salvar(Renda renda){
		
		if(renda.getId() > 0)
			conexao.update(table, renda.toBD(), "_id = ?", new String[]{String.valueOf(renda.getId())});
		else{
			renda.setId(super.ultimoID(table));
			conexao.insert(table, null, renda.toBD());
		}			
		
		return renda;
	}
	
	public void excluir(int rendaID){
		conexao.delete(table, "_id = ?", new String[]{String.valueOf(rendaID)});
	}
	
	public Renda buscarPorID(int rendaID){
		
		Cursor c = conexao.query(table, columns, "_id = ?", new String[]{String.valueOf(rendaID)}, null, null, null);

		if(c.getCount() <= 0) return null;
		
		c.moveToFirst();
		
		int idxId = c.getColumnIndex(columns[0]);
		int idxEntradaID = c.getColumnIndex(columns[2]);
		
		Renda renda = new Renda(c.getInt(idxId), null);
		renda.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
		
		c.close();
		
		return renda;
	}
	
	public List<Renda> listar(int entrada_id){
		
		String where = getWhere(entrada_id);
		String[] whereArgs = getWhereArgs(entrada_id);
											
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, null);
		List<Renda> rendas = new ArrayList<Renda>();
		
		if(c.moveToFirst()){
		
			int idxId = c.getColumnIndex(columns[0]);
			int idxEntradaID = c.getColumnIndex(columns[2]);
			
			do {
										
				Renda renda = new Renda(c.getInt(idxId), null);
				renda.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
				rendas.add(renda);
				
			} while (c.moveToNext());
			
		}					
		c.close();
		return rendas;
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
