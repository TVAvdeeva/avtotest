package data.personals;

public enum UserInfoData {
   FIRST_NAME("Мария"),
   FIRST_NAME_LATIN("Maria"),
   SECOND_NAME("Иванова"),
   SECOND_NAME_LATIN("Ivanova"),
   BLOG_NAME("testblok"),
   BIRTH_DATE("01.01.1985"),

   GENDER ("Женский"),
   WORK ("Инженер"),
   COMPANY ("WB");



   private String name;

   UserInfoData(String name){
      this.name = name;
   }

   public String getName(){
      return this.name;
   }
}

