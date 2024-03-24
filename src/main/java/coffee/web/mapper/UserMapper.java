package coffee.web.mapper;

import coffee.entity.User;
import coffee.web.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDTO>{ }
