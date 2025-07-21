package com.emeal.nttdata.resource;

import com.emeal.nttdata.model.dto.CustomerDto;
import com.emeal.nttdata.service.CustomerService;
import com.emeal.nttdata.util.exception.BusinessException;
import com.emeal.nttdata.util.exception.CustomException;
import com.emeal.nttdata.util.exception.dto.ExceptionResponse;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import static com.emeal.nttdata.util.CustomerUtil.getCurrentDateTimeFormatted;
import static com.emeal.nttdata.util.exception.dto.ExceptionCatalog.ERBS001;
import static com.emeal.nttdata.util.exception.dto.ExceptionCatalog.ERTC001;

@Path("/api/v1/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class CustomerResource {
  private final CustomerService customerService;

  public CustomerResource(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GET
  @Path("/{id}")
  public Uni<Response> getCustomerById(@PathParam("id") String id) {
    return customerService.getCustomerByClientId(id)
        .onItem().transform(customerDto -> Response.ok(customerDto).build())
        .onFailure().recoverWithItem(e -> Response.status(Response.Status.NOT_FOUND)
        .entity("Customer not found with id: " + id).build());
  }

  @GET
  @Path("/document/{document}")
  public Uni<Response> getCustomerByDocument(@PathParam("document") String document) {
    return customerService.getCustomerByDocument(document)
        .onItem().transform(customerDto -> Response.ok(customerDto).build())
        .onFailure().recoverWithItem(e -> Response.status(Response.Status.NOT_FOUND)
        .entity("Customer not found with document: " + document).build());
  }

  @GET
  @Path("/all")
  public Multi<Response> getAllCustomers() {
    return customerService.getAllCustomers()
        .onItem().transform(customerDto -> Response.ok(customerDto).build())
        .onFailure().recoverWithItem(e -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity("Error retrieving customers").build());
  }

  @POST
  public Uni<Response> createCustomer(CustomerDto customerDto) {
    return customerService.createCustomer(customerDto)
        .onItem().transform(createdCustomer -> Response.status(Response.Status.CREATED)
            .entity(createdCustomer).build())
        .onFailure().invoke(e -> log.error("Error creating customer: {}", customerDto, e))
        .onFailure().recoverWithItem(e -> {
          if (e instanceof BusinessException be) {
            return Response.status(be.getHttpStatus())
                .entity(ExceptionResponse.builder()
                    .code(be.getCode())
                    .dateTimeException(getCurrentDateTimeFormatted())
                    .description(be.getDescription())
                    .httpStatus(be.getHttpStatus())
                    .typeError(be.getType())
                    .build())
                .build();
          }
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(ERTC001.getCode() + ": " + ERBS001.getDescription()).build();
        });
  }

  @PUT
  @Path("/{document}/{documentType}")
  public Uni<Response> updateCustomer(@PathParam("document") String document,
                                      @PathParam("documentType") String documentType,
                                      CustomerDto customerDto) {
    return customerService.updateCustomer(document, documentType, customerDto)
        .onItem().transform(updatedCustomer -> Response.ok(updatedCustomer).build())
        .onFailure().invoke(e -> log.error("Error updating customer with document: {} " +
                "and type: {}", document, documentType, e))
        .onFailure().recoverWithItem(e -> {
          if (e instanceof BusinessException) {
            throw (BusinessException) e;
          } else {
            log.error("Unexpected error updating customer", e);
            throw new CustomException("T999", "Unexpected error updating customer",
                Response.Status.INTERNAL_SERVER_ERROR, e);
          }
        });
  }

  @DELETE
  @Path("/{document}/{documentType}")
  public Uni<Response> deleteCustomer(@PathParam("document") String document,
                                      @PathParam("documentType") String documentType) {
    return customerService.deleteCustomer(document, documentType)
        .onItem().transform(deleted -> Response.noContent().build())
        .onFailure().recoverWithItem(e -> Response.status(Response.Status.NOT_FOUND)
        .entity("Error deleting customer: " + e.getMessage()).build());
  }

}
