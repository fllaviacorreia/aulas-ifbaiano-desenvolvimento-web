package com.project.payment.domain.service;

import com.project.payment.domain.dto.*;
import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.model.ParcelamentoModel;
import com.project.payment.domain.model.RolesUser;
import com.project.payment.domain.model.User;
import com.project.payment.domain.repository.ClienteRepository;
import com.project.payment.domain.repository.ParcelamentoRepository;
import com.project.payment.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsImplements userDetailsImplements;
    private final ClienteRepository clienteRepository;
    private final GerenciamentoClienteService gerenciamentoClienteService;
    private final ParcelamentoRepository parcelamentoRepository;
    private final GetParcelamentoService getParcelamentoService;

    private ResponseEntity<ResponseAuth> login(String email, String password) {
        //log in
        UserDetails userDetails = userDetailsImplements.loadUserByUsername(email);

        //generate a new authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());

        //set authentication security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // return a token and the user's role
        ResponseAuth responseAuth = new ResponseAuth();
        responseAuth.setToken(jwt(authentication));
        responseAuth.setProfile(getCurrentUser(email));

        return ResponseEntity.ok(responseAuth);
    }

    public ResponseEntity<ResponseAuth> authenticate(@NotNull LoginDTO loginDTO) {
        Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword()))
            throw new NegocioException("email ou senha incorretos");

        return login(loginDTO.getEmail(), loginDTO.getPassword());
    }

    @Transactional
    public ResponseEntity<ResponseAuth> registerAdmin(RegisterDTO user){
        User userFinal = setUser(user);
        userFinal.setRole(RolesUser.ADMIN);
        gerenciamentoClienteService.save(userFinal.getClienteModel());
        Optional<ClienteModel> clienteModelDB = clienteRepository.findByCpf(user.getClienteModel().getCpf());

       
        userFinal.getClienteModel().setId(clienteModelDB.get().getId());
        userRepository.save(userFinal);
        return login(user.getLoginDTO().getEmail(), user.getLoginDTO().getPassword());
    }

    @Transactional
    public ResponseEntity<ResponseAuth> register(RegisterDTO user) {
        // verifica se o cliente cadastrado pelo admin (cria somente na tabela cliente) possui usuário
        Optional<ClienteModel> clienteModelDB = clienteRepository.findByCpf(user.getClienteModel().getCpf());

        if (clienteModelDB.isEmpty()){
            // cliente não existe
            User userFinal = setUser(user);

            ClienteModel clienteModel = gerenciamentoClienteService.save(user.getClienteModel());
            userFinal.setClienteModel(clienteModel);
            userRepository.save(userFinal);
        }
        else{
            // cliente existe
            Optional<User> userDB = userRepository.findByClienteModel(clienteModelDB.get());

            if (clienteModelDB.isPresent() && userDB.isPresent()) {
                // cliente já possui usuário
                throw new NegocioException("Já existe uma conta de usuário vinculada a esse CPF.");
            } else{
                // cliente não possui usuário
                User userFinal = setUser(user);
                userFinal.getClienteModel().setId(clienteModelDB.get().getId());
                gerenciamentoClienteService.save(userFinal.getClienteModel());
                userRepository.save(userFinal);
            }
        }
        return login(user.getLoginDTO().getEmail(), user.getLoginDTO().getPassword());
    }

    public ProfileDTO getCurrentUser(String email) {
        UserDetails userDetails = userDetailsImplements.loadUserByUsername(email);
        ClienteModel clienteModel = userRepository.findByEmail(email).get().getClienteModel();
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setEmail(userDetails.getUsername());
        profileDTO.setPassword(userDetails.getPassword());
        profileDTO.setAuthority(userDetails.getAuthorities().toString());
        profileDTO.setEnabled(userDetails.isEnabled());
        profileDTO.setName(clienteModel.getNome());
        profileDTO.setPhone(clienteModel.getPhone());
        profileDTO.setCpf(clienteModel.getCpf());
        profileDTO.setGender(clienteModel.getGender());
        return profileDTO;
    }

    public ResponseEntity<List<ParcelamentoModel>> getParcelamentos(String email) {
        ClienteModel clienteModel = userRepository.findByEmail(email).get().getClienteModel();
        return getParcelamentoService.getParcelamentosByClienteCpf(clienteModel.getCpf());
    }

    public ResponseEntity<String> putCurrentUser(String cpf, UpdateProfileDTO updateProfileDTO) {
        ClienteModel clienteModel = clienteRepository.findByCpf(cpf).get();
        clienteModel.setPhone(updateProfileDTO.getPhone());
        clienteModel.setNome(updateProfileDTO.getNome());
        clienteRepository.save(clienteModel);
        return ResponseEntity.ok("perfil alterado");
    }

    public ResponseEntity<String> putPassword(String email, String password) {
        if (password == null || password.isEmpty())
            throw new NegocioException("Erro ao alterar senha");
        User user = userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok("perfil alterado");
    }

    public String jwt(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return token;
    }

    private User setUser(RegisterDTO userReq){
        User user = new User();
        user.setEmail(userReq.getLoginDTO().getEmail());
        user.setPassword(passwordEncoder.encode(userReq.getLoginDTO().getPassword()));
        user.setActive(true);
        user.setRole(RolesUser.CLIENT);
        user.setClienteModel(new ClienteModel());
        user.getClienteModel().setActive(true);
        user.getClienteModel().setNome(userReq.getClienteModel().getNome());
        user.getClienteModel().setPhone(userReq.getClienteModel().getPhone());
        user.getClienteModel().setCpf(userReq.getClienteModel().getCpf());
        user.getClienteModel().setActive(true);
        return user;
    }
}
