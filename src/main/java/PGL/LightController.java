package PGL;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@CrossOrigin(origins = {"http://localhost:8100", "file://","http://localhost:8000", "127.0.0.1", "http://192.168.0.17:8000", "http://10.200.3.220:8000"})
public class LightController {
    private LightPost[] lightPosts;

    public LightController() {
        lightPosts = new LightPost[]{new LightPost("LightPost1", 0), new LightPost("LightPost2", 0), new LightPost("LightPost3", 0), new LightPost("LightPost4",0)};
    }

    @RequestMapping("/lit")
    public LightPost lightRandomLightPost() {
        Random rnd = new Random();
        int lightPostToLight = rnd.nextInt(4);
        while (lightPosts[lightPostToLight].getColor() != 0) {
            lightPostToLight = rnd.nextInt(4);
        }
        for (LightPost l : lightPosts) {
            l.setColor(0);
        }
        lightPosts[lightPostToLight].setColor(1);
        return lightPosts[lightPostToLight]; //.name + " is lit";
    }

    @RequestMapping("/lightpost")
    public LightPost getLightPost(
            @RequestParam(value = "id", defaultValue = "0") String lightPostId
    ) {
        Integer lId = Integer.parseInt(lightPostId);
        return lightPosts[lId];
    }
}

