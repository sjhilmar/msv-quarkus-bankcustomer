package com.emeal.nttdata.util.exception.dto;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Formatter;

import static com.emeal.nttdata.util.CustomerUtil.getCurrentDateTimeFormatted;

/**
 * <br/> ExceptionResponse <br/>
 * <b>Class</b>: ExceptionResponse<br/>
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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class ExceptionResponse {
  private String code;
  private String description;
  private String typeError;
  private Response.Status httpStatus;
  private String dateTimeException;

  public String getDateTimeException() {
    return getCurrentDateTimeFormatted();
  }
}
