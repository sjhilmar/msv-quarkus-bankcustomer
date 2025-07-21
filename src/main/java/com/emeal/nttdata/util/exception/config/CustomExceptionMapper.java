package com.emeal.nttdata.util.exception.config;

import com.emeal.nttdata.util.exception.CustomException;
import com.emeal.nttdata.util.exception.dto.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static com.emeal.nttdata.util.CustomerUtil.getCurrentDateTimeFormatted;

/**
 * <br/> CustomExceptionMapper <br/>
 * <b>Class</b>: CustomExceptionMapper<br/>
 * Copyright: &copy; 2025 JMR Code.<br/>
 *
 * @author JMR Code <br/>
 * <u>Developed by</u>: <br/>
 * <ul>
 *     <li>Sergi Jhilmar Alvarez Toledo (SJAT)</li>
 * </ul>
 * <u>Changes</u>:<br/>
 * <ul>
 *     <li>Jul. 20, 2025 Creaci&oacute;n de Clase.</li>
 * </ul>
 * @version 1.0
 */
@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

  /**
   * @param exception the exception to map to a response.
   * @return
   */
  @Override
  public Response toResponse(CustomException exception) {
    ExceptionResponse response = ExceptionResponse.builder()
        .code(exception.getCode())
        .description(exception.getDescription())
        .typeError(exception.getType())
        .httpStatus(exception.getHttpStatus())
        .dateTimeException(getCurrentDateTimeFormatted())
        .build();

    return Response.status(exception.getHttpStatus())
        .entity(response)
        .build();
  }
}
