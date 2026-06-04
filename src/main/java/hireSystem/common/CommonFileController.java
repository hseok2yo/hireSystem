package hireSystem.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hireSystem/common")
public class CommonFileController {

    @Resource(name = "commonFileService")
    private CommonFileService commonFileService;

    @RequestMapping("/download.do")
    public void download(
            @RequestParam("type") String type,
            @RequestParam("id")   int id,
            HttpServletResponse response) throws Exception {
        commonFileService.download(type, id, response);
    }
}