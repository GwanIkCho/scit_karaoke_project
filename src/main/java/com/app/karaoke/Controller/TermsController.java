package com.app.karaoke.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/terms")
public class TermsController {

    @GetMapping("service-terms-web")
    public String serviceTermsWeb() {
        return "/terms/service-terms-web";
    }

    @GetMapping("information-web")
    public String informationWeb() {
        return "/terms/information-web";
    }

    @GetMapping("marketing-terms-web")
    public String margetingTermsWeb() {
        return "/terms/marketing-terms-web";
    }

    @GetMapping("privacyprovision-web")
    public String privacyProvisioningWeb() {
        return "/terms/privacyprovision-web";
    }


}
