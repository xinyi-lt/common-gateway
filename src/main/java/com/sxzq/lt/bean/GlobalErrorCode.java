package com.sxzq.lt.bean;

/**
 * 全局异常编码
 * Created by feizi on 2018/6/15.
 */
public enum GlobalErrorCode implements ErrorCode {

    /**
     ****************2xx 成功 **************************
     */
    SUCCESS(200, "OK"),

    /**
     ****************3xx 重定向 ************************
     */

    /**
     ****************4xx 客户机中出现的错误 **************
     */
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /**
     ****************5xx 服务器中出现的错误 **************
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /**
     ****************6xx 应用通用异常信息 **************
     */
    INVALID_PARAM(600, "无效参数"),
    UNSUPPORT_IMAGE_TYPE(601, "该图片格式暂不支持"),
    UNKNOW_STORE_PATH(602, "找不到文件"),
    MISSING_PARAMETER(603, "必填参数缺失"),
    SIGNATURE_DOESNOT_MATCH(604, "方法签名不匹配"),
    NULL_TOKEN(605, "用户token信息不存在"),
    MAX_UPLOAD_SIZE_EXCEEDED(606, "上传文件大小超过限制"),
    HTTP_REQUEST_METHOD_NOTSPPORTED(607, "请求方法不支持"),
    RESOURCE_NOT_FOUND(608, "资源不存在,或查询数据库的结果为空"),
    VERIFICATION_CODE_ERROR(609, "验证码错误"),
    SEND_EMAIL_FAILED(610, "邮件发送失败"),
    SEND_PHONECODE_FAILED(611, "手机验证码发送失败"),
    PARAMETER_FORMAT_NOT_SUPPORTED(612, "参数数据格式不对"),
    INSERT_FAILED(613, "入库失败"),
    DATA_NOT_EXIST(614, "数据不存在"),
    NOT_COMPLIANCE(615, "不合规"),
    /**
     * 服务接口异常
     */
    SERVICE_INTERFACE_EXCEPTION(630, "服务接口异常"),

    /**
     ****************7xx 用户相关异常信息 **************
     */
    USER_UNBIND_EMAIL(700, "用户尚未绑定邮箱"),
    PHONENO_HAS_EXISTED(701, "手机号码已存在"),
    EMAIL_HAS_EXISTED(702, "邮箱已存在"),
    IDCARD_HAS_EXISTED(703, "用户身份证号已存在"),
    USERNAME_OR_PASSWORD_INCORRECT(704, "用户名或密码错误"),
    USER_NO_OPERATION_PERMISSION(705, "用户没有操作权限"),

    /**
     ****************8xx MQ消息相关异常信息 **************
     */
    SAVE_MESSAGE_IS_NULL(801, "保存的消息为空"),
    MESSAGE_CONSUMER_QUEUE_IS_NULL(802, "消息的消费队列为空"),
    MESSAGE_MQ_CONFIG_ERROR(803, "MQ配置错误"),
    MESSAGE_MQ_INSTANCE_ERROR(804, "MQ消息中间内部错误"),
    PARAM_ERROR(805, "参数错误"),
    MESSAGE_SEND_NOT_SUCCESS(806, "MQ消息中间内部错误");

    /**
     * 状态编码
     */
    private final int code;

    /**
     * 提示消息
     */
    private final String message;

    GlobalErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
