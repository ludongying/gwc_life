package com.seven.gwc.config.constant;

/**
 * jwt相关配置
 *
 * @author GD
 * @date 2020-02-24 23:44
 */
public interface JwtConstants {

    String AUTH_HEADER = "authorization";

    String AUTH_NAME = "restapiuser";

    String AUTH_PATH = "/gwcApi/apiLogin";

    String SECRET = "ZGIyNTk5YTMwNjllNDc4ZTkwZTEyZmExMGJiZWIxMDM=";

    Long EXPIRATION = 172800L;

}
