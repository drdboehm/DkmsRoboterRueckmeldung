package de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.parser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import de.css_lims.dkmsroboterrueckmeldung.DkmsRoboterRueckmeldung;
import de.css_lims.dkmsroboterrueckmeldung.DkmsRoboterRueckmeldungClient;
import de.css_lims.dkmsroboterrueckmeldung.DkmsRoboterRueckmeldung_Service;
import de.css_lims.dkmsroboterrueckmeldung.RueckmeldungRequest;
import de.css_lims.dkmsroboterrueckmeldung.RueckmeldungResponse;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.converter.MaterialConverter;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.converter.TargetConverter;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.converter.ZuordnungConverter;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types.Material;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types.Target;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types.Zuordnung;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

@Parameters(separators = "=")
public class DkmsRoboterRueckmeldungCLI {

    @Parameter(names = { "--rTyp", "-rt" }, arity = 1, required = true)
    private String requestTyp;

    @Parameter(names = { "--rID", "-r" }, arity = 1, required = true)
    private String roboterId;

    @Parameter(names = { "--pID", "-p" }, arity = 1, required = true)
    private String programmId;

    @Parameter(names = { "--uID", "-u" }, arity = 1, required = true)
    private String userBedienId;

    @Parameter(names = { "--kID", "-k" }, arity = 1)
    private String userKontrolleurId;

    @Parameter(names = { "--rackID", "-ra" }, arity = 1, required = true)
    private String rackId;

    @Parameter(names = { "--material", "-m" }, description = "Materials List", converter = MaterialConverter.class)
    private static List<Material> materials = new ArrayList<>();

    @Parameter(names = { "--target", "-t" }, description = "Target List", converter = TargetConverter.class)
    private static List<Target> targets = new ArrayList<>();

    @Parameter(names = { "--zuordnung", "-z" }, description = "Zuordnung", converter = ZuordnungConverter.class)
    private static List<Zuordnung> zuordnungen = new ArrayList<>();

    @Parameter(names = "--password", description = "Connection password", password = true, echoInput = true)
    private String password;

    @Parameter(names = "--file", converter = FileConverter.class)
    File file;

    public static void main(String[] args) {
        DkmsRoboterRueckmeldungCLI dkmsCLI = new DkmsRoboterRueckmeldungCLI();
        new JCommander(dkmsCLI, args);
        dkmsCLI.run();
    } // end main

    private void run() {

        System.out.printf("Request-Typ: %s\tRoboterId: %s\tProgrammId:  %s%n", requestTyp, roboterId, programmId);
        System.out.printf("UserId:  %s\tKontrolleur-Id: %s  %n", userBedienId, userKontrolleurId);
        System.out.printf("RackId: %s%n", rackId);
        for (Material s : materials) {
            System.out.println(s);
        }
        for (Target t : targets) {
            System.out.println(t);
        }

        for (Zuordnung z : zuordnungen) {
            System.out.println(z);
        }

    //    DkmsRoboterRueckmeldungClient dkmsRRClient = new DkmsRoboterRueckmeldungClient();
        
        DkmsRoboterRueckmeldung_Service dkmsRoboterRueckmeldung_Service = new DkmsRoboterRueckmeldung_Service();
        DkmsRoboterRueckmeldung dkmsRoboterRueckmeldung = dkmsRoboterRueckmeldung_Service.getDkmsRoboterRueckmeldung();
        
        RueckmeldungRequest rueckmeldungRequest =
            DkmsRoboterRueckmeldungClient.initializeStaticRequest(Integer.parseInt(requestTyp), rackId, roboterId,
                                                                  programmId, userBedienId, userKontrolleurId,
                                                                  "hinweisText");
        RueckmeldungResponse rueckmeldungResponse =
            dkmsRoboterRueckmeldung.dkmsRoboterRueckmeldung(rueckmeldungRequest);
        System.out.println(rueckmeldungResponse.getStatus());
    }

    public static Material getMaterialByPosition(String position) {
        for (Material m : getMaterials()) {
            if (m.getPosition().equals(position)) {
                return m;
            }
        }
        return null;
    }

    public static Target getTargetByPosition(String position) {
        for (Target t : getTargets()) {
            if (t.getPosition().equals(position)) {
                return t;
            }
        }
        return null;
    }

    public static List<Material> getMaterials() {
        return materials;
    }

    public static List<Target> getTargets() {
        return targets;
    }

    public static void setMaterials(List<Material> materials) {
        DkmsRoboterRueckmeldungCLI.materials = materials;
    }

    public static void setTargets(List<Target> targets) {
        DkmsRoboterRueckmeldungCLI.targets = targets;
    }

    public static void setZuordnungen(List<Zuordnung> zuordnungen) {
        DkmsRoboterRueckmeldungCLI.zuordnungen = zuordnungen;
    }

    public static List<Zuordnung> getZuordnungen() {
        return zuordnungen;
    }
}
