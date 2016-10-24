package de.css_lims.dkmsroboterrueckmeldung.dkms.parser;


import de.css_lims.dkmsroboterrueckmeldung.DkmsRoboterRueckmeldung;
import de.css_lims.dkmsroboterrueckmeldung.DkmsRoboterRueckmeldungClient;
import de.css_lims.dkmsroboterrueckmeldung.DkmsRoboterRueckmeldung_Service;
import de.css_lims.dkmsroboterrueckmeldung.RueckmeldungRequest;
import de.css_lims.dkmsroboterrueckmeldung.RueckmeldungResponse;
import de.css_lims.dkmsroboterrueckmeldung.dkms.converter.MaterialConverter;
import de.css_lims.dkmsroboterrueckmeldung.dkms.converter.TargetConverter;
import de.css_lims.dkmsroboterrueckmeldung.dkms.converter.ZuordnungConverter;
import de.css_lims.dkmsroboterrueckmeldung.dkms.types.DynamischeTypen;
import de.css_lims.dkmsroboterrueckmeldung.dkms.types.Material;
import de.css_lims.dkmsroboterrueckmeldung.dkms.types.Target;
import de.css_lims.dkmsroboterrueckmeldung.dkms.types.Zuordnung;

import java.util.ArrayList;
import java.util.List;

@Parameters(separators = "=")
public class DkmsRoboterRueckmeldungCLI {

    @Parameter(names = { "--rTyp", "-rt" }, description = "Request-Typ <0 = Validierung, 1 = Run>", arity = 1,
               required = true)
    private String requestTyp;

    @Parameter(names = { "--rID", "-r" }, description = "Roboter-Id", arity = 1, required = true)
    private String roboterId;

    @Parameter(names = { "--pID", "-p" }, description = "Roboter-Programm-Id", arity = 1, required = true)
    private String programmId;

    @Parameter(names = { "--uID", "-u" }, description = "Bedien-Nutzer-Id", arity = 1, required = true)
    private String userBedienId;

    @Parameter(names = { "--kID", "-k" }, description = "Kontrolleur-Id", arity = 1)
    private String userKontrolleurId;

    @Parameter(names = { "--rackID", "-ra" }, description = "Rack-Id", arity = 1, required = true)
    private String rackId;

    @Parameter(names = { "--hinweisText", "-hw" }, description = "Hinweistext", arity = 1)
    private String hinweisText = "";

    @Parameter(names = { "--material", "-m" }, description = "Materials List", converter = MaterialConverter.class)
    private static List<Material> materials = new ArrayList<>();

    @Parameter(names = { "--target", "-t" }, description = "Target List", converter = TargetConverter.class)
    private static List<Target> targets = new ArrayList<>();

    @Parameter(names = { "--zuordnung", "-z" }, description = "Zuordnung", converter = ZuordnungConverter.class)
    private static List<Zuordnung> zuordnungen = new ArrayList<>();


    @Parameter(names = { "--help", "-h" }, description = "this help", help = true)
    private boolean help = false;

    public static void main(String[] args) {
        DkmsRoboterRueckmeldungCLI dkmsCLI = new DkmsRoboterRueckmeldungCLI();
        JCommander jc = new JCommander(dkmsCLI, args);
        jc.setProgramName("java -jar DkmsRoboterCLI.jar");
        if (dkmsCLI.help) {
            jc.usage();
            return;
        }

        dkmsCLI.run();

    } // end main

    private void run() {

        printOutConsoleInput();


        DkmsRoboterRueckmeldung_Service dkmsRoboterRueckmeldung_Service = new DkmsRoboterRueckmeldung_Service();
        DkmsRoboterRueckmeldung dkmsRoboterRueckmeldung = dkmsRoboterRueckmeldung_Service.getDkmsRoboterRueckmeldung();


        RueckmeldungRequest rueckmeldungRequest =
            DkmsRoboterRueckmeldungClient.initializeStaticRequest(Integer.parseInt(requestTyp), rackId, roboterId,
                                                                  programmId, userBedienId, userKontrolleurId,
                                                                  hinweisText);


        if (!materials.isEmpty()) {
            DkmsRoboterRueckmeldungClient.createDynamischenTypAndAdd2dTypList(DynamischeTypen.materials);
            for (Material m : materials) {
                DkmsRoboterRueckmeldungClient.createDynamischesObject2DynamischenTypAtPosition(DynamischeTypen.materials,
                                                                                               m.getPosition(),
                                                                                               m.getMaterialId(),
                                                                                               m.getName(),
                                                                                               "96Plate");
            }
        }
        if (!targets.isEmpty()) {
            DkmsRoboterRueckmeldungClient.createDynamischenTypAndAdd2dTypList(DynamischeTypen.targets);
            for (Target t : targets) {
                DkmsRoboterRueckmeldungClient.createDynamischesObject2DynamischenTypAtPosition(DynamischeTypen.targets,
                                                                                               t.getPosition(),
                                                                                               t.getName(),
                                                                                               t.getTargetId(),
                                                                                               "96Plate");
            }
        }
        if (!zuordnungen.isEmpty()) {
            DkmsRoboterRueckmeldungClient.createDynamischenTypAndAdd2dTypList(DynamischeTypen.zuordnung);
            for (Zuordnung z : zuordnungen) {
                for (Target t : z.getTargetList())
                    for (Material m : z.getMaterialList())
                        DkmsRoboterRueckmeldungClient.createDynamischesObject2DynamischenTypAtPosition(DynamischeTypen.zuordnung,
                                                                                                       m.getPosition(),
                                                                                                       m.getMaterialId(),
                                                                                                       t.getTargetId());
            }
        }


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

    private void printOutConsoleInput() {
        System.out.printf("Request-Typ: %s\tRoboterId: %s\tProgrammId:  %s%n", requestTyp, roboterId, programmId);
        System.out.printf("UserId:  %s\tKontrolleur-Id: %s  %n", userBedienId, userKontrolleurId);
        System.out.printf("RackId: %s\tHinweis: %s%n", rackId, hinweisText);

        for (Material s : materials) {
            System.out.println(s);
        }
        for (Target t : targets) {
            System.out.println(t);
        }

        for (Zuordnung z : zuordnungen) {
            System.out.println(z);
        }
    }
}
