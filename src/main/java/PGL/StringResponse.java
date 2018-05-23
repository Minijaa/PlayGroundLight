package PGL;

import com.fasterxml.jackson.annotation.JsonProperty;
public class StringResponse {
    private String data;

    public StringResponse(String s){
        data = s;
    }

    public StringResponse(){
        //empty
    }

    @JsonProperty
    public String getData() {
        return data;
    }
}