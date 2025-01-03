package entity;

public class Photo {
    private int id;
    private int effectId;
    private String tableName;

    public Photo() {
    }

    public Photo(int id, int effectId, String tableName) {
        this.id = id;
        this.effectId = effectId;
        this.tableName = tableName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEffectId() {
        return effectId;
    }

    public void setEffectId(int effectId) {
        this.effectId = effectId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
