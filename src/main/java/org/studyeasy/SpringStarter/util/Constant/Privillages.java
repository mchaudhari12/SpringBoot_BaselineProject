package org.studyeasy.SpringStarter.util.Constant;

public enum Privillages {
    
    RESET_ANY_USER_PASSWORD(1L,"RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL(2L,"ACCESS_ADMIN_PANEL");

    private Long Id;
    private String Privillage;
    
    private Privillages(Long id, String Privillage) {
        Id = id;
        this.Privillage = Privillage;
    }

    public Long getId() {
        return Id;
    }

    public String getPrivillage() {
        return Privillage;
    }

    

}
