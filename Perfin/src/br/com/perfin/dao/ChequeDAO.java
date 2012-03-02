package br.com.perfin.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.model.Cheque;

public class ChequeDAO extends DAO {
			
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private SQLiteDatabase conexao;
	private String table = "cheque";
	private String[] columns = {"_id","data_pg","entrada_id"};
	
	private EntradaDAO entradaDAO;
	
	public ChequeDAO(SQLiteDatabase conexao) {
		super(conexao);
		this.conexao = conexao;
		this.entradaDAO = new EntradaDAO(conexao);
	}

	public Cheque salvar(Cheque cheque){
		
		if(cheque.getId() > 0)
			conexao.update(table, cheque.toBD(), "_id = ?", new String[]{String.valueOf(cheque.getId())});
		else{
			cheque.setId(super.ultimoID(table));
			conexao.insert(table, null, cheque.toBD());
		}			
		
		return cheque;
	}
	
	public void excluir(int chequeID){
		conexao.delete(table, "_id = ?", new String[]{String.valueOf(chequeID)});
	}
	
	public Cheque buscarPorID(int chequeID){
		
		Cursor c = conexao.query(table, columns, "_id = ?", new String[]{String.valueOf(chequeID)}, null, null, null);

		if(c.getCount() <= 0) return null;
		
		c.moveToFirst();
		
		int idxId = c.getColumnIndex(columns[0]);
		int idxData_pg = c.getColumnIndex(columns[1]);		
		int idxEntradaID = c.getColumnIndex(columns[2]);
		
		Date dt;
		try {
			dt = sdf.parse(c.getString(idxData_pg));
		} catch (ParseException e) {
			dt = null;
		}
		
		Cheque cheque = new Cheque(c.getInt(idxId), dt, null);
		cheque.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
		
		c.close();
		
		return cheque;
	}
	
	public List<Cheque> listar(Date data_pg, int entrada_id){
		
		String where = getWhere(data_pg, entrada_id);
		String[] whereArgs = getWhereArgs(data_pg, entrada_id);
											
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, "data_pg ASC");
		List<Cheque> cheques = new ArrayList<Cheque>();
		
		if(c.moveToFirst()){
		
			int idxId = c.getColumnIndex(columns[0]);
			int idxData_pg = c.getColumnIndex(columns[1]);		
			int idxEntradaID = c.getColumnIndex(columns[2]);
			
			Date dt;
			try {
				dt = sdf.parse(c.getString(idxData_pg));
			} catch (ParseException e) {
				dt = null;
			}
			
			do {
										
				Cheque cheque = new Cheque(c.getInt(idxId), dt, null);
				cheque.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
				cheques.add(cheque);
				
			} while (c.moveToNext());
			
		}					
		c.close();
		return cheques;
	}
	
	private String getWhere(Date data_pg, int entrada_id){
		String where = " 1 = 1 ";
		
		if(data_pg != null){			
			where += " AND data_pg = ? ";
		}
					
		if(entrada_id > 0){
			where += " AND entradaID = ? ";
		}
		
		return where;
		
	}
	
	private String[] getWhereArgs(Date data_pg, int entrada_id){
		
		List<String> args = new ArrayList<String>();
		
		if(data_pg != null){
			args.add("%"+sdf.format(data_pg)+"%");
		}
				
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
