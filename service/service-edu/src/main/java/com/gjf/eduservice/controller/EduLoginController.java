package com.gjf.eduservice.controller;

import com.gjf.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin  //解决跨域问题
public class EduLoginController {

    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606391846361&di=1fc5d0add7ef267fc50253ec5eab888b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3D580e773405f431adbcd243317b37ac0f%2F50f2f9dde71190ef9c7f0079c71b9d16fffa60dc.jpg");
    }

}
