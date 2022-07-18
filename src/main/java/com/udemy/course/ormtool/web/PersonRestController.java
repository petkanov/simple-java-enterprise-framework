package com.udemy.course.ormtool.web;

import com.udemy.course.ormtool.model.Bank;
import com.udemy.course.ormtool.model.Currency;
import com.udemy.course.ormtool.model.Person;
import com.udemy.course.ormtool.web.model.PersonDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@Path("/person")
public class PersonRestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper mapper = new ModelMapper();

    @GET
    @Path("/{personId}")
    public PersonDTO getGroupsList(@PathParam("personId") int userId) {
        Person person = new Person("Virgilius Lorentis", 23);
        Bank bank = new Bank("JPMorgan");
        bank.createAccount(person, Currency.EURO);
        bank.createAccount(new Person("John Pierpont Morgan",31), Currency.USD);
        person.addFriend(new Person("Laura Smith", 19));

        PersonDTO personDTO = mapper.map(person, PersonDTO.class);

        log.error("--------------------------------------------------------------------------------------");
        log.info(person.toString());
        log.error("--------------------------------------------------------------------------------------");

        return personDTO;
    }

    @POST
    @Path("/pp")
    public int createPerson(Person person, @Context HttpHeaders headers) {
        log.info("--------------------------------------------------------------------------------------");
        log.info(person.toString());
        log.info(headers.getRequestHeaders().toString());
        log.info("--------------------------------------------------------------------------------------");
        return 99;
    }
}
