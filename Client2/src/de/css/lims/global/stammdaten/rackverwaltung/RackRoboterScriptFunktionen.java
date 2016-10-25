package de.css.lims.global.stammdaten.rackverwaltung;

import java.math.BigDecimal;

import java.util.ArrayList;

import de.integris.kit.bas.BigDecimalTools;
import de.integris.kit.bas.Constants;
import de.integris.kit.bas.IArray;
import de.integris.kit.bas.MutableArray;
import de.integris.kit.bas.MutableDictionary;
import de.integris.kit.ctl.AndQualifier;
import de.integris.kit.ctl.EditingContext;
import de.integris.kit.ctl.FetchSpecification;
import de.integris.kit.ctl.KeyObjectQualifier;
import de.integris.kit.ctl.KeyValueQualifier;
import de.integris.kit.ctl.PropertyEdit;
import de.integris.kit.ctl.Qualifier;
import de.integris.mawi.Charge;
import de.integris.mawi.Lagerbuchungsschluessel;

import de.css.lims.auftrag.MaterialzuordnungVersuchsschritt;
import de.css.lims.auftrag.UnterAuftrag;
import de.css.lims.auftrag.Versuch;
import de.css.lims.auftrag.Versuchschritt;
import de.css.lims.auftrag.VersuchschrittInterface;
import de.css.lims.global.basisdaten.Limseinstellungen;
import de.css.lims.global.stammdaten.Einheit;
import de.css.lims.global.stammdaten.Material;
import de.css.lims.global.stammdaten.Materialbestandsveraenderung;
import de.css.lims.global.stammdaten.Rack;
import de.css.lims.global.stammdaten.Rackbelegung;
import de.css.lims.global.stammdaten.Rackposition;
import de.css.lims.global.stammdaten.Racktyp;
import de.css.lims.global.stammdaten.material.LagerBestandsBucher;
import de.css.lims.global.stammdaten.rackverwaltung.ScriptParameterContainer.DynamischesObjekt;
import de.css.lims.global.tools.ArrayTools;

public class RackRoboterScriptFunktionen {
    public final static Integer AGGREGIERT = Constants.Integer0;
    public final static Integer EINZELN = Constants.Integer1;

    /**
     * Eine ProbeIdZuordnung wird auf einer Rackbelegung für ein zu scannendes
     * Rack zugeordnet
     *
     * @param ursprungsRackposition
     * @param neueProbe
     * @return
     * @throws Exception
     */
    private static Rackposition probeAufNeuesRackZuordnen(Rackposition ursprungsRackposition, UnterAuftrag probe,
                                                          Rackbelegung rackbelegung) throws Exception {

        if (ursprungsRackposition == null)
            throw new IllegalArgumentException("Rackposition darf nicht leer sein!");

        Rackposition neueVerbundeneRackposition = null;

        if (rackbelegung != null) {
            if (Rack.STATUS_UNBELEGT.equals(rackbelegung.getRack().getStatus())) {

                rackbelegung.setStatus(Rackbelegung.STATUS_BELEGUNG_WIRD_DURCHGEFUEHRT);
                rackbelegung.getRack().setStatus(Rack.STATUS_BELEGUNG_WIRD_DURCHGEFUEHRT);

                rackbelegung.markiereNaechsteZubelegendePosition(rackbelegung.getLetzteScanposition());
            }
            neueVerbundeneRackposition = rackbelegung.getLetzteScanposition();
            if (neueVerbundeneRackposition == null) {
                neueVerbundeneRackposition = rackbelegung.findeNaechsteBelegbarePosition(null);
                if (neueVerbundeneRackposition == null)
                    ursprungsRackposition.getEditingContext()
                        .error("Keine belegbare Rackposition für Rackbelegung {0} gefunden!",
                               new Object[] { rackbelegung });
            }
            neueVerbundeneRackposition.belegePosition(probe);

            Rackposition naechsteBelegbarePos =
                rackbelegung.markiereNaechsteZubelegendePosition(neueVerbundeneRackposition);
            if (naechsteBelegbarePos == null) {

                rackbelegung.belegungAbschliessen();
            }
        }

        return neueVerbundeneRackposition;
    }

    public static UnterAuftrag teilmengeInNeueProbeAuslagern(Rackposition ursprungsPosition,
                                                             Rackbelegung rackbelegung) throws Exception {
        UnterAuftrag erstellteTeilProbe = null;

        if (ursprungsPosition == null)
            throw new IllegalArgumentException("No position submitted!");

        UnterAuftrag itsProbe = ursprungsPosition.getUnterauftrag();
        EditingContext context = ursprungsPosition.getEditingContext();

        if (itsProbe == null)
            context.error("Die von Ihnen gescannte Rackposition hat keinen Probenbezug!");

        // neue Teilprobe erstellen
        IArray<UnterAuftrag> neueTeilProben = itsProbe.erzeugeTeilproben(1, true);

        if (neueTeilProben == null || neueTeilProben.isEmpty())
            context.error("Beim erstellen der Teilproben ist ein Fehler aufgetreten!");

        erstellteTeilProbe = neueTeilProben.firstObject();

        probeAufNeuesRackZuordnen(ursprungsPosition, erstellteTeilProbe, rackbelegung);

        return erstellteTeilProbe;
    }

    public static void mixedPipetting(Versuchschritt versuchschritt,
                                      ScriptParameterContainer container) throws Exception {

        ArrayList<DynamischesObjekt> targetRacks = container.getDynamischeObjekte("targets");
        if (targetRacks != null) {

            for (DynamischesObjekt targetRack : targetRacks) {
                String rackId = (String) targetRack.getValue("rackId");
                String rackTypNr = (String) targetRack.getValue("rackTypId");
                String versuchsTemplateId = (String) targetRack.getValue("versuchsTemplateId");
                Racktyp racktyp =
                    (Racktyp) versuchschritt.getOrFetchObject(Racktyp.class,
                                                              new KeyValueQualifier(Racktyp.NR, rackTypNr));

                Versuch template =
                    (Versuch) versuchschritt.getOrFetchObject(Versuch.class,
                                                              new KeyValueQualifier(Versuch.NR, versuchsTemplateId));
                if (template != null) {
                    // Rack anlegen oder suchen
                    if (racktyp != null) {
                        Rack zielRack = rackSuchenOderAnlegen(rackId, racktyp, versuchschritt.getEditingContext());
                        if (zielRack != null) {
                            // Rack teilmengenentnahme

                            Rackbelegung quellBelegung = container.getRack().getAktuelleRackbelegung();

                            Rackbelegung zielBelegung = zielRack.erstelleNeueRackbelegung();
                            zielBelegung.setKunde(quellBelegung.getKunde());
                            zielBelegung.setVersuchsTemplate(template);


                            for (Rackposition rackposition : quellBelegung.getBelegteRackpositionen()) {
                                teilmengeInNeueProbeAuslagern(rackposition, zielBelegung);
                            }
                            zielBelegung.belegungAbschliessen();

                            // Materialien Zuordnen
                            ScriptParameterContainer materialContainer = new ScriptParameterContainer(container);
                            materialContainer.setRack(zielRack);

                            ArrayList<DynamischesObjekt> materialZuordnungen =
                                container.getDynamischeObjekte("materialRackZuordnung", "rackId", rackId);
                            if (materialZuordnungen != null && !materialZuordnungen.isEmpty()) {
                                for (DynamischesObjekt materialZuordnung : materialZuordnungen) {

                                    String materialId = (String) materialZuordnung.getValue("materialId");

                                    ArrayList<DynamischesObjekt> materials =
                                        container.getDynamischeObjekte("materials", "materialId", materialId);
                                    if (materials != null && !materials.isEmpty()) {
                                        materialContainer.addDynamischesObjekt(materials.get(0));
                                    }

                                }
                                Versuchschritt versuchschrittZielBelegung = zielBelegung.bestimmeNaechstenSchritt();

                                materialienRueckmeldenUndVersucheLagerbuchung(versuchschrittZielBelegung,
                                                                              materialContainer,
                                                                              RackRoboterScriptFunktionen.EINZELN,
                                                                              RackRoboterScriptFunktionen.EINZELN);
                                RackSkriptFunktionen.alleGleichenSchritteRueckmelden(versuchschrittZielBelegung);
                            }

                        }

                    } else {
                        throw new Exception("Es existiert kein RackTyp mir der Nr:" + rackTypNr);
                    }
                } else {
                    throw new Exception("Es existiert kein VersuchsTemplate mir der Nr:" + versuchsTemplateId);
                }


            }

        } else {
            throw new Exception("Der ParameterContainer enthält keine Objekt vom Typ:\"targets\"");
        }

    }

    /**
     *
     * @param versuchschritt
     * @param container
     * @return
     * @throws Exception
     */
    public static Rackbelegung rackWechseln(Versuchschritt versuchschritt,
                                            ScriptParameterContainer container) throws Exception {
        EditingContext context = versuchschritt.getEditingContext();
        Rackposition bearbeitbarePos = versuchschritt.getVersuch()
                                                     .getUnterauftrag()
                                                     .getRackpositionVonRackbelegungBearbeitbar();

        Rackbelegung quellBelegung = bearbeitbarePos.getRackbelegung();

        Rackbelegung zielBelegung = neuesRackAnfordern(quellBelegung, container);
        RackSkriptFunktionen.alleGleichenSchritteRueckmelden(versuchschritt);
        if (zielBelegung != null) {
            if (zielBelegung.getVersuchsTemplate() == null)
                zielBelegung.setVersuchsTemplate(quellBelegung.getVersuchsTemplate());
            if (zielBelegung.getKunde() == null)
                zielBelegung.setKunde(quellBelegung.getKunde());

            zielBelegung.setKunde(quellBelegung.getKunde());

            MutableArray<Rackposition> quellPositionen =
                new MutableArray<>(quellBelegung.getRackpositionenNachBefuellungstyp());
            Rackposition naechsteFreiePos = null;
            while (!quellPositionen.isEmpty()) {
                Rackposition quellPosition = quellPositionen.firstObject();

                naechsteFreiePos = zielBelegung.findeNaechsteBelegbarePosition(naechsteFreiePos);
                zielBelegung.getRack().setStatus(Rack.STATUS_BELEGUNG_WIRD_DURCHGEFUEHRT);
                if (naechsteFreiePos == null) {
                    zielBelegung.belegungAbschliessen();

                    zielBelegung.getRack().setStatus(Rack.STATUS_BELEGT);
                    zielBelegung = neuesRackAnfordern(quellBelegung, container);
                    naechsteFreiePos = zielBelegung.findeNaechsteBelegbarePosition(null);
                    if (naechsteFreiePos == null) {
                        context.error("Das Rack konnte nicht verteilt werden!");
                    }
                }

                naechsteFreiePos.setUnterauftrag(quellPosition.getUnterauftrag());
                naechsteFreiePos.belegtFormatSetzen();

                quellPositionen.removeObject(quellPosition);

            }

            if (zielBelegung.findeNaechsteBelegbarePosition(naechsteFreiePos) == null) {
                zielBelegung.belegungAbschliessen();
            }
            quellBelegung.setStatus(Rackbelegung.STATUS_BEARBEITUNG_ABGESCHLOSSEN);

            quellBelegung.getRack().setStatus(Rack.STATUS_UNBELEGT);
        } else {
            context.error("Der Rackwechsel wurde abgebrochen!");
        }
        return zielBelegung;

    }

    public static Rackbelegung neuesRackAnfordern(Rackbelegung rackbelegung,
                                                  ScriptParameterContainer container) throws Exception {

        EditingContext context = rackbelegung.getEditingContext();
        ArrayList<DynamischesObjekt> dyns = container.getDynamischeObjekte("targets");

        if (dyns != null && !dyns.isEmpty()) {
            DynamischesObjekt dynamischesObjekt = dyns.get(0);
            String rackNummer = (String) dynamischesObjekt.getValue("rackId");

            if (rackNummer != null) {
                if (!rackNummer.equals(rackbelegung.getRack().getNr())) {
                    Qualifier qualifier = new KeyValueQualifier(Rack.NR, rackNummer);

                    IArray<Rack> ar = context.getOrFetchObjects(new FetchSpecification(Rack.class, qualifier));
                    if (ar != null && !ar.isEmpty()) {

                        Rack rack = ar.firstObject();
                        if (!Rack.STATUS_BELEGT.equals(rack.getStatus())) {
                            Rackbelegung neueBelegung = rack.getAktuelleRackbelegung();
                            if (neueBelegung != null &&
                                Rackbelegung.STATUS_BELEGUNG_WIRD_DURCHGEFUEHRT.equals(neueBelegung.getStatus())) {
                                return neueBelegung;
                            } else {
                                return rack.erstelleNeueRackbelegung();
                            }

                        } else {
                            context.error("Dieses Rack mit der Nummer: {0} ist bereits vollständig belegt!",
                                          new Object[] { rackNummer });
                        }

                    } else {
                        context.error("Es konnte kein Rack mit dieser Nummer gefunden werden!");
                    }
                } else {
                    context.error("Auf dasselbe Rack kann nicht gewechselt werden!");
                }

            } else {
                context.error("Es wurde keine Racknummer eingegeben!");
            }

        }

        return null;
    }

    /**
     * Erzeugt für alle MaterialzuordnungVersuchsschritt am übergebenen
     * Versuchsschritt Materialbestandsveränderungen. Fordert dazu die
     * entsprechenden Chargen an. Bucht danach die Materialbestandsveränderungen
     * in die WaWI. Bei Mißerfolgt ->Ignorieren und weitermachen
     *
     * @param versuchschritt
     * @param bestandsveraenderungTyp
     *            EINZELN->Jeder einzelne Materialverbrauch am Versuchschritt
     *            wird als eigenständige Materialbestandsveränderung erzeugt.
     *            AGGREGIERT->Es wird eine Aggregierte Menge über die gleiche
     *            Charge erzeugt und dazu eine Materialbestandsveränderung
     *            angelegt
     * @param lagerbuchungTyp
     *            EINZELN->Es werden alle Materialbestandsveränderungen einzeln
     *            in die WaWi gebucht. AGGREGIERT->Es werden alle möglichen
     *            materialbestandveränderungen zusammengefasst als einzelne
     *            Buchung gebucht.
     * @throws Exception
     */
    public static void materialienRueckmeldenUndVersucheLagerbuchung(Versuchschritt versuchschritt,
                                                                     ScriptParameterContainer container,
                                                                     Integer bestandsveraenderungTyp,
                                                                     Integer lagerbuchungTyp) throws Exception {
        EditingContext context = versuchschritt.getEditingContext();

        IArray<Materialbestandsveraenderung> ar =
            materialienRueckmeldenOhneLagerbuchung(versuchschritt, container, bestandsveraenderungTyp);
        Lagerbuchungsschluessel buchungsSchluessel = Limseinstellungen.getLagerbuchungsSchluesselAbgang(context);
        if (buchungsSchluessel != null) {
            try {
                if (EINZELN.equals(lagerbuchungTyp)) {
                    LagerBestandsBucher.bucheMaterialbestandsveraenderungEinzeln(context, buchungsSchluessel, ar);
                } else if (AGGREGIERT.equals(lagerbuchungTyp)) {
                    LagerBestandsBucher.bucheMaterialbestandsveraenderungAggregiert(context, buchungsSchluessel, ar);
                }

            } catch (Exception e) {
                context.warning("Materialbestandsveränderung konnte nicht gebucht werden!");
            }
        } else {
            context.warning("Es wurde kein Lagerbuchungsschlüssel für Abgänge in den Limseinstellungen definiert!");
        }

    }

    /**
     * Erzeugt, für alle MaterialzuordnungVersuchsschritt am übergebenen
     * Versuchsschritt, Materialbestandsveränderungen. Fordert dazu die
     * entsprechenden Chargen an. Multipliziert die Mengen mit der Anzahl an
     * Rackposition auf dem Rack. Kein Versuch der Buchung in WaWi
     *
     * @param versuchschritt
     * @param bestandsveraenderungTyp
     *            EINZELN->Jeder einzelne Materialverbrauch am Versuchschritt
     *            wird als eigenständige Materialbestandsveränderung erzeugt.
     *            AGGREGIERT->Es wird eine Aggregierte Menge über die gleiche
     *            Charge erzeugt und dazu eine Materialbestandsveränderung
     *            angelegt
     * @param lagerbuchungTyp
     *            EINZELN->Es werden alle Materialbestandsveränderungen einzeln
     *            in die WaWi gebucht. AGGREGIERT->Es werden alle möglichen
     *            materialbestandveränderungen zusammengefasst als einzelne
     *            Buchung gebucht.
     * @throws Exception
     */
    public static IArray<Materialbestandsveraenderung> materialienRueckmeldenOhneLagerbuchung(Versuchschritt versuchschritt,
                                                                                              ScriptParameterContainer container,
                                                                                              Integer bestandsveraendungTyp) throws Exception {
        Rackposition bearbeitbarePos = versuchschritt.getVersuch()
                                                     .getUnterauftrag()
                                                     .getRackpositionVonRackbelegungBearbeitbar();

        return materialienRueckmeldenOhneLagerbuchung(versuchschritt, container,
                                                      bearbeitbarePos.getRackbelegung().getBelegteRackpositionen(),
                                                      bestandsveraendungTyp);

    }

    /**
     * Erzeugt, für alle MaterialzuordnungVersuchsschritt am übergebenen
     * Versuchsschritt, Materialbestandsveränderungen. Fordert dazu die
     * entsprechenden Chargen an. Multipliziert die Mengen mit der Anzahl an
     * Rackposition auf dem Rack. Kein Versuch der Buchung in WaWi
     *
     * @param versuchschritt
     * @param bestandsveraenderungTyp
     *            EINZELN->Jeder einzelne Materialverbrauch am Versuchschritt
     *            wird als eigenständige Materialbestandsveränderung erzeugt.
     *            AGGREGIERT->Es wird eine Aggregierte Menge über die gleiche
     *            Charge erzeugt und dazu eine Materialbestandsveränderung
     *            angelegt
     * @param lagerbuchungTyp
     *            EINZELN->Es werden alle Materialbestandsveränderungen einzeln
     *            in die WaWi gebucht. AGGREGIERT->Es werden alle möglichen
     *            materialbestandveränderungen zusammengefasst als einzelne
     *            Buchung gebucht.
     * @throws Exception
     */
    public static IArray<Materialbestandsveraenderung> materialienRueckmeldenOhneLagerbuchung(Versuchschritt versuchschritt,
                                                                                              ScriptParameterContainer container,
                                                                                              IArray<Rackposition> rackpositionen,
                                                                                              Integer bestandsveraendungTyp) throws Exception {

        MutableArray<MaterialzuordnungVersuchsschritt> mzvs = new MutableArray<>();
        MutableArray<Materialbestandsveraenderung> bestandsVeraenderungen = new MutableArray<>();

        for (Rackposition rackposition : rackpositionen) {
            AndQualifier mzvsQualifier = new AndQualifier();
            mzvsQualifier.addToQualifiers(new KeyObjectQualifier(MaterialzuordnungVersuchsschritt.VERSUCHSSCHRITT +
                                                                 "." + Versuchschritt.VERSUCH + "." +
                                                                 Versuch.UNTERAUFTRAG, rackposition.getUnterauftrag()));
            mzvsQualifier.addToQualifiers(new KeyValueQualifier(MaterialzuordnungVersuchsschritt.VERSUCHSSCHRITT + "." +
                                                                VersuchschrittInterface.POSITION,
                                                                versuchschritt.getPosition()));
            mzvs.addObjects(rackposition.getOrFetchObjects(MaterialzuordnungVersuchsschritt.class, mzvsQualifier));

        }

        MutableDictionary materialGrpen =
            ArrayTools.ermittleGruppenMitglieder(mzvs,
                                                 new String[] { MaterialzuordnungVersuchsschritt.MATERIAL + "." +
                                                                Material.NR });
        for (int i = 0; i < materialGrpen.getKeys().count(); i++) {
            IArray<MaterialzuordnungVersuchsschritt> ar =
                (IArray<MaterialzuordnungVersuchsschritt>) materialGrpen.getObject(materialGrpen.getKeys()
                                                                                   .getObjectAt(i));
            bestandsVeraenderungen.addObjects(materialRueckmelden(container, ar, bestandsveraendungTyp,
                                                                  versuchschritt.getEditingContext()));
        }
        return bestandsVeraenderungen;

    }

    public static IArray<Materialbestandsveraenderung> materialRueckmelden(ScriptParameterContainer container,
                                                                           IArray<MaterialzuordnungVersuchsschritt> zuBuchendeMatanf,
                                                                           Integer typ,
                                                                           EditingContext context) throws Exception {

        MutableArray<Materialbestandsveraenderung> ar = new MutableArray<>();

        if (zuBuchendeMatanf == null || zuBuchendeMatanf.count() < 1)
            return ar;

        MaterialzuordnungVersuchsschritt ersteMatanf = zuBuchendeMatanf.firstObject();
        Material material = ersteMatanf.getMaterial();
        Versuchschritt versuchschritt = ersteMatanf.getVersuchsschritt();
        Rackposition bearbeitbarePos = versuchschritt.getVersuch()
                                                     .getUnterauftrag()
                                                     .getRackpositionVonRackbelegungBearbeitbar();
        IArray<Rackposition> rackpositionen = bearbeitbarePos.getRackbelegung().getBelegteRackpositionen();

        DynamischesObjekt charge = container.getDynamischesObjekt("materials", "materialId", material.getNr());
        if (charge != null) {
            String chargeNr = (String) charge.getValue("markierung");

            if (chargeNr != null) {

                if (EINZELN.equals(typ)) {
                    for (MaterialzuordnungVersuchsschritt matAnf : zuBuchendeMatanf) {
                        ar.addObject(erzeugeMaterialBestandsaenderung(matAnf, Constants.Integer1, chargeNr, matAnf,
                                                                      context));
                    }
                } else if (AGGREGIERT.equals(typ)) {
                    ar.addObject(erzeugeMaterialBestandsaenderung(ersteMatanf, zuBuchendeMatanf.count(), chargeNr, null,
                                                                  context));
                }

            } else {
                context.rollbackEdit(context.getCurrentEdit(), null);
                throw new Exception("Es konnte keine ChargenNr aus den Roboterdaten ermittelt werden!");
            }
        } else {
            context.rollbackEdit(context.getCurrentEdit(), null);
            throw new Exception("Es konnte kein Parameter:charge aus den Roboterdaten ermittelt werden!");
        }

        return ar;
    }

    /**
     * Erzeugt eine Bestandsveränderung am übergebenen Material mit der
     * angegebenen Charge und der (anzahl)*(Menge in Materialzuordnung)
     *
     * @param materialZuordnung
     * @param anzahl
     * @param charge
     *            ChargeString für Materialbestandsveraenderung
     * @param context
     * @throws Exception
     */
    public static Materialbestandsveraenderung erzeugeMaterialBestandsaenderung(MaterialzuordnungVersuchsschritt materialZuordnung,
                                                                                Integer anzahl, String charge,
                                                                                MaterialzuordnungVersuchsschritt mzvs,
                                                                                EditingContext context) throws Exception {
        return erzeugeMaterialBestandsaenderung(materialZuordnung.getMaterial(), materialZuordnung.getMenge(),
                                                materialZuordnung.getEinheit(), anzahl, charge, mzvs, context);

    }

    /**
     * Legt neue Racks mit den übergebenen Nummern an. Wenn
     *
     * Falls es eine Nummer schon gibt, dann soll der gesamte Prozess
     * abgebrochen werden.
     *
     * @param rackNummern
     * @param rackTyp
     * @return angelegte Racktypen
     * @throws Exception
     */
    public static IArray<Rack> neueRacksAnlegen(IArray<String> rackNummern, Racktyp typ,
                                                EditingContext context) throws Exception {
        MutableArray<Rack> ar = new MutableArray<>();
        for (String nr : rackNummern) {
            ar.addObject(neuesRackAnlegen(nr, typ, context));
        }
        return ar;
    }

    /**
     *
     * @param nummer
     * @param typ
     * @return
     * @throws Exception
     */
    public static Rack rackSuchenOderAnlegen(String rackNummer, Racktyp racktyp,
                                             EditingContext context) throws Exception {
        if (rackNummer != null && racktyp != null && context != null) {
            IArray<Rack> ar =
                context.getOrFetchObjects(new FetchSpecification(Rack.class,
                                                                 new KeyValueQualifier(Rack.NR, rackNummer)));
            if (ar == null || ar.isEmpty()) {


                PropertyEdit edit = context.beginEdit("neuesRackAnlegen");
                try {
                    Rack rack = new Rack(edit);
                    rack.setNr(rackNummer);
                    rack.setRacktyp(racktyp);


                    context.commitEdit(edit);
                    return rack;
                } catch (Exception e) {
                    context.rollbackEdit(edit, e);
                }

            } else {
                return ar.firstObject();
            }

        } else {
            throw new IllegalArgumentException("racknummer, racktyp, context == null");
        }

        return null;
    }

    /**
     * Sucht die Racks mit den angegebenen Nummern. Werden Sie nicht gefunden,
     * dann werden sie neu angelegt.
     *
     * @param nummern
     * @param typ
     * @throws Exception
     */
    public static IArray<Rack> racksSuchenOderAnlegen(IArray<String> nummern, Racktyp typ,
                                                      EditingContext context) throws Exception {
        MutableArray<Rack> ar = new MutableArray<>();
        for (String nr : nummern) {
            ar.addObject(rackSuchenOderAnlegen(nr, typ, context));
        }
        return ar;
    }

    public static void materialRueckmeldungMitAnlage(MaterialzuordnungVersuchsschritt materialzuordnungVersuchsschritt) {

    }

    /**
     * Legt ein neues Rack mit der übergebenen Nummer an.
     *
     * Falls es die Nummer schon gibt, dann soll der gesamte Prozess abgebrochen
     * werden.
     *
     * @param rackNummer
     *            * @param rackTyp
     * @return
     * @throws Exception
     */
    public static Rack neuesRackAnlegen(String rackNummer, Racktyp racktyp, EditingContext context) throws Exception {

        if (rackNummer != null && racktyp != null && context != null) {
            IArray<Rack> ar =
                context.getOrFetchObjects(new FetchSpecification(Rack.class,
                                                                 new KeyValueQualifier(Rack.NR, rackNummer)));
            if (ar == null || !ar.isEmpty()) {
                IArray<Racktyp> typen =
                    context.getOrFetchObjects(new FetchSpecification(Racktyp.class,
                                                                     new KeyValueQualifier(Racktyp.NR, racktyp)));


                PropertyEdit edit = context.beginEdit("neuesRackAnlegen");
                try {
                    Rack rack = new Rack(edit);
                    rack.setNr(rackNummer);
                    if (typen != null && !typen.isEmpty()) {
                        rack.setRacktyp(typen.firstObject());
                    } else {
                        throw new Exception("Es konnte kein Racktyp mit der Nr:" + racktyp + " gefunden werden!");
                    }

                    context.commitEdit(edit);
                    return rack;
                } catch (Exception e) {
                    context.rollbackEdit(edit, e);
                }

            } else {
                throw new Exception("Es existiert bereits ein Rack mit dieser Nummer!");
            }

        } else {
            throw new IllegalArgumentException("racknummer, racktyp, context == null");
        }

        return null;
    }

    public static Rackposition ermittlePosition(String rackId, String positionId) {
        return null;
    }

    /**
     * Verschiebt die Probe der SourcePosition an die TargetPosition. Auch
     * Rackübergreifend
     *
     * @param positionSource
     * @param positionTarget
     */
    public static void positionVerschieben(Rackposition positionSource, Rackposition positionTarget) {

    }

    public static Materialbestandsveraenderung erzeugeMaterialBestandsaenderung(Material material, BigDecimal menge,
                                                                                Einheit einheit, Integer anzahl,
                                                                                String charge,
                                                                                MaterialzuordnungVersuchsschritt mzvs,
                                                                                EditingContext context) throws Exception {
        PropertyEdit edit = context.beginEdit(null);
        try {

            Materialbestandsveraenderung mbv = material.createNewObjectIntoBestandsveraenderungen();
            mbv.setMenge(BigDecimalTools.multiply(BigDecimalTools.negate(menge), BigDecimal.valueOf(anzahl)));
            mbv.setEinheit(einheit);
            IArray<Charge> ar =
                context.getOrFetchObjects(new FetchSpecification(Charge.class,
                                                                 new KeyValueQualifier(Charge.NR, charge)));
            if (ar != null && !ar.isEmpty()) {
                mbv.setChargeMaWi(ar.firstObject());
            } else {
                mbv.setCharge(charge);
            }
            mbv.setMaterialzuordnungVersuchsschritt(mzvs);

            context.commitEdit(edit);
            return mbv;
        } catch (Exception e) {
            context.rollbackEdit(edit, e);
        }
        return null;
    }

}
