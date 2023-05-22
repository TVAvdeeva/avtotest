package data.personals;

public enum LanguageData {
   BEGINNER("Начальный уровень (Beginner)"),
   INTERMEDIATE("Средний (Intermediate)");


   private String name;

   LanguageData(String name){
      this.name = name;
   }

   public String getName(){
      return this.name;
   }
}
