package br.com.perfin.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.model.Cartao;

public class CartaoDAO extends DAO {
			
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private SQLiteDatabase conexao;
	private String table = "cartao";
	private String[] columns = {"_id","parcelas","datafaturamento","status","entradaID"};
	
	private EntradaDAO entradaDAO;
	
	public CartaoDAO(SQLiteDatabase conexao) {
		super(conexao);
		this.conexao = conexao;
		this.entradaDAO = new EntradaDAO(conexao);
	}

	public Cartao salvar(Cartao cartao){
		if(cartao.getId() > 0)
			conexao.update(table, cartao.toBD(), "_id = ?", new String[]{String.valueOf(cartao.getId())});
		else{
			cartao.setId(super.ultimoID(table));
			conexao.insert(table, null, cartao.toBD());
		}			
		
		return cartao;
	}
	
	public void excluir(int cartaoID){
		conexao.delete(table, "_id = ?", new String[]{String.valueOf(cartaoID)});
	}
	
	public Cartao buscarPorID(int cartaoID){
		
		Cursor c = conexao.query(table, columns, "_id = ?", new String[]{String.valueOf(cartaoID)}, null, null, null);

		if(c.getCount() <= 0) return null;
		
		c.moveToFirst();
		
		int idxId = c.getColumnIndex(columns[0]);
		int idxParcela = c.getColumnIndex(columns[1]);
		int idxData = c.getColumnIndex(columns[2]);
		int idxStatus = c.getColumnIndex(columns[3]);
		int idxEntradaID = c.getColumnIndex(columns[4]);
				
		Date dt;
		try {
			dt = sdf.parse(c.getString(idxData));
		} catch (ParseException e) {
			dt = null;
		}catch (NullPointerException e) {
			dt = null;
		}
		
		Cartao cartao = new Cartao(c.getInt(idxId), c.getInt(idxParcela), dt, c.getString(idxStatus), null);
		cartao.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
		
		c.close();
		
		return cartao;
	}
	
	public List<Cartao> listar(int parcela, Date data, String status, int entrada_id){
		
		String where = getWhere(parcela, data, status, entrada_id);
		String[] whereArgs = getWhereArgs(parcela, data, status, entrada_id);
											
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, "parcelas ASC");
		List<Cartao> cartaos = new ArrayList<Cartao>();
		
		if(c.moveToFirst()){
		
			int idxId = c.getColumnIndex(columns[0]);
			int idxParcela = c.getColumnIndex(columns[1]);
			int idxData = c.getColumnIndex(columns[2]);
			int idxStatus = c.getColumnIndex(columns[3]);
			int idxEntradaID = c.getColumnIndex(columns[4]);
			
			do {
				Date dt;
				try {
					dt = sdf.parse(c.getString(idxData));
				} catch (ParseException e) {
					dt = null;
				} catch (NullPointerException e) {
					dt = null;
				}
				
			
										
				Cartao cartao = new Cartao(c.getInt(idxId), c.getInt(idxParcela), dt, c.getString(idxStatus), null);
				cartao.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
				cartaos.add(cartao);
				
			} while (c.moveToNext());
			
		}					
		c.close();
		return cartaos;
	}
	
	public List<Cartao> listarNP(Date data, String status){
		
		String where = getWhere(data, status);
		String[] whereArgs = getWhereArgs(data, status);
											
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, "parcelas ASC");
		List<Cartao> cartaos = new ArrayList<Cartao>();
		
		if(c.moveToFirst()){
		
			int idxId = c.getColumnIndex(columns[0]);
			int idxParcela = c.getColumnIndex(columns[1]);
			int idxData = c.getColumnIndex(columns[2]);
			int idxStatus = c.getColumnIndex(columns[3]);
			int idxEntradaID = c.getColumnIndex(columns[4]);
			
			do {
				Date dt;
				try {
					dt = sdf.parse(c.getString(idxData));
				} catch (ParseException e) {
					dt = null;
				} catch (NullPointerException e) {
					dt = null;
				}
				
			
										
				Cartao cartao = new Cartao(c.getInt(idxId), c.getInt(idxParcela), dt, c.getString(idxStatus), null);
				cartao.setEntrada(entradaDAO.buscarPorID(c.getInt(idxEntradaID)));
				cartaos.add(cartao);
				
			} while (c.moveToNext());
			
		}					
		c.close();
		return cartaos;
	}
	
	private String getWhere(int parcela, Date data, String status, int entrada_id){
		String where = " 1 = 1 ";
		
		if(parcela > 0){			
			where += " AND parcelas = ? ";
		}
		
		if(data != null){			
			where += " AND datafaturamento >= ? ";
		}
					
		if(status != null && status.trim().length() > 0){
			where += " AND status like ? ";
		}
		
		if(entrada_id > 0){
			where += " AND entradaID = ? ";
		}
		
		return where;
		
	}
	
	private String[] getWhereArgs(int parcela, Date data, String status, int entrada_id){
		
		List<String> args = new ArrayList<String>();
		
		if(parcela > 0){							
			args.add(String.valueOf(parcela));
		}
				
		if(data != null){
			args.add(sdf.format(data));
		}
		
		if(status != null && status.trim().length() > 0){			
			args.add("%"+status+"%");
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
	
	private String getWhere(Date data, String status){
		String where = " 1 = 1 ";
		
		if(data != null){			
			where += " AND datafaturamento <= ? ";
		}
					
		if(status != null && status.trim().length() > 0){
			where += " AND status like ? ";
		}
		
		return where;
		
	}
	
	private String[] getWhereArgs(Date data, String status){
		
		List<String> args = new ArrayList<String>();
		
		if(data != null){
			args.add(sdf.format(data));
		}
		
		if(status != null && status.trim().length() > 0){			
			args.add("%"+status+"%");
		}
		
		String[] whereArgs = new String[args.size()];
		
		for (int i = 0; i < args.size(); i++) {
			whereArgs[i] = args.get(i);			
		}
		
		return whereArgs;
		
	}
	
}
