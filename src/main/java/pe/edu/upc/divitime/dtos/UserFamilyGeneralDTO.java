package pe.edu.upc.divitime.dtos;

public class UserFamilyGeneralDTO {
    private int idUserFamily;
    private int userId;
    private int familyId;
    private Character familyPosition;

    public int getIdUserFamily() {
        return idUserFamily;
    }

    public void setIdUserFamily(int idUserFamily) {
        this.idUserFamily = idUserFamily;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public Character getFamilyPosition() {
        return familyPosition;
    }

    public void setFamilyPosition(Character familyPosition) {
        this.familyPosition = familyPosition;
    }
}
