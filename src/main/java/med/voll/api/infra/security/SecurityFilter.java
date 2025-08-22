package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recuperar Token
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null){
            // Validar Token e recuperar o Subject (Usuário)
            var subject = tokenService.getSubject(tokenJWT);

            // Pegar o usuário
            var usuario = repository.findByLogin(subject);

            // Criar o DTO que representa o usuário
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            // Autenticação forçada
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null){
            System.out.println(authorizationHeader.replace("Bearer ", ""));
            return authorizationHeader.replace("Bearer ", "");

        }

        return null;
    }


}
