enum Horario {
    ALMOCO{
        @Override
        public String toString() {
          return "almoco";
        }
      },
    JANTAR{
        @Override
        public String toString() {
          return "jantar";
        }
      };

    public static Horario convertToHorario(String horario) {
        switch(horario) {
            case "almoco":
                return Horario.ALMOCO;
            case "jantar":
                return Horario.JANTAR;
            default:
                throw new IllegalArgumentException("Database field horario was incorrect!");
        }
    }

    
}