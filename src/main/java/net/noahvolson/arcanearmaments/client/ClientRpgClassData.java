package net.noahvolson.arcanearmaments.client;

public class ClientRpgClassData {
    private static String clientRpgClass;

    public static void set(String rpgClass) {
        clientRpgClass = rpgClass;
    }

    public static String getRpgClass() {
        return clientRpgClass;
    }
}
