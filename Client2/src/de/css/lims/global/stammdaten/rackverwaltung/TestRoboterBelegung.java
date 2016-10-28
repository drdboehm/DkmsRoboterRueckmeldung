package de.css.lims.global.stammdaten.rackverwaltung;

import java.util.ArrayList;

import de.integris.basisdaten.Kunde;
import de.integris.kit.bas.IArray;
import de.integris.kit.ctl.EditingContext;
import de.integris.kit.ctl.FetchSpecification;
import de.integris.kit.ctl.KeyValueQualifier;

import de.css.lims.auftrag.Versuch;
import de.css.lims.basisklassen.controller.LimsManager;
import de.css.lims.global.stammdaten.Rack;
import de.css.lims.global.stammdaten.Rackbelegung;
import de.css.lims.global.stammdaten.rackverwaltung.ScriptParameterContainer.DynamischesObjekt;

public class TestRoboterBelegung implements BelegungsInterface {

	//@Override
	public void rackBelegungGestartet(ScriptParameterContainer container,
			Rackbelegung arg0, String arg1, EditingContext arg2,
			LimsManager arg3) throws Exception {

	}

	//@Override
	public void rackUnbelegt(ScriptParameterContainer container, Rack rack,
			String scannedString, EditingContext context, LimsManager manager)
			throws Exception {
		Versuch template = null;
		Kunde kunde = null;

		String templateNr;

		ArrayList<DynamischesObjekt> ar = container
				.getDynamischeObjekte("versuchsTemplate");
		if (ar != null && !ar.isEmpty()) {
			DynamischesObjekt dynamischesObjekt = ar.get(0);
			templateNr = (String) dynamischesObjekt.getValue("templateId");
			if (templateNr != null) {
				IArray<Versuch> templates = context
						.getOrFetchObjects(new FetchSpecification(
								Versuch.class, new KeyValueQualifier(
										Versuch.NR, templateNr)));
				if (templates != null && !templates.isEmpty()) {
					template = templates.firstObject();
				} else {
					throw new Exception(
							"Es konnte kein Versuchstemplate mit der Nr:"
									+ templateNr + " gefunden werden!");
				}
			} else {
				throw new Exception(
						"Das Dynamische Objekt vom Typ \"versuchsTemplate\" besitzt keinen Wert mit dem Schlüssel:\"templateId\"!");
			}
		} else {
			throw new Exception(
					"Es konnte kein DynamischesObjekt vom Typ:\"versuchsTemplate\n im ParameterContainer gefunden werden!");
		}

		String kundeNr;

		ar = container.getDynamischeObjekte("kunde");
		if (ar != null && !ar.isEmpty()) {
			DynamischesObjekt dynamischesObjekt = ar.get(0);
			kundeNr = (String) dynamischesObjekt.getValue("kundeId");
			if (kundeNr != null) {
				IArray<Kunde> kunden = context
						.getOrFetchObjects(new FetchSpecification(Kunde.class,
								new KeyValueQualifier(Kunde.NR, kundeNr)));
				if (kunden != null && !kunden.isEmpty()) {
					kunde = kunden.firstObject();
				} else {
					throw new Exception("Es konnte kein Kunde mit der Nr:"
							+ kundeNr + " gefunden werden!");
				}
			} else {
				throw new Exception(
						"Das Dynamische Objekt vom Typ \"kunde\" besitzt keinen Wert mit dem Schlüssel:\"kundeId\"!");
			}
		} else {
			throw new Exception(
					"Es konnte kein DynamischesObjekt vom Typ:\"kunde\n im ParameterContainer gefunden werden!");
		}

		RackBelegungsScriptFunktionen.rackBelegungStarten(scannedString, rack,
				template, kunde, context);

	}

    @Override
    public void rackUnbelegt(Rack rack, String string, EditingContext editingContext,
                             LimsManager limsManager) throws Exception {
        // TODO Implement this method

    }

    @Override
    public void rackBelegungGestartet(Rackbelegung rackbelegung, String string, EditingContext editingContext,
                                      LimsManager limsManager) throws Exception {
        // TODO Implement this method

    }
}
