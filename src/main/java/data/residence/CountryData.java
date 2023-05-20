package data.residence;

public enum CountryData {
  Russia("Россия"),
  Belarus("Белоруссия");

  private String name;

  CountryData(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
