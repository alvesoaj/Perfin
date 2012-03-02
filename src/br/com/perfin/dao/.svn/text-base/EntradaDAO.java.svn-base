package br.com.perfin.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.model.Cartao;
import br.com.perfin.model.Cheque;
import br.com.perfin.model.Dinheiro;
import br.com.perfin.model.Entrada;
import br.com.perfin.model.EntradaJoinAll;
import br.com.perfin.model.EntradaJoinCartao;
import br.com.perfin.model.EntradaJoinCheque;
import br.com.perfin.model.EntradaJoinDinheiro;
import br.com.perfin.model.EntradaJoinRenda;
import br.com.perfin.model.Renda;
import br.com.perfin.util.Constantes;

public class EntradaDAO extends DAO{
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private SQLiteDatabase conexao;
	private String table = "entrada";
	private String[] columns = {"_id","data","valor","descricao","categoriaID"};
	
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
		int idxCategoriaID = c.getColumnIndex("categoriaID");
		
		Date dt;
		try {
			dt = sdf.parse(c.getString(idxData));
		} catch (ParseException e) {
			dt = null;
		}
		
		Entrada entrada = new Entrada(c.getInt(idxId), dt, c.getFloat(idxValor), c.getString(idxDescricao), null);
		entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
		
		return entrada;
	}
	
	public List<EntradaJoinCartao> listar_cartao(String status, Date data_ini, Date data_fim){
		String sql = "SELECT En._id E_id, En.data E_data, En.valor E_valor, En.descricao E_descricao, En.categoriaID E_categoriaID," +
				"Ca._id C_id, Ca.parcelas C_parcelas, Ca.datafaturamento C_datafaturamento, Ca.status C_status " +
				"FROM Entrada En INNER JOIN Cartao Ca ON En._id=Ca.entradaID ";
		
		String where = getWhere(status, data_ini, data_fim, Constantes.G_CARTAO);
		String[] whereArgs = null;
		
		if(where.length() > 0){
			whereArgs = getWhereArgs(status, data_ini, data_fim);
		}
		
		String sql_order = " ORDER BY En.data,Ca.entradaID,Ca.parcelas ";
		
		sql += where+sql_order;
		
		Cursor c = conexao.rawQuery(sql, whereArgs);
		List<EntradaJoinCartao> entradas = new ArrayList<EntradaJoinCartao>();
		
		if(c.moveToFirst()){
			
				//dados da table entrada
				int idxId_Entrada = c.getColumnIndex("E_id");
				int idxData_Entrada = c.getColumnIndex("E_data");
				int idxValor = c.getColumnIndex("E_valor");
				int idxDescricao = c.getColumnIndex("E_descricao");
				int idxCategoriaID = c.getColumnIndex("E_categoriaID");
				
				//dados da tebla cartão
				int idxId_Cartao = c.getColumnIndex("C_id");
				int idxParcela = c.getColumnIndex("C_parcelas");
				int idxData_Cartao = c.getColumnIndex("C_datafaturamento");
				int idxStatus = c.getColumnIndex("C_status");
			
			do {	
				Date dt;
				Date dt2;
				try {
					dt = sdf.parse(c.getString(idxData_Entrada));
				} catch (ParseException e) {
					dt = null;
				}
				
				try{
					dt2 = sdf.parse(c.getString(idxData_Cartao));
				} catch (ParseException e2) {
					dt2 = null;
				}
				catch (NullPointerException e3) {
					dt2 = null;
				}
				
				Entrada entrada = new Entrada(c.getInt(idxId_Entrada), dt, c.getFloat(idxValor), c.getString(idxDescricao), null);
				entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
				
				Cartao cartao = new Cartao(c.getInt(idxId_Cartao), c.getInt(idxParcela), dt2, c.getString(idxStatus), entrada);
				
				EntradaJoinCartao ec = new EntradaJoinCartao(entrada, cartao);
				
				entradas.add(ec);
				
			} while (c.moveToNext());
		}
		return entradas;
		
	}
	
	public List<EntradaJoinCheque> listar_cheque(String status, Date data_ini, Date data_fim){
		String sql = "SELECT En._id E_id, En.data E_data, En.valor E_valor, En.descricao E_descricao, En.categoriaID E_categoriaID," +
				"Ch._id C_id, Ch.data_pg C_data_pg, Ch.status C_status " +
				"FROM Entrada En INNER JOIN Cheque Ch ON En._id=Ch.entradaID ";
		
		String where = getWhere(status, data_ini, data_fim, Constantes.G_CHEQUE);
		String[] whereArgs = null;
		
		if(where.length() > 0){
			whereArgs = getWhereArgs(status, data_ini, data_fim);
		}
		
		String sql_order = " ORDER BY En.data,Ch.entradaID ";
		
		sql += where+sql_order;
		
		Cursor c = conexao.rawQuery(sql, whereArgs);
		List<EntradaJoinCheque> entradas = new ArrayList<EntradaJoinCheque>();
		
		if(c.moveToFirst()){
				//dados da table entrada
				int idxId_Entrada = c.getColumnIndex("E_id");
				int idxData_Entrada = c.getColumnIndex("E_data");
				int idxValor = c.getColumnIndex("E_valor");
				int idxDescricao = c.getColumnIndex("E_descricao");
				int idxCategoriaID = c.getColumnIndex("E_categoriaID");
				
				//dados da tebla cheque
				int idxId_Cheque = c.getColumnIndex("C_id");
				int idxData_pg = c.getColumnIndex("C_data_pg");
				int idxStatus = c.getColumnIndex("C_status");
				
			do {
						
				Date dt;
				Date dt2;
				try {
					dt = sdf.parse(c.getString(idxData_Entrada));
				} catch (ParseException e) {
					dt = null;
				}
				
				try{
					dt2 = sdf.parse(c.getString(idxData_pg));
				} catch (ParseException e2) {
					dt2 = null;
				}
				catch (NullPointerException e3) {
					dt2 = null;
				}
				
				Entrada entrada = new Entrada(c.getInt(idxId_Entrada), dt, c.getFloat(idxValor), c.getString(idxDescricao), null);
				entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
				
				Cheque cheque = new Cheque(c.getInt(idxId_Cheque), dt2, c.getString(idxStatus), entrada);
				
				EntradaJoinCheque ec = new EntradaJoinCheque(entrada, cheque);
				
				entradas.add(ec);
				
			} while (c.moveToNext());
		}
		return entradas;
		
	}
	
	public List<EntradaJoinDinheiro> listar_dinheiro(Date data_ini, Date data_fim){
		String sql = "SELECT En._id E_id, En.data E_data, En.valor E_valor, En.descricao E_descricao, En.categoriaID E_categoriaID," +
				"Di._id D_id " +
				"FROM Entrada En INNER JOIN Dinheiro Di ON En._id=Di.entradaID ";
		
		String where = getWhere(data_ini, data_fim);
		String[] whereArgs = null;
		
		if(where.length() > 0){
			whereArgs = getWhereArgs(data_ini, data_fim);
		}
		
		String sql_order = " ORDER BY En.data,Di.entradaID ";
		
		sql += where+sql_order;
		
		Cursor c = conexao.rawQuery(sql, whereArgs);
		List<EntradaJoinDinheiro> entradas = new ArrayList<EntradaJoinDinheiro>();
		
		if(c.moveToFirst()){
			
				//dados da table entrada
				int idxId_Entrada = c.getColumnIndex("E_id");
				int idxData_Entrada = c.getColumnIndex("E_data");
				int idxValor = c.getColumnIndex("E_valor");
				int idxDescricao = c.getColumnIndex("E_descricao");
				int idxCategoriaID = c.getColumnIndex("E_categoriaID");
				
				//dados da tebla dinheiro
				int idxId_Dinheiro = c.getColumnIndex("D_id");
			
			do {	
				Date dt;
				try {
					dt = sdf.parse(c.getString(idxData_Entrada));
				} catch (ParseException e) {
					dt = null;
				}
				
				Entrada entrada = new Entrada(c.getInt(idxId_Entrada), dt, c.getFloat(idxValor), c.getString(idxDescricao), null);
				entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
				
				Dinheiro dinheiro = new Dinheiro(c.getInt(idxId_Dinheiro), entrada);
				
				EntradaJoinDinheiro ec = new EntradaJoinDinheiro(entrada, dinheiro);
				
				entradas.add(ec);
				
			} while (c.moveToNext());
		}
		return entradas;
		
	}
	
	public List<EntradaJoinRenda> listar_renda(Date data_ini, Date data_fim){
		String sql = "SELECT En._id E_id, En.data E_data, En.valor E_valor, En.descricao E_descricao, En.categoriaID E_categoriaID," +
				"Re._id R_id " +
				"FROM Entrada En INNER JOIN Renda Re ON En._id=Re.entradaID ";
		
		String where = getWhere(data_ini, data_fim);
		String[] whereArgs = null;
		
		if(where.length() > 0){
			whereArgs = getWhereArgs(data_ini, data_fim);
		}
		
		String sql_order = " ORDER BY En.data,Re.entradaID ";
		
		sql += where+sql_order;
		
		Cursor c = conexao.rawQuery(sql, whereArgs);
		List<EntradaJoinRenda> entradas = new ArrayList<EntradaJoinRenda>();
		
		if(c.moveToFirst()){
			
				//dados da table entrada
				int idxId_Entrada = c.getColumnIndex("E_id");
				int idxData_Entrada = c.getColumnIndex("E_data");
				int idxValor = c.getColumnIndex("E_valor");
				int idxDescricao = c.getColumnIndex("E_descricao");
				int idxCategoriaID = c.getColumnIndex("E_categoriaID");
				
				//dados da tebla renda
				int idxId_Renda = c.getColumnIndex("R_id");
			
			do {
				Date dt;
				try {
					dt = sdf.parse(c.getString(idxData_Entrada));
				} catch (ParseException e) {
					dt = null;
				}
				
				Entrada entrada = new Entrada(c.getInt(idxId_Entrada), dt, c.getFloat(idxValor), c.getString(idxDescricao), null);
				entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
				
				Renda renda = new Renda(c.getInt(idxId_Renda), entrada);
				
				EntradaJoinRenda ec = new EntradaJoinRenda(entrada, renda);
				
				entradas.add(ec);
				
			} while (c.moveToNext());
		}
		return entradas;
		
	}
	
	public List<EntradaJoinAll> listar_all(String status_card, String status_cheque, Date data_ini, Date data_fim){
		
		List<EntradaJoinCartao> listcard = listar_cartao(status_card, data_ini, data_fim);
		List<EntradaJoinCheque> listcheque = listar_cheque(status_cheque, data_ini, data_fim);
		List<EntradaJoinDinheiro> listdinheiro = listar_dinheiro(data_ini, data_fim);
		List<EntradaJoinRenda> listrenda = listar_renda(data_ini, data_fim);
		
		List<EntradaJoinAll> entradas = new ArrayList<EntradaJoinAll>();
		
		if(listcard.size() > 0){
			for (int i = 0; i < listcard.size(); i++) {
				EntradaJoinAll ec = new EntradaJoinAll(listcard.get(i).getCartao().getId(), listcard.get(i).getCartao().getDataFaturamento(), listcard.get(i).getEntrada(), "Cartão");
				entradas.add(ec);
			}
		}
		
		if(listdinheiro.size() > 0){
			for (int i = 0; i < listdinheiro.size(); i++) {
				EntradaJoinAll ec = new EntradaJoinAll(listdinheiro.get(i).getDinheiro().getId(), listdinheiro.get(i).getEntrada().getData(), listdinheiro.get(i).getEntrada(), "Dinheiro");
				if(entradas.size() > 0){
					for (int j = 0; j < entradas.size(); j++) {
						if(!entradas.get(j).getData().before(ec.getData())){
							entradas.add(j, ec);
							break;
						}else{
							if(j == entradas.size()-1){
								entradas.add(ec);
							}
						}
					}
					
				}else entradas.add(ec);
			}
		}
		
		if(listcheque.size() > 0){
			for (int i = 0; i < listcheque.size(); i++) {
				EntradaJoinAll ec = new EntradaJoinAll(listcheque.get(i).getCheque().getId(), listcheque.get(i).getCheque().getData_pg(), listcheque.get(i).getEntrada(), "Cheque");
				if(entradas.size() > 0){
					for (int j = 0; j < entradas.size(); j++) {
						if(!entradas.get(j).getData().before(ec.getData())){
							entradas.add(j, ec);
							break;
						}else{
							if(j == entradas.size()-1){
								entradas.add(ec);
							}
						}
					}
					
				}else entradas.add(ec);

			}
		}
		
		if(listrenda.size() > 0){
			for (int i = 0; i < listrenda.size(); i++) {
				EntradaJoinAll ec = new EntradaJoinAll(listrenda.get(i).getRenda().getId(), listrenda.get(i).getEntrada().getData(), listrenda.get(i).getEntrada(), "Renda");
				if(entradas.size() > 0){
					for (int j = 0; j < entradas.size(); j++) {
						if(!entradas.get(j).getData().before(ec.getData())){
							entradas.add(j, ec);
							break;
						}else{
							if(j == entradas.size()-1){
								entradas.add(ec);
							}
						}
					}
					
				}else entradas.add(ec);
			}
		}
		
		return entradas;
		
	}
	
	public List<Entrada> listar(Date data, Float valor, String descricao, int categoriaID){
		String where = getWhere(data, valor, descricao, categoriaID);
		String[] whereArgs = null;
		
		if(where.length() > 0){
			whereArgs = getWhereArgs(data, valor, descricao, categoriaID);
		}
		
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, null);
		List<Entrada> entradas = new ArrayList<Entrada>();
		
		if(c.moveToFirst()){
			do {
				int idxId = c.getColumnIndex("_id");
				int idxData = c.getColumnIndex("data");
				int idxValor = c.getColumnIndex("valor");
				int idxDescricao = c.getColumnIndex("descricao");
				int idxCategoriaID = c.getColumnIndex("categoriaID");
				
				Date dt;
				try {
					dt = sdf.parse(c.getString(idxData));
				} catch (ParseException e) {
					dt = null;
				}
				
				Entrada entrada = new Entrada(c.getInt(idxId), dt, c.getFloat(idxValor), c.getString(idxDescricao), null);
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
				int idxCategoriaID = c.getColumnIndex("categoriaID");
				Date dt;
				
				try {
					dt = sdf.parse(c.getString(idxData));
				} catch (ParseException e) {
					dt = null;
				}
				Entrada entrada = new Entrada(c.getInt(idxId), dt, c.getFloat(idxValor), c.getString(idxDescricao), null);
				entrada.setCategoria(categoriaDAO.buscarPorID(c.getInt(idxCategoriaID)));
				entradas.add(entrada);
			} while (c.moveToNext());
		}
		return entradas;
	}
	
	private String getWhere(Date data, Float valor, String descricao, int categoriaID){
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
		
		if(categoriaID > 0){
			where += " AND categoriaID = ? ";
		}
		
		return where;
		
	}
	
	private String getWhere(String status, Date data_ini, Date data_fim, int Type){
		String where = " WHERE 1 = 1 ";
		
		if(status != null && status.trim().length() > 0){
			where += " AND status like ? ";
		}
		
		switch (Type) {
		case Constantes.G_CARTAO:
			if(data_ini != null){
				if(data_fim != null){
					where += " AND Ca.datafaturamento BETWEEN ? AND ? OR En.data BETWEEN ? AND ? ";
				}else{
					where += " AND Ca.datafaturamento >= ? OR En.data >= ? ";
				}
			}
			break;
		case Constantes.G_CHEQUE:
			if(data_ini != null){
				if(data_fim != null){
					where += " AND Ch.data_pg BETWEEN ? AND ? OR En.data BETWEEN ? AND ? ";
				}else{
					where += " AND Ch.data_pg >= ? OR En.data >= ? ";
				}
			}
			break;
		default:
			break;
		}
		
		if(data_ini != null)
			if(status != null && status.trim().length() > 0){
				where += " AND status like ? ";
			}
		
		return where;
		
	}
	
	private String getWhere(Date data_ini, Date data_fim){
		String where = " WHERE 1 = 1 ";
		
		if(data_ini != null){
				if(data_fim != null){
					where += " AND En.data BETWEEN ? AND ? ";
				}else{
					where += " AND En.data >= ? ";
				}
			}

		return where;
		
	}
	
	private String[] getWhereArgs(Date data, Float valor, String descricao, int categoriaID){
		
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
		
		if(categoriaID > 0){
			args.add(String.valueOf(categoriaID));
		}
		
		String[] whereArgs = new String[args.size()];
		
		for (int i = 0; i < args.size(); i++) {
			whereArgs[i] = args.get(i);			
		}
		
		return whereArgs;
		
	}
	
	private String[] getWhereArgs(String status, Date data_ini, Date data_fim){
		
		List<String> args = new ArrayList<String>();
		
		if(status != null && status.trim().length() > 0){
			args.add("%"+status+"%");
		}
		
		if(data_ini != null){
			args.add(sdf.format(data_ini));
			if(data_fim != null){
				args.add(sdf.format(data_fim));
				args.add(sdf.format(data_ini));
				args.add(sdf.format(data_fim));
			}else{
				args.add(sdf.format(data_ini));
			}
		}
		
		String[] whereArgs = new String[args.size()];
		
		for (int i = 0; i < args.size(); i++) {
			whereArgs[i] = args.get(i);			
		}
		
		if(data_ini != null)
			if(status != null && status.trim().length() > 0){
				args.add("%"+status+"%");
			}
		
		return whereArgs;
		
	}
	
	private String[] getWhereArgs(Date data_ini, Date data_fim){
		
		List<String> args = new ArrayList<String>();
		
		if(data_ini != null){
			args.add(sdf.format(data_ini));
			if(data_fim != null){
				args.add(sdf.format(data_fim));
			}
		}
		
		String[] whereArgs = new String[args.size()];
		
		for (int i = 0; i < args.size(); i++) {
			whereArgs[i] = args.get(i);			
		}
		
		return whereArgs;
		
	}
	
}
