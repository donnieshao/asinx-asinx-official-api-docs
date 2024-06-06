package com.asinx.api;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.asinx.constants.AsinxPayMethods;
import com.asinx.models.ApiResponse;
import com.asinx.models.APApiBaseRequest;
import com.asinx.models.*;
import com.asinx.models.SystemClockRequest;
import com.asinx.utils.Base64ImgUtil;
import com.asinx.utils.APEncryptUtil;
import com.google.common.base.Strings;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;

public class AsinxPayApi {

    // test env gateway
    private static final String GATEWAY = "https://test.asinx.io/api-web";
    // APPID
    private static final String APP_ID = "app_447770";
    // SECRET
    private static String APP_SECRET = "b635dd5c87f7bf73387929203321b1e1";


    private static final int NOTIFY_TIMEOUT = 15000;

    private static final int NOTIFY_CONNECT_TIMEOUT = 1000;

    // if use proxy ,set this value true
    private static boolean useProxy = false;

    // proxy ip
    private static String proxyAddress = "127.0.0.1";

    // proxy port
    private static int proxyPort = 7070;


    /**
     * get system clock(system status)
     */
    public static void getSystemClock() {

        SystemClockRequest request = new SystemClockRequest();
        String result = postData(null, AsinxPayMethods.SYS_CLOCK, request);
        System.out.println("getSystemClock response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("getSystemClock response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("getSystemClock encode===>" + descStr);
        }
    }

    /**
     * get template list
     */
    public static void bankcardTemplateList() {
        BankcardTemplateListRequest request = new BankcardTemplateListRequest();
        String result = postData(null, AsinxPayMethods.BANKCARD_TEMPLATE_LIST, request);
        System.out.println("bankcardTemplateList response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("bankcardTemplateList response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("bankcardTemplateList encode result===>" + descStr);
        }
    }

    /**
     * user register,get user unique ID
     *
     * @param mobilePrefix mobile prefix
     * @param mobileNumber mobile number
     */
    public static void userRegister(String mobilePrefix, String mobileNumber, String email) {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setMobilePrefix(mobilePrefix);
        request.setMobileNumber(mobileNumber);
        request.setEmail(email);
        String result = postData(null, AsinxPayMethods.USER_REGISTER, request);
        System.out.println("userRegister response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("userRegister response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("userRegister encode result===>" + descStr);
        }
    }

    /**
     * set user profession and user info
     *
     * @param uId
     */
    public static void setUserProfession(String uId) {
        UserSetProfessionRequest request = new UserSetProfessionRequest();
        request.setFirst_name("ming");
        request.setFirst_name_en("ming");
        request.setLast_name("li");
        request.setLast_name_en("li");
        request.setBirthday("2000-01-01");
        request.setId_type("passport");
        request.setCountry("CN");
        request.setNumber("123456");
        request.setExpiry_date("2027-01-01");
        // TODO 路径按需修改
        request.setFrontImg(Base64ImgUtil.GetImageStr("/Users/donnie/asinx-official-api-docs/src/main/resources/passport1.jpg", "jpg"));
        request.setBackImg(Base64ImgUtil.GetImageStr("/Users/donnie/asinx-official-api-docs/src/main/resources/passport2.jpg", "jpg"));
        String result = postData(uId, AsinxPayMethods.SET_USER_PROFESSION, request);
        System.out.println("setUserProfession response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("setUserProfession response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("setUserProfession encode result===>" + descStr);
        }
    }

    public static void setHolderInfo(String uId) {
        SetCardHolderRequest request = new SetCardHolderRequest();
        request.setFirstName("ming");
        request.setLastName("ming");
        request.setPhoneNumber("123456");
        request.setCountryCode("CN");
        request.setResidentialAddressCity("BJ");
        request.setResidentialAddressCountry("China");
        request.setResidentialAddressLine1("line1");
        request.setResidentialAddressLine2("line2");
        request.setResidentialAddressState("BJ");
        request.setResidentialAddressPostalCode("10010");
        String result = postData(uId, AsinxPayMethods.SET_HOLDER_INFO, request);
        System.out.println("setHolderInfo response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("setHolderInfo response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("setHolderInfo encode result===>" + descStr);
        }
    }

    /**
     * apply bankcard
     *
     * @param uId
     * @param bankcardId
     * @param residenceAddress
     */
    public static void applyBankcard(String uId, Integer bankcardId, Integer userBankcardId, String residenceAddress) {
        ApplyBankcardRequest request = new ApplyBankcardRequest();
        request.setBankcardId(bankcardId);
//        request.setUserBankcardId(userBankcardId);
        request.setResidenceAddress(residenceAddress);
//        request.setTag("111111liwheefowhfoij");
        String result = postData(uId, AsinxPayMethods.APPLY_BANKCARD, request);
        System.out.println("applyBankcard response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("applyBankcard response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("applyBankcard encode result===>" + descStr);
        }
    }

    /**
     * recharge bankcard
     *
     * @param uId
     * @param userBankcardId
     * @param amount
     */
    public static void rechargeBankcard(String uId, Integer userBankcardId, BigDecimal amount, BigDecimal targetAmount) {
        RechargeBankcardRequest request = new RechargeBankcardRequest();
        request.setUserBankcardId(userBankcardId);
        request.setAmount(amount);
        request.setTargetAmount(targetAmount);
        String result = postData(uId, AsinxPayMethods.RECHARGE_BANKCARD, request);
        System.out.println("rechargeBankcard response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("rechargeBankcard response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("success rechargeBankcard encode result===>" + descStr);
        }else{
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("failed rechargeBankcard encode result===>" + descStr);
        }
    }

    /**
     * set bankcard pin
     *
     * @param uId
     * @param userBankcardId
     * @param pin
     */
    public static void setBankcardPin(String uId, Integer userBankcardId, String pin) {
        SetBankcardPinRequest request = new SetBankcardPinRequest();
        request.setUserBankcardId(userBankcardId);
        request.setPin(pin);
        String result = postData(uId, AsinxPayMethods.SET_BANKCARD_PIN, request);
        System.out.println("setBankcardPin response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("setBankcardPin response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("setBankcardPin encode result===>" + descStr);
        }
    }

    /**
     * query bankcard transactions
     *
     * @param uId
     * @param userBankcardId
     */
    public static void queryBankcardTransactions(String uId, Integer userBankcardId) {
        QueryBankcardTransactionsRequest request = new QueryBankcardTransactionsRequest();
        request.setUserBankcardId(userBankcardId);
//        request.setFromTimestamp(1690878577000L);
//        request.setEndTimestamp(1690878578000L);
        request.setPageSize(100);
        request.setPageNum(1);
        String result = postData(uId, AsinxPayMethods.QUERY_BANKCARD_TRANSACTIONS, request);
        System.out.println("queryBankcardTransactions response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("queryBankcardTransactions response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("queryBankcardTransactions encode result===>" + descStr);
        }
    }

    /**
     * query bankcard transactions
     *
     * @param uId
     * @param userBankcardId
     */
    public static void queryBankcardBalance(String uId, Integer userBankcardId) {
        QueryBankcardBalanceRequest request = new QueryBankcardBalanceRequest();
        request.setUserBankcardId(userBankcardId);
        String result = postData(uId, AsinxPayMethods.QUERY_BANKCARD_BALANCE, request);
        System.out.println("queryBankcardBalance response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("queryBankcardBalance response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("queryBankcardBalance encode result===>" + descStr);
        }
    }

    /**
     * query bankcard Information
     *
     * @param uId
     * @param userBankcardId
     */
    public static void queryBankcardInfo(String uId, Integer userBankcardId) {
        QueryBankcardInfoRequest request = new QueryBankcardInfoRequest();
        request.setUserBankcardId(userBankcardId);
        String result = postData(uId, AsinxPayMethods.QUERY_BANKCARD_INFO, request);
        System.out.println("queryBankcardInfo response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("queryBankcardInfo response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("queryBankcardInfo encode result===>" + descStr);
        }
    }

    public static void queryBankcardOrder(String uId, Integer userBankcardId,String requestOrderId) {
        QueryBankcardOrderRequest request = new QueryBankcardOrderRequest();
        request.setUserBankcardId(userBankcardId);
        request.setRequestOrderId(requestOrderId);
        String result = postData(uId, AsinxPayMethods.CARD_ORDER, request);
        System.out.println("queryBankcardOrder response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("queryBankcardOrder response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("queryBankcardOrder encode result===>" + descStr);
        }
    }

    /**
     * uer recharge info
     *
     * @param uId
     */
    public static void userUSDRechargeInfo(String uId, BigDecimal amount) {
        UserRechargeInfoRequest request = new UserRechargeInfoRequest();
        request.setAmount(amount);
        String result = postData(uId, AsinxPayMethods.USD_RECHARGE_INFO, request);
        System.out.println("userRechargeInfo response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("userRechargeInfo response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("userRechargeInfo encode result===>" + descStr);
        }
    }

    /**
     *
     */
    @Deprecated
    public static void accountAsset() {
        QueryMerchantAssetRequest request = new QueryMerchantAssetRequest();
        String result = postData(null, AsinxPayMethods.USER_ACCOUNT_ASSET, request);
        System.out.println("AccountAsset response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("AccountAsset response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("AccountAsset encode result===>" + descStr);
        }
    }

    public static void accountRecharge() {
        QueryAccountRechargeRequest request = new QueryAccountRechargeRequest();
        request.setPageSize(10);
//        request.setUid("ewaoaylm5ueywbib");
//        request.setSymbol("USDT");
        request.setPageNum(1);
        String result = postData(null, AsinxPayMethods.USER_ACCOUNT_USER_RECHARGE, request);
        System.out.println("accountRecharge response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("merchantAsset response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("accountRecharge encode result===>" + descStr);
        }
    }

    public static void merchantAsset() {
        QueryMerchantAssetRequest request = new QueryMerchantAssetRequest();
        String result = postData(null, AsinxPayMethods.MERCHANT_ASSET, request);
        System.out.println("merchantAsset response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("merchantAsset response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("accountRecharge encode result===>" + descStr);
        }
    }

    public static void merchantRecharge() {
        QueryMerchantRechargeRequest request = new QueryMerchantRechargeRequest();
        request.setPageSize(10);
        request.setPageNum(1);
        String result = postData(null, AsinxPayMethods.MERCHANT_RECHARGE, request);
        System.out.println("merchantRecharge response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("merchantRecharge response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("merchantRecharge encode result===>" + descStr);
        }
    }

    public static void updateBankcardStatus(String uId, Integer userBankcardId,boolean enable) {
        UpdateBankcardStatusRequest request = new UpdateBankcardStatusRequest();
        request.setEnable(enable);
        request.setUserBankcardId(userBankcardId);

        String result = postData(uId, AsinxPayMethods.UPDATE_CARD_STATUS, request);
        System.out.println("updateBankcardStatus response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("updateBankcardStatus response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("updateBankcardStatus encode result===>" + descStr);
        }
    }

    public static void closeBankcard(String uId, Integer userBankcardId) {
        CloseBankcardRequest request = new CloseBankcardRequest();
        request.setUserBankcardId(userBankcardId);

        String result = postData(uId, AsinxPayMethods.CLOSE_CARD, request);
        System.out.println("closeBankcard response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("closeBankcard response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("closeBankcard encode result===>" + descStr);
        }
    }

    public static void  kycGateway() {
        KycGatewayRequest request = new KycGatewayRequest();
        request.setDoneViewURL("https://www.asinx.io/done");
        request.setTimeoutViewURL("https://www.asinx.io/timeout");
        String result = postData(null, AsinxPayMethods.KYC_GATEWAY, request);
        System.out.println("kycGateway response String:  " + result);
        ApiResponse<String> apiResponse = JSON.parseObject(result, new TypeReference<ApiResponse<String>>() {
        });
        System.out.println("kycGateway response Object:  " + apiResponse);
        if (apiResponse.isSuccess()) {
            String descStr = APEncryptUtil.decode(APP_SECRET, apiResponse.getResult());
            System.out.println("kycGateway encode result===>" + descStr);
        }
    }

    public static void main(String[] args) {
//        getSystemClock();

//        bankcardTemplateList();
//        updateBankcardStatus();
//        setHolderInfo("35920");
//        kycGateway();

//        userRegister("86","889988","122132221212211@188.com");
//        setUserProfession("35920");
//        applyBankcard("36046",24,null,"KR");
//        rechargeBankcard("36046",19280,new BigDecimal(8),new BigDecimal(50));
//        closeBankcard("36046",19280);
        queryBankcardOrder("36046",19280,"CLOSE2406031639263553919");
//        updateBankcardStatus("35987",19272,true);

//        setBankcardPin("35910",136,"123456");
//        queryBankcardTransactions("35974",19163);
//        queryBankcardBalance("35987",19250);
//        queryBankcardInfo("36046",19280);
//        userUSDRechargeInfo("35910",new BigDecimal(2));
//        accountAsset();
//        accountRecharge();
//        merchantAsset();
//        merchantRecharge();
    }

    /**
     * util method
     * send post data
     *
     * @param uId
     * @param method
     * @param request
     * @return
     */
    private static String postData(String uId, String method, APApiBaseRequest request) {

        String jsonDataString = JSON.toJSONString(request);
        String url = GATEWAY + method;
        System.out.println("url=" + url);
        System.out.println("post path：" + method);
        System.out.println("body：" + jsonDataString);

        String sendContent = method + jsonDataString;
        System.out.println("originString=" + sendContent);
        String signature = APEncryptUtil.encode(APP_SECRET, sendContent);
        System.out.println("sign=" + signature);
        HttpRequest httpRequest = HttpRequest.post(url).header("appId", APP_ID).header("sign", signature);

        if (!Strings.isNullOrEmpty(uId)) {
            httpRequest.header("uId", uId);
        }

        System.out.println("post all headers: " + httpRequest.headers());

        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyAddress, proxyPort));
            httpRequest.setProxy(proxy);
        }

        httpRequest.timeout(NOTIFY_TIMEOUT)
                .body(jsonDataString)
                .charset(StandardCharsets.UTF_8)
                .setConnectionTimeout(NOTIFY_CONNECT_TIMEOUT);
        return httpRequest.execute().body();
    }
}
