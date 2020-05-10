package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.GraphListener;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country, DefaultEdge> grafo;
	private Map<Integer, Country> idMap;
	private BordersDAO dao;

	public Model() {
		idMap = new HashMap<Integer,Country>();
		dao = new BordersDAO();
	
	}
	
	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		this.dao.loadAllCountries(idMap, anno);
		
		for(Country c: idMap.values()) {
			this.grafo.addVertex(c);
		}
		
		for(Border b: dao.getCountryPairs(idMap, anno)) {
			Graphs.addEdgeWithVertices(this.grafo, b.getC1(), b.getC2());
		}
		
	}
	
	public int componentiConnessi() {
		
		ConnectivityInspector<Country,DefaultEdge> graf = new ConnectivityInspector<>(this.grafo);
		int componenti = graf.connectedSets().size();
		
		return componenti;
	}
	public Set<Country> getVertex(){
		return this.grafo.vertexSet();
	}
	
	public int numConfini(Country cou) {
		int count = 0;
		
		for(DefaultEdge e: this.grafo.edgeSet()) {
			if(cou.equals(this.grafo.getEdgeSource(e)) || cou.equals(this.grafo.getEdgeTarget(e))) {
				count++;
			}
		}
		
		return count;
	}
	
	
	public int vertexNumber() {
		return this.grafo.vertexSet().size();
	}
	
	public int edgeNumber() {
		return this.grafo.edgeSet().size();
	}
	
	
	public Set<Country> trovaVicini(Country c){
		
		//BreadthFirstIterator<Country, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo,c);
	
		ConnectivityInspector<Country,DefaultEdge> graf = new ConnectivityInspector<>(this.grafo);
		return graf.connectedSetOf(c);
		//return it.getGraph().vertexSet();
		
	}
	

}
