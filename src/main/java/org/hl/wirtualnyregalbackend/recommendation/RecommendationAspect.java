package org.hl.wirtualnyregalbackend.recommendation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
@Aspect
public class RecommendationAspect {

    private final RecommendationService recommendationService;

    public RecommendationAspect(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @After("@annotation(recommendation)")
    public void collectRecommendationData(JoinPoint joinPoint, Recommendation recommendation) {
        String resourceIdParamName = recommendation.resourceIdParamName();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();
        int resourceIndex = -1;
        for(int i = 0; i < parameters.length; i++) {
            if(parameters[i].getName().equals(resourceIdParamName)) {
                resourceIndex = i;
                break;
            }
        }

        String bookId = (String) joinPoint.getArgs()[resourceIndex];
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        recommendationService.saveBookAndGenreRecommendationAsync(bookId, currentUser);
    }

}
