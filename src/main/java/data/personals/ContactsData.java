package data.personals;

public enum ContactsData {
    VK("VK"),
    TELEGRAM(" Тelegram"),
    VIBER("Viber");

    private String name;
    ContactsData(String name){ this.name=name;}

    public String getName() {return this.name;}



}
