package com.emeal.nttdata.model.dto;

import com.emeal.nttdata.model.enums.CustomerType;
import com.emeal.nttdata.model.enums.DocumentType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CustomerDto {

  @Pattern(regexp = "^[A-Za-z0-9]{1,11}$",
      message = "El documento debe tener hasta 11 caracteres alfanuméricos")
  private String document;
  private DocumentType documentType;
  @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$", message = "El nombre solo debe contener letras")
  private String firstName;
  @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ]+$", message = "El apellido solo debe contener letras")
  private String lastName;
  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
      message = "El correo electrónico no es válido")
  private String email;
  @Pattern(regexp = "^\\d{9}$", message = "El número de teléfono debe tener 9 dígitos")
  private String phoneNumber;
  @Size(max = 100, message = "La dirección debe tener hasta 100 caracteres")
  private String address;
  @Pattern(regexp = "^[MF]$", message = "El género debe ser 'M' o 'F'")
  private char gender;
  @Pattern(regexp = "^(Soltero|Casado|Viudo|Divorciado)$",
      message = "El estado civil debe ser Soltero, Casado, Viudo o Divorciado")
  private String state;
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
      message = "La fecha de nacimiento debe tener el formato yyyy-MM-dd")
  private String dateOfBirth;
  private CustomerType customerType;

}
