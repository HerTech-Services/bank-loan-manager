package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.ParametersDTO;

/**
 * Mapper for the entity {@link Parameters} and its DTO {@link ParametersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParametersMapper extends EntityMapper<ParametersDTO, Parameters> {
    default Parameters fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parameters parameters = new Parameters();
        parameters.setId(id);
        return parameters;
    }
}
