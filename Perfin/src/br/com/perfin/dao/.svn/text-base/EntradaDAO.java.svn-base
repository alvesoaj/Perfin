package br.com.perfin.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.perfin.model.Entrada;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EntradaDAO extends DAO{
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private SQLiteDatabase conexao;
	private String table = "entrada";
	private String[] columns = {"_id","data","valor","descricao","status","categoriaID"};
	
	private CategoriaDAO categoriaDAO;
	
	public EntradaDAO(SQLiteDatabase conexao) {
		super(conexao);
		this.conexao = conexao;
		this.categoriaDAO = new CategoriaDAO(conexao);
	}

	public Entrada salvar(Entrada entrada){
		
		if(entrada.getId() > 0)
			conexao.update(table, entrada.toBD(), "_id = ?", new String[]{String.valueOf(entrada.getId())});
		else{
			entrada.setId(super.ultimoID(table));
			conexao.insert(table, null, entrada.toBD());
		}
		
		return entrada;
	}
	
	public void excluir(int entradaID){
		conexao.delete(table, "_id = ?", new String[]{String.valueOf(entradaID)});
	}
	
	public Entrada buscarPorID(int entradaID){
		
		Cursor c = conexao.query(table, columns, "_id = ?", new String[]{String.valueOf(entradaID)}, null, null, null);

		if(c.getCount() <= 0) return null;
		
		c.moveToFirst();
		
		int idxId = c.getColumnIndex("_id");
		int idxData = c.getColumnIndex("data");
		int idxValor = c.getColumnIndex("valor");
		int idxDescricao = c.getColumnIndex("descricao");
		int idxStatus = c.getColumnIndex("status");
		int idxCategoriaID = c.getColumnIndex("categoriaID");
		
		Date dt;
		try {
			dt = sdf.parse(c.getString(idxData));
		} catch (ParseException e) {
			dt = null;
		}
		
		Entrada entrada = new Entrada(c.getInt(idxId), dt, c.getFloat(idxValor), c.getString(idxDescricao), c.getString(idxStatus), null);
		entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
		
		return entrada;
	}
	
	public List<Entrada> listar(Date data, Float valor, String descricao, String status, int categoriaID){
		String where = getWhere(data, valor, descricao, status, categoriaID);
		String[] whereArgs = null;
		
		if(where.length() > 0){
			whereArgs = getWhereArgs(data, valor, descricao, status, categoriaID);
		}
		
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, null);
		List<Entrada> entradas = new ArrayList<Entrada>();
		
		if(c.moveToFirst()){
			do {
				int idxId = c.getColumnIndex("_id");
				int idxData = c.getColumnIndex("data");
				int idxValor = c.getColumnIndex("valor");
				int idxDescricao = c.getColumnIndex("descricao");
				int idxStatus = c.getColumnIndex("status");
				int idxCategoriaID = c.getColumnIndex("categoriaID");
				
				Date dt;
				try {
					dt = sdf.parse(c.getString(idxData));
				} catch (ParseException e) {
					dt = null;
				}
				
				Entrada entrada = new Entrada(c.getInt(idxId), dt, c.getFloat(idxValor), c.getString(idxDescricao), c.getString(idxStatus), null);
				entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
				entradas.add(entrada);
			} while (c.moveToNext());
		}
		return entradas;
	}
	
	public List<Entrada> listar(){
		Cursor c = conexao.query(table, columns, null, null, null, null, null);
		List<Entrada> entradas = new ArrayList<Entrada>();
		if(c.moveToFirst()){
			do {
				int idxId = c.getColumnIndex("_id");
				int idxData = c.getColumnIndex("data");
				int idxValor = c.getColumnIndex("valor");
				int idxDescricao = c.getColumnIndex("descricao");
				int idxStatus = c.getColumnIndex("status");
				int idxCategoriaID = c.getColumnIndex("categoriaID");
				Date dt;
				
				try {
					dt = sdf.parse(c.getString(idxData));
				} catch (ParseException e) {
					dt = null;
				}
				Entrada entrada = new Entrada(c.getInt(idxId), dt, c.getFloat(idxValor), c.getString(idxDescricao), c.getString(idxStatus), null);
				entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
				entradas.add(entrada);
			} while (c.moveToNext());
		}
		return entradas;
	}
	
	private String getWhere(Date data, Float valor, String descricao, String status, int categoriaID){
		String where = " 1 = 1 ";
		
		if(data != null){
			//matricula = matricula.replaceAll("'", "\'");
			where += " AND data = ? ";
		}
		
		if(valor != null && valor > 0){
			//nome = nome.replaceAll("'", "\'");
			where += " AND valor = ? ";
		}
		
		if(descricao.trim().length() > 0){
			//nome = nome.replaceAll("'", "\'");
			where += " AND descricao like ? ";
		}
		
		if(status.trim().length() > 0){
			//nome = nome.replaceAll("'", "\'");
			where += " AND status like ? ";
		}
		
		if(categoriaID > 0){
			where += " AND categoriaID = ? ";
		}
		
		return where;
		
	}
	
	private String[] getWhereArgs(Date data, Float valor, String descricao, String status, int categoriaID){
		
		List<String> args = new ArrayList<String>();
		
		if(data != null){
			args.add("%"+sdf.format(data)+"%");
		}
		
		if(valor != null && valor > 0){
			args.add("%"+String.valueOf(valor)+"%");
		}
		
		if(descricao.trim().length() > 0){
			args.add("%"+descricao+"%");
		}
		
		if(status.trim().length() > 0){
			args.add("%"+status+"%");
		}
		
		if(categoriaID > 0){
			args.add(String.valueOf(categoriaID));
		}
		
		String[] whereArgs = new String[args.size()];
		
		for (int i = 0; i < args.size(); i++) {
			whereArgs[i] = args.get(i);			
		}
		
		return whereArgs;
		
	}
	
}
