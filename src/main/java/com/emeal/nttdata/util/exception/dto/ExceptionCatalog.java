package com.emeal.nttdata.util.exception.dto;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/> ExceptionCatalog <br/>
 * <b>Class</b>: ExceptionCatalog<br/>
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
@AllArgsConstructor
@Getter
public enum ExceptionCatalog {

  ERTC001("ERBS001", "Error al procesar la solicitud", Response.Status.BAD_REQUEST),
  ERBS001("ERBS002", "Customer already exists",Response.Status.CONFLICT),
  ERBS002("ERBS003", "Customer does not exist", Response.Status.NOT_FOUND);

  private final String code;
  private final String description;
  private final Response.Status httpStatus;

}
