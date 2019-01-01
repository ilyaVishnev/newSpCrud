package com.avito;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/image")
public class ImageController {

    final Logger logger = Logger.getLogger(ImageController.class.getName());
    private final String[] arrayParametrs = new String[1];

    @GetMapping
    @ResponseBody
    protected String getImage() {
        JSONObject object = new JSONObject();
        String fileway = arrayParametrs[0];
        if (fileway.equals("")) {
            fileway = "/pictures/emptyPhoto.JPG";
        } else {
            fileway = "/pictures/" + fileway.substring(fileway.lastIndexOf("\\") + 1);
        }
        object.put("image", fileway);
        return object.toString();

    }

    @PostMapping
    protected String writeImage(@RequestParam("file") MultipartFile mFile) throws IOException {
        if (!mFile.isEmpty()) {
            File file = new File(arrayParametrs[0] = "C:\\projects\\myMVC\\src\\main\\web\\pictures\\" + mFile.getOriginalFilename());
            mFile.transferTo(file);
        }
        return "createCar";
    }
}
