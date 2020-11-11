package tect.her.ccm.service.mapper;

import org.mapstruct.*;
import tect.her.ccm.domain.*;
import tect.her.ccm.service.dto.EmployeEntiteDTO;

/**
 * Mapper for the entity {@link EmployeEntite} and its DTO {@link EmployeEntiteDTO}.
 */
@Mapper(componentModel = "spring", uses = { EmployeMapper.class, EntiteMapper.class })
public interface EmployeEntiteMapper extends EntityMapper<EmployeEntiteDTO, EmployeEntite> {
    @Mapping(source = "employe.id", target = "employeId")
    @Mapping(source = "employe.nomEmp", target = "employeNomEmp")
    @Mapping(source = "entite.id", target = "entiteId")
    @Mapping(source = "entite.label", target = "entiteLabel")
    EmployeEntiteDTO toDto(EmployeEntite employeEntite);

    @Mapping(source = "employeId", target = "employe")
    @Mapping(source = "entiteId", target = "entite")
    EmployeEntite toEntity(EmployeEntiteDTO employeEntiteDTO);

    default EmployeEntite fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeEntite employeEntite = new EmployeEntite();
        employeEntite.setId(id);
        return employeEntite;
    }
}
