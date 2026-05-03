package pe.edu.upc.divitime.dtos;

public class UserFamilyDTO {
    private int userId;
    private int familyId;
    private Character familyPosition;

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
