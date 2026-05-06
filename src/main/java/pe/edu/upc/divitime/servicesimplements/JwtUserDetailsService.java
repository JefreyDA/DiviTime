package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.repositories.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository repo;


    @Override
    public UserDetails loadUserByUsername(String emailUser) throws UsernameNotFoundException {

        User user = repo.findOneByEmailUser(emailUser);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not exists: " + emailUser));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(rol -> {
            authorities.add(new SimpleGrantedAuthority(rol.getNameRole()));
        });

        UserDetails ud = new org.springframework.security.core.userdetails.User(
                user.getEmailUser(),
                user.getPasswordUser(),
                true,
                true,
                true,
                true,
                authorities
        );

        return ud;
    }
}
