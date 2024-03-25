package coffee.web.mapper;

import coffee.entity.User;
import coffee.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDTO>{
    @Override
    User toEntity(UserDTO dto);
}
