package com.seven.gwc.core.base;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(maxAge = 3600)
@RequestMapping(produces = "application/json")
public class BaseController {

    protected static BaseResult SUCCESS = new BaseResult(true,200,"请求成功");

}
