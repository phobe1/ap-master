package com.you.ap.provider.config;


public class AlipayConfig {
        // 1.商户appid
        public static String APPID = "2017121900985060";

        // 2.私钥 pkcs8格式的
        public static String RSA_PRIVATE_KEY ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQChoa7DseUBZyr7LBbIkQKKpyJWaDLSLcAclnHT/FZdLuj3A3HHD7z+CX99Rgyd96cDIK9WDbj59rxyF6bqN5bUooo/Nrnt5vy75WXbP4UI9fAteipWGCxMbCbtYXvr9cNiteEb/jm2o4GtOjBF3r70N5zQd9e1qe+hwQrhRPqioXS4iSJ/mnIzNKyy5DWmQSyMf0fULxHRFz+/GWMf23bp7rZW87k3BmikNvvLMbJcQY7QEiZFAlJNVKVeMRKoN67xcUWb3nyXhzQlpz3MKevCELJWPvNT5RiQdEAawncCKca8QjHH16IkEeENiE06D2nVd32qDsqrON0AyOe1RxAxAgMBAAECggEAFkNR8iyXsYjHV89r1/Z5ViXNSf+SOHcKxiVGyn8wWidy+UslKdkWHBusztGOPVf2Tiz7WawMNYnxzPlLCWncwa8dwbVFCOrPFwZy8C8goOpXSbxRBojP2TrwZbQOwKKaZgdNWXC7/FcrLnQcYBNdVAAw/lnzgVdF5Y7Uc9DvgOTpzhvSAm7su+3LBfgmn764ayDfHrxHWuwembe8wr7K+8BO0pXKoob7jialx2zJAwDLInUxViad6XrVYiQSWRYF7QJJN5RHFP6C77glwZ2K4tauwwieXyHC3Wx4rdbw1VJr74ggEsrnYhYFgVT+SfWGU8lK9KTad0IkchIUG6r+UQKBgQDnwh7trVyK1LnhSxxyBIbNbJ9Fu51IVtzxZgvWq0j2A+3j8lTfOlcpwNALXVESlW9V0XfsKUCGirtcCYPJHtydrhHs2BuM+LYwIthuoRZa3pay8MBrWMX+r7Pfey/GXTrN4dpvO8tbKcwWHA0tSoo03+0U65QP4CTOnBO0510vVQKBgQCyicCdLTExrLyPr/MxpAPuXzi80z91C8ErGg6kHyqhfwzqPw4n4ZV283KgDoRqWoW0hVQZoJC0P1vdKzsgfv55SM+6frE/12BnEE31i5MrPOCkOqksE0ensqhwcGTQV7Qlv1IE/tGnSUx4Aq5S+9yotpf45YHfg/bTfCVpUXhFbQKBgQDLUcM+cqPIbD2yh+mpoYz9tTi5vZGLgCEPap4iM1yr7+CI5PVObWlAiRY7kh9o9U6adE0IE1y3bAhZA0PpcrjLYIX7SdBJyXxUGI3V6F6rU7HTxGCbDGOPP2fHPNQ561qGeojeow+Rp8aS8wMgY8+B+Z2Nh+r6ToMb+1NSltNqjQKBgHT6hNV3EtB1WWbb5gAUcu7wPQCbeOx7sylGfWlTcl82G4QvpFrFF2VExUnRuTjl7s0EvsG7zpQLH2meNbNpAdr9tf1/nSszNtrQeAP2W4rs7FMujEFwsHZUPMDPI37OuB4zaM4VF9D/2baYKxf8Jev1F0XhOmL01/hhUzHaS29RAoGAeoHheKRayofb8qYUMUqIawKyguH4dAF+nG/bc10nRlG1IlZjJs7r0De9w04lwNnDCw/Wo+EfI/eMZjymTKxyJkeO0EyBUUlsYE3w8QDvsKO8mtCaitkf8gf5vYQhKPO154rn7+rM4Nm7guwaJ6FncT65TArRnKWJcDMeOCZhZK4=";


        // 3.支付宝公钥
        public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAthuLwYUuTRwjIgNSiVFoTZDfn6Hdt0Eyz4noDcfXqySG89qFWRtFktUCmFrJJNHlwArymuZDzlGfaosUIb9HD3ULJaK+oyGKgNvDR6PgWpXIo5Oth/sx9aHR8fXxhz5lMwN1t2FwwdM54OxbLAt+Vh6akjENJTF9zsPDX0HzAyRGX/IwPUvNns/IX3yNE0z0zVczxFGtLTUwaO2YG9TmAzKAizJB2E826KhD2pbxYhGNYmgCAm9Qq6PMwJ8g7m/4yAU8cN2E64u+91ATPiQv68+nhLaMYFjjq1tua+oCW5sxAJUTQi3WQ1di3P2CpLzF79A3FvqNp2S9wwLIJyoSTwIDAQAB";

        // 4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
        public static String notify_url = "http://47.104.8.224:8900/ap/alipay/notify_url";

        // 5.页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
        public static String return_url = "http://47.104.8.224:8900/ap/alipay/return_url";

        // 6.请求网关地址
        public static String URL = "https://openapi.alipay.com/gateway.do";

        // 7.编码
        public static String CHARSET = "UTF-8";

        // 8.返回格式
        public static String FORMAT = "json";

        // 9.加密类型
        public static String SIGNTYPE = "RSA2";


}

