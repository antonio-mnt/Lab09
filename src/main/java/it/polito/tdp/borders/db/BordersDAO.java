package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer,Country> idMap, int anno) {

		String sql = "SELECT distinct StateAbb,CCode,StateNme " + 
				     "FROM contiguity, country " + 
				     "WHERE (CCode = state1no || CCode = state2no) AND YEAR <= ? AND conttype = 1 " + 
				     "ORDER BY StateAbb";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("CCode"))) {
					Country c = new Country(rs.getString("StateAbb"),rs.getInt("CCode"),rs.getString("StateNme"));
					idMap.put(c.getCode(), c);
				}
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(Map<Integer,Country> idMap, int anno) {
		
		String sql = "SELECT state1no, state1ab, state2no, state2ab, year " + 
				"FROM contiguity " + 
				"WHERE state1no > state2no and YEAR <= ? AND conttype = 1";
		
		List<Border> result = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Country c1 = idMap.get(rs.getInt("state1no"));
				Country c2 = idMap.get(rs.getInt("state2no"));
				
				if(c1!=null && c2!=null) {
					result.add(new Border(c1,c2));
				}else {
					System.out.println(rs.getString("state1ab")+" "+rs.getString("state2ab"));
				}
				
			}
			
			
			conn.close();
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		//System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return result;
	}
}
