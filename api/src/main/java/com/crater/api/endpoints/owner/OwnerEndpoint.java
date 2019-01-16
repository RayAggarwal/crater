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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/owner/", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerEndpoint extends AbstractEndpoint {

    private final OwnerService ownerService;
    private final ModelMapper modelMapper;

    @Autowired
    public OwnerEndpoint(OwnerService ownerService, ModelMapper modelMapper) {
        this.ownerService = ownerService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerDTO> registerOwner(OwnerRegistrationDTO ownerRegistrationDTO) {
        Owner owner =  modelMapper.map(ownerRegistrationDTO, Owner.class);
        ownerService.createOwner(owner);
        OwnerDTO returnDTO = modelMapper.map(owner, OwnerDTO.class);
        return respondCreated(returnDTO);
    }
}
