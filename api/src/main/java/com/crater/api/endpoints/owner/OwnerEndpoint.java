package com.crater.api.endpoints.owner;

import com.crater.api.dto.OwnerDTO;
import com.crater.api.dto.OwnerRegistrationDTO;
import com.crater.api.endpoints.AbstractEndpoint;
import com.crater.api.entity.Owner;
import com.crater.api.service.OwnerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/owner", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerEndpoint extends AbstractEndpoint {

    private final OwnerService ownerService;
    private final ModelMapper modelMapper;

    /**
     * Constructor
     * @param ownerService The {@link Owner} service where all business logic exists
     * @param modelMapper The {@link ModelMapper} bean to map DTO -> entity and vice versa
     */
    @Autowired
    public OwnerEndpoint(OwnerService ownerService, ModelMapper modelMapper) {
        this.ownerService = ownerService;
        this.modelMapper = modelMapper;
    }

    /**
     * Endpoint to create a new {@link Owner} in the database along with the associated schema and tables
     * @param ownerRegistrationDTO The registration info for the new {@link Owner}
     * @return A DTO with the saved {@link Owner} info such as applicationID
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> registerOwner(@Valid @RequestBody OwnerRegistrationDTO ownerRegistrationDTO) {
        Owner owner =  modelMapper.map(ownerRegistrationDTO, Owner.class);
        ownerService.createOwner(owner);
        OwnerDTO returnDTO = modelMapper.map(owner, OwnerDTO.class);
        return respondCreated(returnDTO);
    }
}
