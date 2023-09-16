package xyz.sallai.r1.controller;

import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.CrossOrigin;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.RestController;

/**
 * Description: [对类的简单描述]
 * <p>
 * Author:
 * Date: 2023/8/11
 */
@Controller
@CrossOrigin
public class WebPageController {
    @GetMapping(path = "/")
    public String index() {
        return "forward:/index.html";
    }
}
