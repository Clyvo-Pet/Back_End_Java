package br.com.fiap.ClyvoPet.plan.service;

import br.com.fiap.ClyvoPet.exception.BusinessException;
import br.com.fiap.ClyvoPet.exception.ResourceNotFoundException;
import br.com.fiap.ClyvoPet.plan.dto.PlanRequest;
import br.com.fiap.ClyvoPet.plan.model.Plan;
import br.com.fiap.ClyvoPet.plan.repository.PlanRepository;
import br.com.fiap.ClyvoPet.signature.service.SignatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final SignatureService signatureService;

    @Transactional
    public Plan create(PlanRequest request) {
        if (planRepository.findBySignatureId(request.getSignatureId()).isPresent())
            throw new BusinessException("Esta assinatura já possui um plano vinculado.");
        var signature = signatureService.findById(request.getSignatureId());
        Plan plan = Plan.builder()
                .name(request.getName()).monthlyPrice(request.getMonthlyPrice())
                .consultationsMonth(request.getConsultationsMonth())
                .mktDiscount(request.getMktDiscount()).benefits(request.getBenefits())
                .active(request.getActive()).signature(signature).build();
        return planRepository.save(plan);
    }

    public Plan findById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plano não encontrado: " + id));
    }

    @Transactional
    public Plan update(Long id, PlanRequest request) {
        Plan plan = findById(id);
        plan.setName(request.getName()); plan.setMonthlyPrice(request.getMonthlyPrice());
        plan.setConsultationsMonth(request.getConsultationsMonth());
        plan.setMktDiscount(request.getMktDiscount()); plan.setBenefits(request.getBenefits());
        plan.setActive(request.getActive());
        return planRepository.save(plan);
    }
}
