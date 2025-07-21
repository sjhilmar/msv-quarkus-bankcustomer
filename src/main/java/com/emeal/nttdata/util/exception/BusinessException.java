package com.emeal.nttdata.util.exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

import static com.emeal.nttdata.util.Constants.EXCEPTION_TYPE_BISNESS;

/**
 * <br/> BusinessException <br/>
 * <b>Class</b>: BusinessException<br/>
 * Copyright: &copy; 2025 JMR Code.<br/>
 *
 * @author JMR Code <br/>
 * <u>Developed by</u>: <br/>
 * <ul>
 *     <li>Sergi Jhilmar Alvarez Toledo (SJAT)</li>
 * </ul>
 * <u>Changes</u>:<br/>
 * <ul>
 *     <li>Jul. 19, 2025 Creaci&oacute;n de Clase.</li>
 * </ul>
 * @version 1.0
 */
@Getter
public class BusinessException extends RuntimeException{

    private final String code;
    private final String description;
    private final String type;
    private final Response.Status httpStatus;

    public BusinessException(String code,
                             String description,
                             Response.Status httpStatus) {
        super(description);
        this.code = code;
        this.description = description;
        this.type = EXCEPTION_TYPE_BISNESS;
        this.httpStatus = httpStatus;
    }

    public BusinessException(String code,
                             String description,
                             Response.Status httpStatus,
                             Throwable cause) {
        super(description, cause);
        this.code = code;
        this.description = description;
        this.type = EXCEPTION_TYPE_BISNESS;
        this.httpStatus = httpStatus;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.code = null;
        this.description = cause.getMessage();
        this.type = EXCEPTION_TYPE_BISNESS;
        this.httpStatus = Response.Status.INTERNAL_SERVER_ERROR;
    }

}
