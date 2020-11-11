package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.EmployeDTO;

/**
 * Mapper for the entity {@link Employe} and its DTO {@link EmployeDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface EmployeMapper extends EntityMapper<EmployeDTO, Employe> {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    EmployeDTO toDto(Employe employe);

    @Mapping(source = "userId", target = "user")
    Employe toEntity(EmployeDTO employeDTO);

    default Employe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employe employe = new Employe();
        employe.setId(id);
        return employe;
    }
}
