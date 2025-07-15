package com.emeal.nttdata.resource;

import com.emeal.nttdata.model.dto.CustomerDto;
import com.emeal.nttdata.service.CustomerService;
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

@Path("/api/v1/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
  private final CustomerService customerService;

  public CustomerResource(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GET
  @Path("/{id}")
  public Uni<Response> getCustomerById(@PathParam("id") String id) {
    return customerService.getCustomerById(id)
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
        .onFailure().recoverWithItem(e -> Response.status(Response.Status.BAD_REQUEST)
        .entity("Error creating customer: " + e.getMessage()).build());
  }

  @PUT
  @Path("/{id}")
  public Uni<Response> updateCustomer(@PathParam("id") String id, CustomerDto customerDto) {
    return customerService.updateCustomer(id, customerDto)
        .onItem().transform(updatedCustomer -> Response.ok(updatedCustomer).build())
        .onFailure().recoverWithItem(e -> Response.status(Response.Status.NOT_FOUND)
        .entity("Error updating customer: " + e.getMessage()).build());
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> deleteCustomer(@PathParam("id") String id) {
    return customerService.deleteCustomer(id)
        .onItem().transform(deleted -> Response.noContent().build())
        .onFailure().recoverWithItem(e -> Response.status(Response.Status.NOT_FOUND)
        .entity("Error deleting customer: " + e.getMessage()).build());
  }

}
