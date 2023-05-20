package data.residence;

public enum ResidenceData implements ICountryData {
  ResidenceOne("Владивосток", CountryData.Russia);

  private String name;
  private CountryData сountryData;

  ResidenceData(String name, CountryData сountryData) {
    this.name = name;
    this.сountryData = сountryData;
  }

  public String getName() {
    return name;
  }

  public CountryData getCountryData() {
    return сountryData;
  }

}
