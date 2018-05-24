package PGL;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class UserJson {

    private String name;
    private String email;
    private boolean online;
    private boolean checkedIn;
    private String checkedInPlayground;

    public UserJson(User temp){
        setName(temp.getName());
        setCheckedIn(temp.isCheckedIn());
        setCheckedInPlayground(temp.getCheckedInPlayground());
        setOnline(temp.isOnline());
        setEmail(temp.getEmail());
    }

    public UserJson(){

    }

    @JsonProperty
    public boolean isCheckedIn() {
        return checkedIn;
    }
    @JsonProperty
    public boolean isOnline() {
        return online;
    }
    @JsonProperty
    public String getCheckedInPlayground() {
        return checkedInPlayground;
    }
    @JsonProperty
    public String getEmail() {
        return email;
    }
    @JsonProperty
    public String getName() {
        return name;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public void setCheckedInPlayground(String checkedInPlayground) {
        this.checkedInPlayground = checkedInPlayground;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
