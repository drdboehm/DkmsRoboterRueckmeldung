package de.css.lims.global.stammdaten.rackverwaltung;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.integris.kit.adm.User;

import de.css.lims.global.stammdaten.Rack;

public class ScriptParameterContainer {

	public final static Integer WEBSERVICE = new Integer(0);
	public final static Integer USER = new Integer(1);

	private Integer initTyp;
	private Rack rack;
	private User bediener;
	private User kontrolleur;
	private String programmId;
	private String roboterId;
	private Integer requestTyp;
	private String requestId;
	private String hinweisText;
	private Date zeitstempelDate;
	private Integer zeitStempdelUhrzeit;

	private ArrayList<DynamischesObjekt> dynamischeObjekte = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		ScriptParameterContainer container = new ScriptParameterContainer();
		ScriptParameterContainer.DynamischesObjekt dyn1 = container.new DynamischesObjekt(
				"charge");
		dyn1.addValue("material", "kupfer");
		dyn1.addValue("menge", "15");
		dyn1.addValue("charge", "testCharge01");

		ScriptParameterContainer.DynamischesObjekt dyn2 = container.new DynamischesObjekt(
				"charge");
		dyn2.addValue("material", "eisen");
		dyn2.addValue("menge", "13");
		dyn2.addValue("charge", "testCharge02");

		container.addDynamischesObjekt(dyn2);
		container.addDynamischesObjekt(dyn1);
		String s = (String) container.getDynamischesObjekt("charge",
				"material", "kupfer").getValue("charge1");
		System.out.println(s);
	}

	public DynamischesObjekt getDynamischesObjekt(String typ, String property,
			Object wert) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put(property, wert);
		return getDynamischesObjekt(typ, map);

	}

	public DynamischesObjekt getDynamischesObjekt(String typ,
			HashMap<String, Object> qualifierMap) throws Exception {
		ArrayList<DynamischesObjekt> ar = getDynamischeObjekte(typ,
				qualifierMap);

		if (!ar.isEmpty()) {
			return ar.get(0);
		}
		return null;

	}

	public ArrayList<DynamischesObjekt> getDynamischeObjekte() {
		return dynamischeObjekte;
	}

	public ArrayList<DynamischesObjekt> getDynamischeObjekte(String typ,
			HashMap<String, Object> qualifierMap) throws Exception {
		DynamischesObjekt dynObj = null;
		ArrayList<DynamischesObjekt> ar = new ArrayList<>();
		for (DynamischesObjekt dynamischesObjekt : dynamischeObjekte) {

			if (typ.equals(dynamischesObjekt.getTyp())) {
				dynObj = dynamischesObjekt;
				for (String key : qualifierMap.keySet()) {

					if (!qualifierMap.get(key).equals(
							dynamischesObjekt.getValues().get(key))) {
						dynObj = null;
						break;

					}
				}

			}
		}
		if (dynObj != null) {
			ar.add(dynObj);
		} else {
			StringBuffer buffer = new StringBuffer();
			for (String key : qualifierMap.keySet()) {
				buffer.append("propertyName:" + key + "-Wert:"
						+ qualifierMap.get(key) + ";");
			}
			throw new Exception(
					"Es konnte kein Parameter nach den Kriterien, Typ:" + typ
							+ "; " + buffer.toString() + " gefunden werden!");
		}
		return ar;
	}

	public ArrayList<DynamischesObjekt> getDynamischeObjekte(String typ,
			String property, Object wert) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put(property, wert);
		return getDynamischeObjekte(typ, map);
	}

	public DynamischesObjekt getDynamischesObjekt(Integer position) {
		for(DynamischesObjekt dynamischesObjekt : getDynamischeObjekte()){
			if (dynamischesObjekt.getPosition().equals(position)) {
				return dynamischesObjekt;
			}
		}
		return null;
	}

	public void addDynamischesObjekt(DynamischesObjekt dynamischesObjekt) {
		dynamischeObjekte.add(dynamischesObjekt);
	}

	public User getKontrolleur() {
		return kontrolleur;
	}

	public void setKontrolleur(User kontrolleur) {
		this.kontrolleur = kontrolleur;
	}

	public String getProgrammId() {
		return programmId;
	}

	public void setProgrammId(String programmId) {
		this.programmId = programmId;
	}

	public String getRoboterId() {
		return roboterId;
	}

	public void setRoboterId(String roboterId) {
		this.roboterId = roboterId;
	}

	public Integer getRequestTyp() {
		return requestTyp;
	}

	public void setRequestTyp(Integer requestTyp) {
		this.requestTyp = requestTyp;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getHinweisText() {
		return hinweisText;
	}

	public void setHinweisText(String hinweisText) {
		this.hinweisText = hinweisText;
	}

	public Date getZeitstempelDate() {
		return zeitstempelDate;
	}

	public void setZeitstempelDate(Date zeitstempelDate) {
		this.zeitstempelDate = zeitstempelDate;
	}

	public Integer getZeitStempdelUhrzeit() {
		return zeitStempdelUhrzeit;
	}

	public void setZeitStempdelUhrzeit(Integer zeitStempdelUhrzeit) {
		this.zeitStempdelUhrzeit = zeitStempdelUhrzeit;
	}

	public Integer getInitTyp() {
		return initTyp;
	}

	public void setInitTyp(Integer initTyp) {
		this.initTyp = initTyp;
	}

	public Rack getRack() {
		return rack;
	}

	public void setRack(Rack rack) {
		this.rack = rack;
	}

	public User getBediener() {
		return bediener;
	}

	public void setBediener(User bediener) {
		this.bediener = bediener;
	}

	Integer getAnzahlObjekteVomTyp(String typ) {
		return null;
	}



	public class DynamischesObjekt {

		private String typ;
		private Integer position;

		private HashMap<String, Object> values = new HashMap<>();

		public DynamischesObjekt(String typ) {
			this.typ = typ;
		}

		public Object getValue(String property) throws Exception {
			if (getValues() != null && getValues().get(property) != null) {
				return getValues().get(property);
			} else {
				if (USER.equals(getInitTyp())) {
					StringBuffer buffer = new StringBuffer();
					if (getValues() != null) {
						for (String key : getValues().keySet()) {
							buffer.append("property:" + key + "-Wert:"
									+ getValues().get(key) + ";");
						}
					}

					return StringAnforderungFrame
							.stringAnfordern("Wert für PropertyNamen" + property
									+ " für Objekt vom Typ:" + getTyp()
									+ "; mit weiteren Werten "
									+ buffer.toString()
							+ " angeben");
				} else {
					throw new Exception("Es konnte kein Wert für das Property:"
							+ property + "; im DynamischenObjekt vom Typ:"
							+ getTyp() + "; gefunden werden:");
				}

			}
		}

		public void addValue(String propertyName, Object wert) {
			values.put(propertyName, wert);
		}

		public HashMap<String, Object> getValues() {
			return values;
		}

		public String getTyp() {
			return typ;
		}

		public void setTyp(String typ) {
			this.typ = typ;
		}

		public Integer getPosition() {
			return position;
		}

		public void setPosition(Integer position) {
			this.position = position;
		}

	}
}