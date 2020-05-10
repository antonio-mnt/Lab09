package it.polito.tdp.borders.model;

public class Country {
	
	private String stateAbb;
	private int code;
	private String stateName;
	
	public Country(String stateAbb, int code, String stateName) {
		super();
		this.stateAbb = stateAbb;
		this.code = code;
		this.stateName = stateName;
	}

	public String getStateAbb() {
		return stateAbb;
	}

	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (code != other.code)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s %-33s", stateAbb, stateName);
	}
	
	

}
