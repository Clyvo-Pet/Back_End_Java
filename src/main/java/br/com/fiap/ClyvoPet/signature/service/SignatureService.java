package br.com.fiap.ClyvoPet.signature.service;

import br.com.fiap.ClyvoPet.exception.BusinessException;
import br.com.fiap.ClyvoPet.exception.ResourceNotFoundException;
import br.com.fiap.ClyvoPet.signature.dto.SignatureRequest;
import br.com.fiap.ClyvoPet.signature.model.Signature;
import br.com.fiap.ClyvoPet.signature.repository.SignatureRepository;
import br.com.fiap.ClyvoPet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignatureService {

    private final SignatureRepository signatureRepository;
    private final UserService userService;

    @Transactional
    public Signature create(SignatureRequest request) {
        signatureRepository.findByUserIdAndStatus(request.getUserId(), "Ativo")
                .ifPresent(s -> { throw new BusinessException("Usuário já possui uma assinatura ativa."); });
        if (request.getEndDate().isBefore(request.getStartDate()))
            throw new BusinessException("Data de fim deve ser posterior à data de início.");
        var user = userService.findOrThrow(request.getUserId());
        Signature sig = Signature.builder()
                .user(user).status("Ativo")
                .startDate(request.getStartDate()).endDate(request.getEndDate()).build();
        return signatureRepository.save(sig);
    }

    public Signature findById(Long id) {
        return signatureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assinatura não encontrada: " + id));
    }

    public List<Signature> findByUserId(Long userId) {
        return signatureRepository.findByUserId(userId);
    }

    @Transactional
    public Signature cancel(Long id) {
        Signature sig = findById(id);
        if ("Cancelado".equals(sig.getStatus()))
            throw new BusinessException("Assinatura já está cancelada.");
        sig.setStatus("Cancelado");
        return signatureRepository.save(sig);
    }

    public Optional<Signature> findActiveByUserId(Long userId) {
        return signatureRepository.findByUserIdAndStatus(userId, "Ativo");
    }
}
