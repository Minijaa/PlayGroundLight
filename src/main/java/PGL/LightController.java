//package PGL;
//
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Random;
//
//////@CrossOrigin(origins = {"http://localhost:8100", "file://", "http://localhost:8000", "127.0.0.1", "http://192.168.0.17:8000", "http://10.200.3.220:8000", "https://www.musikshopen.com:8000", "https://www.musikshopen.com", "https://www.musikshopen.com:8100", "https://www.musikshopen.com:443"})
//public class LightController {
//    private LightPost[] lightPosts;
//
//    public LightController() {
//        lightPosts = new LightPost[]{new LightPost("LightPost1", 0), new LightPost("LightPost2", 0), new LightPost("LightPost3", 0), new LightPost("LightPost4", 0)};
//    }
//
//    //@RequestMapping("/lit")
//    // Light a random lightpost green or red
//    // Red = 0, Green = 1
////    public LightPost lightRandomLightPost(int color) {
////        if (color < 0 || color > 1) {
////            throw new IllegalArgumentException("Illegal color");
////        }
////        Random rnd = new Random();
////        int offColor;
////        if (color == 0) {
////            offColor = 1;
////        } else {
////            offColor = 0;
////        }
////        int lightPostToLight = rnd.nextInt(4);
////        while (lightPosts[lightPostToLight].getColor() != 0) {
////            lightPostToLight = rnd.nextInt(4);
////        }
////        for (LightPost l : lightPosts) {
////            System.out.println(l.getName() + " får färgen " + offColor);
////            l.setColor(offColor);
////        }
////        lightPosts[lightPostToLight].setColor(color);
////        System.out.println(lightPosts[lightPostToLight].getName() + " är tänd med färgen " + color + "ny instans: " + this.toString());
////        return lightPosts[lightPostToLight]; //.name + " is lit";
////    }
//
//    // Light all lightposts green or red
//    // Red = 0, Green = 1
//    public void lightAllLightPosts(int color) {
//        if (color < 0 || color > 1) {
//            throw new IllegalArgumentException("Illegal color");
//        }
//        for (LightPost l : lightPosts) {
//            l.setColor(color);
//        }
//    }
//
//    @RequestMapping("/lightpost")
//    public LightPost getLightPost(
//            @RequestParam(value = "id", defaultValue = "0") String lightPostId
//    ) {
//        Integer lId = Integer.parseInt(lightPostId);
//        System.out.println("LightPost instans" + this.toString());
//        return lightPosts[lId];
//    }
//}
//
